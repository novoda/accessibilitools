package com.novoda.accessibility;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ActionsMenuAccessibilityDelegate extends AccessibilityDelegateCompat {

    private final ActionsMenu menu;

    public static ActionsMenuAccessibilityDelegate create(Context context, @MenuRes int menuRes, MenuItem.OnMenuItemClickListener menuItemClickListener) {
        MenuInflater menuInflater = new MenuInflater(context);
        ActionsMenu menu = new ActionsMenu(context.getResources());
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setOnMenuItemClickListener(menuItemClickListener);
        }
        menuInflater.inflate(menuRes, menu);
        return new ActionsMenuAccessibilityDelegate(menu);
    }

    private ActionsMenuAccessibilityDelegate(ActionsMenu menu) {
        this.menu = menu;
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
        ActionMenuItem item = menu.findItem(actionId);
        if (item == null) {
            return super.performAccessibilityAction(host, actionId, args);
        } else {
            item.invokeAction();
            return true;
        }
    }
}
