package com.novoda.accessibility;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class ActionsAccessibilityDelegate extends AccessibilityDelegateCompat {

    private final Resources resources;
    private final Actions actions;
    private final UsageHints usageHints;

    public ActionsAccessibilityDelegate(Resources resources, Actions actions) {
        this(resources, actions, new UsageHints(resources));
    }

    public ActionsAccessibilityDelegate(Resources resources, Actions actions, UsageHints usageHints) {
        this.resources = resources;
        this.actions = actions;
        this.usageHints = usageHints;
    }

    /**
     * Label describing the action that will be performed on click
     *
     * @deprecated Create UsageHints explicitly and pass via the constructor.
     */
    @Deprecated
    public void setClickLabel(@StringRes int clickLabel) {
        usageHints.setClickLabel(clickLabel);
    }

    /**
     * Label describing the action that will be performed on click
     *
     * @deprecated Create UsageHints explicitly and pass via the constructor.
     */
    @Deprecated
    public void setClickLabel(CharSequence clickLabel) {
        usageHints.setClickLabel(clickLabel);
    }

    /**
     * Label describing the action that will be performed on long click
     *
     * @deprecated Create UsageHints explicitly and pass via the constructor.
     */
    @Deprecated
    public void setLongClickLabel(@StringRes int longClickLabel) {
        usageHints.setLongClickLabel(longClickLabel);
    }

    /**
     * Label describing the action that will be performed on long click
     *
     * @deprecated Create UsageHints explicitly and pass via the constructor.
     */
    @Deprecated
    public void setLongClickLabel(CharSequence longClickLabel) {
        usageHints.setLongClickLabel(longClickLabel);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        for (Action action : actions) {
            String label = resources.getString(action.getLabel());
            info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(action.getId(), label));
        }
        usageHints.addClickEventUsageHints(host, info);
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
