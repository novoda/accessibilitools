package com.novoda.accessibility;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class ActionsMenuAccessibilityDelegate extends AccessibilityDelegateCompat {

    private final Menu menu;
    private final MenuItem.OnMenuItemClickListener menuItemClickListener;
    private final UsageHints usageHints;

    public ActionsMenuAccessibilityDelegate(Resources resources, Menu menu, MenuItem.OnMenuItemClickListener menuItemClickListener) {
        this(menu, menuItemClickListener, new UsageHints(resources));
    }

    public ActionsMenuAccessibilityDelegate(Menu menu, MenuItem.OnMenuItemClickListener menuItemClickListener, UsageHints usageHints) {
        this.menu = menu;
        this.menuItemClickListener = menuItemClickListener;
        this.usageHints = usageHints;
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(item.getItemId(), item.getTitle()));
        }
        usageHints.addClickEventUsageHints(host, info);
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
