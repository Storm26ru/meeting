package com.storm.meeting;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.storm.meeting.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Calendar dateAndTime=Calendar.getInstance();
    Meeting meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        meeting = new Meeting();
        binding.setMeeting(meeting);
        binding.btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this,d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        binding.btTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(MainActivity.this,t,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE),true).show();
            }
        });
        binding.btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                if(meeting.checkField()){
                    builder.setTitle("Предупреждение ");
                    builder.setMessage(meeting.date.get()==null ? "Не выбрана дата встречи" :
                            "Не выбрано время встречи");
                    builder.setPositiveButton("OK",(dialog,which)->dialog.dismiss());
                }else{
                    builder.setTitle("Встреча");
                    builder.setMessage("Создать встречу "+meeting.date.get()+" в "+meeting.time.get()+" ?");
                    builder.setPositiveButton("Да",(dialog,which)->{
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra(Meeting.class.getSimpleName(),meeting);
                        startActivity(intent);
                    });
                    builder.setNegativeButton("Нет",(dialog,which)->dialog.dismiss());
                }
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        binding.btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(meeting.checkField()){
                    binding.btConfirm.callOnClick();
                }else{
                String text = "Встреча назначена на "+meeting.date.get()+" в "+meeting.time.get();
                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(intent,"Отправить через"));
                }
            }
        });

    }
    private void setInitialDate(){
        meeting.date.set(DateUtils.formatDateTime(this,dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_SHOW_YEAR));
    }
    private void setInitialTime(){
        meeting.time.set(DateUtils.formatDateTime(this,dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR,year);
            dateAndTime.set(Calendar.MONTH,monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            setInitialDate();
        }
    };
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            dateAndTime.set(Calendar.MINUTE,minute);
            setInitialTime();
        }
    };
}
