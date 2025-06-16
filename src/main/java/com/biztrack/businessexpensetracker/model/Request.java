package com.biztrack.businessexpensetracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "UserID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Requests_User")
    )
    private User user;

    @Column(name = "Purpose", length = 100, nullable = false)
    private String purpose;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "IsReported", columnDefinition = "BIT DEFAULT 0")
    private Boolean isReported;

    @Column(name = "Description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "StatusID", nullable = false, foreignKey = @ForeignKey(name = "FK_Request_Status"))
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

    @OneToMany(mappedBy = "request")
    private List<RequestDetail> requestDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<RequestDetail> getRequestDetails(){
        return requestDetails;
    }

    public void setRequestDetails(List<RequestDetail> requestDetails){
        this.requestDetails = requestDetails;
    }
}
