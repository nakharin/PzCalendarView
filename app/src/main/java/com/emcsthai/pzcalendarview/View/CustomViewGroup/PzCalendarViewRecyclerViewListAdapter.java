package com.emcsthai.pzcalendarview.View.CustomViewGroup;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emcsthai.pzcalendarview.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by nakarin on 2/28/2017 AD.
 */

public class PzCalendarViewRecyclerViewListAdapter extends RecyclerView.Adapter<PzCalendarViewRecyclerViewListAdapter.RecyclerViewHolder> {

    private static final String TAG = "PzCalendarViewRecyclerV";

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitleMonth;
        RecyclerView rcvGridMonth;

        public RecyclerViewHolder(View view) {
            super(view);
            txtTitleMonth = (TextView) view.findViewById(R.id.pz_calendar_view_text_view_month);
            rcvGridMonth = (RecyclerView) view.findViewById(R.id.pz_calendar_view_recycler_view_grid);
        }
    }

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;
    private static final int MAX_DAY_COLUMN = 7;

    private Context mContext;

    private List<Calendar> calendarList;

    public PzCalendarViewRecyclerViewListAdapter(List<Calendar> calendarList) {
        this.calendarList = calendarList;
    }

    @Override
    public PzCalendarViewRecyclerViewListAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pz_layout_calendar_view_recycler_view_list, parent, false);
        mContext = parent.getContext();
        return new PzCalendarViewRecyclerViewListAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PzCalendarViewRecyclerViewListAdapter.RecyclerViewHolder holder, int position) {

        final Calendar calendar = getItem(position);

        String Month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        Log.i(TAG, "Month [" + position + "] : " + Month);

        // Method from this class
        updateTitleCalendar(holder, calendar);

        // Method from this class
        updateGridCalendar(holder, calendar);
    }

    @Override
    public int getItemCount() {
        if (calendarList == null) {
            return 0;
        }
        return calendarList.size();
    }

    public Calendar getItem(int position) {
        return calendarList.get(position);
    }

    private void updateTitleCalendar(RecyclerViewHolder holder, Calendar calendar) {
        String Month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        int Year = calendar.get(Calendar.YEAR);
        holder.txtTitleMonth.setText(String.format("%s %s", Month, Year));
    }

    private void updateGridCalendar(RecyclerViewHolder holder, Calendar calendar) {

        ArrayList<Date> arrayListDate = new ArrayList<>();
        HashSet<Date> eventDays = new HashSet<>();

        // determine the cell for current month's beginning
        int firstDayOfTheMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);

        // fill cells (42 days calendar as per our business logic)
        while (arrayListDate.size() < DAYS_COUNT) {
            arrayListDate.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, MAX_DAY_COLUMN);
        holder.rcvGridMonth.setHasFixedSize(true);
        holder.rcvGridMonth.setLayoutManager(layoutManager);

        PzCalendarViewRecyclerViewGridAdapter adapter = new PzCalendarViewRecyclerViewGridAdapter(arrayListDate, eventDays);
        holder.rcvGridMonth.setAdapter(adapter);
    }
}
