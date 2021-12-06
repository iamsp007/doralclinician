package com.android.doral.patient;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.ChatActivity;
import com.android.doral.R;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.adapter.AppointmentAdapter1;
import com.android.doral.databinding.ListItemBindingImpl;
import com.android.doral.retrofit.model.Datum;
import com.android.doral.retrofit.model.PatientResponseModel;
import com.android.doral.retrofit.model.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Datum> requestModels;
    private OnItemClickListener itemClickListener;

    public RecyclerViewAdapter(Context context, List<Datum> requestModels) {
        this.context = context;
        this.requestModels =  requestModels;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(Datum data) {
        requestModels= Collections.singletonList(data);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.username);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String x = capitalize(requestModels.get(position).getFirstName());
        String y = capitalize(requestModels.get(position).getLastName());

        holder.mTitle.setText(x + " " + y);
       // holder.mTitle.setText(requestModels.get(position).getFirstName());
        Log.e("vbhjgujh",requestModels.get(position).getId().toString());

        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(view.getContext(),"click on item: "+data,Toast.LENGTH_LONG).show();
                Intent i = new Intent(view.getContext(), ChatActivity.class)
                        .putExtra("parentID",requestModels.get(position).getId().toString())
                        .putExtra("name",requestModels.get(position).getFirstName());
                view.getContext().startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return requestModels.size();

    }
    public void removeItem(int position) {
        requestModels.remove(position);
        notifyItemRemoved(position);
    }
    public void setData(List<Datum> data) {
        requestModels = data;
        notifyDataSetChanged();
    }
    public List<Datum> getData() {
        return requestModels;
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


