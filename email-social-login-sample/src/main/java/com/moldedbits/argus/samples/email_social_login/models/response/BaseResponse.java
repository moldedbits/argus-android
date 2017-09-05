package com.moldedbits.argus.samples.email_social_login.models.response;

import com.google.gson.annotations.SerializedName;
import com.moldedbits.argus.samples.email_social_login.models.ApiError;

import lombok.Getter;
import lombok.Setter;

public class BaseResponse<T> {

    @Getter
    @Setter
    Status status;

    @Getter
    @Setter
    ApiError error;

    @Getter
    @Setter
    T result;

    public enum Status {
        @SerializedName("success")
        SUCCESS("success"),

        @SerializedName("failure")
        FAILURE("failure");

        @Getter
        String value;

        Status(String value) {
            this.value = value;
        }
    }
}
