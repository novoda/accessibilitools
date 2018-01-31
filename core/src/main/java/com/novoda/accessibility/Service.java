package com.novoda.accessibility;

/**
 * An enumeration of all the known accessibility services and their fully qualified names.
 * <p>
 * These are the values returned from android.accessibilityservice.AccessibilityServiceInfo#getId().
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
