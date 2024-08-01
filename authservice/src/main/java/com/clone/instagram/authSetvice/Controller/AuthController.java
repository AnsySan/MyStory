package com.clone.instagram.authSetvice.Controller;

import com.clone.instagram.authSetvice.Exception.BadRequestException;
import com.clone.instagram.authSetvice.Exception.EmailAlreadyExistsException;
import com.clone.instagram.authSetvice.Exception.UsernameAlreadyExistsException;
import com.clone.instagram.authSetvice.Payload.Response.UserResponse;
import com.clone.instagram.authSetvice.Repository.UserRepository;
import com.clone.instagram.authSetvice.Payload.Response.ApiResponse;
import com.clone.instagram.authSetvice.Payload.Response.JwtResponse;
import com.clone.instagram.authSetvice.Payload.Request.LoginRequest;
import com.clone.instagram.authSetvice.Payload.Request.SignUpRequest;
import com.clone.instagram.authSetvice.Service.UserService;
import com.clone.instagram.authSetvice.entity.InstaUserDetails;
import com.clone.instagram.authSetvice.entity.User;
import com.clone.instagram.authSetvice.util.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/sign")
    public ResponseEntity<?> sign(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("creating user {}", signUpRequest.getUsername());

        User user = User
                .builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .build();

        try {
            userService.register(user);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            throw new BadRequestException(e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

    @PutMapping("/users/me/picture")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateProfilePicture(
            @RequestBody String profilePicture,
            @AuthenticationPrincipal InstaUserDetails userDetails) {

        userService.updateProfilePicture(profilePicture, userDetails.getId());

        return ResponseEntity.ok()
                .body(new ApiResponse(true,"Profile picture updated successfully"));
    }

    @GetMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUser(@PathVariable("username") String username) {
        log.info("retrieving user {}", username);

        return  userService
                .findByUsername(username)
                .map(user -> ResponseEntity.ok(user))
                .orElseThrow(() -> new ResourceNotFoundException(username));
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        log.info("retrieving all users");

        return ResponseEntity
                .ok(userService.findAll());
    }

    @GetMapping(value = "/users/summary/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserSummary(@PathVariable("username") String username) {
        log.info("retrieving user {}", username);

        return  userService
                .findByUsername(username)
                .map(user -> ResponseEntity.ok(convertTo(user)))
                .orElseThrow(() -> new ResourceNotFoundException(username));
    }@PostMapping(value = "/users/summary/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserSummaries(@RequestBody List<String> usernames) {
        log.info("retrieving summaries for {} usernames", usernames.size());

        List<UserResponse> summaries =
                userService
                        .findByUsernameIn(usernames)
                        .stream()
                        .map(user -> convertTo(user))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(summaries);

    }

    private UserResponse convertTo(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .profilePicture(user.getUserProfile().getProfilePictureUrl())
                .build();
    }
}