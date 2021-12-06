package com.android.doral.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.ProfessionalActivity;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.StateLicenseItemBinding;
import com.android.doral.retrofit.model.StateLicense;
import java.util.List;

public class StateLicenseAdapter extends RecyclerView.Adapter<StateLicenseAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    List<StateLicense> stateModelList;


    public StateLicenseAdapter(Context context,List<StateLicense> stateModelList) {
        this.activity = context;
        this.stateModelList = stateModelList;

    }


    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        StateLicenseItemBinding binding = StateLicenseItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (stateModelList!=null){
            holder.binding.tvState.setText("State: "+stateModelList.get(position).getState());
            holder.binding.tvLicenseNo.setText("License No: "+stateModelList.get(position).getNumber());
            holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ProfessionalActivity)activity).deleteStateLicenceItem(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return stateModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        StateLicenseItemBinding binding;

        public MyViewHolder(StateLicenseItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}