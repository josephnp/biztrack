package com.biztrack.businessexpensetracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
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

//    @Column(name = "CreatedBy", nullable = false, updatable = false)
//    private UUID createdBy;
//
//    @CreationTimestamp
//    @Column(name = "CreatedDate", nullable = false, updatable = false)
//    private LocalDateTime createdDate;
//
//    @Column(name = "ModifiedBy", insertable = false)
//    private UUID modifiedBy;
//
//    @UpdateTimestamp
//    @Column(name = "ModifiedDate", insertable = false)
//    private LocalDateTime modifiedDate;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive = true;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

//    public UUID getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(UUID createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public LocalDateTime getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(LocalDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public UUID getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public void setModifiedBy(UUID modifiedBy) {
//        this.modifiedBy = modifiedBy;
//    }
//
//    public LocalDateTime getModifiedDate() {
//        return modifiedDate;
//    }
//
//    public void setModifiedDate(LocalDateTime modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


}
