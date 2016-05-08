package com.ironfactory.allinoneenglish.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by IronFactory on 2016. 1. 28..
 */
public class FontUtils {
    public static void setGlobalFont(Context context, View view, String fontName) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View v = viewGroup.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
                    }

                    setGlobalFont(context, v, fontName);
                }
            }
        }
    }


    // stack overflow 발생
    public static void setGlobalTextSize(View view, int size) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View v = viewGroup.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTextSize(size);
                    }

                    setGlobalTextSize(view, size);
                }
            }
        }
    }
}
