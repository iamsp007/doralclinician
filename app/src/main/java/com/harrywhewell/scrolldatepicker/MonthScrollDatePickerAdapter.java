package com.harrywhewell.scrolldatepicker;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.R;

import org.joda.time.LocalDate;
import org.joda.time.Months;

/**
 * Adapter to supply MonthScrollDatePicker with date data
 */
 class MonthScrollDatePickerAdapter extends RecyclerView.Adapter<MonthScrollDatePickerViewHolder>{

    private LocalDate startDate;
    private LocalDate endDate;

    private TitleValueCallback callback;


    private Style style;

    private Integer selectedPosition = null;


    public MonthScrollDatePickerAdapter(Style style, TitleValueCallback callback) {
        this.callback = callback;
        this.startDate = new LocalDate();
        this.style = style;
        callback.onTitleValueReturned(startDate);
    }

    public void setStartDate(int month, int year){
        this.startDate = startDate.withMonthOfYear(month).withYear(year);
        callback.onTitleValueReturned(startDate);
    }

    public void setStartMonth(int month){
        this.startDate = startDate.withMonthOfYear(month);
        callback.onTitleValueReturned(startDate);
    }

    public void setEndDate(int month, int year){
        this.endDate = startDate.withMonthOfYear(month).withYear(year);
    }
    @Override
    public MonthScrollDatePickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.month_list_item,
                parent,
                false);
        return new MonthScrollDatePickerViewHolder(style, itemView);
    }

    @Override
    public void onBindViewHolder(final MonthScrollDatePickerViewHolder holder, final int position) {
        LocalDate dateTime = startDate.plusMonths(position);
        callback.onTitleValueReturned(dateTime);

        holder.onBind(dateTime, new OnChildClickedListener() {
            @Override
            public void onChildClick(boolean clicked) {
                if(clicked){
                    if(selectedPosition != null){
                        notifyItemChanged(selectedPosition);
                    }
                    selectedPosition = holder.getAdapterPosition();
                }
            }
        });
        if(selectedPosition != null && selectedPosition == holder.getAdapterPosition()){
            holder.styleViewSection(true);
        }
    }

    @Override
    public int getItemCount() {
        if(endDate != null){
            return Months.monthsBetween(startDate, endDate).getMonths() + 1;
        }else{
            return Integer.MAX_VALUE;
        }

    }

}
