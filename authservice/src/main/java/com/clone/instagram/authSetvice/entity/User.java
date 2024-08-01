package com.clone.instagram.authSetvice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {

    @Id
    private String id;

    @NotBlank
    @Size(max = 15)
    private String username;

    @Email
    @NotBlank
    @Size(max = 40)
    private String email;


    @JsonIgnore
    @Size(max = 100)
    @NotBlank
    private String password;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private boolean active;

    private Profile userProfile;

    private Set<Role> roles ;

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.active = user.active;
        this.userProfile = user.userProfile;
        this.roles = user.roles;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = true;
        this.roles = new HashSet<>() {{ new Role("USER"); }};
    }

}