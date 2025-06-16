package com.biztrack.businessexpensetracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "RequestID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Report_Request")
    )
    private Request request;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "RefundAmount")
    private Double refundAmount;

    @Column(name = "RefundReceiptUrl")
    private String refundReceiptURL;

    @ManyToOne
    @JoinColumn(name = "StatusID", nullable = false, foreignKey = @ForeignKey(name = "FK_Report_Status"))
    private Status status;

    @Column(name = "Comment")
    private String comment;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private UUID createdBy;

    @CreationTimestamp
    @Column(name = "CreatedDate", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "ModifiedBy", insertable = false)
    private UUID modifiedBy;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", insertable = false)
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "report")
    private List<ReportDetail> reportDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public UUID getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UUID modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<ReportDetail> getReportDetails(){
        return reportDetails;
    }

    public void setReportDetails(List<ReportDetail> reportDetails){
        this.reportDetails = reportDetails;
    }
}
