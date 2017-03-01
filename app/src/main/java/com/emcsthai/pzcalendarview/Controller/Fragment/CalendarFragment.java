package com.emcsthai.pzcalendarview.Controller.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CalendarFragment extends Fragment {

    private static final String TAG = "CalendarFragment";

    private SectionedRecyclerViewAdapter sectionAdapter;

    public CalendarFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        sectionAdapter = new SectionedRecyclerViewAdapter();

        List<Calendar> calendarList = get12MonthOfYear();

        for (int i = 0; i < calendarList.size(); i++) {

            Calendar calendar = calendarList.get(i);

            String Month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
            int Year = calendar.get(GregorianCalendar.YEAR);
            String title = String.format("%s %s", Month, Year);

            List<Date> dateList = new ArrayList<>();
            List<Calendar> calendarListChild = new ArrayList<>();

            // determine the cell for current month's beginning
            int firstDayOfTheMonth = calendarList.get(i).get(Calendar.DAY_OF_WEEK) - 1;

            int monthMaxDays = calendarList.get(i).getActualMaximum(Calendar.DAY_OF_MONTH);

            int COUNT_ROWS = (monthMaxDays + firstDayOfTheMonth) / 7 + 1;

            int DAYS_COUNT = COUNT_ROWS * 7;

            // move calendar backwards to the beginning of the week
            calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);

            while (dateList.size() < DAYS_COUNT) {
                calendarListChild.add(calendar);
                dateList.add(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            MonthSection monthSection = new MonthSection(title, calendarListChild, dateList);
            sectionAdapter.addSection(monthSection);
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 7);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 7;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(sectionAdapter);
    }

    public List<Calendar> get12MonthOfYear() {

        List<Calendar> calendarList = new ArrayList<>();

//        for (int i = 0; i < 12; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 9);
            calendarList.add(calendar);
//        }

        return calendarList;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    class HeaderMonthHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView tvMonthOfYear;

        public HeaderMonthHolder(View view) {
            super(view);
            rootView = view;
            tvMonthOfYear = (TextView) view.findViewById(R.id.tv_month_of_year);
        }
    }

    class ItemDayHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView tvDayOfMonth;

        public ItemDayHolder(View view) {
            super(view);
            rootView = view;
            tvDayOfMonth = (TextView) view.findViewById(R.id.tv_day_of_month);
        }
    }

    public class MonthSection extends StatelessSection {

        private String title;
        private List<Calendar> calendarList;
        private List<Date> dateList;

        public MonthSection(String title, List<Calendar> calendarList, List<Date> dateList) {
            super(R.layout.section_month_of_year_header, R.layout.section_day_of_month_item);
            this.title = title;
            this.calendarList = calendarList;
            this.dateList = dateList;
        }

        @Override
        public int getContentItemsTotal() {
            return calendarList.size();
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderMonthHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderMonthHolder headerHolder = (HeaderMonthHolder) holder;
            headerHolder.tvMonthOfYear.setText(title);
            headerHolder.tvMonthOfYear.setTextColor(Color.WHITE);
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemDayHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemDayHolder itemHolder = (ItemDayHolder) holder;

            // day in question
            Calendar calendar = calendarList.get(position);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            Date date = dateList.get(position);
            Calendar dateCal = Calendar.getInstance();
            dateCal.setTime(date);

            int day = dateCal.get(Calendar.DAY_OF_MONTH);
            int cMonth = dateCal.get(Calendar.MONTH) + 1;
            int cYear = dateCal.get(Calendar.YEAR);

            // clear styling
            itemHolder.tvDayOfMonth.setTypeface(null, Typeface.NORMAL);
            itemHolder.tvDayOfMonth.setTextColor(Color.WHITE);

            Log.i(TAG, "month : " + month);
            Log.i(TAG, "cMonth : " + cMonth);

            Log.d(TAG, "year : " + year);
            Log.d(TAG, "cYear : " + cYear);

            if (month == cMonth && year == cYear) {
                itemHolder.tvDayOfMonth.setTypeface(null, Typeface.BOLD);
                itemHolder.tvDayOfMonth.setTextColor(Color.WHITE);
            } else {
                itemHolder.tvDayOfMonth.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            }

            itemHolder.tvDayOfMonth.setText(String.valueOf(date.getDate()));
        }
    }
}
