package com.assignment.transaction.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionHistoryResponse {
    private Integer offset;
    private Integer limit;
    private List<HistoryResponse> records;
}
