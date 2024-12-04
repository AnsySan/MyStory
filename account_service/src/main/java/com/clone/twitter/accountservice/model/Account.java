package com.clone.twitter.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number", length = 20, nullable = false)
    private String number;

    @Column(name = "user_id")
    private Long userId; //

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "type", nullable = false, length = 128)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "currency", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "status", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "closed_at")
    private Instant closedAt;

    @Version
    @Column(name = "version", nullable = false)
    private long version;
}
