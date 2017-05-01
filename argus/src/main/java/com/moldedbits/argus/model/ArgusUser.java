package com.moldedbits.argus.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class for Argus User. You can either extend this, or map it to your user class.
 */

public class ArgusUser {

    /**
     * Username
     */
    @Getter @Setter String username;
    /**
     * email
     */
    @Getter @Setter String email;
    /**
     * password
     */
    @Getter @Setter String password;

    public ArgusUser(String username) {
        this.username = username;
    }
}
