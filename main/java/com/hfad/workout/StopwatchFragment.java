package com.hfad.workout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Locale;
import android.widget.Button;

public class StopwatchFragment extends Fragment implements View.OnClickListener {
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("секунд");
            running = savedInstanceState.getBoolean("работает");
            wasRunning = savedInstanceState.getBoolean("не работает");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        runTimer(layout);
        Button startButton = (Button)layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        Button stopButton = (Button)layout.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        Button resetButton = (Button)layout.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        return layout;
    }
    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("секунд", seconds);
        savedInstanceState.putBoolean("работает", running);
        savedInstanceState.putBoolean("не работает", wasRunning);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                onClickStart();
                break;
            case R.id.stop_button:
                onClickStop();
                break;
            case R.id.reset_button:
                onClickReset();
                break;
        }
    }

    private void onClickStart() {
        running = true;
    }
    private void onClickStop() {
        running = false;
    }
    private void onClickReset() {
        running = false;
        seconds = 0;
    }

    private void runTimer(View view) {
        final TextView timeView = (TextView) view.findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}