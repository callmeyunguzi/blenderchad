package com.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button playButton;
    private TextView textTime;
    private SeekBar seekBar;
    private boolean isTimerOn;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.button);
        textTime = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        isTimerOn = false;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                long progressMillis = progress * 1000;
                updateTimer(progressMillis);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //textTime.setText(String.valueOf(seekBar.getProgress()));

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //textTime.setText(String.valueOf(seekBar.getProgress()));

            }
        });

    }

    public void start(View view) {

        if(!isTimerOn) {
            playButton.setText("Stop");
            seekBar.setEnabled(false);
            isTimerOn = true;

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer(millisUntilFinished);

                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),
                            R.raw.bell_sound);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
        } else {
            resetTimer();


        }

    }

    private void updateTimer(long millisUntilFinished) {
        int minutes = (int) millisUntilFinished/1000/60;
        int seconds = (int) millisUntilFinished/1000 - (minutes * 60);
        if(minutes <= 9 && seconds <= 9){
            textTime.setText("0"+minutes+":0"+seconds);
        }else if(minutes <= 9 && seconds > 9){
            textTime.setText("0"+minutes+":"+seconds);
        }else{
            textTime.setText(minutes+":0"+seconds);
        }

    }

    private void resetTimer() {
        countDownTimer.cancel();
        textTime.setText("00:30");
        playButton.setText("Start");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        isTimerOn = false;
    }
}