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

    private final String qualifiedName;

    Service(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String qualifiedName() {
        return qualifiedName;
    }
}
