package com.keaweaquarian.awsimageupload.datastore;

import com.keaweaquarian.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("577d2417-614c-4b0b-9e6c-68123eba161c"), "Ben Keith", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("c7c9ca75-5a7c-45e9-b343-618cdffb23bc"), "Henry David", null));
    }

    public static List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
