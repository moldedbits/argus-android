package com.moldedbits.argus.samples.email_social_login.models;


import com.moldedbits.argus.samples.email_social_login.models.response.BaseResponse;

import lombok.Getter;
import lombok.Setter;

public class ApiError {

    @Getter
    @Setter
    String errors;

    @Getter
    @Setter
    BaseResponse.Status success;
}
