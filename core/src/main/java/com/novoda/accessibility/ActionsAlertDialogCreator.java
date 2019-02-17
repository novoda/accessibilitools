package com.novoda.accessibility;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

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
