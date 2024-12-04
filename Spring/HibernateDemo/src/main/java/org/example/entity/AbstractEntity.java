package org.example.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    private Long id;
    private Long createdAt;

    //getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    @Column(nullable = false, updatable = false)
    public Long getCreatedAt() {
        return createdAt;
    }

    //setter
    public void setId(Long id) {
        this.id = id;
    }
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Transient
    public ZonedDateTime getCreatedAtAsZonedDateTime() {
        return Instant.ofEpochMilli(this.createdAt).atZone(ZoneId.systemDefault());
    }

    @PrePersist
    public void preInsert() {
        setCreatedAt(System.currentTimeMillis());
    }
}
