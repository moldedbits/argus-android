package com.moldedbits.argus.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class for Argus User. You can either extend this, or map it to your user class.
 */

public class ArgusUser implements IArgusUser {

    /**
     * Username
     */
    @Getter @Setter public String username;
}
