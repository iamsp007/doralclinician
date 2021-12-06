package com.android.doral.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.TransitAddressItemBinding;
import com.android.doral.databinding.TransitRecommendedItemBinding;
import com.android.doral.dialog.TransitModeRecommendedDialog;
import com.android.doral.model.StepsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TransitModeRecommendedAdapter extends RecyclerView.Adapter<TransitModeRecommendedAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    ArrayList<JSONObject> rootList;
    TransitModeRecommendedDialog dialog;
    TransitHorizontalAdapter adapter;
    ArrayList<JSONArray> list;

    public TransitModeRecommendedAdapter(TransitModeRecommendedDialog dialog,Context context,ArrayList<JSONObject> rootList) {

        this.activity = context;
        this.rootList = rootList;
        this.dialog = dialog;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TransitRecommendedItemBinding binding = TransitRecommendedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position==0){
            holder.binding.tvRecommended.setVisibility(View.VISIBLE);
        }else {
            holder.binding.tvRecommended.setVisibility(View.GONE);
        }

        holder.binding.relRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TransitModeRecommendedDialog)dialog).showDetails(rootList.get(position));
            }
        });

        try {
            JSONArray jLegs = ((JSONObject) rootList.get(position)).getJSONArray("legs");

            for (int j = 0; j < jLegs.length(); j++) {

                JSONObject duration = ((JSONObject) jLegs.get(j)).getJSONObject("duration");
                JSONObject departure_time = ((JSONObject) jLegs.get(j)).getJSONObject("departure_time");
                JSONObject arrival_time = ((JSONObject) jLegs.get(j)).getJSONObject("arrival_time");
                JSONObject distance = ((JSONObject) jLegs.get(j)).getJSONObject("distance");
                holder.binding.tvTotalTimeDuration.setText(duration.getString("text"));
                holder.binding.tvMileDuration.setText(distance.getString("text"));
                holder.binding.tvArrivalDip.setText(departure_time.getString("text")+"-"+arrival_time.getString("text"));
                JSONArray stepsJsonArray=((JSONObject) jLegs.get(j)).getJSONArray("steps");

                for (int k = 0; k <stepsJsonArray.length() ; k++) {

                    String html_instructions = ((JSONObject) stepsJsonArray.get(0)).getString("html_instructions");
                    JSONObject durationSteps = ((JSONObject) stepsJsonArray.get(0)).getJSONObject("duration");
                    holder.binding.tvHtmlInstruction.setText(durationSteps.getString("text")+" "+html_instructions);

                }

                adapter = new TransitHorizontalAdapter(activity,stepsJsonArray);
                holder.binding.rvHorizontal.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false));
                holder.binding.rvHorizontal.setAdapter(adapter);

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

        TransitRecommendedItemBinding binding;

        public MyViewHolder(TransitRecommendedItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

    }


}