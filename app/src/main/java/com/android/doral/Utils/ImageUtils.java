package com.android.doral.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class ImageUtils {

    public static void loadImage(Context context, String filePath, int placeholder, ImageView imageView) {
        if (!TextUtils.isEmpty(filePath)) {
            Glide.with(context)
                    .load(filePath)
                    .apply(new RequestOptions().placeholder(placeholder).error(placeholder))
                    .into(imageView);
        } else {
            imageView.setImageResource(placeholder);
        }

    }
}
