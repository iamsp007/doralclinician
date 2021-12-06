package com.android.doral.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.doral.adapter.ReasonAdapter;
import com.android.doral.adapter.TransitModeAddressAdapter;
import com.android.doral.databinding.TransitAddressItemBinding;
import com.android.doral.databinding.TransitModeAddressDialogBinding;
import com.android.doral.databinding.TransitModeDialogBinding;
import com.android.doral.model.StepsModel;

import java.util.List;

import static com.android.doral.Utils.Utility.Bus;
import static com.android.doral.Utils.Utility.Subway;
import static com.android.doral.Utils.Utility.TRAVEL_MODES;
import static com.android.doral.Utils.Utility.Train;


public class TransitModeDialog extends AppCompatDialog implements View.OnClickListener {
    private final Context context;
    private final View.OnClickListener onClickListener;
    TransitModeAddressDialogBinding mBinding;
    TransitModeAddressAdapter adapter;
    List<StepsModel> listSteps;
    String arrivalTime="",startAddress="";

    public TransitModeDialog(String arrivalTime,String startAddress,List<StepsModel> listSteps,Context context, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;
        this.listSteps = listSteps;
        this.arrivalTime = arrivalTime;
        this.startAddress = startAddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = TransitModeAddressDialogBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(mBinding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.92);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        mBinding.tvTime.setText(arrivalTime);
        mBinding.tvAddress.setText(startAddress);
        adapter = new TransitModeAddressAdapter(context,listSteps);
        mBinding.rvTransitAddress.setLayoutManager(new LinearLayoutManager(context));
        mBinding.rvTransitAddress.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

    }
}
