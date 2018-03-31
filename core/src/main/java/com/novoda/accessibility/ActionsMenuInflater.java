package com.novoda.accessibility;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActionsMenuInflater {

    public static Menu inflate(Context context, @MenuRes int menuRes, MenuItem.OnMenuItemClickListener menuItemClickListener) {
        MenuInflater menuInflater = new MenuInflater(context);
        ActionsMenu menu = new ActionsMenu(context.getResources());
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setOnMenuItemClickListener(menuItemClickListener);
        }
        menuInflater.inflate(menuRes, menu);
        return menu;
    }
}
