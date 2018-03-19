package com.wrist_trainer.bivsa.wristtrainer.Components;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wrist_trainer.bivsa.wristtrainer.R;

public class LoadActivity extends AppCompatActivity {

    private CountDownTimer timer = new CountDownTimer(5000,5000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(LoadActivity.this, MainActivity.class);
            startActivity(intent);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        timer.start();
    }
}
