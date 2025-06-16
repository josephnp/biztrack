package com.biztrack.businessexpensetracker.dto.validation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValReportDetailDTO {
    private Long id;
    private Double amount;
    @JsonProperty("receipt-url")
    private String receiptURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReceiptURL() {
        return receiptURL;
    }

    public void setReceiptURL(String receiptURL) {
        this.receiptURL = receiptURL;
    }
}
