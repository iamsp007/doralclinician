package com.android.doral.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.RowNurseEducationHistoryBinding;
import com.android.doral.retrofit.model.NurseEducationHistoryModel;

import java.util.ArrayList;


public class NurseSchoolDetailAdapter extends RecyclerView.Adapter<NurseSchoolDetailAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private ArrayList<NurseEducationHistoryModel> requestModels;

    public NurseSchoolDetailAdapter(Context context, ArrayList<NurseEducationHistoryModel> requestModels) {
        this.activity = context;
        this.requestModels = requestModels;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowNurseEducationHistoryBinding binding = RowNurseEducationHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
}

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.tvNameAddress.setText(requestModels.get(position).getName());
        holder.binding.tvGraduate.setText(requestModels.get(position).getIsGraduate());
        holder.binding.tvDegree.setText(requestModels.get(position).getDegree());
        holder.binding.tvYearCompleted.setText(requestModels.get(position).getYear());
//        Log.e("gggggg",requestModels.get(position).getIsGraduate());



            holder.binding.year.setVisibility(requestModels.get(position).getIsGraduate().equalsIgnoreCase("yes") ? View.VISIBLE : View.GONE);



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

        RowNurseEducationHistoryBinding binding;

        public MyViewHolder(RowNurseEducationHistoryBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

}