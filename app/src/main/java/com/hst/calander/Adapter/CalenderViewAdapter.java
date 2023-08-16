package com.hst.calander.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.hst.calander.R;

import java.text.SimpleDateFormat;
import java.util.List;

import com.hst.calander.Model.CalenderViewModel;


public class CalenderViewAdapter extends RecyclerView.Adapter<CalenderViewAdapter.AlbumViewHolder> {
    private Context context;
    private List<CalenderViewModel> list;

    public CalenderViewAdapter(Context context, List<CalenderViewModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AlbumViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_calender, (ViewGroup) null, false));
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder albumViewHolder, int i) {
        if (i == 0) {
            albumViewHolder.tvDayName.setText(getDayName(this.list.get(i).getDate()));
            albumViewHolder.tvDayMonth.setText(getDayMonth(this.list.get(i).getDate()));
        }
        if (this.list.get(i).isNote()) {
            albumViewHolder.tvDescription.setVisibility(View.VISIBLE);
            albumViewHolder.tvTime.setVisibility(View.VISIBLE);
            albumViewHolder.tvTitle.setText(this.list.get(i).getTitle());
            albumViewHolder.tvDescription.setText(this.list.get(i).getDescription());
            albumViewHolder.tvTime.setText(this.list.get(i).getTime());
            albumViewHolder.backColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e4ecf0")));
            return;
        }
        albumViewHolder.tvDescription.setVisibility(View.GONE);
        albumViewHolder.tvTime.setVisibility(View.GONE);
        albumViewHolder.tvTitle.setText(this.list.get(i).getTitle());
        albumViewHolder.backColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f2eee7")));
    }

    public String getDayName(String str) {
        try {
            try {
                return new SimpleDateFormat("EEEE").format(new SimpleDateFormat("dd-MM-yyyy").parse(str));
            } catch (Exception unused) {
                return new SimpleDateFormat("EEEE").format(new SimpleDateFormat("MMM d, yyyy").parse(str));
            }
        } catch (Exception unused2) {
            return "";
        }
    }

    public String getDayMonth(String str) {
        try {
            try {
                return new SimpleDateFormat("dd").format(new SimpleDateFormat("MMM d, yyyy").parse(str));
            } catch (Exception unused) {
                return new SimpleDateFormat("dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(str));
            }
        } catch (Exception unused2) {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout backColor;
        TextView tvDayMonth;
        TextView tvDayName;
        TextView tvDescription;
        TextView tvTime;
        TextView tvTitle;

        AlbumViewHolder(View view) {
            super(view);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            this.tvTime = (TextView) view.findViewById(R.id.tvTime);
            this.tvDayName = (TextView) view.findViewById(R.id.tvDayName);
            this.tvDayMonth = (TextView) view.findViewById(R.id.tvDayMonth);
            this.backColor = (LinearLayout) view.findViewById(R.id.backColor);
        }
    }
}
