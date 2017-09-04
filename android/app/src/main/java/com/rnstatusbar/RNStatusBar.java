package com.rnstatusbar;

import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by jerome on 2017/9/4.
 */
public class RNStatusBar extends ReactContextBaseJavaModule {
    private static final String TAG = RNStatusBar.class.getSimpleName();
    private int mDefaultUiOptions = -1;
    private int mApplicationWindowFlags = -1;

    public RNStatusBar(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void showStatusBar(final boolean bShow, final Callback callback) {

        getCurrentActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (bShow)
                    showStatusBar();
                else
                    hideStatusBar();
            }

        });

        if (callback != null) {
            callback.invoke();
        }
    }

    private void hideStatusBar() {
        if (mDefaultUiOptions == -1 && mApplicationWindowFlags == -1) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                mDefaultUiOptions = getCurrentActivity().getWindow().getDecorView().getSystemUiVisibility();
            } else {
                mApplicationWindowFlags = getCurrentActivity().getWindow().getAttributes().flags;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // for new api versions. (API>=19)
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN  // hide status bar.(API 16)
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide navigation bar.(API 14)
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; // automatically hide after a short timeout.(API 19)
            getCurrentActivity().getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) { // for lower api versions. (API<19 && API>11)
            getCurrentActivity().getWindow().getDecorView().setSystemUiVisibility(View.GONE);

        } else {
            WindowManager.LayoutParams attrs = getCurrentActivity().getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            getCurrentActivity().getWindow().setAttributes(attrs);
        }
    }

    private void showStatusBar() {
        if (mDefaultUiOptions == -1 && mApplicationWindowFlags == -1) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                mDefaultUiOptions = getCurrentActivity().getWindow().getDecorView().getSystemUiVisibility();
            } else {
                mApplicationWindowFlags = getCurrentActivity().getWindow().getAttributes().flags;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // for new api versions. (API>=19)
            getCurrentActivity().getWindow().getDecorView().setSystemUiVisibility(mDefaultUiOptions);

        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) { // for lower api versions. (API<19 && API>11)
            getCurrentActivity().getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);

        } else {
            WindowManager.LayoutParams attrs = getCurrentActivity().getWindow().getAttributes();
            if (attrs.flags != mApplicationWindowFlags) {
                attrs.flags = mApplicationWindowFlags;
                getCurrentActivity().getWindow().setAttributes(attrs);
            }
        }
    }
}
