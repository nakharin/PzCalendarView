package com.emcsthai.pzcalendarview.View.CustomViewGroup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emcsthai.pzcalendarview.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by nakarin on 2/27/2017 AD.
 */

public class PzCalendarViewAdapter extends ArrayAdapter<Date> {
    // days with events
    private HashSet<Date> eventDays;

    // for view inflation
    private LayoutInflater inflater;

    public PzCalendarViewAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
        super(context, R.layout.pz_layout_calendar_view_day, days);
        this.eventDays = eventDays;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        // day in question
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();

        // today
        Date today = new Date();

        // inflate item if it does not exist yet
        if (view == null)
            view = inflater.inflate(R.layout.pz_layout_calendar_view_day, parent, false);

        // if this day has an event, specify event image
        view.setBackgroundResource(0);
        if (eventDays != null) {
            for (Date eventDate : eventDays) {
                if (eventDate.getDate() == day &&
                        eventDate.getMonth() == month &&
                        eventDate.getYear() == year) {
                    // mark this day for event
//                        view.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.colorPrimary));
                    break;
                }
            }
        }

        // clear styling
        ((TextView) view).setTypeface(null, Typeface.NORMAL);
        ((TextView) view).setTextColor(Color.WHITE);

        if (month != today.getMonth() || year != today.getYear()) {
            // if this day is outside current month, grey it out
            ((TextView) view).setTextColor(ContextCompat.getColor(parent.getContext(), R.color.colorEmpty));
        } else if (day == today.getDate()) {
            // if it is today, set it to blue/bold
            ((TextView) view).setTypeface(null, Typeface.BOLD);
            ((TextView) view).setTextColor(Color.WHITE);
            ((TextView) view).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_day_selected));
        }

        // set text
        ((TextView) view).setText(String.valueOf(date.getDate()));

        return view;
    }
}