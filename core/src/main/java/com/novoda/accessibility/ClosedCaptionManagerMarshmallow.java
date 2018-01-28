package com.novoda.accessibility;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.CaptioningManager;

class ClosedCaptionManagerMarshmallow implements CaptionManager {

    private final CaptioningManager captioningManager;

    @TargetApi(Build.VERSION_CODES.M)
    public static CaptionManager newInstance(Context context) {
        CaptioningManager captioningManager = (CaptioningManager) context.getSystemService(Context.CAPTIONING_SERVICE);
        return new ClosedCaptionManagerMarshmallow(captioningManager);
    }

    ClosedCaptionManagerMarshmallow(CaptioningManager captioningManager) {
        this.captioningManager = captioningManager;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean isClosedCaptioningEnabled() {
        return captioningManager.isEnabled();
    }
}
