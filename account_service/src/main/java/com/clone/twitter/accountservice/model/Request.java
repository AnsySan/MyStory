package com.clone.twitter.accountservice.model;

import com.clone.twitter.accountservice.util.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "request")
public class Request {
    @Id
    private Long id;

    @Column(name = "idempotent_token")
    private UUID idempotentToken;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @Column(name = "lock_value", nullable = false)
    private String lockValue;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "input_data")
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> inputData;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Column(name = "status_details")
    private String details;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "version", nullable = false)
    private Long version;
}