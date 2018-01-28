package com.novoda.accessibility;

class DummyClosedCaptionManager implements CaptionManager {

    DummyClosedCaptionManager() {
        // no-op
    }

    @Override
    public boolean isClosedCaptioningEnabled() {
        return false;
    }
}
