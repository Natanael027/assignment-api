package com.assignment.transaction.controller;

import com.assignment.transaction.exception.UserException;
import com.assignment.transaction.model.transaction.*;
import com.assignment.transaction.model.ResponseFomat;
import com.assignment.transaction.service.JwtService;
import com.assignment.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TransactionController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance")
    public ResponseEntity<?> getUserBalance(@RequestHeader("Authorization") String authHeader) throws Exception {
        String userEmail = getEmail(authHeader);
        Integer userBalance  = transactionService.getUserBalance(userEmail);

        ResponseFomat response = new ResponseFomat(0, "Sukses", new BalanceResponse(userBalance));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/topup")
    public ResponseEntity<?> userTopup(@Valid @RequestBody TopupRequest topupRequest, BindingResult bindingResult,
                                       @RequestHeader("Authorization") String authHeader) throws Exception {
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            throw new UserException("Error topup user", errorMessages);
        }
        String userEmail = getEmail(authHeader);
        Integer newBalance = transactionService.topUpUserBalance(userEmail, topupRequest.getTop_up_amount());

        ResponseFomat response = new ResponseFomat(0, "Top Up Balance berhasil", new BalanceResponse(newBalance));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/transaction")
    public ResponseEntity<?> userTransaction(@Valid @RequestBody TransactionRequest transactionRequest, BindingResult bindingResult,
                                       @RequestHeader("Authorization") String authHeader) throws Exception {
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            throw new UserException("Error userTransaction", errorMessages);
        }
        String userEmail = getEmail(authHeader);
        TransactionResponse transactionResponse = transactionService.userTransaction(userEmail, transactionRequest.getService_code());

        ResponseFomat response = new ResponseFomat(0, "Transaksi berhasil", transactionResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/transaction/history")
    public ResponseEntity<?> getUserTransactionHistory(@RequestHeader("Authorization") String authHeader, @RequestParam("offset") Integer offset,
                                                       @RequestParam("limit") Integer limit) throws Exception {
        String userEmail = getEmail(authHeader);
        List<HistoryResponse> transactionHistory = transactionService.getTransactionHistory(userEmail, offset, limit);
        TransactionHistoryResponse responseForm = new TransactionHistoryResponse(offset, limit, transactionHistory);

        ResponseFomat response = new ResponseFomat(0, "Sukses", responseForm);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String getEmail(String authHeader){
        String token = authHeader.substring(7);
        return jwtService.getUsernameFromToken(token);
    }

}
