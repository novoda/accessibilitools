package com.novoda.accessibility;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClosedCaptionManagerFactoryTest {

    private ClosedCaptionManagerFactory closedCaptionManagerFactory;

    @Mock
    AndroidVersion mockAndroidVersion;

    @Mock
    Context mockContext;

    @Before
    public void setUp() {
        initMocks(this);
        closedCaptionManagerFactory = new ClosedCaptionManagerFactory(mockAndroidVersion);
    }

    @Test
    public void givenAndroidVersionMarshmallowOrHigher_whenCreatingANewCaptionManager_thenMarshmallowClosedCaptionManagerReturned() {
        when(mockAndroidVersion.isMarshmallowOrHigher()).thenReturn(true);

        CaptionManager captionManager = closedCaptionManagerFactory.createCaptionManager(mockContext);

        assertThat(captionManager).isInstanceOf(ClosedCaptionManagerMarshmallow.class);
    }

    @Test
    public void givenAndroidVersionKitKatOrHigher_whenCreatingANewCaptionManager_thenKitKatClosedCaptionManagerReturned() {
        when(mockAndroidVersion.isKitKatOrHigher()).thenReturn(true);

        CaptionManager captionManager = closedCaptionManagerFactory.createCaptionManager(mockContext);

        assertThat(captionManager).isInstanceOf(ClosedCaptionManagerKitKat.class);
    }

    @Test
    public void givenAndroidVersionLowerThanKitKat_whenCreatingANewCaptionManager_thenDummyClosedCaptionManagerReturned() {
        when(mockAndroidVersion.isKitKatOrHigher()).thenReturn(false);

        CaptionManager captionManager = closedCaptionManagerFactory.createCaptionManager(mockContext);

        assertThat(captionManager).isInstanceOf(DummyClosedCaptionManager.class);
    }
}
