package com.biztrack.businessexpensetracker.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResReportDTO {
    private Long id;
    private ResStatusDTO status;
    private Double amount;
    private Double refundAmount;
    private String refundReceiptURL;
    @JsonProperty("report-detail")
    private List<ResReportDetailDTO> reportDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResStatusDTO getStatus() {
        return status;
    }

    public void setStatus(ResStatusDTO status) {
        this.status = status;
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

    public List<ResReportDetailDTO> getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(List<ResReportDetailDTO> reportDetails) {
        this.reportDetails = reportDetails;
    }


}
