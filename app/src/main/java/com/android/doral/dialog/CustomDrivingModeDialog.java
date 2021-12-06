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

import com.android.doral.databinding.DrivingModeDialogBinding;

import static com.android.doral.Utils.Utility.Bicycling;
import static com.android.doral.Utils.Utility.Driving;
import static com.android.doral.Utils.Utility.TRAVEL_MODES;
import static com.android.doral.Utils.Utility.Transit;
import static com.android.doral.Utils.Utility.Walking;


public class CustomDrivingModeDialog extends AppCompatDialog implements View.OnClickListener {
    private final Context context;

    private final View.OnClickListener onClickListener;
    DrivingModeDialogBinding mBinding;

    public CustomDrivingModeDialog(Context context, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = DrivingModeDialogBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(mBinding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.92);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        mBinding.linDriving.setOnClickListener(this);
        mBinding.linTransit.setOnClickListener(this);
        mBinding.linWalking.setOnClickListener(this);
        mBinding.linBicycle.setOnClickListener(this); 
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mBinding.linDriving.getId()) {
            TRAVEL_MODES=Driving;
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            dismiss();
        }
        if (v.getId() == mBinding.linTransit.getId()) {
            TRAVEL_MODES=Transit;
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            dismiss();
        }
        if (v.getId() == mBinding.linWalking.getId()) {
            TRAVEL_MODES=Walking;
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            dismiss();
        }
        if (v.getId() == mBinding.linBicycle.getId()) {
            TRAVEL_MODES=Bicycling;
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            dismiss();
        }
    }
}
