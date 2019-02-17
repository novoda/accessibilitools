package com.novoda.accessibility;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

class ActionsMenu implements Menu {

    private final Resources resources;
    private final List<ActionMenuItem> menuItems = new ArrayList<>();

    ActionsMenu(Resources resources) {
        this.resources = resources;
    }

    @Override
    public ActionMenuItem add(@StringRes int titleRes) {
        return add(resources.getString(titleRes));
    }

    @Override
    public ActionMenuItem add(CharSequence title) {
        throw new UnsupportedOperationException("Items without IDs explicitly set aren't supported.");
    }

    @Override
    public ActionMenuItem add(int groupId, int itemId, int order, @StringRes int titleRes) {
        return add(groupId, itemId, order, resources.getString(titleRes));
    }

    @Override
    public ActionMenuItem add(int groupId, int itemId, int order, CharSequence title) {
        if (itemId == 0 || menuItemsContainsItemWithId(itemId)) {
            throw new IllegalArgumentException("itemId for \"" + title + "\" is missing or matches another item in menu.");
        }
        ActionMenuItem menuItem = new ActionMenuItem(resources, itemId, title);
        menuItems.add(menuItem);
        return menuItem;
    }

    private boolean menuItemsContainsItemWithId(int itemId) {
        for (ActionMenuItem menuItem : menuItems) {
            if (menuItem.getItemId() == itemId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SubMenu addSubMenu(CharSequence title) {
        return addSubMenu(0, 0, 0, title);
    }

    @Override
    public SubMenu addSubMenu(@StringRes int titleRes) {
        return addSubMenu(resources.getString(titleRes));
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, @StringRes int titleRes) {
        return addSubMenu(groupId, itemId, order, resources.getString(titleRes));
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeItem(int id) {
        ActionMenuItem itemToRemove = null;
        for (ActionMenuItem menuItem : menuItems) {
            if (menuItem.getItemId() == id) {
                itemToRemove = menuItem;
                break;
            }
        }
        menuItems.remove(itemToRemove);
    }

    @Override
    public void removeGroup(int groupId) {
        List<ActionMenuItem> itemsToRemove = new ArrayList<>();
        for (ActionMenuItem menuItem : menuItems) {
            if (menuItem.getGroupId() == groupId) {
                itemsToRemove.add(menuItem);
            }
        }
        menuItems.removeAll(itemsToRemove);
    }

    @Override
    public void clear() {
        menuItems.clear();
    }

    @Override
    public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGroupVisible(int group, boolean visible) {
        for (ActionMenuItem menuItem : menuItems) {
            if (menuItem.getGroupId() == group) {
                menuItem.setVisible(visible);
            }
        }
    }

    @Override
    public void setGroupEnabled(int group, boolean enabled) {
        for (ActionMenuItem menuItem : menuItems) {
            if (menuItem.getGroupId() == group) {
                menuItem.setEnabled(enabled);
            }
        }
    }

    @Override
    public boolean hasVisibleItems() {
        for (ActionMenuItem menuItem : menuItems) {
            if (menuItem.isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public ActionMenuItem findItem(int id) {
        for (ActionMenuItem menuItem : menuItems) {
            if (menuItem.getItemId() == id) {
                return menuItem;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return menuItems.size();
    }

    @Override
    public ActionMenuItem getItem(int index) {
        return menuItems.get(index);
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean performIdentifierAction(int id, int flags) {
        ActionMenuItem menuItem = findItem(id);
        return menuItem != null && menuItem.invokeAction();
    }

    @Override
    public void setQwertyMode(boolean isQwerty) {
        throw new UnsupportedOperationException();
    }
}
