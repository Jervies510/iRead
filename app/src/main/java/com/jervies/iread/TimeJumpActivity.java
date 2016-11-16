package com.jervies.iread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 该类用于实现欢迎页面的定时跳转
 */
public class TimeJumpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_jump);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(TimeJumpActivity.this,WelcomeActivity.class));
                finish();
            }
        };
        timer.schedule(timerTask,2000);
    }
}
