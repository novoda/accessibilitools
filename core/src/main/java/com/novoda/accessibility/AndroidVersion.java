package com.novoda.accessibility;

import android.os.Build;

public class AndroidVersion {

    private final int deviceVersion;

    public static AndroidVersion newInstance() {
        return new AndroidVersion(Build.VERSION.SDK_INT);
    }

    AndroidVersion(int deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public boolean isKitKatOrHigher() {
        return deviceVersion >= Build.VERSION_CODES.KITKAT;
    }

}
