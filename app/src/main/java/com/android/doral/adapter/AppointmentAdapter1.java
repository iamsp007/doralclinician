package com.android.doral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Patient_list;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.AppointmentItem1Binding;
import com.android.doral.retrofit.model.AppontmentModel;

import java.util.ArrayList;
import java.util.List;


public class AppointmentAdapter1 extends RecyclerView.Adapter<AppointmentAdapter1.MyViewHolder> implements Filterable {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<AppontmentModel> requestModels = new ArrayList<>();
    private List<AppontmentModel> templist;

    public AppointmentAdapter1(Context context) {

        this.activity = context;
        this.requestModels = new ArrayList<>();
        this.templist = new ArrayList<>();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        AppointmentItem1Binding binding = AppointmentItem1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


       if (requestModels.get(position).getPatients()==null){
           holder.binding.tvName.setText("Edword Norton");
       }else {
           holder.binding.tvName.setText(requestModels.get(position).getPatients().getFullName());
       }


        if (requestModels.get(position).getService() != null && StringUtils.isNotEmpty(requestModels.get(position).getService().getName())) {
            holder.binding.tvAddress.setVisibility(View.VISIBLE);
            holder.binding.tvAddress.setText("Service: " + requestModels.get(position).getService().getName());
        } else {
            holder.binding.tvAddress.setVisibility(View.GONE);
        }
        String start_date = DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_7, requestModels.get(position).getStart_datetime(), true);
        String time = DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_15, requestModels.get(position).getStart_datetime(), true);
        String end_date = DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_7, requestModels.get(position).getEnd_datetime(), true);
        holder.binding.tvDay.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, "dd", requestModels.get(position).getStart_datetime(), true));
        holder.binding.tvMonth.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, "MMM", requestModels.get(position).getStart_datetime(), true));
        holder.binding.tvDate.setText("Date: " + start_date);
        holder.binding.tvTime.setText(time);

        holder.binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!requestModels.get(holder.getAdapterPosition()).getStatus().equalsIgnoreCase("cancel")) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(holder.getAdapterPosition(), 1, requestModels.get(holder.getAdapterPosition()));
                    }
                }

            }
        });
        holder.binding.tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemClickListener != null) {
                  /*  if (StringUtils.isNotEmpty(requestModels.get(holder.getAdapterPosition()).getParent_id())){
                        itemClickListener.onItemClick(holder.getAdapterPosition(), 3, requestModels.get(holder.getAdapterPosition()));
                    }else {
                        itemClickListener.onItemClick(holder.getAdapterPosition(), 2, requestModels.get(holder.getAdapterPosition()));
                    }*/
                    itemClickListener.onItemClick(holder.getAdapterPosition(), 2, requestModels.get(holder.getAdapterPosition()));

                }


            }
        });
        if (requestModels.get(holder.getAdapterPosition()).getStatus().equalsIgnoreCase("cancel")) {
            holder.binding.llBoton.setVisibility(View.GONE);
            holder.binding.tvStatus.setVisibility(View.VISIBLE);

        } else {
            holder.binding.llBoton.setVisibility(View.VISIBLE);
            holder.binding.tvStatus.setVisibility(View.GONE);
        }

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

    public void remove(int pos) {
        requestModels.remove(pos);
        notifyItemRemoved(pos);
    }

    public AppontmentModel getData(int pos) {
        return requestModels.get(pos);

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

        AppointmentItem1Binding binding;

        public MyViewHolder(AppointmentItem1Binding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}