package com.emcsthai.pzcalendarview.View.CustomViewGroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.emcsthai.pzcalendarview.R;
import com.emcsthai.pzcalendarview.View.CustomViewGroup.Template.BaseCustomViewGroup;
import com.emcsthai.pzcalendarview.View.CustomViewGroup.Template.State.BundleSavedState;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PzCalendarView extends BaseCustomViewGroup {

    private RecyclerView recyclerView;

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
        recyclerView = (RecyclerView) findViewById(R.id.pz_calendar_view_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Calendar> calendarArrayList = get12MonthOfYear();

        PzCalendarViewRecyclerViewListAdapter adapter = new PzCalendarViewRecyclerViewListAdapter(calendarArrayList);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<Calendar> get12MonthOfYear() {

        ArrayList<Calendar> calendarArrayList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, i);
            calendarArrayList.add(calendar);
        }

        return calendarArrayList;
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
