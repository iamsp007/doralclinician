package com.android.doral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.AppointmentItemBinding;
import com.android.doral.databinding.DoctorItemBinding;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.RequestModel;

import java.util.ArrayList;
import java.util.List;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> implements Filterable {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<AppontmentModel> requestModels;
    private List<AppontmentModel> templist;

    public AppointmentAdapter(Context context) {

        this.activity = context;
        this.requestModels = new ArrayList<>();
        this.templist = new ArrayList<>();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        AppointmentItemBinding binding = AppointmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.binding.tvName.setText(requestModels.get(position).getPatients().getFullName());
        holder.binding.tvAddress.setText(requestModels.get(position).getTitle());
        String start_date = DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_15, requestModels.get(position).getStart_datetime(), true);
        String end_date = DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_15, requestModels.get(position).getEnd_datetime(), true);
        holder.binding.tvDate.setText(start_date + " - " + end_date);
    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }


    public void setData(List<AppontmentModel> data) {
        requestModels = data;
        templist = data;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    requestModels = templist;
                } else {
                    List<AppontmentModel> filteredList = new ArrayList<>();
                    for (AppontmentModel row : templist) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_8, row.getBook_datetime(), false).toLowerCase().equalsIgnoreCase(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    requestModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = requestModels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                requestModels = (List<AppontmentModel>) filterResults.values;


                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AppointmentItemBinding binding;

        public MyViewHolder(AppointmentItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}