package com.clone.twitter.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account_numbers_sequence")
public class AccountNumberSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "current_count", nullable = false)
    private Long currentCount;
}