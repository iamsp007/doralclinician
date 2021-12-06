package com.android.doral.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.LoginActivity;
import com.android.doral.NewDashboardActivity;
import com.android.doral.NurseReferenceRecordActivity;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.RowEmergencyContactRecordBinding;
import com.android.doral.retrofit.model.EmergencyDataModel;

import java.util.ArrayList;
import java.util.List;


public class NurseReferenceAdapter extends RecyclerView.Adapter<NurseReferenceAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<EmergencyDataModel> requestModels;

    public NurseReferenceAdapter(Context context, List<EmergencyDataModel> requestModels) {
        this.activity = context;
        this.requestModels = requestModels;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowEmergencyContactRecordBinding binding = RowEmergencyContactRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MyViewHolder(binding);
}

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.linPersonRelation.setVisibility(View.GONE);
      //  holder.binding.relation.setVisibility(View.GONE);
        holder.binding.tvEmergencyName.setText(requestModels.get(position).getName());
        holder.binding.tvEmergencyAddress.setText(requestModels.get(position).getAddress());
        holder.binding.tvEmergencyPhone.setText(requestModels.get(position).getPhoneNo());
        holder.binding.relation.setVisibility(View.GONE);
     //   holder.binding.tvEmergencyRelation.setText(requestModels.get(position).getRelation());
        holder.binding.tvEmergencyPersonRelateYou.setText(requestModels.get(position).getPersonRelation());
        holder.binding.tvEmergencyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                requestModels.get(position).setName(holder.binding.tvEmergencyName.getText().toString());
//                holder.binding.tvEmergencyName.setText(requestModels.get(position).getName());



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



//        holder.binding.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (itemClickListener != null) {
////                    itemClickListener.onItemClick(holder.getAdapterPosition(), 1, requestModels.get(holder.getAdapterPosition()));
////                }
//                view.getContext().startActivity(new Intent(view.getContext(), NurseReferenceRecordActivity.class)
//                        .putExtra("name",requestModels.get(position).getName())
//                        .putExtra("zipcode",requestModels.get(position).getZipcode())
//                        .putExtra("phone",requestModels.get(position).getPhoneNo())
//                        .putExtra("address",requestModels.get(position).getAddress())
//                        .putExtra("apt",requestModels.get(position).getBuilding())
//
//
//                );
//
////                Log.e("state",requestModels.get(position).getState());
//            }
//        });

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

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RowEmergencyContactRecordBinding binding;

        public MyViewHolder(RowEmergencyContactRecordBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }





}