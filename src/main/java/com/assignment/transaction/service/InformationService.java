package com.assignment.transaction.service;

import com.assignment.transaction.entity.Banner;
import com.assignment.transaction.entity.Services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InformationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Banner> getBanners() throws Exception {
        try {
            String sql = "SELECT * FROM banner;";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Banner.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            log.error("Error executing getBanners ", e);
            throw new Exception("Error executing getBanners, " + e.getMessage());
        }
    }

    public List<Services> getServices() throws Exception {
        try {
            String sql = "SELECT * FROM service;";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Services.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            log.error("Error executing getServices ", e);
            throw new Exception("Error executing getServices, " + e.getMessage());
        }
    }
}
