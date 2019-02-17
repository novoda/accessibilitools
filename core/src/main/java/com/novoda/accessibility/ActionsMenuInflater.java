package com.novoda.accessibility;

import android.content.Context;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.MenuRes;

public class ActionsMenuInflater {

    private final MenuInflater menuInflater;
    private final Resources resources;

    public static ActionsMenuInflater from(Context context) {
        MenuInflater menuInflater = new MenuInflater(context);
        Resources resources = context.getResources();
        return new ActionsMenuInflater(menuInflater, resources);
    }

    private ActionsMenuInflater(MenuInflater menuInflater, Resources resources) {
        this.menuInflater = menuInflater;
        this.resources = resources;
    }

    public Menu inflate(@MenuRes int menuRes, MenuItem.OnMenuItemClickListener menuItemClickListener) {
        ActionsMenu menu = new ActionsMenu(resources);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setOnMenuItemClickListener(menuItemClickListener);
        }
        menuInflater.inflate(menuRes, menu);
        return menu;
    }
}
