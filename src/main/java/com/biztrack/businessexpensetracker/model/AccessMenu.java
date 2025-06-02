//package com.biztrack.businessexpensetracker.model;
//
//import jakarta.persistence.*;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Table(name = "AccessMenu")
//public class AccessMenu {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "RoleID", nullable = false, foreignKey = @ForeignKey(name = "FK_AccessMenus_Role"))
//    private Role roleId;
//
//    @Column(name = "Menu")
//    private String menu;
//
//    @Column(name = "CreatedBy", nullable = false, updatable = false)
//    private UUID createdBy;
//
//    @Column(name = "CreatedDate", insertable = false)
//    private LocalDateTime createdDate;
//
//    @Column(name = "ModifiedBy", insertable = false)
//    private UUID modifiedBy;
//
//    @UpdateTimestamp
//    @Column(name = "ModifiedDate", insertable = false)
//    private LocalDateTime modifiedDate;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Role getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(Role roleId) {
//        this.roleId = roleId;
//    }
//
//    public String getMenu() {
//        return menu;
//    }
//
//    public void setMenu(String menu) {
//        this.menu = menu;
//    }
//
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
//}
