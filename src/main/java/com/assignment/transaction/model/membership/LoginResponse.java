package com.assignment.transaction.model.membership;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginResponse {
    @NotNull(message = "Paramater email tidak boleh kosong")
    @Email(message = "Paramater email tidak sesuai format")
    private String email;

    @NotNull(message = "Paramater password tidak boleh kosong")
    @Length(min = 8, message = "Paramater password tidak boleh kurang dari 8 karakter")
    private String password;
}
