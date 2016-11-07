package com.novoda.accessibility.demo.custom_actions;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novoda.accessibility.AccessibilityServices;
import com.novoda.accessibility.Action;
import com.novoda.accessibility.Actions;
import com.novoda.accessibility.ActionsAccessibilityDelegate;
import com.novoda.accessibility.ActionsAlertDialogCreator;
import com.novoda.accessibility.demo.R;

import java.util.Arrays;

public class TweetView extends LinearLayout {

    private final ActionsAlertDialogCreator actionsAlertDialogCreator;

    private TextView tweetTextView;
    private View replyButton;
    private View retweetButton;
    private AccessibilityServices services;

    public TweetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        actionsAlertDialogCreator = new ActionsAlertDialogCreator(context, R.string.tweet_actions_title);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        services = AccessibilityServices.newInstance(getContext());

        View.inflate(getContext(), R.layout.merge_tweet, this);
        tweetTextView = (TextView) findViewById(R.id.tweet_text);
        replyButton = findViewById(R.id.tweet_button_reply);
        retweetButton = findViewById(R.id.tweet_button_retweet);

    }

    public void display(final String tweet, final Listener listener) {
        Actions actions = createActions(tweet, listener);
        ActionsAccessibilityDelegate delegate = new ActionsAccessibilityDelegate(getResources(), actions);
        delegate.setClickLabel(R.string.tweet_actions_usage_hint);
        ViewCompat.setAccessibilityDelegate(this, delegate);

        tweetTextView.setText(tweet);

        if (services.isSpokenFeedbackEnabled()) {
            setClickListenerToShowDialogFor(actions);
        } else {
            setIndividualClickListeners(tweet, listener);
        }
    }

    private void setClickListenerToShowDialogFor(final Actions actions) {
        setButtonsAsClickableFalseToFixBehaviorChangeOnLollipopPlus();

        setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogFor(actions);
                    }
                }
        );
    }

    private void setButtonsAsClickableFalseToFixBehaviorChangeOnLollipopPlus() {
        // https://code.google.com/p/android/issues/detail?id=205431
        replyButton.setClickable(false);
        retweetButton.setClickable(false);
    }

    private void setIndividualClickListeners(final String tweet, final Listener listener) {
        setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(tweet);
                    }
                }
        );

        replyButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickReply(tweet);
                    }
                }
        );

        retweetButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickRetweet(tweet);
                    }
                }
        );
    }

    private Actions createActions(final String tweet, final Listener listener) {
        return new Actions(
                Arrays.asList(
                        new Action(
                                R.id.tweet_action_open, R.string.tweet_action_open, new Runnable() {
                            @Override
                            public void run() {
                                listener.onClick(tweet);
                            }
                        }),
                        new Action(
                                R.id.tweet_action_reply, R.string.tweet_action_reply, new Runnable() {
                            @Override
                            public void run() {
                                listener.onClickReply(tweet);
                            }
                        }),
                        new Action(
                                R.id.tweet_action_retweet, R.string.tweet_action_retweet, new Runnable() {
                            @Override
                            public void run() {
                                listener.onClickRetweet(tweet);
                            }
                        })
                )
        );
    }

    private void showAlertDialogFor(Actions actions) {
        AlertDialog dialog = actionsAlertDialogCreator.create(actions);
        dialog.show();
    }

    public interface Listener {

        void onClick(String tweet);

        void onClickReply(String tweet);

        void onClickRetweet(String tweet);

    }

}
