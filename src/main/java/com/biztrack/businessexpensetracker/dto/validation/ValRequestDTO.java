package com.biztrack.businessexpensetracker.dto.validation;

import com.biztrack.businessexpensetracker.model.RequestDetail;
import com.biztrack.businessexpensetracker.model.Status;
import com.biztrack.businessexpensetracker.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

public class ValRequestDTO {
    private Long id;
    private String purpose;
    private Double amount;
    @JsonProperty("request-detail")
    private List<ValRequestDetailDTO> requestDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ValRequestDetailDTO> getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(List<ValRequestDetailDTO> requestDetails) {
        this.requestDetails = requestDetails;
    }
}
