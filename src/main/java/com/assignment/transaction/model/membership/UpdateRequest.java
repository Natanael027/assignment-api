package com.assignment.transaction.model.membership;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRequest {
    @NotNull(message = "Paramater first name tidak boleh kosong")
    private String first_name;

    @NotNull(message = "Paramater last name tidak boleh kosong")
    private String last_name;
}
