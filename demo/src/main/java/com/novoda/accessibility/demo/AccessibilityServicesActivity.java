package com.novoda.accessibility.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.novoda.accessibility.AccessibilityServices;

public class AccessibilityServicesActivity extends AppCompatActivity {

    private AccessibilityServices accessibilityServices;
    private TextView spokenFeedback;
    private TextView talkbackFeedback;
    private TextView switchAccessFeedback;
    private TextView selectToSpeakFeedback;
    private TextView closedCaptioningStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility_checker);

        accessibilityServices = AccessibilityServices.newInstance(this);
        spokenFeedback = findViewById(R.id.spoken_feedback_service_status);
        talkbackFeedback = findViewById(R.id.talkback_status);
        switchAccessFeedback = findViewById(R.id.switch_access_status);
        selectToSpeakFeedback = findViewById(R.id.select_to_speak_status);
        closedCaptioningStatus = findViewById(R.id.closedcaptioning_status);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (accessibilityServices.isSpokenFeedbackEnabled()) {
            spokenFeedback.setText("Spoken feedback is enabled");
        } else {
            spokenFeedback.setText("Spoken feedback is not enabled");
        }

        if (accessibilityServices.isTalkBackEnabled()) {
            talkbackFeedback.setText("TalkBack is enabled");
        } else {
            talkbackFeedback.setText("TalkBack is not enabled");
        }

        if (accessibilityServices.isSwitchAccessEnabled()) {
            switchAccessFeedback.setText("Switch Access is enabled");
        } else {
            switchAccessFeedback.setText("Switch Access is not enabled");
        }

        if (accessibilityServices.isSelectToSpeakEnabled()) {
            selectToSpeakFeedback.setText("Select To Speak is enabled");
        } else {
            selectToSpeakFeedback.setText("Select To Speak is not enabled");
        }

        if (accessibilityServices.isClosedCaptioningEnabled()) {
            closedCaptioningStatus.setText("Closed captioning is enabled");
        } else {
            closedCaptioningStatus.setText("Closed captioning is not enabled");
        }
    }
}
