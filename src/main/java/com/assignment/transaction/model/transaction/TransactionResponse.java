package com.assignment.transaction.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private String invoice_number;
    private String service_code;
    private String service_name;
    private String transaction_type;
    private int total_amount;
    private String created_on;
}
