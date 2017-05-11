package com.moldedbits.argus.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

import com.moldedbits.argus.R;

/**
 * Created by abhishek
 * on 11/05/17.
 */

public final class ViewUtils {
    // utility class fo not create an object
    private ViewUtils() {
        throw new RuntimeException("Not suppose to create an object of utility class");
    }

    public static int fetchAccentColor(final Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

}
