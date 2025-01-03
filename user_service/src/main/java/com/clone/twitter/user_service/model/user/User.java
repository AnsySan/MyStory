package com.clone.twitter.user_service.model.user;

import com.clone.twitter.user_service.model.MentorshipRequest;
import com.clone.twitter.user_service.model.goal.Goal;
import com.clone.twitter.user_service.model.goal.GoalInvitation;
import com.clone.twitter.user_service.model.jira.JiraAccount;
import com.clone.twitter.user_service.model.skill.Skill;
import com.clone.twitter.user_service.model.contact.Contact;
import com.clone.twitter.user_service.model.contact.ContactPreference;
import com.clone.twitter.user_service.model.country.Country;
import com.clone.twitter.user_service.model.event.Event;
import com.clone.twitter.user_service.model.event.Rating;
import com.clone.twitter.user_service.model.premium.Premium;
import com.clone.twitter.user_service.model.recomendation.Recommendation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", length = 64, nullable = false, unique = true)
    private String username;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    private String email;

    @Column(name = "phone", length = 32, unique = true)
    private String phone;

    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "about_me", length = 4096)
    private String aboutMe;

    @OneToOne(mappedBy = "user")
    private JiraAccount jiraAccount;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "city", length = 64)
    private String city;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "subscription",
            joinColumns = @JoinColumn(name = "followee_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> followees;

    @OneToMany(mappedBy = "owner")
    private List<Event> ownedEvents;

    @ManyToMany(mappedBy = "mentors")
    private List<User> mentees;

    @ManyToMany
    @JoinTable(name = "mentorship",
            joinColumns = @JoinColumn(name = "mentee_id"),
            inverseJoinColumns = @JoinColumn(name = "mentor_id"))
    private List<User> mentors;

    @OneToMany(mappedBy = "receiver")
    private List<MentorshipRequest> receivedMentorshipRequests;

    @OneToMany(mappedBy = "requester")
    private List<MentorshipRequest> sentMentorshipRequests;

    @OneToMany(mappedBy = "inviter")
    private List<GoalInvitation> sentGoalInvitations;

    @OneToMany(mappedBy = "invited")
    private List<GoalInvitation> receivedGoalInvitations;

    @OneToMany(mappedBy = "mentor")
    private List<Goal> setGoals;

    @ManyToMany(mappedBy = "users")
    private List<Goal> goals;

    @ManyToMany(mappedBy = "users")
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> participatedEvents;

    @OneToMany(mappedBy = "author")
    private List<Recommendation> recommendationsGiven;

    @OneToMany(mappedBy = "receiver")
    private List<Recommendation> recommendationsReceived;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fileId", column = @Column(name = "profile_pic_file_id")),
            @AttributeOverride(name = "smallFileId", column = @Column(name = "profile_pic_small_file_id"))
    })
    private UserProfilePicture userProfilePicture;

    @OneToOne(mappedBy = "user")
    private ContactPreference contactPreference;

    @OneToOne(mappedBy = "user")
    private Premium premium;
}
