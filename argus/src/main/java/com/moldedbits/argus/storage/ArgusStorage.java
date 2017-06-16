package com.moldedbits.argus.storage;

/**
 * Created by abhishek
 * on 04/05/17.
 * Custom user storage should implement this interface in order to be able to talk to Argus
 * There is a default implementation available @link{com.moldedbits.argus.storage.DefaultArgusStorage}
 */
// TODO: 30/05/17 change this to something specific
public interface ArgusStorage {

    void putString(String key, String value);

    String getString(String key, String defaultValue);
}
