package com.novoda.accessibility;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;

public class ActionsMenuAlertDialog {

    public static AlertDialog.Builder create(Context context, Menu menu, MenuItem.OnMenuItemClickListener menuItemClickListener) {
        CharSequence[] itemLabels = new CharSequence[menu.size()];
        for (int i = 0; i < menu.size(); i++) {
            itemLabels[i] = menu.getItem(i).getTitle();
        }

        return new AlertDialog.Builder(context).setItems(
                itemLabels,
                (dialog, index) -> {
                    menuItemClickListener.onMenuItemClick(menu.getItem(index));
                    dialog.dismiss();
                }
        );
    }
}
