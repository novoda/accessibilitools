package com.novoda.accessibility;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.accessibility.CaptioningManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@TargetApi(Build.VERSION_CODES.M)
public class ClosedCaptionManagerMarshmallowTest {

    @Mock
    CaptioningManager mockCaptioningManager;

    private ClosedCaptionManagerMarshmallow closedCaptionManager;

    @Before
    public void setUp() {
        initMocks(this);
        closedCaptionManager = new ClosedCaptionManagerMarshmallow(mockCaptioningManager);
    }

    @Test
    public void whenCheckingIfCloseCaptioningEnabled_thenCaptioningManagerIsCalled() {
        closedCaptionManager.isClosedCaptioningEnabled();

        verify(mockCaptioningManager).isEnabled();
    }

    @Test
    public void whenSystemCaptioningManagerIsEnabled_thenClosedCaptioningIsEnabled() {
        when(mockCaptioningManager.isEnabled()).thenReturn(true);

        assertThat(closedCaptionManager.isClosedCaptioningEnabled()).isTrue();
    }

    @Test
    public void whenSystemCaptioningManagerIsDisabled_thenClosedCaptioningIsDisabled() {
        when(mockCaptioningManager.isEnabled()).thenReturn(false);

        assertThat(closedCaptionManager.isClosedCaptioningEnabled()).isFalse();
    }
}
