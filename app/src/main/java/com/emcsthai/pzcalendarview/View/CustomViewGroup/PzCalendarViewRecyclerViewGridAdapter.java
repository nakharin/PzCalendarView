package com.emcsthai.pzcalendarview.View.CustomViewGroup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.emcsthai.pzcalendarview.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by nakarin on 2/28/2017 AD.
 */

public class PzCalendarViewRecyclerViewGridAdapter extends RecyclerView.Adapter<PzCalendarViewRecyclerViewGridAdapter.RecyclerViewHolder> {


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        FrameLayout frameLayoutDay;
        TextView txtDay;

        public RecyclerViewHolder(View view) {
            super(view);
            frameLayoutDay = (FrameLayout) view.findViewById(R.id.pz_calendar_view_frame_layout_day);
            txtDay = (TextView) view.findViewById(R.id.pz_calendar_view_text_view_day);
        }
    }

    private Context mContext;
    private ArrayList<Date> arrayListDate;
    private HashSet<Date> eventDays;

    public PzCalendarViewRecyclerViewGridAdapter(ArrayList<Date> arrayListDate, HashSet<Date> eventDays) {
        this.arrayListDate = arrayListDate;
        this.eventDays = eventDays;
    }

    @Override
    public PzCalendarViewRecyclerViewGridAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pz_layout_calendar_view_recycler_view_grid, parent, false);

        mContext = parent.getContext();

        return new PzCalendarViewRecyclerViewGridAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PzCalendarViewRecyclerViewGridAdapter.RecyclerViewHolder holder, int position) {

        // day in question
        final Date date = getItem(position);
        int dayOfMonth = date.getDate();
        int monthOfYear = date.getMonth();
        int year = date.getYear();

        // today
        Date today = new Date();

        // if this day has an event, specify event image
        holder.frameLayoutDay.setBackgroundResource(0);
        if (eventDays != null) {
            for (Date eventDate : eventDays) {
                if (eventDate.getDate() == dayOfMonth &&
                        eventDate.getMonth() == monthOfYear &&
                        eventDate.getYear() == year) {
                    // mark this day for event
                    holder.frameLayoutDay.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                    break;
                }
            }
        }

        // clear styling
        holder.txtDay.setTypeface(null, Typeface.NORMAL);
        holder.txtDay.setTextColor(Color.WHITE);

        if (monthOfYear != today.getMonth() || year != today.getYear()) {
            // if this day is outside current month, grey it out
            holder.txtDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorEmpty));
        } else if (dayOfMonth == today.getDate()) {
            // if it is today, set it to blue/bold
            holder.txtDay.setTypeface(null, Typeface.BOLD);
            holder.txtDay.setTextColor(Color.WHITE);
            holder.txtDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_day_selected));
        }

        // set text
        holder.txtDay.setText(String.valueOf(date.getDate()));
    }

    @Override
    public int getItemCount() {
        return arrayListDate.size();
    }

    public Date getItem(int position) {
        return arrayListDate.get(position);
    }
}
