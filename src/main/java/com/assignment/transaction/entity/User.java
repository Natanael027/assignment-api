package com.assignment.transaction.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Min(value = 8)
    private String password;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "balance", columnDefinition = "int default 0")
    private Integer balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;


    public User convertModel(Object source) {
        if (source == null) {
            return this;
        }

        Field[] sourceFields = source.getClass().getDeclaredFields();
        for (Field sourceField : sourceFields) {
            try {
                sourceField.setAccessible(true);
                String sourceFieldName = sourceField.getName();
                String targetFieldName = sourceFieldName;

                if ("first_name".equals(sourceFieldName)) {
                    targetFieldName = "firstName";
                } else if ("last_name".equals(sourceFieldName)) {
                    targetFieldName = "lastName";
                }
                try {
                    Field targetField = this.getClass().getDeclaredField(targetFieldName);
                    targetField.setAccessible(true);

                    if (targetField.getType().isAssignableFrom(sourceField.getType())) {
                        targetField.set(this, sourceField.get(source));
                    }
                } catch (NoSuchFieldException e) {
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
