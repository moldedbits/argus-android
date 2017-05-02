package com.moldedbits.argus.sample;

import com.moldedbits.argus.model.IArgusUser;

public class SampleArgusUser implements IArgusUser {

    private String username;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = "Sample " + username;
    }
}
