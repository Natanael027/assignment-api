package com.assignment.transaction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Banner {
    @Id
    private String banner_name;
    private String banner_image;
    private String description;
}
