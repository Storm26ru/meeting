package com.storm.meeting;


import androidx.databinding.ObservableField;

import java.io.Serializable;

public class Meeting implements Serializable {
    public final ObservableField<String> date = new ObservableField<>();
    public final ObservableField<String> time= new ObservableField<>();

    public boolean checkField(){
        return (date.get() == null | time.get() == null);
    }
}