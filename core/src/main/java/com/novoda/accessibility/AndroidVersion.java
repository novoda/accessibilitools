package com.novoda.accessibility;

import android.os.Build;

final class AndroidVersion {

    private final int deviceVersion;

    static AndroidVersion newInstance() {
        return new AndroidVersion(Build.VERSION.SDK_INT);
    }

    private AndroidVersion(int deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public boolean isKitKatOrHigher() {
        return deviceVersion >= Build.VERSION_CODES.KITKAT;
    }

}
