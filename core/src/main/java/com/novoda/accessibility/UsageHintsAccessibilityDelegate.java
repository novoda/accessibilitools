package com.novoda.accessibility;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class UsageHintsAccessibilityDelegate extends AccessibilityDelegateCompat {

    private final UsageHints usageHints;

    public UsageHintsAccessibilityDelegate(Resources resources) {
        this.usageHints = new UsageHints(resources);
    }

    /**
     * Label describing the action that will be performed on click
     */
    public void setClickLabel(@StringRes int clickLabel) {
        usageHints.setClickLabel(clickLabel);
    }

    /**
     * Label describing the action that will be performed on click
     */
    public void setClickLabel(CharSequence clickLabel) {
        usageHints.setClickLabel(clickLabel);
    }

    /**
     * Label describing the action that will be performed on long click
     */
    public void setLongClickLabel(@StringRes int longClickLabel) {
        usageHints.setLongClickLabel(longClickLabel);
    }

    /**
     * Label describing the action that will be performed on long click
     */
    public void setLongClickLabel(CharSequence longClickLabel) {
        usageHints.setLongClickLabel(longClickLabel);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        usageHints.addClickEventUsageHints(host, info);
    }
}
