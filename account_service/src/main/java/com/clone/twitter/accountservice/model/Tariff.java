package com.clone.twitter.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tariff")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tariff_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TariffType tariffType;

    @Column(name = "current_rate")
    private BigDecimal currentRate;

    @Column(name = "rate_history")
    private String rateHistory;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "currentTariff")
    private List<SavingAccount> savingAccount;
}