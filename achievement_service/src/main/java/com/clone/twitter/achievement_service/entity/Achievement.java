package com.clone.twitter.achievement_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="achievement")
public class Achievement {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, unique = true, length = 128)
    private String title;

    @Column(name = "description", nullable = false, unique = true, length = 1024)
    private String description;

    @Column(name = "rarity", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Rarity rarity;

    @OneToMany(mappedBy = "achievement")
    private List<UserAchievement> userAchievements;

    @OneToMany(mappedBy = "achievement")
    private List<AchievementProgress> progresses;

    @Column(name = "points", nullable = false)
    private long points;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}