package com.android.doral.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.databinding.DoctorItemBinding;
import com.android.doral.databinding.QouetionItemBinding;
import com.android.doral.retrofit.model.QuestionModel;
import com.android.doral.retrofit.model.RequestModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class QuetionAdapter extends RecyclerView.Adapter<QuetionAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<QuestionModel> requestModels;
    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public QuetionAdapter(Context context, List<QuestionModel> requestModels) {

        this.activity = context;
        this.requestModels = requestModels;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        QouetionItemBinding binding = QouetionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.tvQuestion.setText((position + 1) + ". " + requestModels.get(holder.getAdapterPosition()).getQuestion());

        if (position == 2 || position == 4 || position == 9) {

            holder.binding.rbYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.tvDate.setVisibility(View.VISIBLE);
                }
            });

            holder.binding.rbNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.tvDate.setVisibility(View.GONE);
                }
            });

        } else {
            holder.binding.tvDate.setVisibility(View.GONE);
        }


        if (position == 9) {
            holder.binding.rbYes.setVisibility(View.VISIBLE);


            holder.binding.v1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.v1.setChecked(true);
                    holder.binding.v2.setChecked(false);
                    holder.binding.v3.setChecked(false);
                    holder.binding.v4.setChecked(false);
                    requestModels.get(position).setReceived_vaccine(holder.binding.v1.getText().toString());
                }
            });

            holder.binding.v2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.v1.setChecked(false);
                    holder.binding.v2.setChecked(true);
                    holder.binding.v3.setChecked(false);
                    holder.binding.v4.setChecked(false);
                    requestModels.get(position).setReceived_vaccine(holder.binding.v2.getText().toString());
                }
            });
            holder.binding.v3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.v1.setChecked(false);
                    holder.binding.v2.setChecked(false);
                    holder.binding.v3.setChecked(true);
                    holder.binding.v4.setChecked(false);
                    requestModels.get(position).setReceived_vaccine(holder.binding.v3.getText().toString());
                }
            });
            holder.binding.v4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.v1.setChecked(false);
                    holder.binding.v2.setChecked(false);
                    holder.binding.v3.setChecked(false);
                    holder.binding.v4.setChecked(true);
                    requestModels.get(position).setReceived_vaccine(holder.binding.v4.getText().toString());
                }
            });

            holder.binding.rbYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.vaccine.setVisibility(View.VISIBLE);
                }
            });

            holder.binding.rbNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.vaccine.setVisibility(View.GONE);
                    holder.binding.v1.setChecked(false);
                    holder.binding.v2.setChecked(false);
                    holder.binding.v3.setChecked(false);
                    holder.binding.v4.setChecked(false);
                    requestModels.get(position).setReceived_vaccine("");

                }
            });


        } else {
            holder.binding.rbYes.setVisibility(View.VISIBLE);
        }

        holder.binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        /*birth_date = i + "/" + (i1 + 1) + "/" + i2;
                        binding.activitySignUpTvDOB.setText(birth_date);*/

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        requestModels.get(holder.getAdapterPosition()).setDate(dateformat.format(calendar.getTime()));
                        holder.binding.tvDate.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }


    public void setData(List<QuestionModel> data) {
        requestModels = data;
        notifyDataSetChanged();
    }

    public List<QuestionModel> getData() {
        return requestModels;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        QouetionItemBinding binding;

        public MyViewHolder(QouetionItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

            binding.rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    RadioButton radioButton = binding.getRoot().findViewById(radioGroup.getCheckedRadioButtonId());
                    requestModels.get(getAdapterPosition()).setAns(radioButton.getText().toString());

                    if (getAdapterPosition() == 9) {

                        if (radioButton.getText().toString().equalsIgnoreCase("no")) {
                            requestModels.get(getAdapterPosition()).setReceived_vaccine("");
                        } else {
                            requestModels.get(getAdapterPosition()).setReceived_vaccine("Moderna");
                        }
                    }
                }
            });

        }
    }

}