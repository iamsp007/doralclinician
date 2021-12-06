package com.android.doral;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.retrofit.Data;
import com.android.doral.retrofit.model.Datum;
import com.android.doral.retrofit.model.Datum1;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.MyViewHolder> {
    private List<Datum1> data;
    private Intent intent;
    String message;
    private Context context;


    public RecyclerViewAdapter1(Context context, List<Datum1> data) {
        this.context = context;
        this.data = data;
    }


    public void setData(List<Datum1> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.username1);

        }
    }
    

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mTitle.setText(data.get(position).getChat());
        Log.e("gffygyguj", String.valueOf(data.size()));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Datum1 item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public List<Datum1> getData() {
        return data;
    }
}
