package com.android.doral.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.R;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.ReferenceItemBinding;
import com.android.doral.retrofit.model.ReferenceModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ReferanceAdapter extends RecyclerView.Adapter<ReferanceAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<ReferenceModel> requestModels;
    private String mpattern = "+1 (###) ###-####";

    public ReferanceAdapter(Context context) {

        this.activity = context;
        this.requestModels = new ArrayList<>();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ReferenceItemBinding binding = ReferenceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.binding.tvRefrenceCount.setText(String.format(activity.getString(R.string.reference_one), "" + position));

        holder.binding.tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestModels.get(holder.getAdapterPosition()).setReferance_name(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.binding.edtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestModels.get(holder.getAdapterPosition()).setReference_address(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        int maxLength = mpattern.length();
        holder.binding.edtPhoneNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        holder.binding.edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestModels.get(holder.getAdapterPosition()).setReference_phone(s.toString());

                StringBuilder phone = new StringBuilder(s);


                if (count > 0 && !isValid(phone.toString())) {
                    for (int i = 0; i < phone.length(); i++) {

                        char c = mpattern.charAt(i);

                        if ((c != '#') && (c != phone.charAt(i))) {
                            phone.insert(i, c);
                        }
                    }

                    holder.binding.edtPhoneNumber.setText(phone);
                    holder.binding.edtPhoneNumber.setSelection(holder.binding.edtPhoneNumber.getText().length());
                    requestModels.get(holder.getAdapterPosition()).setReference_phone(phone.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.binding.edtRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.spRelation.performClick();
            }
        });
        holder.binding.spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    holder.binding.edtRelation.setText(holder.binding.spRelation.getSelectedItem().toString());
                    requestModels.get(holder.getAdapterPosition()).setReference_relationship(holder.binding.spRelation.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }


    public void setData(List<ReferenceModel> data) {
        requestModels = data;
        notifyDataSetChanged();
    }

    public void setData(ReferenceModel data) {
        requestModels.add(data);
        notifyDataSetChanged();
    }

    public List<ReferenceModel> getData() {
        return requestModels;
    }

    public String getDataInString() {
        List<ReferenceModel> list = new ArrayList<>();
        for (int i = 0; i < requestModels.size(); i++) {
            if (!requestModels.get(i).getReferance_name().equalsIgnoreCase("")) {
                list.add(requestModels.get(i));
            }
        }
        return new Gson().toJson(list);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ReferenceItemBinding binding;

        public MyViewHolder(ReferenceItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

    private boolean isValid(String phone) {
        for (int i = 0; i < phone.length(); i++) {
            char c = mpattern.charAt(i);

            if (c == '#') continue;

            if (c != phone.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}