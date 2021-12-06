package com.android.doral.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.doral.R;
import com.android.doral.Utils.ImageUtils;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.Utility;
import com.android.doral.databinding.RoalrequestItemBinding;
import com.android.doral.dialog.SendBroadcastCategoriesDialog;
import com.android.doral.dialog.SendBroadcastSubcategoriesDialog;
import com.android.doral.retrofit.model.VenderModel;
import java.util.List;

public class RoadLRequestAdapter extends RecyclerView.Adapter<RoadLRequestAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<VenderModel> requestModels;

    public RoadLRequestAdapter(Context context, List<VenderModel> requestModels) {
        this.activity = context;
        this.requestModels = requestModels;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RoalrequestItemBinding binding = RoalrequestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.tvTitle.setText(requestModels.get(position).getName());
        ImageUtils.loadImage(activity, requestModels.get(position).getIcon(), R.drawable.ic_loading, holder.binding.imgIcon);

        if (requestModels.get(position).getCheck() != null) {
            holder.binding.imgSelect.setImageResource(R.drawable.ic_selected);
            requestModels.get(position).setChecked(true);
        } else {
            holder.binding.imgSelect.setImageResource(R.drawable.ic_check_unselect);
            requestModels.get(position).setChecked(false);
        }

        holder.binding.imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!requestModels.get(holder.getAdapterPosition()).isChecked()) {

                    new SendBroadcastCategoriesDialog(activity, requestModels.get(position).getRole_id(), new SendBroadListener() {
                        @Override
                        public void onSelect(String testName) {

                            Utility.CATEGORIES_NAME=testName;
                            if (Utility.IS_SUBCATEGORIES_Available){

                                new SendBroadcastSubcategoriesDialog(activity, Utility.CATEGORIES_ID, new SendBroadListener() {
                                    @Override
                                    public void onSelect(String testName) {

                                        Utility.SUBCATEGORIES_NAME=testName;

                                        holder.binding.imgSelect.setImageResource(R.drawable.ic_selected);
                                        requestModels.get(holder.getAdapterPosition()).setChecked(true);
                                        requestModels.get(holder.getAdapterPosition()).setTest_name(testName);

                                        if (itemClickListener != null) {
                                            itemClickListener.onItemClick(holder.getAdapterPosition(), 1, requestModels.get(holder.getAdapterPosition()));
                                        }

                                    }
                                }).show();

                            }else {

                                Utility.SUBCATEGORIES_NAME="";

                                holder.binding.imgSelect.setImageResource(R.drawable.ic_selected);
                                requestModels.get(holder.getAdapterPosition()).setChecked(true);
                                requestModels.get(holder.getAdapterPosition()).setTest_name(testName);

                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(holder.getAdapterPosition(), 1, requestModels.get(holder.getAdapterPosition()));
                                }

                            }
                        }
                    }).show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }


    public void setData(List<VenderModel> data) {
        requestModels = data;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoalrequestItemBinding binding;

        public MyViewHolder(RoalrequestItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

    public interface SendBroadListener {
        void onSelect(String testName);
    }
}