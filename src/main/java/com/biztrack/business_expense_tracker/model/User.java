package com.biztrack.business_expense_tracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "ID")
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "RoleID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Users_Role")
    )
    private Role role;

    @Column(name = "EmployeeNumber", length = 40, nullable = false, unique = true)
    private String employeeNumber;

    @Column(name = "FullName", length = 100, nullable = false)
    private String fullName;

    @Column(name = "Email", length = 100, nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(
            name = "DepartmentID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Users_Department")
    )
    private Department department;

    @Column(name = "Password", length = 255, nullable = false)
    private String password;

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

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive = false;

}
