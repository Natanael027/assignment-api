package com.assignment.transaction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "service")
public class Services {
    @Id
    private String service_code;
    private String service_name;
    private String service_icon;
    private Integer service_tarif;
}
