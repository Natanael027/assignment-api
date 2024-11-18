package com.assignment.transaction.model.membership;

import com.assignment.transaction.validation.ValidImageType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterRequest {

    @NotNull(message = "Paramater email tidak boleh kosong")
    @Email(message = "Paramater email tidak sesuai format")
    private String email;

    @NotNull(message = "Paramater first name tidak boleh kosong")
    private String first_name;

    @NotNull(message = "Paramater last name tidak boleh kosong")
    private String last_name;

    @NotNull(message = "Paramater password tidak boleh kosong")
    @Length(min = 8, message = "Paramater password tidak boleh kurang dari 8 karakter")
    private String password;

    @ValidImageType
    private byte[] profile_image;
}
