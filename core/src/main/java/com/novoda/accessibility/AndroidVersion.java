package com.novoda.accessibility;

import android.os.Build;

class AndroidVersion {

    private final int deviceVersion;

    static AndroidVersion newInstance() {
        return new AndroidVersion(Build.VERSION.SDK_INT);
    }

    AndroidVersion(int deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public boolean isKitKatOrHigher() {
        return deviceVersion >= Build.VERSION_CODES.KITKAT;
    }

    public boolean isMarshmallowOrHigher() {
        return deviceVersion >= Build.VERSION_CODES.M;
    }
}
