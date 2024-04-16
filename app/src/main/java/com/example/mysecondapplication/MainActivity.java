package com.example.mysecondapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar m_mySeekBar;
    private ProgressBar m_myProgressBar;
    private TextView m_myProgressValueTv;
    private TextView m_resultTv;
    private int m_counter = 0;
    private Timer m_myTimer;
    private Handler m_handler;
    private SharedPreferences m_sharedPreferences;
    private TextView m_firstname;
    private TextView m_lastname;


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            m_myProgressValueTv.setText("Progress : " + String.valueOf(m_counter));
            m_myProgressBar.setProgress(m_counter);
            m_resultTv.setText(String.valueOf(m_counter));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_mySeekBar = findViewById(R.id.mySeekBar);
        m_myProgressBar = findViewById(R.id.myProgressBar);
        m_myProgressValueTv = findViewById(R.id.progressValue);
        m_resultTv = findViewById(R.id.resultTv);
        m_firstname = findViewById(R.id.firstname);
        m_lastname = findViewById(R.id.lastname);

        m_mySeekBar.setOnSeekBarChangeListener(this);
        m_handler = new MyHandler();
        m_sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String firstName = m_sharedPreferences.getString("firstname", "");
        String lastName = m_sharedPreferences.getString("lastname", "");

        m_firstname.setText(firstName);
        m_lastname.setText(lastName);

    }

    public void onApplyButtonClick(View view) {
        SharedPreferences.Editor editor = m_sharedPreferences.edit();
        editor.putString("firstName", m_firstname.getText().toString());
        editor.putString("lastName", m_lastname.getText().toString());
        editor.apply();
        Toast.makeText(this, "Valeurs sauvegardées avec succès", Toast.LENGTH_SHORT).show();
    }


    public void onClickStartToggleBt(View view) {
        ToggleButton myToggleBt = (ToggleButton) view;
        if (!myToggleBt.isChecked()) {
            m_myTimer = new Timer();
            m_myTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    m_counter ++;
                    m_handler.sendEmptyMessage(0);
                }
            }, 0, 1000);
        } else {
            m_myTimer.cancel();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        m_counter = progress;
        m_myProgressBar.setProgress(progress);
        m_myProgressValueTv.setText("Progress : " + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}