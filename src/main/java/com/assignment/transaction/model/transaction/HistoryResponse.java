package com.assignment.transaction.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryResponse {
    private String invoice_number;
    private String transaction_type;
    private String description;
    private int total_amount;
    private String created_on;
}
