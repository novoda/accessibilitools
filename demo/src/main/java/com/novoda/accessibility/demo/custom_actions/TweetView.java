package com.novoda.accessibility.demo.custom_actions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;

import com.novoda.accessibility.AccessibilitoolsViewCompat;
import com.novoda.accessibility.AccessibilityActionsAlertDialog;
import com.novoda.accessibility.AccessibilityServices;
import com.novoda.accessibility.UsageHintsAccessibilityDelegate;
import com.novoda.accessibility.demo.R;

public class TweetView extends LinearLayout {

    private TextView tweetTextView;
    private View replyButton;
    private View retweetButton;

    private AccessibilityServices services;

    public TweetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        services = AccessibilityServices.newInstance(getContext());

        View.inflate(getContext(), R.layout.merge_tweet, this);
        tweetTextView = findViewById(R.id.tweet_text);
        replyButton = findViewById(R.id.tweet_button_reply);
        retweetButton = findViewById(R.id.tweet_button_retweet);
    }

    public void display(String tweet, Listener listener) {
        tweetTextView.setText(tweet);
        setContentDescription(tweet);

        replyButton.setOnClickListener(v -> listener.onClickReply(tweet));
        retweetButton.setOnClickListener(v -> listener.onClickRetweet(tweet));

        // set Accessibility Actions ("on this View, you can do these things")
        CharSequence openLabel = getResources().getString(R.string.tweet_action_open);
        CharSequence replyLabel = getResources().getString(R.string.tweet_action_reply);
        CharSequence retweetLabel = getResources().getString(R.string.tweet_action_retweet);

        // this is to avoid stacking actions
        AccessibilitoolsViewCompat.removeAccessibilityAction(this, openLabel, replyLabel, retweetLabel);

        ViewCompat.addAccessibilityAction(this, openLabel, (view, arguments) -> {
            listener.onClick(tweet);
            return true;
        });

        ViewCompat.addAccessibilityAction(this, replyLabel, (view, arguments) -> {
            listener.onClickReply(tweet);
            return true;
        });

        ViewCompat.addAccessibilityAction(this, retweetLabel, (view, arguments) -> {
            listener.onClickRetweet(tweet);
            return true;
        });

        UsageHintsAccessibilityDelegate delegate = new UsageHintsAccessibilityDelegate(getResources());
        // what will happen when you click on this TweetView? We're going to open an alert dialog showing all actions.
        delegate.setClickLabel(R.string.tweet_actions_usage_hint);
        ViewCompat.setAccessibilityDelegate(this, delegate);

        if (services.isSpokenFeedbackEnabled()) {
            AlertDialog alertDialog = AccessibilityActionsAlertDialog.createFrom(this);
            setOnClickListener(v -> alertDialog.show());

            replyButton.setVisibility(GONE);
            retweetButton.setVisibility(GONE);
        } else {
            setOnClickListener(v -> listener.onClick(tweet));

            replyButton.setVisibility(VISIBLE);
            retweetButton.setVisibility(VISIBLE);
        }
    }

    public interface Listener {

        void onClick(String tweet);

        void onClickReply(String tweet);

        void onClickRetweet(String tweet);
    }
}
