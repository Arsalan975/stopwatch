// MainActivity.java
package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView timerDisplay;
    private Button startButton, pauseButton, resetButton;
    private Handler handler = new Handler();
    private long startTime = 0L;
    private boolean isRunning = false;
    private long timeElapsed = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerDisplay = findViewById(R.id.timerDisplay);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis() - timeElapsed;
                    handler.post(updateTimer);
                    isRunning = true;
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    handler.removeCallbacks(updateTimer);
                    timeElapsed = System.currentTimeMillis() - startTime;
                    isRunning = false;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimer);
                isRunning = false;
                startTime = 0L;
                timeElapsed = 0L;
                timerDisplay.setText("00:00:000");
            }
        });
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeElapsed = System.currentTimeMillis() - startTime;
            int minutes = (int) (timeElapsed / 60000);
            int seconds = (int) (timeElapsed / 1000) % 60;
            int milliseconds = (int) (timeElapsed % 1000);
            timerDisplay.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));
            handler.postDelayed(this, 10);
        }
    };
}
