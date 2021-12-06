package com.android.doral.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.GetRequestActivity;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.HistoryItemBinding;
import com.android.doral.databinding.RequestItemBinding;
import com.android.doral.directions.GetDirectionsResponse;
import com.android.doral.directions.OnGoogleMapEventListener;
import com.android.doral.retrofit.model.RequestModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.doral.Utils.Utility.Driving;
import static com.android.doral.Utils.Utility.TRAVEL_MODES;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<RequestModel> requestModels;

    public HistoryAdapter(Context context) {
        this.activity = context;
        this.requestModels = new ArrayList<>();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HistoryItemBinding binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (requestModels.get(position).getDetail() != null) {
            if (StringUtils.isNotEmpty(requestModels.get(position).getDetail().getFullName())) {
                holder.binding.tvName.setText(requestModels.get(position).getDetail().getFullName());
            }

            if (StringUtils.isNotEmpty(requestModels.get(position).getDetail().getEmail())) {
                holder.binding.tvAddress.setText(requestModels.get(position).getDetail().getEmail());
            }
        }


        if (StringUtils.isNotEmpty(requestModels.get(position).getDieses())) {
            holder.binding.tvDisease.setText(requestModels.get(position).getDieses());
        }

        if (StringUtils.isNotEmpty(requestModels.get(position).getSymptoms())) {
            holder.binding.tvSymptoms.setText(requestModels.get(position).getSymptoms());
        }
        if (StringUtils.isNotEmpty(requestModels.get(position).getSub_test_name())) {
            holder.binding.tvTest.setText(requestModels.get(position).getSub_test_name());
        }else {
            holder.binding.tvTest.setText(requestModels.get(position).getTest_name());
        }
        if (StringUtils.isNotEmpty(requestModels.get(position).getPatient_detail().getDemographic().getAddress().getFullAddress())) {
            holder.binding.tvAddressPatient.setText(requestModels.get(position).getPatient_detail().getDemographic().getAddress().getFullAddress());
        }

        if (new MyPref(activity).getUserData().getRoles().get(new MyPref(activity).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

            holder.binding.llInsurance.setVisibility(View.GONE);
            holder.binding.llDiagnosis.setVisibility(View.GONE);

        } else {

            holder.binding.llInsurance.setVisibility(View.VISIBLE);
            holder.binding.llDiagnosis.setVisibility(View.VISIBLE);

            if (StringUtils.isNotEmpty(requestModels.get(position).getPatient_detail().getDemographic().getInsurance())) {

                holder.binding.tvInsurance.setText(requestModels.get(position).getPatient_detail().getDemographic().getInsurance());

            }else {

                holder.binding.tvInsurance.setText("Not Applicable");

            }

            if (StringUtils.isNotEmpty(requestModels.get(position).getPatient_detail().getDemographic().getDiagnosis())) {

                holder.binding.tvDiagnosis.setText(requestModels.get(position).getPatient_detail().getDemographic().getDiagnosis());

            }else {

                holder.binding.tvDiagnosis.setText("-");

            }

        }


        if (requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("1")) {
            try {
                TRAVEL_MODES=Driving;
                new GetDirectionsResponse(activity, new LatLng(Double.parseDouble(new MyPref(activity).getData(MyPref.Keys.LAT)),
                        Double.parseDouble(new MyPref(activity).getData(MyPref.Keys.LAG))), new LatLng(Double.parseDouble(requestModels.get(position).getLatitude()), Double.parseDouble(requestModels.get(position).getLongitude())), new OnGoogleMapEventListener() {
                    @Override
                    public void latlngList(PolylineOptions lineOptions, final List<LatLng> latLngList) {

                    }

                    @Override
                    public void getDuration(JSONObject durations, JSONObject distance, JSONArray stepsData) {


                        try {
                            holder.binding.llDistance.setVisibility(View.VISIBLE);
                            holder.binding.llTime.setVisibility(View.VISIBLE);

                            Double km = Double.parseDouble(distance.getString("value")) / 1609;


                            // binding.tvKm.setText(distance.getString("text"));
                            holder.binding.tvDistance.setText(String.format("%.2f Miles", km));

                            // holder.binding.tvDistance.setText(distance.getString("text"));
                            holder.binding.tvTime.setText(durations.getString("text"));
                            requestModels.get(holder.getAdapterPosition()).setDistance(String.format("%.2f Miles", km));
                            requestModels.get(holder.getAdapterPosition()).setTravel_time(durations.getString("text"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception ae) {
                ae.printStackTrace();
            }

        } else {
            holder.binding.llDistance.setVisibility(View.GONE);
            holder.binding.llTime.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }


    public void setData(List<RequestModel> data) {
        requestModels = data;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        HistoryItemBinding binding;

        public MyViewHolder(HistoryItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

    }

}