package com.android.doral;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.RowEmergencyContactRecordBinding;
import com.android.doral.databinding.RowWorkDetailsBinding;
import com.android.doral.retrofit.model.CompanyModel;
import com.android.doral.retrofit.model.EmergencyDataModel;

import java.util.ArrayList;

public class WorkDetailAdapter extends RecyclerView.Adapter<WorkDetailAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private ArrayList<CompanyModel> requestModels;

    public WorkDetailAdapter(Context context, ArrayList<CompanyModel> requestModels) {
        this.activity = context;
        this.requestModels = requestModels;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowWorkDetailsBinding binding = RowWorkDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.tvCompany.setText(requestModels.get(position).getCompany_name());
        holder.binding.tvPosition.setText(requestModels.get(position).getPosition());
        holder.binding.tvDuration.setText(requestModels.get(position).getStart_date()+" "+requestModels.get(position).getEnd_date());
        holder.binding.tvReason.setText(requestModels.get(position).getReason());

        holder.binding.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(holder.getAdapterPosition(), 1, requestModels.get(holder.getAdapterPosition()));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        RowWorkDetailsBinding binding;

        public MyViewHolder(RowWorkDetailsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }


    }

}