package com.novoda.accessibility;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_CLICK;
import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_LONG_CLICK;

public class UsageHints {

    private final Resources resources;

    private CharSequence clickLabel = null;
    private CharSequence longClickLabel = null;

    public UsageHints(Resources resources) {
        this.resources = resources;
    }

    /**
     * Label describing the action that will be performed on click
     */
    public void setClickLabel(@StringRes int clickLabel) {
        setClickLabel(resources.getString(clickLabel));
    }

    /**
     * Label describing the action that will be performed on click
     */
    public void setClickLabel(CharSequence clickLabel) {
        this.clickLabel = clickLabel;
    }

    /**
     * Label describing the action that will be performed on long click
     */
    public void setLongClickLabel(@StringRes int longClickLabel) {
        setLongClickLabel(resources.getString(longClickLabel));
    }

    /**
     * Label describing the action that will be performed on long click
     */
    public void setLongClickLabel(CharSequence longClickLabel) {
        this.longClickLabel = longClickLabel;
    }


    void addClickEventUsageHints(View host, AccessibilityNodeInfoCompat info) {
        if (host.isClickable()) {
            info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(ACTION_CLICK, clickLabel));
        }

        if (host.isLongClickable()) {
            info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(ACTION_LONG_CLICK, longClickLabel));
        }
    }
}
