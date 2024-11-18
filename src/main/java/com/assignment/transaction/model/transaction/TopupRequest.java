package com.assignment.transaction.model.transaction;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopupRequest {

    @NotNull(message = "Parameter top_up_amount tidak boleh kosong")
    @Min(value = 1, message = "Parameter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")
    private int top_up_amount;
}
