package com.moldedbits.argus.storage;
import com.moldedbits.argus.model.ArgusUser;

/**
 * Created by abhishek
 * on 04/05/17.
 * Custom user storage should implement this interface in order to be able to talk to Argus
 * There is a default implementation available @link{com.moldedbits.argus.storage.DefaultArgusStorage}
 */

public interface ArgusStorage {
    void setCurrentUser(ArgusUser user);
    ArgusUser getCurrentUser();
}
