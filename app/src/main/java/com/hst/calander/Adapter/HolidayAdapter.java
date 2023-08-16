package com.hst.calander.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.hst.calander.Model.HolidayModel;
import com.hst.calander.R;


public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.AlbumViewHolder> {
    private Context context;
    boolean isInterNational;
    private List<HolidayModel> list;

    public HolidayAdapter(Context context, List<HolidayModel> list, boolean z) {
        this.list = list;
        this.context = context;
        this.isInterNational = z;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AlbumViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_holiday_layout, (ViewGroup) null, false));
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder albumViewHolder, int i) {
        if (i == 0) {
            albumViewHolder.tvYear.setVisibility(View.VISIBLE);
            albumViewHolder.tvYear.setText(this.list.get(i).getHeadingDate());
        } else if (!this.list.get(i).getHeadingDate().equals(this.list.get(i - 1).getHeadingDate())) {
            albumViewHolder.tvYear.setVisibility(View.VISIBLE);
            albumViewHolder.tvYear.setText(this.list.get(i).getHeadingDate());
        } else {
            albumViewHolder.tvYear.setVisibility(View.GONE);
        }
        if (this.isInterNational) {
            albumViewHolder.tvYear.setVisibility(View.GONE);
        }
        albumViewHolder.tvHolidayName.setText(this.list.get(i).getHolidayName());
        albumViewHolder.tvDayName.setText(this.list.get(i).getHolidayDayName());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayName;
        TextView tvHolidayName;
        TextView tvYear;

        AlbumViewHolder(View view) {
            super(view);
            this.tvYear = (TextView) view.findViewById(R.id.tvYear);
            this.tvHolidayName = (TextView) view.findViewById(R.id.tvHolidayName);
            this.tvDayName = (TextView) view.findViewById(R.id.tvDayName);
        }
    }
}
