package com.hst.calander.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import com.hst.calander.Model.NoteModel;
import com.hst.calander.R;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.AlbumViewHolder> {
    private Context context;
    private List<NoteModel> list;

    public NoteAdapter(Context context, List<NoteModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AlbumViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note_layout, (ViewGroup) null, false));
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder albumViewHolder, int i) {
        if (i == 0) {
            albumViewHolder.tvDayName.setText(getDayName(this.list.get(i).getDate()));
            albumViewHolder.tvDayMonth.setText(getDayMonth(this.list.get(i).getDate()));
            albumViewHolder.tvYear.setText(getYear(this.list.get(i).getDate()));
        } else if (!this.list.get(i).getDate().equals(this.list.get(i - 1).getDate())) {
            albumViewHolder.tvDayName.setText(getDayName(this.list.get(i).getDate()));
            albumViewHolder.tvDayMonth.setText(getDayMonth(this.list.get(i).getDate()));
            albumViewHolder.tvYear.setText(getYear(this.list.get(i).getDate()));
        }
        albumViewHolder.tvTitle.setText(this.list.get(i).getTitle());
        albumViewHolder.tvDescription.setText(this.list.get(i).getDescription());
        albumViewHolder.tvTime.setText(this.list.get(i).getTime());
    }

    public String getDayName(String str) {
        try {
            return new SimpleDateFormat("EEEE").format(new SimpleDateFormat("dd-MM-yyyy").parse(str));
        } catch (Exception unused) {
            return "";
        }
    }

    public String getDayMonth(String str) {
        try {
            return new SimpleDateFormat("dd/MM").format(new SimpleDateFormat("dd-MM-yyyy").parse(str));
        } catch (Exception unused) {
            return "";
        }
    }

    public String getYear(String str) {
        try {
            return new SimpleDateFormat("yyyy").format(new SimpleDateFormat("dd-MM-yyyy").parse(str));
        } catch (Exception unused) {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayMonth;
        TextView tvDayName;
        TextView tvDescription;
        TextView tvTime;
        TextView tvTitle;
        TextView tvYear;

        AlbumViewHolder(View view) {
            super(view);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            this.tvTime = (TextView) view.findViewById(R.id.tvTime);
            this.tvDayName = (TextView) view.findViewById(R.id.tvDayName);
            this.tvDayMonth = (TextView) view.findViewById(R.id.tvDayMonth);
            this.tvYear = (TextView) view.findViewById(R.id.tvYear);
        }
    }
}
