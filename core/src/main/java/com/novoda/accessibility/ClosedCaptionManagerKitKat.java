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
     * <p>This method implementation was copy/pasted <a href="http://bit.ly/1TILAzT">from the AOSP.</a></p>
     * <b>Why?</b>
     * <p>In KitKat devices, when getting the CaptioningManager it creates a Handler without specifying
     * the Looper. So if you run the getSystemService(CAPTIONING) in a worker thread wich it does not
     * have any Looper attached it will crash. Since we are only using this to get the status of the
     * captions in accessibility we opted for copy this code instead of getting the system service and
     * leave the thread/looper as it is. This was introduced because we needed to run that in a job
     * scheduler library.</p>
     */
    private int getCaptionSettingAvailability() {
        return Settings.Secure.getInt(contentResolver, ACCESSIBILITY_CAPTIONING, DISABLED);
    }

}
