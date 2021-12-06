package com.android.doral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.ProfessionalActivity;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.BoardCertificateItemBinding;
import com.android.doral.databinding.StateLicenseItemBinding;
import com.android.doral.retrofit.model.BoardCertificate;
import com.android.doral.retrofit.model.StateLicense;

import java.util.List;

public class BoardCertificateAdapter extends RecyclerView.Adapter<BoardCertificateAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    List<BoardCertificate> stateModelList;


    public BoardCertificateAdapter(Context context, List<BoardCertificate> stateModelList) {
        this.activity = context;
        this.stateModelList = stateModelList;

    }


    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BoardCertificateItemBinding binding = BoardCertificateItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (stateModelList!=null){
            holder.binding.tvCertificate.setText("Certificate: "+stateModelList.get(position).getCertificate());
            holder.binding.tvStatus.setText("Status : "+stateModelList.get(position).getStatus());
            holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ProfessionalActivity)activity).deleteBoardCertificateItem(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return stateModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        BoardCertificateItemBinding binding;

        public MyViewHolder(BoardCertificateItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}