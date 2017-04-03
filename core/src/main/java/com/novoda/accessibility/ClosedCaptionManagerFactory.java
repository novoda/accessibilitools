package com.novoda.accessibility;

import android.content.Context;

class ClosedCaptionManagerFactory {

    private final AndroidVersion androidVersion;

    public static ClosedCaptionManagerFactory newInstance() {
        AndroidVersion androidVersion = AndroidVersion.newInstance();
        return new ClosedCaptionManagerFactory(androidVersion);
    }

    ClosedCaptionManagerFactory(AndroidVersion androidVersion) {
        this.androidVersion = androidVersion;
    }

    public CaptionManager createCaptionManager(Context context) {
        if (androidVersion.isMarshmallowHigher()) {
            return ClosedCaptionManagerMarshmallow.newInstance(context);
        } else {
            return new DummyClosedCaptionManager();
        }
    }

}
