package com.biztrack.business_expense_tracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Reports")
public class Report{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "RequestID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Report_Requests")
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

}
