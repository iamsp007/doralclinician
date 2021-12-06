package com.android.Vendor.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.recyclerview.widget.RecyclerView;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.OnTrackItemBinding;
import com.android.doral.retrofit.model.AppontmentModel;
import java.util.ArrayList;
import java.util.List;

public class OnTrackAdapter extends RecyclerView.Adapter<OnTrackAdapter.MyViewHolder> implements Filterable {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<AppontmentModel> requestModels = new ArrayList<>();
    private List<AppontmentModel> templist;

    public OnTrackAdapter(Context context) {

        this.activity = context;
        this.requestModels = new ArrayList<>();
        this.templist = new ArrayList<>();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OnTrackItemBinding binding = OnTrackItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public void setData(List<AppontmentModel> data) {
        requestModels = data;
        templist = data;
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        requestModels.remove(pos);
        notifyItemRemoved(pos);
    }

    public AppontmentModel getData(int pos) {
        return requestModels.get(pos);

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    requestModels = templist;
                } else {
                    List<AppontmentModel> filteredList = new ArrayList<>();
                    for (AppontmentModel row : templist) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_8, row.getBook_datetime(), false).toLowerCase().equalsIgnoreCase(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    requestModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = requestModels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                requestModels = (List<AppontmentModel>) filterResults.values;


                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        OnTrackItemBinding binding;

        public MyViewHolder(OnTrackItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

}