package com.novoda.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

public final class AccessibilityServices {

    private final AccessibilityManager accessibilityManager;
    private final CaptionManager captionManager;

    public static AccessibilityServices newInstance(Context context) {
        ClosedCaptionManagerFactory closedCaptionManagerFactory = ClosedCaptionManagerFactory.newInstance();
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        CaptionManager captionManager = closedCaptionManagerFactory.createCaptionManager(context);
        return new AccessibilityServices(accessibilityManager, captionManager);
    }

    @VisibleForTesting
    AccessibilityServices(AccessibilityManager accessibilityManager, CaptionManager captionManager) {
        this.accessibilityManager = accessibilityManager;
        this.captionManager = captionManager;
    }

    /**
     * Reports if any services offering spoken feedback are enabled.
     * <p/>
     * Be aware it will return true when TalkBack is enabled, even if it's suspended.
     */
    public boolean isSpokenFeedbackEnabled() {
        List<AccessibilityServiceInfo> enabledServices = getEnabledServicesFor(AccessibilityServiceInfo.FEEDBACK_SPOKEN);
        return !enabledServices.isEmpty();
    }

    private List<AccessibilityServiceInfo> getEnabledServicesFor(int feedbackTypeFlags) {
        return accessibilityManager.getEnabledAccessibilityServiceList(feedbackTypeFlags);
    }

    /**
     * Reports if video captioning is enabled on the device.
     */
    public boolean isClosedCaptioningEnabled() {
        return captionManager.isClosedCaptioningEnabled();
    }

}
