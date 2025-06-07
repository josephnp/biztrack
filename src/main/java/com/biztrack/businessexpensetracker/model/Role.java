package com.biztrack.businessexpensetracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "RoleMenu", // nama tabel join
            joinColumns = @JoinColumn(name = "RoleID"),  // FK ke Role
            inverseJoinColumns = @JoinColumn(name = "MenuID")  // FK ke Menu
    )
    private Set<Menu> menus = new HashSet<>();
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Description")
    private String description;

    public Role() {

    }

    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

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

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

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
