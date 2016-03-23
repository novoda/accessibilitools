package com.novoda.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityManager;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccessibilityServicesTest {

    @Mock
    AccessibilityManager mockAccessibilityManager;

    @Mock
    AccessibilityServiceInfo mockAccessibilityServiceInfo;

    @Mock
    CaptionManager mockCaptionManager;

    private AccessibilityServices accessibilityServices;

    @Before
    public void setUp() {
        initMocks(this);
        accessibilityServices = new AccessibilityServices(mockAccessibilityManager, mockCaptionManager);
    }

    @Test
    public void whenAccessibilityServiceInfoListIsNotEmpty_thenSpokenFeedbackIsEnabled() {
        List<AccessibilityServiceInfo> serviceInfoList = Collections.singletonList(mockAccessibilityServiceInfo);
        when(mockAccessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN))
                .thenReturn(serviceInfoList);

        boolean spokenFeedbackEnabled = accessibilityServices.isSpokenFeedbackEnabled();

        assertThat(spokenFeedbackEnabled).isTrue();
    }

    @Test
    public void whenAccessibilityServiceInfoListIsEmpty_thenSpokenFeedbackIsDisabled() {
        when(mockAccessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN))
                .thenReturn(Collections.<AccessibilityServiceInfo>emptyList());

        boolean spokenFeedbackEnabled = accessibilityServices.isSpokenFeedbackEnabled();

        assertThat(spokenFeedbackEnabled).isFalse();
    }

}
