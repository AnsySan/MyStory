package com.clone.twitter.userservice.service.avatar;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.clone.twitter.userservice.dto.avatar.UserProfilePictureDto;
import com.clone.twitter.userservice.dto.user.UserDto;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.model.user.UserProfilePicture;
import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.mapper.avatar.PictureMapper;
import com.clone.twitter.userservice.repository.UserRepository;
import com.clone.twitter.userservice.service.cloud.S3Service;
import com.clone.twitter.userservice.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfilePictureService {
    @Value("${randomAvatar.url}")
    private String url;
    @Value("${aws.smallSize}")
    private int smallSize;
    @Value("${aws.largeSize}")
    private int largeSize;
    @Value("${aws.bucketName}")
    private String bucketName;
    private final S3Service s3Service;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PictureMapper pictureMapper;

    private InputStream compressPic(InputStream inputStream, int size) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BufferedImage scaledImage = Thumbnails.of(inputStream).size(size, size).asBufferedImage();
            ImageIO.write(scaledImage, "jpg", outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = outputStream.toByteArray();

        return new ByteArrayInputStream(bytes);
    }

    @Transactional
    @Retryable(retryFor = {RestClientException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 3))
    public void generateAndSetPic(UserDto user) {
        byte[] image = restTemplate.getForObject(url + user.getUsername(), byte[].class);
        if (image == null || image.length == 0) {
            throw new DataValidationException("Failed to get the generated image");
        }
        String nameForSmallPic = "small" + user.getUsername() + LocalDateTime.now() + ".jpg";
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");

        InputStream inputStream = new ByteArrayInputStream(image);
        s3Service.uploadFile(bucketName, nameForSmallPic, compressPic(inputStream, smallSize));
        UserProfilePicture userProfilePic = new UserProfilePicture();
        userProfilePic.setSmallFileId(nameForSmallPic);
        user.setUserProfilePicture(userProfilePic);
    }

    @Transactional
    public UserProfilePictureDto saveProfilePic(long userId, MultipartFile file){
        User user = userService.getUserEntityById(userId);
        String nameForSmallPic = "small" + file.getName() + LocalDateTime.now();
        String nameForLargePic = "large" + file.getName() + LocalDateTime.now();
        try {
            s3Service.uploadFile(bucketName, nameForSmallPic, compressPic(file.getInputStream(), smallSize));
            s3Service.uploadFile(bucketName, nameForLargePic, compressPic(file.getInputStream(), largeSize));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserProfilePicture userProfilePic = new UserProfilePicture(nameForLargePic, nameForSmallPic);
        user.setUserProfilePic(userProfilePic);
        userRepository.save(user);

        return pictureMapper.toDto(userProfilePic);
    }

    @Transactional
    public InputStreamResource getProfilePic(long userId) {
        User user = userService.getUserEntityById(userId);
        S3Object s3Object = s3Service.getFile(bucketName, user.getUserProfilePic().getFileId());

        return new InputStreamResource(s3Object.getObjectContent());
    }

    @Transactional
    public UserProfilePictureDto deleteProfilePic(long userId) {
        User user = userService.getUserEntityById(userId);
        s3Service.deleteFile(bucketName, user.getUserProfilePic().getFileId());
        s3Service.deleteFile(bucketName, user.getUserProfilePic().getSmallFileId());
        UserProfilePictureDto deletedProfilePicDto = pictureMapper.toDto(user.getUserProfilePic());
        user.getUserProfilePic().setSmallFileId(null);
        user.getUserProfilePic().setFileId(null);
        userRepository.save(user);

        return deletedProfilePicDto;
    }
}