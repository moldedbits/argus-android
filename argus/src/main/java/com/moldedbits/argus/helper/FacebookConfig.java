package com.moldedbits.argus.helper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shishank on 03/05/17.
 */

public class FacebookConfig {

    public static String PUBLIC_PROFILE = "public_profile";
    public static String EMAIL = "email";
    public static String USER_PHOTOS = "user_photos";
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
