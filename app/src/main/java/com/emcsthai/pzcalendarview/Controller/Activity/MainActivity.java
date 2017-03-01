package com.emcsthai.pzcalendarview.Controller.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.emcsthai.pzcalendarview.Controller.Fragment.CalendarFragment;
import com.emcsthai.pzcalendarview.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container,
                            CalendarFragment.newInstance(),
                            "CalendarFragment")
                    .commit();
        }
    }
}
