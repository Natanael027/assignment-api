package com.assignment.transaction.model.membership;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String profileImage;
}
