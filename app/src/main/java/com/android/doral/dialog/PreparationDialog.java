package com.android.doral.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.akexorcist.snaptimepicker.SnapTimePickerDialog;
import com.android.doral.R;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.PreparationTimeDialogBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.retrofit.ApiCallInterface;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PreparationDialog extends BottomSheetDialogFragment {

    private PreparationTimeDialogBinding binding;
    private Context context;

    BasePresenterInterface presenterInterface;
    String request_id;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DATE_FORMATE_4);

    public PreparationDialog(Context context, String request_id, BasePresenterInterface presenterInterface) {
        this.context = context;
        this.presenterInterface = presenterInterface;
        this.request_id = request_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.preparation_time_dialog, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnapTimePickerDialog.Builder builder = new SnapTimePickerDialog.Builder();
                builder.setTitle(R.string.preparation_time);
                builder.setPrefix(R.string.hour);
                builder.setSuffix(R.string.minute);
                SnapTimePickerDialog snapTimePickerDialog = builder.build();
                snapTimePickerDialog.setListener((i, i1) -> {
                    binding.edtTime.setText(i + ":" + i1);
                    calendar.add(Calendar.HOUR, i);
                    calendar.add(Calendar.MINUTE, i1);

                });

                snapTimePickerDialog.show(getParentFragmentManager(), "");
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (StringUtils.isNotEmpty(binding.edtTime.getText().toString().trim())) {

                    /*long totalSeconds=0;
                    String[] minuteTravelSplit=binding.edtTime.getText().toString().trim().toLowerCase().split(":");
                    int hoursTravel=Integer.parseInt(minuteTravelSplit[0].toString());
                    int minutesTravel=Integer.parseInt(minuteTravelSplit[1].toString());
                    if (hoursTravel>0){

                        long secondsHours = TimeUnit.HOURS.toSeconds(hoursTravel);
                        long secondsMinute = TimeUnit.MINUTES.toSeconds(minutesTravel);
                        totalSeconds=secondsHours + secondsMinute;

                    }else {

                        long secondsMinute = TimeUnit.MINUTES.toSeconds(minutesTravel);
                        totalSeconds=secondsMinute;

                    }*/


                    HashMap<String, Object> params = new HashMap<>();
                   // Log.e("daySeconds",""+totalSeconds);
                    params.put("preparation_time", binding.edtTime.getText().toString());
                    //params.put("preparation_time", totalSeconds);
                    params.put("patient_request_id", request_id);
                    params.put("preparasion_date", simpleDateFormat.format(calendar.getTime()));
                    presenterInterface.callAPI(context, null, params, ApiCallInterface.UPDATE_TIME, "");

                } else {

                    Toast.makeText(context, "Please enter preparation time", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}