package com.storm.meeting;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView twResult;
    Meeting meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        twResult = findViewById(R.id.twResult);
        if((savedInstanceState = getIntent().getExtras())!=null){
            meeting = (Meeting) savedInstanceState.getSerializable(Meeting.class.getSimpleName());
            twResult.setText("Дата: "+meeting.date.get()+" Время :"+meeting.time.get());
        }


    }
}