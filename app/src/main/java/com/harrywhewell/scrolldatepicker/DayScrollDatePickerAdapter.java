package com.harrywhewell.scrolldatepicker;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.R;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;

class DayScrollDatePickerAdapter extends RecyclerView.Adapter<DayScrollDatePickerViewHolder>{

   private LocalDate startDate;
   private LocalDate endDate;

   private TitleValueCallback callback;


   private Style style;

   private Integer selectedPosition = null;

   public DayScrollDatePickerAdapter(Style style, TitleValueCallback callback){
       this.callback = callback;
       this.startDate = new LocalDate();
       this.style = style;
       callback.onTitleValueReturned(startDate);
   }

   public void setStartDate(int day, int month, int year){
       this.startDate = startDate.withDayOfMonth(day).withMonthOfYear(month).withYear(year);
       callback.onTitleValueReturned(startDate);
   }

   public void setEndDate(int day, int month, int year){
       this.endDate = startDate.withDayOfMonth(day).withMonthOfYear(month).withYear(year);
   }

   @Override
   public DayScrollDatePickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext()).inflate(
               R.layout.day_list_item,
               parent,
               false);
       return new DayScrollDatePickerViewHolder(style, itemView);
   }

   @Override
   public void onBindViewHolder(final DayScrollDatePickerViewHolder holder, int position) {
       LocalDate dateTime = startDate.plusDays(position);
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
           return Days.daysBetween(startDate, endDate).getDays() + 1;
       }else{
           return Integer.MAX_VALUE;
       }
   }
}
