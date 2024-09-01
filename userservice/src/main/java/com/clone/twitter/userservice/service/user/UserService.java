package com.clone.twitter.userservice.service.user;

import com.clone.twitter.userservice.config.cloud.S3Config;
import com.clone.twitter.userservice.context.UserContext;
import com.clone.twitter.userservice.dto.profile.ProfileViewEventDto;
import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.model.user.UserProfilePicture;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.exception.MessageError;
import com.clone.twitter.userservice.exception.UserNotFoundException;
import com.clone.twitter.userservice.mapper.user.UserMapper;
import com.clone.twitter.userservice.publisher.ProfileViewEventPublisher;
import com.clone.twitter.userservice.repository.UserRepository;
import com.clone.twitter.userservice.service.cloud.S3Service;
import com.clone.twitter.userservice.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserContext userContext;
    private final ProfileViewEventPublisher profileViewEventPublisher;

    @Value("${dicebear.pic-base-url}")
    private String large_avatar;

    @Value("${dicebear.pic-base-url-small}")
    private String small_avatar;

    private final S3Service s3Service;
    private final S3Config s3Config;
    private final String bucketName;
    private final UserValidator userValidator;


    public UserDto getUser(Long userId) {
        User user = getUserEntityById(userId);
        sendProfileViewEventToPublisher(userId);
        return userMapper.toDto(user);
    }

    public User getUserEntityById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(MessageError.USER_NOT_FOUND_EXCEPTION));
    }

    public List<User> getUsersEntityByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public UserDto create(UserDto userDto) {

        checkUserAlreadyExists(userDto);

        User user = userMapper.toEntity(userDto);
        user.setUserProfilePic(getRandomAvatar());
        user.setActive(true);

        User createdUser = userRepository.save(user);
        String fileNameSmallAva = "small_" + user.getId() + ".svg";
        String fileNameLargeAva = "large_" + user.getId() + ".svg";

        s3Service.
                saveSvgToS3(user.getUserProfilePic().getSmallFileId(),
                        bucketName,
                        fileNameSmallAva);
        s3Service.
                saveSvgToS3(user.getUserProfilePic().getFileId(),
                        bucketName,
                        fileNameLargeAva);
        return userMapper.toDto(createdUser);

    }

    public List<UserDto> getUsersByIds(List<Long> ids) {
        return userMapper.toDto(userRepository.findAllById(ids));
    }

    @Transactional
    public void deactivate(long userId) {
        User user = getUserEntityById(userId);
        user.setActive(false);
    }


    private UserProfilePicture getRandomAvatar() {

        UUID seed = UUID.randomUUID();
        return UserProfilePicture.builder().
                smallFileId(small_avatar + seed).
                fileId(large_avatar + seed).build();

    }

    private void checkUserAlreadyExists(UserDto userDto) {

        boolean exists = userRepository.findById(userDto.getId()).isPresent();

        if (exists) {
            log.debug("User with id " + userDto.getId() + " exists");
            throw new DataValidationException("User with id " + userDto.getId() + " exists");
        }
    }

    private void sendProfileViewEventToPublisher(long userId) {
        ProfileViewEventDto event = ProfileViewEventDto.builder()
                .observerId(userContext.getUserId())
                .observedId(userId)
                .viewedAt(LocalDateTime.now())
                .build();
        profileViewEventPublisher.publish(event);
        log.info("Successfully sent data to analytics-service");
    }
}