package com.novoda.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;

/**
 * An enumeration of known accessibility services and their flattened {@link ComponentName}.
 * <p>
 * These are the values returned from {@link AccessibilityServiceInfo#getId()}.
 */
public enum Service {

    TALKBACK("com.google.android.marvin.talkback/.TalkBackService"),
    SWITCH_ACCESS("com.google.android.marvin.talkback/com.android.switchaccess.SwitchAccessService"),
    SELECT_TO_SPEAK("com.google.android.marvin.talkback/com.google.android.accessibility.selecttospeak.SelectToSpeakService");

    private final String flattenedComponentName;

    Service(String flattenedComponentName) {
        this.flattenedComponentName = flattenedComponentName;
    }

    public String flattenedComponentName() {
        return flattenedComponentName;
    }
}
