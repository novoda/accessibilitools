package com.novoda.accessibility;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;

import static android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.ACTION_CLICK;
import static android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.ACTION_LONG_CLICK;

public class UsageHintsAccessibilityDelegate extends AccessibilityDelegateCompat {

    private final Resources resources;
    private CharSequence clickLabel = null;
    private CharSequence longClickLabel = null;

    public UsageHintsAccessibilityDelegate(Resources resources) {
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

    @Override
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);

        addCustomDescriptionForClickEventIfNecessary(host, info);
        addCustomDescriptionForLongClickEventIfNecessary(host, info);
    }

    private void addCustomDescriptionForClickEventIfNecessary(View host, AccessibilityNodeInfoCompat info) {
        if (!host.isClickable() || clickLabel == null) {
            return;
        }

        info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(ACTION_CLICK, clickLabel));
    }

    private void addCustomDescriptionForLongClickEventIfNecessary(View host, AccessibilityNodeInfoCompat info) {
        if (!host.isLongClickable() || longClickLabel == null) {
            return;
        }

        info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(ACTION_LONG_CLICK, longClickLabel));
    }
}
