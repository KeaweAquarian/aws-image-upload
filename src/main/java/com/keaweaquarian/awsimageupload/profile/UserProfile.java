package com.keaweaquarian.awsimageupload.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {

    private final UUID  userProfile;
    private final String username;
    private String userProfileImage;

    public UserProfile(UUID userProfile, String username, String userProfileImage) {
        this.userProfile = userProfile;
        this.username = username;
        this.userProfileImage = userProfileImage;
    }

    public UUID getUserProfile() {
        return userProfile;
    }

//    public void setUserProfile(UUID userProfile) {
//        this.userProfile = userProfile;
//    }

    public String getUsername() {
        return username;
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public Optional<String> getUserProfileImage() {
        return Optional.ofNullable(userProfileImage);
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(userProfile, that.userProfile)
                && Objects.equals(username, that.username)
                && Objects.equals (userProfileImage, that.userProfileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfile, username, userProfileImage);
    }
}
