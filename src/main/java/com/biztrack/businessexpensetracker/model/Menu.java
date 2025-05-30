package com.biztrack.businessexpensetracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Menu")
public class Menu{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private UUID createdBy;

    @Column(name = "CreatedDate", insertable = false)
    private LocalDateTime createdDate;

    @Column(name = "ModifiedBy", insertable = false)
    private UUID modifiedBy;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", insertable = false)
    private LocalDateTime modifiedDate;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public UUID getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy){
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate(){
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate){
        this.createdDate = createdDate;
    }

    public UUID getModifiedBy(){
        return modifiedBy;
    }

    public void setModifiedBy(UUID modifiedBy){
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate(){
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate){
        this.modifiedDate = modifiedDate;
    }
}
