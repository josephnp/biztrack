package com.biztrack.business_expense_tracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "AccessMenu")
public class AccessMenu{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RoleID", nullable = false, foreignKey = @ForeignKey(name = "FK_AccessMenus_Role"))
    private Role roleId;

    @Column(name = "Menu")
    private String menu;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private UUID createdBy;

    @Column(name = "ModifiedBy", insertable = false)
    private LocalDateTime createdDate;

    @Column(name = "ModifiedBy", insertable = false)
    private UUID modifiedBy;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", insertable = false)
    private LocalDateTime modifiedDate;
}
