package com.moldedbits.argus.helper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shishank on 03/05/17.
 */

public class FaceBookConfig {
    @Getter
    @Setter
    public List<String> faceBookPermissions;

    public FaceBookConfig() {
        setDefaultPermission();
    }

    private void setDefaultPermission() {
        faceBookPermissions = new ArrayList<>();
        faceBookPermissions.add("public_profile");
        faceBookPermissions.add("email");
        faceBookPermissions.add("user_photos");
    }
}
