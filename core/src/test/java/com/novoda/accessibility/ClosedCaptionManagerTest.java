package com.novoda.accessibility;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.accessibility.CaptioningManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class ClosedCaptionManagerTest {

    @Mock
    CaptioningManager mockCaptioningManager;

    private ClosedCaptionManager closedCaptionManager;

    @Before
    public void setUp() {
        initMocks(this);
        closedCaptionManager = new ClosedCaptionManager(mockCaptioningManager);
    }

    @Test
    public void whenCheckingIfCloseCaptioningEnabled_thenCaptioningManagerIsCalled() {
        closedCaptionManager.isClosedCaptioningEnabled();

        verify(mockCaptioningManager).isEnabled();
    }
}
