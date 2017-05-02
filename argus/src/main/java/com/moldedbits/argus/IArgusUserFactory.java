package com.moldedbits.argus;

import com.moldedbits.argus.model.IArgusUser;

public class IArgusUserFactory {

    public static IArgusUser newInstance() {
        try {
            Class userClass = Argus.getInstance().getUserClass();
            return (IArgusUser) userClass.newInstance();
        } catch (InstantiationException | IllegalStateException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
