package com.novoda.accessibility.captioning;

import android.content.Context;

import com.novoda.support.DeviceAndroidVersion;

public class CaptionManagerFactory {

    private final DeviceAndroidVersion deviceAndroidVersion;

    public static CaptionManagerFactory newInstance() {
        DeviceAndroidVersion deviceAndroidVersion = DeviceAndroidVersion.newInstance();
        return new CaptionManagerFactory(deviceAndroidVersion);
    }

    CaptionManagerFactory(DeviceAndroidVersion deviceAndroidVersion) {
        this.deviceAndroidVersion = deviceAndroidVersion;
    }

    public CaptionManager getCaptionManager(Context context) {
        if (deviceAndroidVersion.is19KitKatOrOver()) {
            return ClosedCaptionManager.newInstance(context);
        } else {
            return ClosedCaptionManagerCompat.newInstance();
        }
    }
}
