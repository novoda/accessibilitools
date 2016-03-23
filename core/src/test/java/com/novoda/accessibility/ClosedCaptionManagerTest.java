package com.novoda.accessibility;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.accessibility.CaptioningManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class ClosedCaptionManagerTest {

    @Mock
    CaptioningManager mockCaptioningManager;

    private ClosedCaptionManager closedCaptionManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        closedCaptionManager = new ClosedCaptionManager(mockCaptioningManager);
    }

    @Test
    public void whenCheckingIfCloseCaptioningEnabled_thenCaptioningManagerIsCalled() {
        closedCaptionManager.isClosedCaptioningEnabled();

        verify(mockCaptioningManager).isEnabled();
    }
}
