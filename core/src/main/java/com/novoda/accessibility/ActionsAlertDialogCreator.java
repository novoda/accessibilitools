package com.novoda.accessibility;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

public class ActionsAlertDialogCreator {

    private static final int NO_TITLE = 0;
    
    private final Context context;

    @StringRes
    private final int title;

    private final Actions actions;

    public ActionsAlertDialogCreator(Context context, Actions actions) {
        this(context, NO_TITLE, actions);
    }

    public ActionsAlertDialogCreator(Context context, @StringRes int title, Actions actions) {
        this.context = context;
        this.title = title;
        this.actions = actions;
    }

    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(
                collateActionLabels(),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Action action = actions.getAction(which);
                        action.run();
                        dialog.dismiss();
                    }

                }
        );

        if (title != NO_TITLE) {
            builder.setTitle(title);
        }

        return builder.create();
    }

    private CharSequence[] collateActionLabels() {
        CharSequence[] itemLabels = new CharSequence[actions.getCount()];
        for (int i = 0; i < actions.getCount(); i++) {
            itemLabels[i] = context.getResources().getString(actions.getAction(i).getLabel());
        }
        return itemLabels;
    }

}
