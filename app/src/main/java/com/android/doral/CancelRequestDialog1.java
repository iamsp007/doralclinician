package com.android.doral;

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

import com.android.CovidFormActivity;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.CancelRequestDialogBinding;
import com.android.doral.dialog.CancelRequestDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.retrofit.model.RequestModel;
import com.android.doral.retrofit.model.RoadRequestModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CancelRequestDialog1 extends DialogFragment {

    private CancelRequestDialogBinding binding;
    private Context context;
    BasePresenterInterface presenterInterface;
    String request_id,parent_id;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DATE_FORMATE_4);
    RequestModel requestModel;
    RoadRequestModel roadRequestModel;
    String notes;
//    public CancelRequestDialog(Context context, String request_id, BasePresenterInterface presenterInterface, RequestModel requestModel) {
//        this.context = context;
//        this.presenterInterface = presenterInterface;
//        this.request_id = request_id;
//        this.requestModel = requestModel;
//    }



    public CancelRequestDialog1(Context context) {
        this.context = context;
    }

    public CancelRequestDialog1(Context context, String parent_id,String notes) {
        this.context = context;
        this.parent_id = parent_id;
        this.notes = notes;
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
        binding.tvTitle.setText("Notes");
        binding.edtReason.setText(notes);
        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notes = binding.edtReason.getText().toString();
                Intent intent = new Intent(context, Profile.class);
                intent.putExtra("parent_id", parent_id);
                intent.putExtra("notes", notes);
                startActivity(intent);
            }

        });




    }
}
