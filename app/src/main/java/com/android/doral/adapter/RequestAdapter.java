package com.android.doral.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.android.Vendor.Activity.VendorDashboardActivity;
import com.android.doral.ClinicianTrackActivity;
import com.android.doral.GetRequestActivity;
import com.android.doral.LoginActivity;
import com.android.doral.NewDashboardActivity;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.RequestItemBinding;
import com.android.doral.directions.GetDirectionsResponse;
import com.android.doral.directions.OnGoogleMapEventListener;
import com.android.doral.directions.PolyUtil;
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


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<RequestModel> requestModels;

    public RequestAdapter(Context context) {
        this.activity = context;
        this.requestModels = new ArrayList<>();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RequestItemBinding binding = RequestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
                new GetDirectionsResponse(activity, new LatLng(Double.parseDouble(new MyPref(activity).getData(MyPref.Keys.LAT)), Double.parseDouble(new MyPref(activity).getData(MyPref.Keys.LAG))), new LatLng(Double.parseDouble(requestModels.get(position).getLatitude()), Double.parseDouble(requestModels.get(position).getLongitude())), new OnGoogleMapEventListener() {
                    @Override
                    public void latlngList(PolylineOptions lineOptions, final List<LatLng> latLngList) {

                    }

                    @Override
                    public void getDuration(JSONObject durations, JSONObject distance, JSONArray stepsData) {
                        if (durations != null && distance != null) {

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

                        }else {

                            try {
                                holder.binding.llDistance.setVisibility(View.VISIBLE);
                                holder.binding.llTime.setVisibility(View.VISIBLE);
                                // Double km = Double.parseDouble(distance.getString("value")) / 1609;
                                // binding.tvKm.setText(distance.getString("text"));
                                holder.binding.tvDistance.setText(String.format("%.2f Miles", 0.0));
                                // holder.binding.tvDistance.setText(distance.getString("text"));
                                holder.binding.tvTime.setText("00:00:00");
                                requestModels.get(holder.getAdapterPosition()).setDistance("0.0");
                                requestModels.get(holder.getAdapterPosition()).setTravel_time("00:00:00");
                            }catch (IndexOutOfBoundsException e){}

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


        if (!new MyPref(activity).getUserData().getRoles().get(new MyPref(activity).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

            if (StringUtils.isNotEmpty(requestModels.get(holder.getAdapterPosition()).getPreparasion_date()) && StringUtils.isNotEmpty(requestModels.get(holder.getAdapterPosition()).getPreparation_time())) {

                if (requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("3") || requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("4")) {

                    holder.binding.tvAccept.setText("View Profile");
                    holder.binding.tvCancel.setVisibility(View.GONE);

                } else if (requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("1")) {

                    holder.binding.tvAccept.setText("Accept");
                    holder.binding.tvCancel.setVisibility(View.GONE);

                } else {

                    holder.binding.tvAccept.setText("Track");
                    holder.binding.tvCancel.setVisibility(View.VISIBLE);

                }

            } else {

                if (requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("1")) {

                    holder.binding.tvAccept.setText("Accept");
                    holder.binding.tvCancel.setVisibility(View.GONE);

                } else {

                    holder.binding.tvAccept.setText("Preparation Time");
                    holder.binding.tvCancel.setVisibility(View.GONE);

                }

            }
        } else {

            if (requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("3") || requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("4")) {

                holder.binding.tvAccept.setText("View Profile");
                holder.binding.tvCancel.setVisibility(View.GONE);

            } else if (requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("1")) {

                holder.binding.tvAccept.setText("Accept");
                holder.binding.tvCancel.setVisibility(View.GONE);

            } else {

                holder.binding.tvAccept.setText("Track");
                holder.binding.tvCancel.setVisibility(View.VISIBLE);
            }

        }

        if (StringUtils.isNotEmpty(requestModels.get(position).getIs_parking())) {

            if (requestModels.get(position).getIs_parking().equalsIgnoreCase("yes")) {

                holder.binding.tvParking.setText("Parking Available");

            } else {

                holder.binding.tvParking.setText("Parking Not Available");

            }

        } else {

            holder.binding.tvParking.setText("Parking Not Available");

        }

        holder.binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(activity)
                        .setTitle("Cancel")
                        .setMessage("Are you sure you want to cancel")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if(activity instanceof GetRequestActivity){
                                    dialog.dismiss();
                                    ((GetRequestActivity)activity).cancelRequestDialog(requestModels.get(position));
                                }

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        holder.binding.tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (itemClickListener != null) {

                    if (!requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("1")) {

                        if (!new MyPref(activity).getUserData().getRoles().get(new MyPref(activity).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

                            if (StringUtils.isNotEmpty(requestModels.get(holder.getAdapterPosition()).getPreparasion_date()) && StringUtils.isNotEmpty(requestModels.get(holder.getAdapterPosition()).getPreparation_time())) {

                                if (requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("3") || requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("4")) {

                                    itemClickListener.onItemClick(position, 5, requestModels.get(holder.getAdapterPosition()));

                                } else {

                                    itemClickListener.onItemClick(position, 2, requestModels.get(holder.getAdapterPosition()));

                                }

                            } else {

                                itemClickListener.onItemClick(position, 3, requestModels.get(holder.getAdapterPosition()));

                            }

                        } else {

                            if (requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("3") || requestModels.get(holder.getAdapterPosition()).isStatus().equalsIgnoreCase("4")) {

                                itemClickListener.onItemClick(position, 5, requestModels.get(holder.getAdapterPosition()));

                            } else {

                                itemClickListener.onItemClick(position, 6, requestModels.get(holder.getAdapterPosition()));

                            }

                        }

                    } else {

                        itemClickListener.onItemClick(position, 1, requestModels.get(holder.getAdapterPosition()));

                    }
                }
            }
        });
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

        RequestItemBinding binding;

        public MyViewHolder(RequestItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

    }

}