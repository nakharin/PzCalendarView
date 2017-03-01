package com.emcsthai.pzcalendarview.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.emcsthai.pzcalendarview.R;
import com.emcsthai.pzcalendarview.View.CustomViewGroup.PzCalendarView;

public class MainActivity extends AppCompatActivity {

    private PzCalendarView pzCalendarView;

//    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();
    }

    private void initInstance() {
        // findViewById Here

        pzCalendarView = (PzCalendarView) findViewById(R.id.pz_calendar_view);
//        pzCalendarView.updateCalendar();

//        dayPickerView = (DayPickerView) findViewById(R.id.day_picker_view);
    }
}
