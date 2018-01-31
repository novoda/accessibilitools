package com.novoda.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

import static com.novoda.accessibility.Service.SWITCH_ACCESS;

public class AccessibilityServices {

    private final AccessibilityManager accessibilityManager;
    private final CaptionManager captionManager;

    public static AccessibilityServices newInstance(Context context) {
        ClosedCaptionManagerFactory closedCaptionManagerFactory = ClosedCaptionManagerFactory.newInstance();
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        CaptionManager captionManager = closedCaptionManagerFactory.createCaptionManager(context);
        return new AccessibilityServices(accessibilityManager, captionManager);
    }

    AccessibilityServices(AccessibilityManager accessibilityManager, CaptionManager captionManager) {
        this.accessibilityManager = accessibilityManager;
        this.captionManager = captionManager;
    }

    /**
     * Returns true if any accessibility service offering spoken feedback are enabled.
     * <p/>
     * Be aware this will return true even if TalkBack is suspended, since it's still enabled.
     */
    public boolean isSpokenFeedbackEnabled() {
        List<AccessibilityServiceInfo> enabledServices = getEnabledServicesFor(AccessibilityServiceInfo.FEEDBACK_SPOKEN);
        return !enabledServices.isEmpty();
    }

    private List<AccessibilityServiceInfo> getEnabledServicesFor(int feedbackTypeFlags) {
        return accessibilityManager.getEnabledAccessibilityServiceList(feedbackTypeFlags);
    }

    /**
     * Returns true if video captioning is enabled on the device.
     */
    public boolean isClosedCaptioningEnabled() {
        return captionManager.isClosedCaptioningEnabled();
    }

    /**
     * Returns true if the Switch Access accessibility service is enabled.
     */
    public boolean isSwitchAccessEnabled() {
        return isAccessibilityServiceEnabled(SWITCH_ACCESS.flattenedComponentName());
    }

    private boolean isAccessibilityServiceEnabled(String serviceName) {
        List<AccessibilityServiceInfo> enabledServices = getEnabledServicesFor(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo enabledService : enabledServices) {
            if (serviceName.equals(enabledService.getId())) {
                return true;
            }
        }
        return false;
    }
}
