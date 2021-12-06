package com.android.doral.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.Vendor.Activity.VendorDashboardActivity;
import com.android.doral.LoginActivity;
import com.android.doral.NewDashboardActivity;
import com.android.doral.R;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.RoalrequestItemBinding;
import com.android.doral.databinding.StatusItemBinding;
import com.android.doral.dialog.SendbroadcastDialog;
import com.android.doral.retrofit.model.RoadLRequestModel;
import com.android.doral.retrofit.model.UserModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class RoadLStatusAdapter extends RecyclerView.Adapter<RoadLStatusAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<UserModel> requestModels;

    public RoadLStatusAdapter(Context context, List<UserModel> requestModels) {
        this.activity = context;
        this.requestModels = requestModels;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        StatusItemBinding binding = StatusItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (requestModels.get(position).isStatus().equalsIgnoreCase("active") || requestModels.get(position).isStatus().equalsIgnoreCase("1")) {
            holder.binding.imgIcon.setImageResource(R.drawable.ic_loading);
            holder.binding.tvTitle.setText("Searching " + requestModels.get(position).getReferral_type());
        } else if (requestModels.get(position).isStatus().equalsIgnoreCase("complete") || requestModels.get(position).isStatus().equalsIgnoreCase("4")) {
            holder.binding.imgIcon.setImageResource(R.drawable.ic_completed);
            holder.binding.tvTitle.setText("Completed " + requestModels.get(position).getReferral_type());
        } else if (requestModels.get(position).isStatus().equalsIgnoreCase("arrive") || requestModels.get(position).isStatus().equalsIgnoreCase("3")) {
            holder.binding.imgIcon.setImageResource(R.drawable.ic_heartbeat_1);
            holder.binding.tvTitle.setText("Arrived " + requestModels.get(position).getReferral_type());
        } else if (requestModels.get(position).isStatus().equalsIgnoreCase("5")) {
            holder.binding.imgIcon.setImageResource(R.drawable.ic_heartbeat_1);
            holder.binding.tvTitle.setText("Cancelled " + requestModels.get(position).getReferral_type());
        } else if (requestModels.get(position).isStatus().equalsIgnoreCase("7")) {
            holder.binding.imgIcon.setImageResource(R.drawable.ic_heartbeat_1);
            holder.binding.tvTitle.setText("Running " + requestModels.get(position).getReferral_type());
        } else if (requestModels.get(position).isStatus().equalsIgnoreCase("2")) {
            holder.binding.imgIcon.setImageResource(R.drawable.ic_heartbeat_1);
            holder.binding.tvTitle.setText("Accepted " + requestModels.get(position).getReferral_type());
        } else {
            holder.binding.imgIcon.setImageResource(R.drawable.ic_loading);
            holder.binding.tvTitle.setText("Loading " + requestModels.get(position).getReferral_type());
        }

        if (requestModels.get(position).getColor() != null) {

            holder.binding.tvTitle.setTextColor(Color.parseColor(requestModels.get(position).getColor()));
        }

        if (requestModels.get(position).getTravel_time()!=null){

            try {
                long travelTimes=Long.parseLong(requestModels.get(position).getTravel_time());
                String minutesTravel = getSecondsToMinute(travelTimes);
                holder.binding.tvTravelTime.setText("Travel Time: " + minutesTravel);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        try {

            if (requestModels.get(position).getAccepted_time() != null) {
                String totalMinutes = "";
                try {

                    if (requestModels.get(position).getPreparation_time()!=null){

                        String prepareTime=requestModels.get(position).getPreparation_time();
                        String acceptedTime=requestModels.get(position).getAccepted_time();

                        String preparationTimeDiff=getDateTimeDifference(acceptedTime);
                        Log.e("preparationTimeDiff",preparationTimeDiff);

                        String preparation=getPreparationTime(prepareTime, preparationTimeDiff);
                        Log.e("preparation",preparation);


                        String preparationTime = getHH_MM_SS_to_minute(preparation);
                        Log.e("preparationTime",preparationTime);

                        if (requestModels.get(position).getTravel_time() != null) {

                            String travelTime=requestModels.get(position).getTravel_time();
                            holder.binding.tvTravelTime.setText(getDisplayTime(travelTime,preparationTime));

                        }

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {

                if (requestModels.get(position).getTravel_time()!=null){

                    try {
                        long travelTimes=Long.parseLong(requestModels.get(position).getTravel_time());
                        String minutesTravel = getSecondsToMinute(travelTimes);
                        holder.binding.tvTravelTime.setText("Travel Time: " + minutesTravel);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

            }

        }catch (NumberFormatException e){

        }catch (IndexOutOfBoundsException e){}

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    public void setData(List<UserModel> data) {
        requestModels = data;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        StatusItemBinding binding;

        public MyViewHolder(StatusItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

    String getDisplayTime(String travelTimeInSec,String preparationTime) throws ParseException {
        String totalMinutes="";
        long travelTimes=Long.parseLong(travelTimeInSec);
        String minutesTravel = getSecondsToMinute(travelTimes);

        long totalMin=Long.parseLong(getSecondsToOnlyMinute(travelTimeInSec))+Long.parseLong(preparationTime);

        if (totalMin>60){

            SimpleDateFormat sdf = new SimpleDateFormat("mm");
            TimeZone tz = TimeZone.getTimeZone("EST");
            sdf.setTimeZone(tz);
            Date dt = sdf.parse(""+totalMin);
            sdf = new SimpleDateFormat("HH:mm");
            sdf.setTimeZone(tz);
            totalMinutes=sdf.format(dt)+" Hours";

        }else {

            totalMinutes=totalMin+" Min";

        }
        return "Travel Time: "+minutesTravel+" + "+getMinutesToHH_MMAndMM(Long.parseLong(preparationTime))+" = "+totalMinutes;
    }

    public String getMinutesToHH_MMAndMM(long minutes) throws ParseException {
        String properTime="";
        if (minutes>60){

            SimpleDateFormat sdf = new SimpleDateFormat("mm");
            TimeZone tz = TimeZone.getTimeZone("EST");
            sdf.setTimeZone(tz);
            Date dt = sdf.parse(""+minutes);
            sdf = new SimpleDateFormat("HH:mm");
            sdf.setTimeZone(tz);
            properTime=sdf.format(dt)+" Hours";

        }else {

            properTime=minutes+" Min";

        }
        return properTime;
    }

    public String getHH_MM_SS_to_minute(String time) {
        String[] timeSplit = time.split(":");
        String hours =timeSplit[0];
        String min =timeSplit[1];
        String sec =timeSplit[2];
        long minutes = Long.parseLong(hours)*60;
        long minutes1 = Long.parseLong(min);
        long minutes2 = TimeUnit.SECONDS
                .toMinutes(Long.parseLong(sec));
        long totalMinutes=minutes+minutes1+minutes2;
        return ""+totalMinutes;
    }

    public String getSecondsToMinute(long seconds) throws ParseException {
        String totalMinutes="";
        if (seconds>3600){

            SimpleDateFormat sdf = new SimpleDateFormat("ss");
            TimeZone tz = TimeZone.getTimeZone("EST");
            sdf.setTimeZone(tz);
            Date dt = sdf.parse(""+seconds);
            sdf = new SimpleDateFormat("HH:mm");
            sdf.setTimeZone(tz);
            totalMinutes=sdf.format(dt)+" Hours";

        }else {

            SimpleDateFormat sdf = new SimpleDateFormat("ss");
            TimeZone tz = TimeZone.getTimeZone("EST");
            sdf.setTimeZone(tz);
            Date dt = sdf.parse(""+seconds);
            sdf = new SimpleDateFormat("mm");
            sdf.setTimeZone(tz);
            totalMinutes=sdf.format(dt)+" Min";

        }
        return totalMinutes;
    }

    public String getSecondsToOnlyMinute(String seconds) throws ParseException {
        long minutes = TimeUnit.SECONDS
                .toMinutes(Long.parseLong(seconds));
        return ""+minutes;
    }

    public String getCurrentTime() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone tz = TimeZone.getTimeZone("EST");
        formatter.setTimeZone(tz);
        return formatter.format(new Date(System.currentTimeMillis()));
    }

    public String getDateTimeDifference(String acceptedTime) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("EST");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(tz);
        Date startDate = simpleDateFormat.parse(acceptedTime);
        Date endDate = simpleDateFormat.parse(getCurrentTime());
        Log.e("getCurrentTime",getCurrentTime().toString());
        //Date endDate = simpleDateFormat.parse("2021-07-07 12:40:00");
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        long totalHours = (elapsedDays * 24) + elapsedHours;

        return (totalHours < 10 ? "0" + totalHours : totalHours) + ":" + (elapsedMinutes < 10 ? "0" + elapsedMinutes : elapsedMinutes) + ":" + (elapsedSeconds < 10 ? "0" + elapsedSeconds : elapsedSeconds) + "";
    }

    public String getPreparationTime(String endTime,String startTime) {
        String diffTime = "";
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm:ss");
            TimeZone tz = TimeZone.getTimeZone("EST");
            df.setTimeZone(tz);
            Date date1 = df.parse(startTime);
            java.util.Date date2 = df.parse(endTime);
            long diff = date2.getTime() - date1.getTime();
            long timeInSeconds = diff / 1000;
            long hours, minutes, seconds;
            hours = timeInSeconds / 3600;
            timeInSeconds = timeInSeconds - (hours * 3600);
            minutes = timeInSeconds / 60;
            timeInSeconds = timeInSeconds - (minutes * 60);
            seconds = timeInSeconds;
            diffTime = (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds) + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffTime;
    }

}