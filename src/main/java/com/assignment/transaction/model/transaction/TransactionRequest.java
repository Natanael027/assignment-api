package com.assignment.transaction.model.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @NotNull(message = "Paramater service tidak boleh kosong")
    @NotBlank(message = "Paramater service tidak boleh kosong")
    private String service_code;
}
