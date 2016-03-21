package com.novoda.accessibility.captioning;

import android.content.Context;
import android.os.Build;

public class CaptionManagerFactory {

    private final int buildVersion;

    public static CaptionManagerFactory newInstance() {
        return new CaptionManagerFactory(Build.VERSION.SDK_INT);
    }

    CaptionManagerFactory(int buildVersion) {
        this.buildVersion = buildVersion;
    }

    public CaptionManager getCaptionManager(Context context) {
        if (buildVersion >= Build.VERSION_CODES.KITKAT) {
            return ClosedCaptionManager.newInstance(context);
        } else {
            return ClosedCaptionManagerCompat.newInstance();
        }
    }
}
