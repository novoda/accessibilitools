package com.novoda.accessibility;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;

import static android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.ACTION_CLICK;
import static android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.ACTION_LONG_CLICK;

public class ActionsAccessibilityDelegate extends AccessibilityDelegateCompat {

    private static final int NO_CUSTOM_LABEL = 0;

    private final Resources resources;
    private final Actions actions;

    @StringRes
    private int clickLabel = NO_CUSTOM_LABEL;

    @StringRes
    private int longClickLabel = NO_CUSTOM_LABEL;

    public ActionsAccessibilityDelegate(Resources resources, Actions actions) {
        this.resources = resources;
        this.actions = actions;
    }

    public void setClickLabel(@StringRes int clickLabel) {
        this.clickLabel = clickLabel;
    }

    public void setLongClickLabel(@StringRes int longClickLabel) {
        this.longClickLabel = longClickLabel;
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        for (Action action : actions) {
            String label = resources.getString(action.getLabel());
            info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(action.getId(), label));
        }

        addCustomDescriptionForClickEventIfNecessary(host, info);
        addCustomDescriptionForLongClickEventIfNecessary(host, info);
    }

    private void addCustomDescriptionForClickEventIfNecessary(View host, AccessibilityNodeInfoCompat info) {
        if (!host.isClickable() || clickLabel == NO_CUSTOM_LABEL) {
            return;
        }

        String customClickLabelText = resources.getString(clickLabel);
        info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(ACTION_CLICK, customClickLabelText));
    }

    private void addCustomDescriptionForLongClickEventIfNecessary(View host, AccessibilityNodeInfoCompat info) {
        if (!host.isLongClickable() || longClickLabel == NO_CUSTOM_LABEL) {
            return;
        }

        String customLongClickLabelText = resources.getString(longClickLabel);
        info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(ACTION_LONG_CLICK, customLongClickLabelText));
    }

    @Override
    public boolean performAccessibilityAction(View host, int actionId, Bundle args) {
        Action action = actions.findActionById(actionId);
        if (action == null) {
            return super.performAccessibilityAction(host, actionId, args);
        } else {
            action.run();
            return true;
        }
    }

}
