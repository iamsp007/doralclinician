package com.android.doral.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.R;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.TransitDetailsItemBinding;
import com.android.doral.databinding.TransitItemHorizontalBinding;
import com.android.doral.dialog.TransitModeDetailsDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TransitHorizontalAdapter extends RecyclerView.Adapter<TransitHorizontalAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<String> requestModels;
    JSONArray stepsJsonArray;
    private int selectPos = -1;

    public TransitHorizontalAdapter(Context context, JSONArray stepsJsonArray) {

        this.activity = context;
        this.requestModels = new ArrayList<>();
        this.stepsJsonArray = stepsJsonArray;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TransitItemHorizontalBinding binding = TransitItemHorizontalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
            JSONObject stepsObject = ((JSONObject) stepsJsonArray.getJSONObject(position));
            if(stepsObject.getString("travel_mode").equals("WALKING")){

                holder.binding.imgModeType.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_walking_map));
                holder.binding.rootName.setVisibility(View.GONE);
                holder.binding.imgArrow.setVisibility(View.VISIBLE);

            }else if(stepsObject.getString("travel_mode").equals("TRANSIT")){

                JSONObject transitDetails=stepsObject.getJSONObject("transit_details");
                JSONObject line=transitDetails.getJSONObject("line");
                JSONObject vehicleJSONObject=line.getJSONObject("vehicle");

                if (vehicleJSONObject.getString("type").equals("BUS")){

                    holder.binding.imgModeType.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_bus));
                    holder.binding.rootName.setVisibility(View.VISIBLE);
                    holder.binding.rootName.setText(line.getString("short_name"));
                    holder.binding.rootName.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.binding.rootName.setBackgroundColor(Color.parseColor("#3E2BBD"));
                    holder.binding.imgArrow.setVisibility(View.VISIBLE);

                }else if (vehicleJSONObject.getString("type").equals("HEAVY_RAIL")){

                    holder.binding.imgModeType.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_transit));
                    holder.binding.rootName.setVisibility(View.VISIBLE);
                    holder.binding.rootName.setText(line.getString("name"));
                    holder.binding.rootName.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.binding.rootName.setBackgroundColor(Color.parseColor("#640804"));
                    holder.binding.imgArrow.setVisibility(View.VISIBLE);

                }else if (vehicleJSONObject.getString("type").equals("SUBWAY")){

                    holder.binding.imgModeType.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_subway));
                    holder.binding.rootName.setVisibility(View.VISIBLE);
                    holder.binding.rootName.setText(line.getString("short_name"));
                    holder.binding.rootName.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.binding.rootName.setBackgroundColor(Color.parseColor("#722B6A"));
                    holder.binding.imgArrow.setVisibility(View.VISIBLE);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return stepsJsonArray.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TransitItemHorizontalBinding binding;

        public MyViewHolder(TransitItemHorizontalBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}