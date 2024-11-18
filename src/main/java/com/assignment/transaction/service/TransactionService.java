package com.assignment.transaction.service;

import com.assignment.transaction.entity.Services;
import com.assignment.transaction.entity.Transaction;
import com.assignment.transaction.entity.TransactionType;
import com.assignment.transaction.entity.User;
import com.assignment.transaction.model.transaction.HistoryResponse;
import com.assignment.transaction.model.transaction.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer getUserBalance(String email) throws Exception {
        try {
            String sql = "SELECT balance FROM user WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        }catch (EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            log.error("Error executing getUserBalance ", e);
            throw new Exception("Error executing getUserBalance, " + e.getMessage());
        }
    }

    public Integer topUpUserBalance(String email, Integer topUpAmount) throws Exception {
        try {
            User user = membershipService.getUser(email);
            Integer userBalance = user.getBalance();
            Integer newBalance = userBalance + topUpAmount;

            updateUserBalance(email, newBalance);
            Transaction transaction = new Transaction();
            transaction.setInvoice_number(invoiceRandom());
            transaction.setTransaction_type(TransactionType.TOPUP);
            transaction.setDescription("Top Up balance");
            transaction.setTotal_amount(topUpAmount);
            transaction.setCreated_on(LocalDateTime.now());
            transaction.setUser(user);
            insertTransaction(transaction);

            return newBalance;
        }catch (Exception e){
            log.error("Error executing getUserBalance ", e);
            throw new Exception("Error executing getUserBalance, " + e.getMessage());
        }
    }

    public TransactionResponse userTransaction(String email, String serviceCode) throws Exception {
        try {
            User user = membershipService.getUser(email);
            Integer userBalance = user.getBalance();
            Services service = findByServiceCode(serviceCode);
            if (service == null) {
                throw new Exception("Service atau Layanan tidak ditemukan");
            }
            Integer transactionAmount = service.getService_tarif();
            if (userBalance < transactionAmount) {
                throw new Exception("User balance tidak cukup untuk melakukan transaksi");
            }
            userBalance = userBalance - transactionAmount;
            updateUserBalance(email, userBalance);

            Transaction transaction = new Transaction();
            transaction.setInvoice_number(invoiceRandom());
            transaction.setTransaction_type(TransactionType.PAYMENT);
            transaction.setDescription(service.getService_name());
            transaction.setTotal_amount(service.getService_tarif());
            transaction.setCreated_on(LocalDateTime.now());
            transaction.setUser(user);
            insertTransaction(transaction);

            return new TransactionResponse(transaction.getInvoice_number(), serviceCode, service.getService_name(),
                    transaction.getTransaction_type().name(), transactionAmount, String.valueOf(transaction.getCreated_on()));
        }catch (Exception e){
            log.error("Error executing getUserBalance ", e);
            throw new Exception("Error executing getUserBalance, " + e.getMessage());
        }
    }

    public List<HistoryResponse> getTransactionHistory(String email, int offset, int limit) throws Exception {
        User user = membershipService.getUser(email);
        String sql = "SELECT * FROM transaction WHERE user_id = ? ORDER BY created_on DESC LIMIT ? OFFSET ?";
        List<Transaction> transactionList = jdbcTemplate.query(sql, new Object[]{user.getId(), limit, offset},new BeanPropertyRowMapper<>(Transaction.class));
// Convert Transaction list to HistoryResponse list using streams
        List<HistoryResponse> historyList = transactionList.stream()
                .map(transaction -> new HistoryResponse(
                        transaction.getInvoice_number(),
                        transaction.getTransaction_type().name(),  // Convert Enum to String
                        transaction.getDescription(),
                        transaction.getTotal_amount(),
                        String.valueOf(transaction.getCreated_on())
                ))
                .collect(Collectors.toList());

        return historyList;
    }

    public Services findByServiceCode(String serviceCode) {
        String sql = "SELECT * FROM service WHERE service_code = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{serviceCode}, new BeanPropertyRowMapper<>(Services.class));
    }

    public void updateUserBalance(String email, Integer newBalance) {
        String sql = "UPDATE user SET balance = ? WHERE email = ?";
        jdbcTemplate.update(sql, newBalance, email);
    }

    private String invoiceRandom(){
        int randomNumber = (int) (Math.random() * 900000) + 100000;
        return "INV" + randomNumber;
    }

    public int insertTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (invoice_number, transaction_type, description, total_amount, created_on, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, transaction.getInvoice_number(), transaction.getTransaction_type().name(),
                transaction.getDescription(), transaction.getTotal_amount(),transaction.getCreated_on(), transaction.getUser().getId());
    }

}
