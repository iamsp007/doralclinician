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

import com.android.doral.databinding.CustomAlertDialogBinding;


public class CustomAlertDialog extends AppCompatDialog implements View.OnClickListener {
    private final Context context;

    private final View.OnClickListener onClickListener;
    CustomAlertDialogBinding mBinding;
    private String message;
    private String title;
    private String btnPositive;
    private String btnNegative;

    public CustomAlertDialog(Context context, String message, String title, String btnPositive, String btnNegative, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;
        this.message = message;
        this.title = title;
        this.btnPositive = btnPositive;
        this.btnNegative = btnNegative;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(mBinding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.92);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        mBinding.tvOk.setOnClickListener(this);
        mBinding.tvCancel.setOnClickListener(this);
        mBinding.tvOk.setText(btnPositive);
        if (btnNegative.equals("")) {
            mBinding.tvCancel.setVisibility(View.GONE);
        } else {
            mBinding.tvCancel.setVisibility(View.VISIBLE);
        }
        mBinding.tvCancel.setText(btnNegative);
        mBinding.tvErrorMessage.setText(message);
       // mBinding.tvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mBinding.tvOk.getId()) {
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            dismiss();
        }
        if (v.getId() == mBinding.tvCancel.getId()) {

            dismiss();
        }
    }
}
