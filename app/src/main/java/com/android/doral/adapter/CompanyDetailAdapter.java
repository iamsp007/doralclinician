package com.android.doral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.RowEmploymentRecordBinding;
import com.android.doral.retrofit.model.CompanyHistoryModel;

import java.util.ArrayList;


public class CompanyDetailAdapter extends RecyclerView.Adapter<CompanyDetailAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private ArrayList<CompanyHistoryModel> requestModels;

    public CompanyDetailAdapter(Context context, ArrayList<CompanyHistoryModel> requestModels) {
        this.activity = context;
        this.requestModels = requestModels;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowEmploymentRecordBinding binding = RowEmploymentRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
}

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.tvCompanyName.setText(requestModels.get(position).getCompanyName());
        holder.binding.tvAddress.setText(requestModels.get(position).getPhoneNo());
        holder.binding.tvPhone.setText(requestModels.get(position).getAddress());
      //  holder.binding.tvSupervisor.setText(requestModels.get(position).getSupervisor());

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

        RowEmploymentRecordBinding binding;

        public MyViewHolder(RowEmploymentRecordBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
//            binding.edtPhoneNumber.addTextChangedListener(new PhoneTextFormatter(binding.edtPhoneNumber, "+1 (###) ###-####"));
        }
    }

}