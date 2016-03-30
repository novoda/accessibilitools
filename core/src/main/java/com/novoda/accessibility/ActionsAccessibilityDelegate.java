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
    private final int customClickLabel;

    @StringRes
    private final int customLongClickLabel;

    public ActionsAccessibilityDelegate(Resources resources, Actions actions) {
        this(resources, actions, NO_CUSTOM_LABEL);
    }

    public ActionsAccessibilityDelegate(Resources resources, Actions actions, @StringRes int customClickLabel) {
        this(resources, actions, customClickLabel, NO_CUSTOM_LABEL);
    }

    public ActionsAccessibilityDelegate(Resources resources, Actions actions, @StringRes int customClickLabel, @StringRes int customLongClickLabel) {
        this.resources = resources;
        this.actions = actions;
        this.customClickLabel = customClickLabel;
        this.customLongClickLabel = customLongClickLabel;
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
        if (!host.isClickable() || customClickLabel == NO_CUSTOM_LABEL) {
            return;
        }

        String customClickLabelText = resources.getString(customClickLabel);
        info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(ACTION_CLICK, customClickLabelText));
    }

    private void addCustomDescriptionForLongClickEventIfNecessary(View host, AccessibilityNodeInfoCompat info) {
        if (!host.isLongClickable() || customLongClickLabel == NO_CUSTOM_LABEL) {
            return;
        }

        String customLongClickLabelText = resources.getString(customLongClickLabel);
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
