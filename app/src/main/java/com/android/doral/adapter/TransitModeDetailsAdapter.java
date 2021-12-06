package com.android.doral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.TransitDetailsItemBinding;
import com.android.doral.databinding.TransitRecommendedItemBinding;
import com.android.doral.dialog.TransitModeDetailsDialog;
import com.android.doral.dialog.TransitModeRecommendedDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TransitModeDetailsAdapter extends RecyclerView.Adapter<TransitModeDetailsAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<String> requestModels;
    ArrayList<JSONObject> rootList;
    private int selectPos = -1;
    TransitModeDetailsDialog dialog;
    RecyclerView rvHorizontal;

    public TransitModeDetailsAdapter(TransitModeDetailsDialog dialog, Context context, ArrayList<JSONObject> rootList) {

        this.activity = context;
        this.requestModels = new ArrayList<>();
        this.rootList = rootList;
        this.dialog = dialog;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TransitDetailsItemBinding binding = TransitDetailsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
            JSONObject stepsObject = ((JSONObject) rootList.get(position));
            if(stepsObject.getString("travel_mode").equals("WALKING")){

                holder.binding.linWalking.setVisibility(View.VISIBLE);
                holder.binding.linBusTrain.setVisibility(View.GONE);
                String html_instructions = (stepsObject).getString("html_instructions");
                JSONObject durationSteps = (stepsObject).getJSONObject("duration");
                holder.binding.tvStartAddress.setText(html_instructions+" ("+durationSteps.getString("text")+")");
                holder.binding.tvDepartureDuration.setVisibility(View.GONE);
            }else {

                holder.binding.linBusTrain.setVisibility(View.VISIBLE);
                JSONObject transit_details = (stepsObject).getJSONObject("transit_details");
                JSONObject duration = (stepsObject).getJSONObject("duration");
                JSONObject departure_stop=transit_details.getJSONObject("departure_stop");
                JSONObject arrival_stop=transit_details.getJSONObject("arrival_stop");
                JSONObject departure_time=transit_details.getJSONObject("departure_time");
                String headsign=transit_details.getString("headsign");
                String nameDep=departure_stop.getString("name");
                String nameArrival=arrival_stop.getString("name");
                String durationText=duration.getString("text");
                String num_stops=transit_details.getString("num_stops");
                String departureTime=departure_time.getString("text");
                holder.binding.tvDeparture.setText(nameDep);
                holder.binding.tvArrival.setText(nameArrival);
                holder.binding.tvHeadDesign.setText(headsign);
                holder.binding.tvRideTotalRouts.setText("Ride "+durationText+" (Stops"+num_stops+")");
                holder.binding.tvDepartureDuration.setText(departureTime);
                holder.binding.linWalking.setVisibility(View.GONE);
                holder.binding.tvDepartureDuration.setVisibility(View.VISIBLE);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return rootList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        TransitDetailsItemBinding binding;

        public MyViewHolder(TransitDetailsItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}