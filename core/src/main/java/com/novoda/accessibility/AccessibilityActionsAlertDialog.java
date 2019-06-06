package com.novoda.accessibility;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates an AlertDialog with the list of Accessibility Actions.
 * <p>
 * Must be used _after_ the View has accessibility actions applied.
 * This means after setting an `AccessibilityDelegate` on the `View` or
 * after adding actions with `ViewCompat.addAccessibilityAction()`.
 * <p>
 * Will only include the actions that have labels.
 * <p>
 * Will not include `ACTION_CLICK` by default, since the intention is that this `AlertDialog` is
 * shown on `ACTION_CLICK`, it'd be weird to include "Open this AlertDialog" in this `AlertDialog`.
 * <p>
 * You might trigger this `AlertDialog` some other way, so there's a secondary factory method that
 * will include `ACTION_CLICK`.
 */
public class AccessibilityActionsAlertDialog {

    public static AlertDialog createFrom(View view) {
        return createFrom(view, false);
    }

    // TODO: In Kotlin, set default value as `false`
    public static AlertDialog createFrom(View view, boolean includeActionClick) {
        List<AccessibilityActionCompat> accessibilityActions = AccessibilitoolsViewCompat.getActionList(view);
        LabelsAndIds labelsAndIds = LabelsAndIds.from(accessibilityActions, includeActionClick);
        return new AlertDialog.Builder(view.getContext())
                .setItems(labelsAndIds.labels.toArray(new CharSequence[0]), ((dialog, which) -> {
                    ViewCompat.performAccessibilityAction(view, labelsAndIds.ids.get(which), null);
                    dialog.dismiss();
                }))
                .create();
    }

    private static class LabelsAndIds {

        final List<CharSequence> labels;
        final List<Integer> ids;

        static LabelsAndIds from(List<AccessibilityActionCompat> accessibilityActions, boolean includeActionClick) {
            List<CharSequence> labels = new ArrayList<>();
            List<Integer> ids = new ArrayList<>();

            for (AccessibilityActionCompat action : accessibilityActions) {
                @Nullable CharSequence label = action.getLabel();
                if (label == null) {
                    continue;
                }

                int id = action.getId();
                if (id == AccessibilityActionCompat.ACTION_CLICK.getId() && !includeActionClick) {
                    continue;
                }

                labels.add(label);
                ids.add(id);
            }
            return new LabelsAndIds(labels, ids);
        }

        private LabelsAndIds(List<CharSequence> labels, List<Integer> ids) {
            this.labels = labels;
            this.ids = ids;
        }
    }
}

