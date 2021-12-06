package com.android.doral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.DoctorItemBinding;
import com.android.doral.databinding.RequestItemBinding;
import com.android.doral.retrofit.model.RequestModel;

import java.util.ArrayList;
import java.util.List;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<RequestModel> requestModels;

    public DoctorAdapter(Context context) {

        this.activity = context;
        this.requestModels = new ArrayList<>();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        DoctorItemBinding binding = DoctorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public void setData(List<RequestModel> data) {
        requestModels = data;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        DoctorItemBinding binding;

        public MyViewHolder(DoctorItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}