package com.biztrack.businessexpensetracker.dto.validation;

import com.biztrack.businessexpensetracker.dto.rel.RelDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ValReportDTO {
    private Long id;
    private RelDTO request;
    private Double amount;
    @JsonProperty("refund-amount")
    private Double refundAmount;
    @JsonProperty("refund-receipt-url")
    private String refundReceiptURL;
    @JsonProperty("report-detail")
    private List<ValReportDetailDTO> reportDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelDTO getRequest() {
        return request;
    }

    public void setRequest(RelDTO request) {
        this.request = request;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReceiptURL() {
        return refundReceiptURL;
    }

    public void setRefundReceiptURL(String refundReceiptURL) {
        this.refundReceiptURL = refundReceiptURL;
    }

    public List<ValReportDetailDTO> getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(List<ValReportDetailDTO> reportDetails) {
        this.reportDetails = reportDetails;
    }
}
