package com.android.doral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.RowLanguageRecordBinding;
import com.android.doral.retrofit.model.NurseLanguageModel;

import java.util.ArrayList;


public class NurseLanguageDetailAdapter extends RecyclerView.Adapter<NurseLanguageDetailAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private ArrayList<NurseLanguageModel> requestModels;

    public NurseLanguageDetailAdapter(Context context, ArrayList<NurseLanguageModel> requestModels) {
        this.activity = context;
        this.requestModels = requestModels;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowLanguageRecordBinding binding = RowLanguageRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
}

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.binding.tvName.setText(requestModels.get(position).getName());
        String language = "";
        if(requestModels.get(position).isMinimal())
        {
            language="Minimal Level";
        }
        if(requestModels.get(position).isFluent())
        {
            language=language.length()!=0 ? language+",Fluent Level" : "Fluent Level";
        }
        if(requestModels.get(position).isRead())
        {
            language=language.length()!=0 ? language+",Read Level" : "Read Level,";
        }
        if(requestModels.get(position).isWrite())
        {
            language=language.length()!=0 ? language+",Write Level" : "Write Level,";
        }

        holder.binding.tvLanguage.setText(language);

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        RowLanguageRecordBinding binding;

        public MyViewHolder(RowLanguageRecordBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

}