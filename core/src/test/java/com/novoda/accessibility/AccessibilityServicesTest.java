package com.novoda.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityManager;

import org.junit.Test;

import java.util.Collections;

import static android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
import static android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_SPOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AccessibilityServicesTest {

    private final AccessibilityManager accessibilityManager = mock(AccessibilityManager.class);
    private final CaptionManager captionManager = mock(CaptionManager.class);
    private final AccessibilityServices accessibilityServices = new AccessibilityServices(accessibilityManager, captionManager);

    @Test
    public void givenEnabledServicesListForSpokenFeedbackIsNotEmpty_thenReportsSpokenFeedbackIsEnabled() {
        AccessibilityServiceInfo anyServiceInfo = accessibilityServiceInfoWithId("any");
        given(accessibilityManager.getEnabledAccessibilityServiceList(FEEDBACK_SPOKEN))
                .willReturn(Collections.singletonList(anyServiceInfo));

        boolean spokenFeedbackEnabled = accessibilityServices.isSpokenFeedbackEnabled();

        assertThat(spokenFeedbackEnabled).isTrue();
    }

    @Test
    public void givenEnabledServicesListForSpokenFeedbackIsEmpty_thenReportsSpokenFeedbackIsDisabled() {
        given(accessibilityManager.getEnabledAccessibilityServiceList(FEEDBACK_SPOKEN))
                .willReturn(Collections.emptyList());

        boolean spokenFeedbackEnabled = accessibilityServices.isSpokenFeedbackEnabled();

        assertThat(spokenFeedbackEnabled).isFalse();
    }

    @Test
    public void givenEnabledServicesListIncludesSwitchAccess_thenReportsSwitchAccessIsEnabled() {
        AccessibilityServiceInfo switchAccessServiceInfo = accessibilityServiceInfoWithId(Service.SWITCH_ACCESS.flattenedComponentName());
        given(accessibilityManager.getEnabledAccessibilityServiceList(FEEDBACK_ALL_MASK))
                .willReturn(Collections.singletonList(switchAccessServiceInfo));

        boolean switchAccessEnabled = accessibilityServices.isSwitchAccessEnabled();

        assertThat(switchAccessEnabled).isTrue();
    }

    @Test
    public void givenEnabledServicesListDoesNotIncludeSwitchAccess_thenReportsSwitchAccessIsDisabled() {
        AccessibilityServiceInfo anyOtherServiceInfo = accessibilityServiceInfoWithId("not switch access");
        given(accessibilityManager.getEnabledAccessibilityServiceList(FEEDBACK_ALL_MASK))
                .willReturn(Collections.singletonList(anyOtherServiceInfo));

        boolean switchAccessEnabled = accessibilityServices.isSwitchAccessEnabled();

        assertThat(switchAccessEnabled).isFalse();
    }

    private AccessibilityServiceInfo accessibilityServiceInfoWithId(String id) {
        AccessibilityServiceInfo serviceInfo = mock(AccessibilityServiceInfo.class);
        given(serviceInfo.getId()).willReturn(id);
        return serviceInfo;
    }
}
