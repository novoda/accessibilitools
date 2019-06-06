package com.novoda.accessibility;

import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionsAlertDialogCreator {

    private static final int NO_TITLE = 0;

    private final Context context;

    @StringRes
    private final int title;

    public ActionsAlertDialogCreator(Context context) {
        this(context, NO_TITLE);
    }

    public ActionsAlertDialogCreator(Context context, @StringRes int title) {
        this.context = context;
        this.title = title;
    }

    /**
     * Creates an ActionsAlertDialog from the Accessibility actions present on a View.
     */
    public AlertDialog create(View view) {
        AccessibilityNodeInfoCompat accessibilityNodeInfo = AccessibilityNodeInfoCompat.obtain(view);
        ViewCompat.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        List<AccessibilityActionCompat> actionList = accessibilityNodeInfo.getActionList();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ActionLabelsAndIds actionLabelsAndIds = ActionLabelsAndIds.from(actionList);

        builder.setItems(
                actionLabelsAndIds.getLabels(),
                (dialog, positionInDialogList) -> {
                    ViewCompat.performAccessibilityAction(view, actionLabelsAndIds.getId(positionInDialogList), null);
                    dialog.dismiss();
                }
        );

        if (title != NO_TITLE) {
            builder.setTitle(title);
        }

        return builder.create();
    }

    /**
     * Wrapper to collate actions from a `List<AccessibilityActionCompat>` while preserving their
     * order (else a Map would have been sufficient).
     *
     * The preservation of order is important so that we can find the correct action ID to use when
     * a dialog click event occurs (we get the position clicked in the callback).
     */
    private static class ActionLabelsAndIds {

        private final List<CharSequence> labels;
        private final List<Integer> ids;

        /**
         * We skip the ACTION_CLICK because this ActionsAlertDialog is intended to be shown onClick:
         * we don't want that action to be available from the dialog.
         * @param actions
         * @return
         */
        static ActionLabelsAndIds from(List<AccessibilityActionCompat> actions) {
            List<CharSequence> labels = new ArrayList<>();
            List<Integer> ids = new ArrayList<>();
            for (AccessibilityActionCompat action : actions) {
                CharSequence label = action.getLabel();
                int id = action.getId();
                if (label != null && id != AccessibilityActionCompat.ACTION_CLICK.getId()) {
                    labels.add(label);
                    ids.add(id);
                }
            }
            return new ActionLabelsAndIds(labels, ids);
        }

        private ActionLabelsAndIds(List<CharSequence> labels, List<Integer> ids) {
            this.labels = labels;
            this.ids = ids;
        }

        CharSequence[] getLabels() {
            return labels.toArray(new CharSequence[0]);
        }

        Integer getId(int position) {
            return ids.get(position);
        }
    }

    public AlertDialog create(final Actions actions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(
                collateActionLabels(actions),
                (dialog, actionId) -> {
                    Action action = actions.getAction(actionId);
                    action.run();
                    dialog.dismiss();
                }
        );

        if (title != NO_TITLE) {
            builder.setTitle(title);
        }

        return builder.create();
    }

    private CharSequence[] collateActionLabels(Actions actions) {
        CharSequence[] itemLabels = new CharSequence[actions.getCount()];
        for (int i = 0; i < actions.getCount(); i++) {
            itemLabels[i] = context.getResources().getString(actions.getAction(i).getLabel());
        }
        return itemLabels;
    }
}
