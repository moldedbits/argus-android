package com.moldedbits.argus.samples.email_social_login.api;


import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.moldedbits.argus.samples.email_social_login.R;
import com.moldedbits.argus.samples.email_social_login.models.ApiError;
import com.moldedbits.argus.samples.email_social_login.models.response.BaseResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abhishek
 * on 08/04/16.
 */

public abstract class ResponseCallback<T> implements Callback<BaseResponse<T>> {

    private final Activity activity;

    public ResponseCallback(Activity activity) {
        this.activity = activity;
    }

    public abstract void onSuccess(Call<BaseResponse<T>> call, BaseResponse<T> response);

    public abstract void onError(ApiError error);

    public abstract void onRetry(Call<BaseResponse<T>> call);

    @Override
    public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
        String message;
        ApiError error;

        if (response != null) {
            BaseResponse<T> baseResponse = response.body();
            if (baseResponse != null && baseResponse.getResult() != null) {
                if (response.isSuccessful()) {
                    onSuccess(call, baseResponse);
                } else {
                    error = baseResponse.getError();
                    message = error.getErrors();
                    onError(error);
                    showSnackBar(false, message, call);
                }
                return;
            }
        }

        error = getMockError();
        message = error.getErrors();
        onError(getMockError());

        showSnackBar(false, message, call);
    }


    public abstract void onFailure(Throwable throwable);

    @Override
    public void onFailure(Call<BaseResponse<T>> call, Throwable throwable) {
        if (throwable != null) {
            if (throwable instanceof IOException) {
                showSnackBar(true, activity.getString(R.string.internet_slow_or_disconnected),
                        call);
            } else {
                showSnackBar(false, throwable.getMessage(), call);
            }
            onFailure(throwable);
        }
    }

    private ApiError getMockError() {
        ApiError apiError = new ApiError();
        apiError.setErrors(activity.getString(R.string.something_went_wrong));
        apiError.setSuccess(BaseResponse.Status.FAILURE);
        return apiError;
    }

    private void showSnackBar(boolean isClickable, String message,
                              final Call<BaseResponse<T>> call) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message,
                Snackbar.LENGTH_LONG);
        if (isClickable) {
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (call != null) {
                        onRetry(call);
                    }
                }
            });
        }
        snackbar.show();
    }
}
