package com.android.doral.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.R;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.AddcompanyItemBinding;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.CompanyModel;
import com.android.doral.retrofit.model.CountryModel;
import com.android.doral.retrofit.model.DesignationModel;
import com.android.doral.retrofit.model.StateModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder> {

    private Context activity;
    private OnItemClickListener itemClickListener;
    private List<CompanyModel> requestModels;
    List<DesignationModel> designationModels;
    List<StateModel> stateModelList;
    List<CityModel> cityList;
    CountryModel countryList;

    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public CompanyAdapter(Context context) {
        this.activity = context;
        this.requestModels = new ArrayList<>();

    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }

    public void setCountryList(CountryModel countryList) {
        this.countryList = countryList;
    }


    public void setStateModelList(List<StateModel> stateModelList) {
        this.stateModelList = stateModelList;
    }

    public void setDesignationModels(List<DesignationModel> designationModels) {
        this.designationModels = designationModels;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        AddcompanyItemBinding binding = AddcompanyItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (stateModelList != null) {
            setStatteSpinner(holder.binding.spState, stateModelList, requestModels.get(position).getState_id());
        }
        if (designationModels != null) {
            setSpinner(holder.binding.spDesignation, designationModels, requestModels.get(position).getPosition());
        }
       /* if (StringUtils.isNotEmpty(countryList.getName())) {

            holder.binding.tvCountry.setText(countryList.getName());
        }*/
        holder.binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    requestModels.get(holder.getAdapterPosition()).setState_id(((StateModel) holder.binding.spState.getSelectedItem()).getId());
                    holder.binding.tvState.setText(((StateModel) holder.binding.spState.getSelectedItem()).getState());
                    if (StringUtils.isNotEmpty(requestModels.get(holder.getAdapterPosition()).getCity_id())) {

                        requestModels.get(holder.getAdapterPosition()).setCity_id("");
                        holder.binding.tvCity.setText(activity.getResources().getString(R.string.select));

                    }
                    setCitySpinner(holder.binding.spCity, getCityList(((StateModel) holder.binding.spState.getSelectedItem()).getState_code()), requestModels.get(holder.getAdapterPosition()).getCity_id());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    holder.binding.tvCity.setText(((CityModel) holder.binding.spCity.getSelectedItem()).getCity());
                    requestModels.get(holder.getAdapterPosition()).setCity_id(((CityModel) holder.binding.spCity.getSelectedItem()).getId());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.binding.spDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    holder.binding.tvDesignation.setText(((DesignationModel) holder.binding.spDesignation.getSelectedItem()).getName());
                    requestModels.get(holder.getAdapterPosition()).setPosition(((DesignationModel) holder.binding.spDesignation.getSelectedItem()).getName());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.binding.tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.spState.performClick();
            }
        });
        holder.binding.tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.spCity.performClick();
            }
        });
        holder.binding.tvDesignation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.spDesignation.performClick();
            }
        });

        holder.binding.edtCompanyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestModels.get(holder.getAdapterPosition()).setCompany_name(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.binding.edtLeaveReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //requestModels.get(holder.getAdapterPosition()).setWork_gap_reason(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.binding.tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog startpickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                holder.startDate.set(Calendar.YEAR, year);
                                holder.startDate.set(Calendar.MONTH, monthOfYear);
                                holder.startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                requestModels.get(holder.getAdapterPosition()).setStart_date(dateformat.format(holder.startDate.getTime()));
                                holder.binding.tvStartDate.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, requestModels.get(holder.getAdapterPosition()).getStart_date(), false));
                            }
                        }, holder.startDate.get(Calendar.YEAR), holder.startDate.get(Calendar.MONTH), holder.startDate.get(Calendar.DAY_OF_MONTH));

                startpickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                startpickerDialog.show();
            }
        });

        holder.binding.tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isNotEmpty(requestModels.get(holder.getAdapterPosition()).getStart_date())) {
                    DatePickerDialog startpickerDialog = new DatePickerDialog(activity,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    holder.endDate.set(Calendar.YEAR, year);
                                    holder.endDate.set(Calendar.MONTH, monthOfYear);
                                    holder.endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                    requestModels.get(holder.getAdapterPosition()).setEnd_date(dateformat.format(holder.endDate.getTime()));
                                    holder.binding.tvEndDate.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, requestModels.get(holder.getAdapterPosition()).getEnd_date(), false));
                                }
                            }, holder.endDate.get(Calendar.YEAR), holder.startDate.get(Calendar.MONTH), holder.startDate.get(Calendar.DAY_OF_MONTH));

                    startpickerDialog.getDatePicker().setMinDate(holder.startDate.getTimeInMillis() - 1000);
                    startpickerDialog.show();
                } else {
                    Toast.makeText(activity, "Please select start date", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }


    public void setData(List<CompanyModel> data) {
        requestModels = data;
        notifyDataSetChanged();
    }

    public void setData(CompanyModel data) {
        requestModels.add(data);

        notifyItemInserted(requestModels.size() - 1);
    }

    public List<CompanyModel> getData() {
        return requestModels;
    }

    /*public String getDataInString() {
        List<CompanyModel> list = new ArrayList<>();
        for (int i = 0; i < requestModels.size(); i++) {
            if (!requestModels.get(i).getCompany_name().equalsIgnoreCase("") && !requestModels.get(i).getWork_gap_reason().equalsIgnoreCase("")) {
                list.add(requestModels.get(i));
            }
        }
        return new Gson().toJson(list);
    }

    public List<CompanyModel> getSelectedData() {
        List<CompanyModel> list = new ArrayList<>();
        for (int i = 0; i < requestModels.size(); i++) {
            if (!requestModels.get(i).getCompany_name().equalsIgnoreCase("") && !requestModels.get(i).getWork_gap_reason().equalsIgnoreCase("")) {
                list.add(requestModels.get(i));
            }
        }
        return list;
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AddcompanyItemBinding binding;
        private final Calendar startDate = Calendar.getInstance();
        private final Calendar endDate = Calendar.getInstance();

        public MyViewHolder(AddcompanyItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }

    private List<CityModel> getCityList(String state_code) {
        List<CityModel> modelList = new ArrayList<>();

        if (cityList != null) {
            for (int i = 0; i < cityList.size(); i++) {
                if (cityList.get(i).getState_code().equals(state_code)) {
                    modelList.add(cityList.get(i));
                }
            }
        }
        return modelList;
    }

    private void setCitySpinner(AppCompatSpinner spinner, List<CityModel> list, String selection) {

        CityModel spinnerModel = new CityModel("select");
        list.add(0, spinnerModel);

        ArrayAdapter<CityModel> arrayAdapter = new ArrayAdapter<CityModel>(activity, R.layout.spinner_row, list) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View spinnerView = super.getDropDownView(position, convertView, parent);
                TextView spinnerTextV = (TextView) spinnerView;
                if (position == 0) {
                    spinnerTextV.setTextColor(Color.parseColor("#a7a7a6"));
                } else {
                    spinnerTextV.setTextColor(Color.parseColor("#b2000000"));
                }

                return spinnerView;
            }
        };

        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        spinner.setAdapter(arrayAdapter);

        if (StringUtils.isNotEmpty(selection)) {
            setSelectCity(spinner, selection);
        }

    }

    private void setStatteSpinner(AppCompatSpinner spinner, List<StateModel> list, String selection) {

        StateModel spinnerModel = new StateModel("select");
        list.add(0, spinnerModel);

        ArrayAdapter<StateModel> arrayAdapter = new ArrayAdapter<StateModel>(activity, R.layout.spinner_row, list) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View spinnerView = super.getDropDownView(position, convertView, parent);
                TextView spinnerTextV = (TextView) spinnerView;
                if (position == 0) {
                    spinnerTextV.setTextColor(Color.parseColor("#a7a7a6"));
                } else {
                    spinnerTextV.setTextColor(Color.parseColor("#b2000000"));
                }

                return spinnerView;
            }
        };

        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        spinner.setAdapter(arrayAdapter);

        if (StringUtils.isNotEmpty(selection)) {
            setSelectState(spinner, selection);
        }

    }

    private void setSpinner(AppCompatSpinner spinner, List<DesignationModel> list, String selection) {

        DesignationModel spinnerModel = new DesignationModel("select");
        list.add(0, spinnerModel);

        ArrayAdapter<DesignationModel> arrayAdapter = new ArrayAdapter<DesignationModel>(activity, R.layout.spinner_row, list) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View spinnerView = super.getDropDownView(position, convertView, parent);
                TextView spinnerTextV = (TextView) spinnerView;
                if (position == 0) {
                    spinnerTextV.setTextColor(Color.parseColor("#a7a7a6"));
                } else {
                    spinnerTextV.setTextColor(Color.parseColor("#b2000000"));
                }
                return spinnerView;
            }
        };


        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        spinner.setAdapter(arrayAdapter);

        if (StringUtils.isNotEmpty(selection)) {
            setSelectDesignation(spinner, selection);
        }


    }

    public void setSelectState(AppCompatSpinner spnr, String value) {
        ArrayAdapter<StateModel> adapter = (ArrayAdapter<StateModel>) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {

            if (adapter.getItem(position).getId().equals(value)) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    public void setSelectCity(AppCompatSpinner spnr, String value) {
        ArrayAdapter<CityModel> adapter = (ArrayAdapter<CityModel>) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {

            if (adapter.getItem(position).getId().equals(value)) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    public void setSelectDesignation(AppCompatSpinner spnr, String value) {
        ArrayAdapter<CityModel> adapter = (ArrayAdapter<CityModel>) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if (adapter.getItem(position).getId().equals(value)) {
                spnr.setSelection(position);
                return;
            }
        }
    }
}