package com.android.doral.Notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.ChatActivity;
import com.android.doral.GetRequestActivity;
import com.android.doral.Notification.Model.Datum2;
import com.android.doral.R;
import com.android.doral.RoadLStatusActivity;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private List<Datum2> data;
    private Intent intent;
    String message;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public NotificationAdapter(Context context, List<Datum2> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<Datum2> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, type;
        LinearLayout lay;
        AppCompatTextView tv_disease, tv_test, tv_symptoms, tv_address;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tv_name);
            //  type = itemView.findViewById(R.id.userdate);
            lay = itemView.findViewById(R.id.lay);
            tv_disease = itemView.findViewById(R.id.tv_disease);
            tv_test = itemView.findViewById(R.id.tv_test);
            tv_symptoms = itemView.findViewById(R.id.tv_symptoms);
            //  tv_address = itemView.findViewById(R.id.tv_address);


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String x = capitalize(data.get(position).getSender().getFirstName());
        String y = capitalize(data.get(position).getSender().getLastName());

        holder.mTitle.setText(x + " " + y);
//        holder.type.setText(data.get(position).getType());

//        if (data.get(position).getRequest().getDieses().equalsIgnoreCase("")){
//            holder.tv_disease.setText("");
//        }else{
//            holder.tv_disease.setText(data.get(position).getRequest().getDieses());
//        }

//        if (data.get(position).getRequest().getTestName()==null){
//            holder.tv_test.setText("");
//        }else{
//            holder.tv_test.setText(data.get(position).getRequest().getTestName());
//        }
//
//        if (data.get(position).getRequest().getSymptoms()==null){
//            holder.tv_symptoms.setText("");
//        }else{
//            holder.tv_symptoms.setText(data.get(position).getRequest().getSymptoms());
//        }

    //    if (data.get(position).getRequest().getDieses() == null || data.get(position).getRequest().getDieses() == "") {
            holder.tv_disease.setText("");
//     //   }else{
//
//            holder.tv_disease.setText(data.get(position).getRequest().getDieses());
//        }


      //  holder.tv_test.setText(data.get(position).getRequest().getTestName());
      //  holder.tv_symptoms.setText(data.get(position).getRequest().getSymptoms());


        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.getAdapterPosition(), 1, data.get(holder.getAdapterPosition()));
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Datum2 item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public List<Datum2> getData() {
        return data;
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

