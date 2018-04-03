package com.novoda.accessibility.demo.custom_actions;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novoda.accessibility.AccessibilityServices;
import com.novoda.accessibility.ActionsMenuAccessibilityDelegate;
import com.novoda.accessibility.ActionsMenuAlertDialog;
import com.novoda.accessibility.ActionsMenuInflater;
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
        ActionsMenuAccessibilityDelegate delegate = new ActionsMenuAccessibilityDelegate(menu, menuItemClickListener);
//      TODO: add composable delegate - delegate.setClickLabel(R.string.tweet_actions_usage_hint);
        ViewCompat.setAccessibilityDelegate(this, delegate);

        tweetTextView.setText(tweet);

        if (services.isSpokenFeedbackEnabled()) {
            AlertDialog alertDialog = ActionsMenuAlertDialog.create(getContext(), menu, menuItemClickListener).create();
            setClickListenerToShow(alertDialog);
        } else {
            setIndividualClickListeners(tweet, listener);
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

    private void setClickListenerToShow(AlertDialog actionsDialog) {
        setButtonsAsClickableFalseToFixBehaviorChangeOnLollipopPlus();
        setOnClickListener(v -> actionsDialog.show());
    }

    private void setButtonsAsClickableFalseToFixBehaviorChangeOnLollipopPlus() {
        // https://code.google.com/p/android/issues/detail?id=205431
        replyButton.setClickable(false);
        retweetButton.setClickable(false);
    }

    private void setIndividualClickListeners(String tweet, Listener listener) {
        setOnClickListener(v -> listener.onClick(tweet));
        replyButton.setOnClickListener(v -> listener.onClickReply(tweet));
        retweetButton.setOnClickListener(v -> listener.onClickRetweet(tweet));
    }

    public interface Listener {

        void onClick(String tweet);

        void onClickReply(String tweet);

        void onClickRetweet(String tweet);
    }
}
