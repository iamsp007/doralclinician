package com.android.doral.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.RowReasonBinding;
import com.android.doral.databinding.TransitAddressItemBinding;
import com.android.doral.model.StepsModel;
import com.android.doral.retrofit.model.ReasonModel;

import java.util.ArrayList;
import java.util.List;


public class TransitModeAddressAdapter extends RecyclerView.Adapter<TransitModeAddressAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<String> requestModels;
    List<StepsModel> listSteps;
    private int selectPos = -1;

    public TransitModeAddressAdapter(Context context,List<StepsModel> listSteps) {

        this.activity = context;
        this.requestModels = new ArrayList<>();
        this.listSteps = listSteps;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TransitAddressItemBinding binding = TransitAddressItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.binding.tvAddress.setText(Html.fromHtml(listSteps.get(position).getHtml_instructions()));
        holder.binding.tvKm.setText(listSteps.get(position).getKm());
        holder.binding.tvDuration.setText(listSteps.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return listSteps.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        TransitAddressItemBinding binding;

        public MyViewHolder(TransitAddressItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}