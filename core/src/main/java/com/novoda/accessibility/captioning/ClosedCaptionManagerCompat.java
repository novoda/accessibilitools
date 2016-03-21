package com.novoda.accessibility.captioning;

public class ClosedCaptionManagerCompat implements CaptionManager {

    public static CaptionManager newInstance() {
        return new ClosedCaptionManagerCompat();
    }

    public ClosedCaptionManagerCompat() {
        // no-op
    }

    @Override
    public boolean isClosedCaptioningEnabled() {
        return false;
    }
}
