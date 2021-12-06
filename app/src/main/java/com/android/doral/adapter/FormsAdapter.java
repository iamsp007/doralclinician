package com.android.doral.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.FormsItemBinding;
import com.android.doral.databinding.UpcomingVisitItemBinding;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.FormModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.MyViewHolder> implements Filterable {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<FormModel> requestModels = new ArrayList<>();
    private List<FormModel> templist;
    private  String tvName ="";

    public FormsAdapter(Context context) {

        this.activity = context;
        this.requestModels = new ArrayList<>();
        this.templist = new ArrayList<>();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        FormsItemBinding binding = FormsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        tvName = capitalize(requestModels.get(position).getPatient_name());
        holder.binding.tvName.setText(tvName);
        holder.binding.tvAddress.setText(requestModels.get(position).getPhone());
        holder.binding.tvTest.setText(requestModels.get(position).getDose());
        holder.binding.tvVaccineName.setText(requestModels.get(position).getVaccine_name());
        holder.binding.vaccineDate.setText(requestModels.get(position).getVaccine_date());

        Log.e("tvVaccineName--------------->", String.valueOf(requestModels.get(position).getVaccine_name()));
        Log.e("vaccineDate--------------->", String.valueOf(requestModels.get(position).getVaccine_date()));

        holder.binding.tvParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://app.doralhealthconnect.com/covid-19/" + requestModels.get(holder.getAdapterPosition()).getId() + "/detail";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                activity.startActivity(i);
            }
        });

        holder.binding.tvSubmitSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(holder.getAdapterPosition(), 1, requestModels.get(holder.getAdapterPosition()));
                    holder.binding.tvParking.setText("View Form");
                }
            }
        });


        if (!StringUtils.isNotEmpty(requestModels.get(position).getVaccination_signature())) {
            holder.binding.tvParking.setText("Preview Form");
            if (StringUtils.isNotEmpty(requestModels.get(position).getTime_date())) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DATE_FORMATE_4);
                Calendar calendar = Calendar.getInstance();
                try {
                    Date date = simpleDateFormat.parse(requestModels.get(position).getTime_date());
                    calendar.setTime(date);
                    calendar.add(Calendar.MINUTE, 15);
                    if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
                        holder.binding.tvTime.setVisibility(View.GONE);
                        holder.binding.tvSubmitSign.setVisibility(View.VISIBLE);
                    } else {
                        holder.binding.tvTime.setVisibility(View.VISIBLE);
                        holder.binding.tvSubmitSign.setVisibility(View.GONE);
                        reverseTimer(calendar.getTimeInMillis() - System.currentTimeMillis(), holder);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        } else {
            holder.binding.tvTime.setVisibility(View.GONE);
            holder.binding.tvSubmitSign.setVisibility(View.GONE);
        }




    }


    public void reverseTimer(final long min, MyViewHolder holder) {
        if (holder.timer != null) {
            holder.timer.cancel();
        }

        holder.timer = new CountDownTimer(min, 1000) {

            public void onTick(long millisUntilFinished) {
                try {

                    int minute = (int) TimeUnit.MINUTES.toSeconds(millisUntilFinished);
                    holder.binding.tvTime.setText("" + getHourFromMillis(millisUntilFinished));


                } catch (Exception ae) {
                    ae.printStackTrace();
                }
            }

            public void onFinish() {

                holder.binding.tvTime.setVisibility(View.GONE);
                holder.binding.tvSubmitSign.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public String getHourFromMillis(long millis) {
        String hms = "0";
        try {
            hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return hms;
    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }


    public void setData(List<FormModel> data) {
        requestModels = data;
        templist = data;
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        requestModels.remove(pos);
        notifyItemRemoved(pos);
    }

    public FormModel getData(int pos) {
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
                    List<FormModel> filteredList = new ArrayList<>();
                    for (FormModel row : templist) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                       /* if (DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_8, row.getBook_datetime(), false).toLowerCase().equalsIgnoreCase(charString.toLowerCase())) {
                            filteredList.add(row);
                        }*/
                    }

                    requestModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = requestModels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                requestModels = (List<FormModel>) filterResults.values;


                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        FormsItemBinding binding;
        CountDownTimer timer;

        public MyViewHolder(FormsItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

    private String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

}