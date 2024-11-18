package com.assignment.transaction.service;

import com.assignment.transaction.entity.User;
import com.assignment.transaction.model.membership.LoginRequest;
import com.assignment.transaction.model.membership.RegisterRequest;
import com.assignment.transaction.model.membership.UpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class MembershipService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int registerUser(RegisterRequest registerRequest) throws Exception {
        try {
            User user = new User();
            user.convertModel(registerRequest);
            String sql = "INSERT INTO user (email, first_name, last_name, password) " +
                    "VALUES (?, ?, ?, ?)";

            return jdbcTemplate.update(sql, user.getEmail(), user.getFirstName(), user.getLastName(),user.getPassword());
        }catch (Exception e){
            log.error("Error executing registerUser ", e);
            if (e.getMessage().contains("Duplicate entry")){
                throw new Exception("Email sudah terdaftar, harap memasukan email baru.");
            }
            throw new Exception("Database error occurred while registering user.");
        }
    }

    public User getUser(String email) throws Exception {
        try {
            String sql = "SELECT * FROM user WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, new BeanPropertyRowMapper<>(User.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            log.error("Error executing loginUser ", e);
            throw new Exception("Error executing loginUser, " + e.getMessage());
        }
    }

    public Boolean matchCredential(User user, LoginRequest loginRequest){
        if (user == null || loginRequest == null){
            return false;
        }else if (Objects.equals(user.getEmail(), loginRequest.getEmail()) &&
                Objects.equals(user.getPassword(), loginRequest.getPassword())){
            return true;
        }

        return false;
    }

    public int updateUser(UpdateRequest updateRequest, String email) throws Exception {
        try {
            User user = new User();
            user.setFirstName(updateRequest.getFirst_name());
            user.setLastName(updateRequest.getLast_name());

            String sql = "UPDATE user SET first_name = ?, last_name = ? WHERE email = ?";
            return jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), email);
        } catch (Exception e) {
            log.error("Error executing updateUser ", e);
            throw new Exception("Database error occurred while updating user information.");
        }
    }

    public int updateUserImage(String email, String profileImage) throws Exception {
        try {
            String sql = "UPDATE user SET profile_image = ? WHERE email = ?";
            return jdbcTemplate.update(sql, profileImage, email);
        } catch (Exception e) {
            log.error("Error executing updateUserImage ", e);
            throw new Exception("Database error occurred while updating user information.");
        }

    }

}
