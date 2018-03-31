package com.novoda.accessibility;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.MenuRes;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActionsMenuInflater {

    private final MenuInflater menuInflater;
    private final Resources resources;

    public ActionsMenuInflater(Context context) {
        this(new MenuInflater(context), context.getResources());
    }

    ActionsMenuInflater(MenuInflater menuInflater, Resources resources) {
        this.menuInflater = menuInflater;
        this.resources = resources;
    }

    public Menu inflateMenu(@MenuRes int menuRes, MenuItem.OnMenuItemClickListener menuItemClickListener) {
        ActionsMenu menu = new ActionsMenu(resources);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setOnMenuItemClickListener(menuItemClickListener);
        }
        menuInflater.inflate(menuRes, menu);
        return menu;
    }
}
