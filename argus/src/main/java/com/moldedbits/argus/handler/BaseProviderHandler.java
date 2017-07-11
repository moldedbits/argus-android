package com.moldedbits.argus.handler;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moldedbits.argus.ArgusTheme;
import com.moldedbits.argus.R;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.utils.ThemeHelper;

/*
    Handler which will :-
  * Apply Theme to SignIn and SignUp Providers
  * Provides login functionality for specific end point
 */
public abstract class BaseProviderHandler extends BaseProvider implements ThemeHelper {

    @Override
    public void applyTheme(View view, ArgusTheme theme) {
        TextView welcomeTv = (TextView) view.findViewById(R.id.tv_welcome_text);
        if (welcomeTv != null && !TextUtils.isEmpty(theme.getWelcomeText())) {
            welcomeTv.setText(theme.getWelcomeText());
            welcomeTv.setTextSize(theme.getWelcomeTextSize());
            welcomeTv.setTextColor(theme.getWelcomeTextColor());
        }

        if (theme.getButtonDrawable() != 0) {
            Button actionButton = (Button) view.findViewById(R.id.action_button);
            if (actionButton != null) {
                actionButton.setBackgroundResource(theme.getButtonDrawable());
            }
        }

        if (!theme.isShowEditTextDrawables()) {
            View emailIv = view.findViewById(R.id.iv_email_et);
            if (emailIv != null) {
                emailIv.setVisibility(View.GONE);
            }

            View passwordIv = view.findViewById(R.id.iv_password_et);
            if (passwordIv != null) {
                passwordIv.setVisibility(View.GONE);
            }
        }
    }
}
