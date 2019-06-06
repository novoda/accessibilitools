package com.novoda.accessibility;

import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;

import java.util.Arrays;
import java.util.List;

public class AccessibilitoolsViewCompat {

    /**
     * Removes all AccessibilityActions from a View which match any of the passed labels.
     * If there are no matches, nothing is changed.
     */
    public static void removeAccessibilityAction(View view, CharSequence... labels) {
        List<CharSequence> labelsList = Arrays.asList(labels);
        for (AccessibilityActionCompat action : getActionList(view)) {
            if (labelsList.contains(action.getLabel())) {
                ViewCompat.removeAccessibilityAction(view, action.getId());
            }
        }
    }

    public static List<AccessibilityActionCompat> getActionList(View view) {
        AccessibilityNodeInfoCompat info = AccessibilityNodeInfoCompat.obtain(view);
        ViewCompat.onInitializeAccessibilityNodeInfo(view, info);
        return info.getActionList();
    }
}

