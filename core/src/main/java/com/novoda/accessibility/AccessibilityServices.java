package com.novoda.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

public class AccessibilityServices {

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

    public boolean isTalkBackEnabled() {
        return isKnownServiceEnabled(KnownService.TALKBACK);
    }

    public boolean isSwitchAccessEnabled() {
        return isKnownServiceEnabled(KnownService.SWITCH_ACCESS);
    }

    public boolean isSelectToSpeakEnabled() {
        return isKnownServiceEnabled(KnownService.SELECT_TO_SPEAK);
    }

    private boolean isKnownServiceEnabled(KnownService service) {
        List<AccessibilityServiceInfo> enabledServices = getEnabledServicesFor(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo enabledService : enabledServices) {
            if (service.qualifiedName.equals(enabledService.getId())) {
                return true;
            }
        }
        return false;
    }

    private enum KnownService {

        TALKBACK("com.google.android.marvin.talkback/.TalkBackService"),
        SWITCH_ACCESS("com.google.android.marvin.talkback/com.android.switchaccess.SwitchAccessService"),
        SELECT_TO_SPEAK("com.google.android.marvin.talkback/com.google.android.accessibility.selecttospeak.SelectToSpeakService");

        private final String qualifiedName;

        KnownService(String qualifiedName) {
            this.qualifiedName = qualifiedName;
        }
    }
}
