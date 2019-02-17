package com.novoda.accessibility;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

class ActionMenuItem implements MenuItem {

    private static final String TAG = "accessibilitools";

    private final Resources resources;
    private final int id;

    private CharSequence title;
    private boolean enabled;
    @Nullable
    private OnMenuItemClickListener menuItemClickListener;

    ActionMenuItem(Resources resources, int id, CharSequence title) {
        this.resources = resources;
        this.id = id;
        this.title = title;
    }

    @Override
    public int getItemId() {
        return id;
    }

    @Override
    public int getGroupId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getOrder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    public ActionMenuItem setTitle(@StringRes int title) {
        setTitle(resources.getString(title));
        return this;
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public ActionMenuItem setTitleCondensed(@Nullable CharSequence title) {
        if (title != null) {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    @Override
    public CharSequence getTitleCondensed() {
        return title;
    }

    @Override
    public ActionMenuItem setIcon(Drawable icon) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setIcon(int iconRes) {
        if (iconRes != 0) {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    @Override
    public Drawable getIcon() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setIntent(Intent intent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Intent getIntent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setShortcut(char numericChar, char alphaChar) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setNumericShortcut(char numericChar) {
        Log.w(TAG, "setNumericShortcut(char) is not supported but is called by MenuInflater");
        return this;
    }

    @Override
    public char getNumericShortcut() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setAlphabeticShortcut(char alphaChar) {
        Log.w(TAG, "setAlphabeticShortcut(char) is not supported but is called by MenuInflater");
        return this;
    }

    @Override
    public char getAlphabeticShortcut() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setCheckable(boolean checkable) {
        if (checkable) {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    @Override
    public boolean isCheckable() {
        return false;
    }

    @Override
    public ActionMenuItem setChecked(boolean checked) {
        if (checked) {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public ActionMenuItem setVisible(boolean visible) {
        if (!visible) {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    /**
     * For our case, a menu item will always/only be visible if enabled.
     */
    @Override
    public boolean isVisible() {
        return isEnabled();
    }

    @Override
    public ActionMenuItem setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean hasSubMenu() {
        return false;
    }

    @Nullable
    @Override
    public SubMenu getSubMenu() {
        return null;
    }

    @Override
    public ActionMenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
        return this;
    }

    @Nullable
    @Override
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override
    public void setShowAsAction(int actionEnum) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setShowAsActionFlags(int actionEnum) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setActionView(View view) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setActionView(int resId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public View getActionView() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public ActionProvider getActionProvider() {
        return null;
    }

    @Override
    public boolean expandActionView() {
        return false;
    }

    @Override
    public boolean collapseActionView() {
        return false;
    }

    @Override
    public boolean isActionViewExpanded() {
        return false;
    }

    @Override
    public ActionMenuItem setOnActionExpandListener(OnActionExpandListener listener) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns true if action was invoked, else false.
     */
    boolean invokeAction() {
        return isEnabled() && menuItemClickListener != null && menuItemClickListener.onMenuItemClick(this);
    }
}
