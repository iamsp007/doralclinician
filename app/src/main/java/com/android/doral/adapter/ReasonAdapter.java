package com.android.doral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.RowReasonBinding;
import com.android.doral.retrofit.model.ReasonModel;

import java.util.ArrayList;
import java.util.List;


public class ReasonAdapter extends RecyclerView.Adapter<ReasonAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<ReasonModel> requestModels;

    private int selectPos = -1;

    public ReasonAdapter(Context context) {

        this.activity = context;
        this.requestModels = new ArrayList<>();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowReasonBinding binding = RowReasonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (selectPos == position) {
            holder.binding.rowReasonTitle.setChecked(true);
        } else {
            holder.binding.rowReasonTitle.setChecked(false);
        }

        holder.binding.rowReasonTitle.setText(requestModels.get(position).getReason());
        holder.binding.rowReasonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPos = holder.getAdapterPosition();
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(holder.getAdapterPosition(), 1, requestModels.get(holder.getAdapterPosition()));
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }


    public void setData(List<ReasonModel> data) {
        requestModels = data;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RowReasonBinding binding;

        public MyViewHolder(RowReasonBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}