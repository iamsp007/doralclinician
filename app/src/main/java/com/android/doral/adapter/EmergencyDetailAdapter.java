package com.android.doral.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.databinding.RowEmergencyContactRecordBinding;
import com.android.doral.retrofit.model.EmergencyDataModel;

import java.util.ArrayList;


public class EmergencyDetailAdapter extends RecyclerView.Adapter<EmergencyDetailAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private ArrayList<EmergencyDataModel> requestModels;

    public EmergencyDetailAdapter(Context context, ArrayList<EmergencyDataModel> requestModels) {
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
        holder.binding.tvEmergencyName.setText(requestModels.get(position).getName());
        holder.binding.tvEmergencyAddress.setText(requestModels.get(position).getAddress());
        holder.binding.tvEmergencyPhone.setText(requestModels.get(position).getPhoneNo());
        holder.binding.tvEmergencyRelation.setText(requestModels.get(position).getRelation());
        holder.binding.tvEmergencyPersonRelateYou.setText(requestModels.get(position).getPersonRelation());

       // holder.binding.tvEmergencyPhone.addTextChangedListener(new PhoneTextFormatter(holder.binding.tvEmergencyPhone, "+1 (###) ###-####"));
//        holder.binding.tvEmergencyName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                requestModels.get(position).setName(holder.binding.tvEmergencyName.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//        holder.binding.tvEmergencyAddress.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                requestModels.get(position).setAddress(holder.binding.tvEmergencyAddress.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//        holder.binding.tvEmergencyPhone.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                requestModels.get(position).setPhoneNo(holder.binding.tvEmergencyPhone.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//        holder.binding.tvEmergencyRelation.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                requestModels.get(position).setRelation(holder.binding.tvEmergencyRelation.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//        holder.binding.tvEmergencyPersonRelateYou.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                requestModels.get(position).setPersonRelation(holder.binding.tvEmergencyPersonRelateYou.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
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



    public class MyViewHolder extends RecyclerView.ViewHolder {

        RowEmergencyContactRecordBinding binding;

        public MyViewHolder(RowEmergencyContactRecordBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

}