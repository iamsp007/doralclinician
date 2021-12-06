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
import com.android.doral.databinding.TransitModeDialogBinding;

import static com.android.doral.Utils.Utility.Bicycling;
import static com.android.doral.Utils.Utility.Bus;
import static com.android.doral.Utils.Utility.Driving;
import static com.android.doral.Utils.Utility.Subway;
import static com.android.doral.Utils.Utility.TRANSIT_MODES;
import static com.android.doral.Utils.Utility.TRAVEL_MODES;
import static com.android.doral.Utils.Utility.Train;
import static com.android.doral.Utils.Utility.Transit;
import static com.android.doral.Utils.Utility.Walking;


public class CustomTransitModeDialog extends AppCompatDialog implements View.OnClickListener {
    private final Context context;

    private final View.OnClickListener onClickListener;
    TransitModeDialogBinding mBinding;

    public CustomTransitModeDialog(Context context, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = TransitModeDialogBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(mBinding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.92);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        mBinding.linBus.setOnClickListener(this);
        mBinding.linTrain.setOnClickListener(this);
        mBinding.linSubway.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mBinding.linBus.getId()) {
            TRANSIT_MODES=Bus;
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            dismiss();
        }
        if (v.getId() == mBinding.linTrain.getId()) {
            TRANSIT_MODES=Train;
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            dismiss();
        }
        if (v.getId() == mBinding.linSubway.getId()) {
            TRANSIT_MODES=Subway;
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            dismiss();
        }
    }
}
