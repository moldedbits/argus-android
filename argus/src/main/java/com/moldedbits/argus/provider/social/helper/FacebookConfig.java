package com.moldedbits.argus.provider.social.helper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/*
This class will set the permission need by user to login with facebook
 */
public class FacebookConfig {

    public static final String PUBLIC_PROFILE = "public_profile";
    public static final String EMAIL = "email";
    public static final String USER_PHOTOS = "user_photos";
    public static final String USER_FRIENDS = "user_friends";
    public static final String USER_ABOUT_ME = "user_about_me";
    public static final String USER_BIRTHDAY = "user_birthday";
    public static final String USER_LOCATION = "user_location";
    public static final String USER_VIDEOS = "user_videos";

    @Getter
    @Setter
    public List<String> faceBookPermissions;

    public FacebookConfig() {
        setDefaultPermission();
    }

    private void setDefaultPermission() {
        faceBookPermissions = new ArrayList<>();
        faceBookPermissions.add(PUBLIC_PROFILE);
    }
}
