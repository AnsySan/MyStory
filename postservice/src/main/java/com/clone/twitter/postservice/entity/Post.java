package com.clone.twitter.postservice.entity;

import com.clone.twitter.postservice.entity.ad.Ad;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content", nullable = false, length = 4096)
    private String content;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "project_id")
    private Long projectId;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @ToString.Exclude
    private List<PostLike> likes;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Hashtag> hashtags;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany(mappedBy = "posts")
    private List<Album> albums;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Ad ad;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Resource> resources;

    @Column(name = "published", nullable = false)
    private boolean published;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_verify", length = 64, nullable = false)
    @ColumnDefault("UNCHECKED")
    @Enumerated(EnumType.STRING)
    private VerificationStatus isVerify;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "verified_date")
    private LocalDateTime verifiedDate;

    @Column(name = "is_checked_for_spelling", nullable = false)
    private boolean isCheckedForSpelling;

    @Column(name = "views_count", nullable = false)
    @ColumnDefault("0")
    private long viewsCount;
}