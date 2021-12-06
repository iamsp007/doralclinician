package com.android.doral.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Cat;
import com.android.doral.Notification.Model.Datum2;
import com.android.doral.Notification.NotificationAdapter;
import com.android.doral.R;
import com.android.doral.Utils.OnItemClickListener;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategoryAdapter extends RecyclerView.Adapter<com.android.doral.adapter.CategoryAdapter.MyViewHolder> {
    private List<Cat> data;
    private Intent intent;
    String message;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public CategoryAdapter(Context context, List<Cat> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<Cat> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, type;
        LinearLayout lay;
        AppCompatTextView tv_disease, tv_test, tv_symptoms, tv_address;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.textcat);
            //  type = itemView.findViewById(R.id.userdate);
            lay = itemView.findViewById(R.id.lay);

            //  tv_address = itemView.findViewById(R.id.tv_address);


        }
    }


    @Override
    public com.android.doral.adapter.CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat, parent, false);
        return new com.android.doral.adapter.CategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mTitle.setText(data.get(position).getName());

    }





    @Override
    public int getItemCount() {
        return data.size();
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cat item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public List<Cat> getData() {
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

