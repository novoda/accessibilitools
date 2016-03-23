package com.novoda.accessibility;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.CaptioningManager;

final class ClosedCaptionManager implements CaptionManager {

    private final CaptioningManager captioningManager;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static CaptionManager newInstance(Context context) {
        CaptioningManager captioningManager = (CaptioningManager) context.getSystemService(Context.CAPTIONING_SERVICE);
        return new ClosedCaptionManager(captioningManager);
    }

    ClosedCaptionManager(CaptioningManager captioningManager) {
        this.captioningManager = captioningManager;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean isClosedCaptioningEnabled() {
        return captioningManager.isEnabled();
    }

}
