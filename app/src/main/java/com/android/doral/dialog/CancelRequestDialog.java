package com.android.doral.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.akexorcist.snaptimepicker.SnapTimePickerDialog;
import com.android.doral.ClinicianTrackActivity;
import com.android.doral.GetRequestActivity;
import com.android.doral.NewDashboardActivity;
import com.android.doral.Profile;
import com.android.doral.R;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.CancelRequestDialogBinding;
import com.android.doral.databinding.PreparationTimeDialogBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.RequestModel;
import com.android.doral.retrofit.model.RoadRequestModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CancelRequestDialog extends DialogFragment {

    private CancelRequestDialogBinding binding;
    private Context context;
    BasePresenterInterface presenterInterface;
    String request_id;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DATE_FORMATE_4);
    RequestModel requestModel;
    RoadRequestModel roadRequestModel;
    public CancelRequestDialog(Context context, String request_id, BasePresenterInterface presenterInterface, RequestModel requestModel) {
        this.context = context;
        this.presenterInterface = presenterInterface;
        this.request_id = request_id;
        this.requestModel = requestModel;
    }



    public CancelRequestDialog(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.cancel_request_dialog, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (StringUtils.isNotEmpty(binding.edtReason.getText().toString().trim())) {

                    ((GetRequestActivity)context).cancelRequest(requestModel,binding.edtReason.getText().toString());

                } else {

                    Toast.makeText(context, "Please enter reason", Toast.LENGTH_SHORT).show();

                }
            }

        });




    }
}