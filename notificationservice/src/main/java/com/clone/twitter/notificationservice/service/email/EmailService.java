package com.clone.twitter.notificationservice.service.email;

import com.clone.twitter.notificationservice.dto.UserDto;
import com.clone.twitter.notificationservice.enums.PreferredContact;
import com.clone.twitter.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService implements NotificationService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;

    @Override
    public PreferredContact getPreferredContact() {
        return PreferredContact.EMAIL;
    }

    @Override
    public void send(UserDto user, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Notification");
        mailMessage.setText(message);

        try {
            javaMailSender.send(mailMessage);
            log.info("Message sent successfully to {}", user.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("Error during email sending");
        }
    }
}