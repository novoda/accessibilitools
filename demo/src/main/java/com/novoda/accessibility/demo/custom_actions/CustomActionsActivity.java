package com.novoda.accessibility.demo.custom_actions;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.novoda.accessibility.demo.R;

public class CustomActionsActivity extends AppCompatActivity {

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_actions);

        TweetView menuActionsTweetView = findViewById(R.id.tweetMenuActions);
        menuActionsTweetView.display(
                "Tweet view setup with Menu-based actions",
                new TweetView.Listener() {

                    @Override
                    public void onClick(String tweet) {
                        toast("click: " + tweet);
                    }

                    @Override
                    public void onClickReply(String tweet) {
                        toast("reply: " + tweet);
                    }

                    @Override
                    public void onClickRetweet(String tweet) {
                        toast("rt: " + tweet);
                    }

                }
        );

        TweetView viewCompatTweetView = findViewById(R.id.tweetViewCompatActions);
        viewCompatTweetView.displaySimpler(
                "Tweet view setup with ViewCompat",
                new TweetView.Listener() {

                    @Override
                    public void onClick(String tweet) {
                        toast("click: " + tweet);
                    }

                    @Override
                    public void onClickReply(String tweet) {
                        toast("reply: " + tweet);
                    }

                    @Override
                    public void onClickRetweet(String tweet) {
                        toast("rt: " + tweet);
                    }

                }
        );
    }

    private void toast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
