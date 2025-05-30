package com.biztrack.businessexpensetracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles = new HashSet<>();

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description")
    private String description;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
