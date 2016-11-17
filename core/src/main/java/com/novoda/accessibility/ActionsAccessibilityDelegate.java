package com.novoda.accessibility;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;

public class ActionsAccessibilityDelegate extends AccessibilityDelegateCompat {

    private final Resources resources;
    private final Actions actions;
    private final UsageHintsAccessibilityDelegate usageHintsAccessibilityDelegate;

    public ActionsAccessibilityDelegate(Resources resources, Actions actions) {
        this(resources, actions, new UsageHintsAccessibilityDelegate(resources));
    }

    public ActionsAccessibilityDelegate(Resources resources, Actions actions, UsageHintsAccessibilityDelegate usageHintsAccessibilityDelegate) {
        this.resources = resources;
        this.actions = actions;
        this.usageHintsAccessibilityDelegate = usageHintsAccessibilityDelegate;
    }

    /**
     * Label describing the action that will be performed on click
     */
    public void setClickLabel(@StringRes int clickLabel) {
        usageHintsAccessibilityDelegate.setClickLabel(clickLabel);
    }

    /**
     * Label describing the action that will be performed on click
     */
    public void setClickLabel(CharSequence clickLabel) {
        usageHintsAccessibilityDelegate.setClickLabel(clickLabel);
    }

    /**
     * Label describing the action that will be performed on long click
     */
    public void setLongClickLabel(@StringRes int longClickLabel) {
        usageHintsAccessibilityDelegate.setLongClickLabel(longClickLabel);
    }

    /**
     * Label describing the action that will be performed on long click
     */
    public void setLongClickLabel(CharSequence longClickLabel) {
        usageHintsAccessibilityDelegate.setLongClickLabel(longClickLabel);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        for (Action action : actions) {
            String label = resources.getString(action.getLabel());
            info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(action.getId(), label));
        }

        usageHintsAccessibilityDelegate.onInitializeAccessibilityNodeInfo(host, info);
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
