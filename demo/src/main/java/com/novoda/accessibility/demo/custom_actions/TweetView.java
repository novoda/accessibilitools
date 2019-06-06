package com.novoda.accessibility.demo.custom_actions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;

import com.novoda.accessibility.AccessibilityServices;
import com.novoda.accessibility.ActionsAlertDialogCreator;
import com.novoda.accessibility.ActionsMenuAccessibilityDelegate;
import com.novoda.accessibility.ActionsMenuAlertDialog;
import com.novoda.accessibility.ActionsMenuInflater;
import com.novoda.accessibility.UsageHints;
import com.novoda.accessibility.UsageHintsAccessibilityDelegate;
import com.novoda.accessibility.demo.R;

public class TweetView extends LinearLayout {

    private TextView tweetTextView;
    private View replyButton;
    private View retweetButton;

    private ActionsMenuInflater actionsMenuInflater;
    private AccessibilityServices services;

    public TweetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        actionsMenuInflater = ActionsMenuInflater.from(getContext());
        services = AccessibilityServices.newInstance(getContext());

        View.inflate(getContext(), R.layout.merge_tweet, this);
        tweetTextView = findViewById(R.id.tweet_text);
        replyButton = findViewById(R.id.tweet_button_reply);
        retweetButton = findViewById(R.id.tweet_button_retweet);
    }

    public void display(String tweet, Listener listener) {
        MenuItem.OnMenuItemClickListener menuItemClickListener = createMenuItemClickListener(tweet, listener);
        Menu menu = actionsMenuInflater.inflate(R.menu.tweet_actions, menuItemClickListener);
        UsageHints usageHints = new UsageHints(getResources());
        usageHints.setClickLabel(R.string.tweet_actions_usage_hint);

        AccessibilityDelegateCompat delegate = new ActionsMenuAccessibilityDelegate(menu, menuItemClickListener, usageHints);
        ViewCompat.setAccessibilityDelegate(this, delegate);

        setContentDescription(tweet);
        tweetTextView.setText(tweet);

        setOnClickListener(v -> {
            if (services.isSpokenFeedbackEnabled()) {
                AlertDialog alertDialog = ActionsMenuAlertDialog.create(getContext(), menu, menuItemClickListener).create();
                alertDialog.show();
            } else {
                listener.onClick(tweet);
            }
        });

        if (services.isSpokenFeedbackEnabled()) {
            setButtonsAsClickableFalseToFixBehaviorChangeOnLollipopPlus();
        } else {
            replyButton.setOnClickListener(v -> listener.onClickReply(tweet));
            retweetButton.setOnClickListener(v -> listener.onClickRetweet(tweet));
        }
    }

    private MenuItem.OnMenuItemClickListener createMenuItemClickListener(String tweet, Listener listener) {
        return (MenuItem item) -> {
            switch (item.getItemId()) {
                case R.id.tweet_action_open:
                    listener.onClick(tweet);
                    return true;
                case R.id.tweet_action_reply:
                    listener.onClickReply(tweet);
                    return true;
                case R.id.tweet_action_retweet:
                    listener.onClickRetweet(tweet);
                    return true;
                default:
                    throw new RuntimeException("Unhandled action: " + item.getTitle());
            }
        };
    }

    public void displaySimpler(String tweet, Listener listener) {
        /* begin region: stuff you can do in all cases */
        UsageHintsAccessibilityDelegate accessibilityDelegate = new UsageHintsAccessibilityDelegate(getResources());
        accessibilityDelegate.setClickLabel(R.string.tweet_actions_usage_hint);
        ViewCompat.setAccessibilityDelegate(this, accessibilityDelegate);

        setContentDescription(tweet);
        tweetTextView.setText(tweet);

        CharSequence openLabel = getResources().getString(R.string.tweet_action_open);
        ViewCompat.addAccessibilityAction(this, openLabel, (view, arguments) -> {
            listener.onClick(tweet);
            return true;
        });

        CharSequence replyLabel = getResources().getString(R.string.tweet_action_reply);
        ViewCompat.addAccessibilityAction(this, replyLabel, (view, arguments) -> {
            listener.onClickReply(tweet);
            return true;
        });

        CharSequence retweetLabel = getResources().getString(R.string.tweet_action_retweet);
        ViewCompat.addAccessibilityAction(this, retweetLabel, (view, arguments) -> {
            listener.onClickRetweet(tweet);
            return true;
        });
        /* end region: stuff you can do in all cases */

        setOnClickListener(v -> {
//            if (services.isSpokenFeedbackEnabled()) {
                // this needs to be done _after_ the accessibility actions have been set on this View
                AlertDialog alertDialog = new ActionsAlertDialogCreator(getContext()).create(this);
                alertDialog.show();
//            } else {
//                listener.onClick(tweet);
//            }
        });

//        if (services.isSpokenFeedbackEnabled()) {
            setButtonsAsClickableFalseToFixBehaviorChangeOnLollipopPlus();
//        } else {
//            replyButton.setOnClickListener(v -> listener.onClickReply(tweet));
//            retweetButton.setOnClickListener(v -> listener.onClickRetweet(tweet));
//        }
    }

    private void setButtonsAsClickableFalseToFixBehaviorChangeOnLollipopPlus() {
        // https://code.google.com/p/android/issues/detail?id=205431
        replyButton.setClickable(false);
        retweetButton.setClickable(false);
    }

    public interface Listener {

        void onClick(String tweet);

        void onClickReply(String tweet);

        void onClickRetweet(String tweet);
    }
}
