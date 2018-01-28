package com.novoda.accessibility;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class ClosedCaptionManagerKitKat implements CaptionManager {

    private static final int DISABLED = 0;
    private static final int ENABLED = 1;
    private static final String ACCESSIBILITY_CAPTIONING = "accessibility_captioning_enabled";

    private final ContentResolver contentResolver;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static CaptionManager newInstance(Context context) {
        return new ClosedCaptionManagerKitKat(context.getContentResolver());
    }

    public ClosedCaptionManagerKitKat(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean isClosedCaptioningEnabled() {
        return getCaptionSettingAvailability() == ENABLED;
    }

    /**
     * <p>Copied from AOSP implementation of CaptioningManager</p>
     * <p>Pre-Marshmallow, querying for CaptioningManager creates a Handler without specifying the Looper.
     * On worker threads, this causes an exception since it can only be used on the main thread.</p>
     *
     * @see <a href="http://bit.ly/1TILAzT">CaptioningManager</a>
     */
    private int getCaptionSettingAvailability() {
        return Settings.Secure.getInt(contentResolver, ACCESSIBILITY_CAPTIONING, DISABLED);
    }
}
