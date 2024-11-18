package com.assignment.transaction.controller;

import com.assignment.transaction.entity.Banner;
import com.assignment.transaction.entity.Services;
import com.assignment.transaction.model.ResponseFomat;
import com.assignment.transaction.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InformationController {

    @Autowired
    private InformationService informationService;

    @GetMapping("/banner")
    public ResponseEntity<?> getBannersData() throws Exception {
        List<Banner> bannerList = informationService.getBanners();
        ResponseFomat response = new ResponseFomat(0, "Sukses", bannerList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/service")
    public ResponseEntity<?> getServicesData() throws Exception {
        List<Services> bannerList = informationService.getServices();
        ResponseFomat response = new ResponseFomat(0, "Sukses", bannerList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
