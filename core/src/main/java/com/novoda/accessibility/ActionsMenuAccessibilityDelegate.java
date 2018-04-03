package com.novoda.accessibility;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ActionsMenuAccessibilityDelegate extends AccessibilityDelegateCompat {

    private final Menu menu;
    private final MenuItem.OnMenuItemClickListener menuItemClickListener;

    public ActionsMenuAccessibilityDelegate(Menu menu, MenuItem.OnMenuItemClickListener menuItemClickListener) {
        this.menu = menu;
        this.menuItemClickListener = menuItemClickListener;
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(item.getItemId(), item.getTitle()));
        }
    }

    @Override
    public boolean performAccessibilityAction(View host, int actionId, Bundle args) {
        MenuItem item = menu.findItem(actionId);
        if (item == null) {
            return super.performAccessibilityAction(host, actionId, args);
        } else {
            return menuItemClickListener.onMenuItemClick(item);
        }
    }
}
