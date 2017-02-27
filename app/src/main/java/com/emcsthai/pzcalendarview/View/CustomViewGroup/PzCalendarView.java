package com.emcsthai.pzcalendarview.View.CustomViewGroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.GridView;

import com.emcsthai.pzcalendarview.R;
import com.emcsthai.pzcalendarview.View.CustomViewGroup.Template.BaseCustomViewGroup;
import com.emcsthai.pzcalendarview.View.CustomViewGroup.Template.State.BundleSavedState;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PzCalendarView extends BaseCustomViewGroup {

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();

    private GridView calendarGridView;

    public PzCalendarView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public PzCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public PzCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public PzCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.pz_layout_calendar_view_group, this);
    }

    private void initInstances() {
        // findViewById here
        calendarGridView = (GridView) findViewById(R.id.pz_calendar_view_calendar_grid);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

                      btnText = a.getString(
                R.styleable.ButtonView_btn_text);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar() {
        updateCalendar(null);
    }

    private void updateCalendar(HashSet<Date> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) Calendar.getInstance().clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells (42 days calendar as per our business logic)
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        PzCalendarViewAdapter adapter = new PzCalendarViewAdapter(getContext(), cells, events);
        calendarGridView.setAdapter(adapter);

//        // update title
//        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
//        txtDate.setText(sdf.format(currentDate.getTime()));
    }

    /*******************************************************************
     * **************** Save & Restore InstanceState ********************
     *******************************************************************/

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }
}
