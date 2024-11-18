package com.assignment.transaction.controller;

import com.assignment.transaction.entity.User;
import com.assignment.transaction.exception.UserException;
import com.assignment.transaction.model.*;
import com.assignment.transaction.model.membership.LoginRequest;
import com.assignment.transaction.model.membership.ProfileResponse;
import com.assignment.transaction.model.membership.RegisterRequest;
import com.assignment.transaction.model.membership.UpdateRequest;
import com.assignment.transaction.service.MembershipService;
import com.assignment.transaction.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/registration")
    private ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            throw new UserException("Error registering user", errorMessages);
        }
        int status = membershipService.registerUser(registerRequest);
        if (status > 0) {
            ResponseFomat responseFomat = new ResponseFomat(0, "Registrasi berhasil silahkan login", null);
            return new ResponseEntity<>(responseFomat, HttpStatus.OK);
        }else {
            ResponseFomat responseFomat = new ResponseFomat(102, "Database error gagal mendaftarkan user.", null);
            return new ResponseEntity<>(responseFomat, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            throw new UserException("Error registering user", errorMessages);
        }
        User user = membershipService.getUser(loginRequest.getEmail());
        Boolean matches = membershipService.matchCredential(user, loginRequest);
        if (matches){
            String jwt = jwtService.generateToken(user.getUsername());
            ResponseFomat response = new ResponseFomat(0, "Login sukses", new TokenResponse(jwt));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            ResponseFomat responseFomat = new ResponseFomat(103, "Username atau password salah", null);
            return new ResponseEntity<>(responseFomat, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) throws Exception {
        String userEmail = getEmail(authHeader);
        User user = membershipService.getUser(userEmail);
        String imageLink = (user.getProfileImage() != null) ? user.getProfileImage() : "";

        ProfileResponse response = new ProfileResponse(user.getEmail(), user.getFirstName(), user.getLastName(), imageLink);
        ResponseFomat responseFomat = new ResponseFomat(0, "Sukses", response);
        return new ResponseEntity<>(responseFomat, HttpStatus.OK);
    }


    @PutMapping("/profile/update")
    private ResponseEntity<?> updateUserProfile(@Valid @RequestBody UpdateRequest updateRequest, BindingResult bindingResult,
                                                @RequestHeader("Authorization") String authHeader) throws Exception {
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            throw new UserException("Error registering user", errorMessages);
        }
        String userEmail = getEmail(authHeader);
        int rowsUpdated  = membershipService.updateUser(updateRequest, userEmail);
        if (rowsUpdated > 0) {
            User user = membershipService.getUser(userEmail);
            String imageLink = (user.getProfileImage() != null) ? user.getProfileImage() : "";

            ProfileResponse response = new ProfileResponse(user.getEmail(), user.getFirstName(), user.getLastName(), imageLink);
            ResponseFomat responseFomat = new ResponseFomat(0, "Update profile berhasil", response);
            return new ResponseEntity<>(responseFomat, HttpStatus.OK);
        }else {
            ResponseFomat responseFomat = new ResponseFomat(102, "Update profile gagal", null);
            return new ResponseEntity<>(responseFomat, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/profile/image")
    private ResponseEntity<?> updateUserImage(@RequestBody MultipartFile file, @RequestHeader("Authorization") String authHeader,
                                              HttpServletRequest request) throws Exception {
        String contentType = file.getContentType();
        if (contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"))){
            String userEmail = getEmail(authHeader);
            String fileName = file.getOriginalFilename();
            Path path = Paths.get("images" + "/" + fileName);
            Files.createDirectories(path.getParent());  // Create directories if they don't exist
            file.transferTo(path);

            String protocol = request.isSecure() ? "https://" : "http://";
            String host = request.getServerName();
            int port = request.getServerPort();
            String imageUrl = protocol + host + ":" + port + "/images/" + fileName;

            membershipService.updateUserImage(userEmail, imageUrl);
            User user = membershipService.getUser(userEmail);
            String imageLink = (user.getProfileImage() != null) ? user.getProfileImage() : "";

            ProfileResponse response = new ProfileResponse(user.getEmail(), user.getFirstName(), user.getLastName(), imageLink);
            ResponseFomat responseFomat = new ResponseFomat(0, "Update Profile Image berhasil", response);
            return new ResponseEntity<>(responseFomat, HttpStatus.OK);
        }else {
            ResponseFomat responseFomat = new ResponseFomat(102, "Format Image tidak sesuai", null);
            return new ResponseEntity<>(responseFomat, HttpStatus.BAD_REQUEST);
        }
    }


    private String getEmail(String authHeader){
        String token = authHeader.substring(7);
        return jwtService.getUsernameFromToken(token);
    }
}
