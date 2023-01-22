package com.keaweaquarian.awsimageupload.profile;

import com.keaweaquarian.awsimageupload.bucket.BucketName;
import com.keaweaquarian.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {


    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;

    }

    byte[] downloadUserProfileImage(UUID userProfileID) {
        UserProfile user = userProfileDataAccessService
                .getUserProfile()
                .stream()
                .filter(userProfile -> userProfile.getUserProfile().equals(userProfileID))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileID)));
        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getUserProfile());
      return user.getUserProfileImage()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);

    }
    List<UserProfile> getUserProfiles(){

        return userProfileDataAccessService.getUserProfile();
    }


    void uploadUserProfileImage(UUID userProfileID, MultipartFile file) {

        if(!file.isEmpty()){
            if (!Arrays.asList(ContentType.IMAGE_JPEG, ContentType.IMAGE_GIF, ContentType.IMAGE_PNG).contains(file.getContentType())){
                UserProfile user = userProfileDataAccessService
                        .getUserProfile()
                        .stream()
                        .filter(userProfile -> userProfile.getUserProfile().equals(userProfileID))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileID)));
                Map<String, String> metaData = new HashMap<>();
                metaData.put("Content-Type", file.getContentType());
                metaData.put("Content-Length", String.valueOf(file.getSize()));

               String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfile());
               String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
                try {
                    fileStore.save(path, fileName, Optional.of(metaData), file.getInputStream() );

                    user.setUserProfileImage(fileName);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }

            }else throw new IllegalStateException("Image must be of type jpeg, png or gif!");
        }else throw new IllegalStateException("File is empty!");

    }


}
