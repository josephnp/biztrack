package com.biztrack.businessexpensetracker.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResRequestDTO {
    private Long id;
    private ResStatusDTO status;
    private String purpose;
    private Double amount;
    private Boolean isReported;
    private String comment;
    @JsonProperty("request-detail")
    private List<ResRequestDetailDTO> requestDetails;

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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getReported() {
        return isReported;
    }

    public void setReported(Boolean reported) {
        isReported = reported;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ResRequestDetailDTO> getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(List<ResRequestDetailDTO> requestDetails) {
        this.requestDetails = requestDetails;
    }

}
