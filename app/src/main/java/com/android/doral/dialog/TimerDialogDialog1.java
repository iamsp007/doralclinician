package com.android.doral.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.android.doral.Utils.MyPref;
import com.android.doral.databinding.TimerDialogBinding;
import com.uzairiqbal.circulartimerview.CircularTimerListener;
import com.uzairiqbal.circulartimerview.TimeFormatEnum;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class TimerDialogDialog1 extends AppCompatDialog implements View.OnClickListener {
    private final Context context;

    private final View.OnClickListener onClickListener;
    TimerDialogBinding mBinding;
    CountDownTimer timer;

    int minute = 15;

    public TimerDialogDialog1(Context context, int minute, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;
        this.minute = minute;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = TimerDialogBinding.inflate(LayoutInflater.from(context), null, false);
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
        mBinding.tvOk.setText("Ready!");

        // countDown(15);

        mBinding.progressCircular.setCircularTimerListener(new CircularTimerListener() {
            @Override
            public String updateDataOnTick(long remainingTimeInMs) {
                // return String.valueOf((int) Math.ceil((remainingTimeInMs / 1000.f)));
                return getHourFromMillis(remainingTimeInMs);
            }

            @Override
            public void onTimerFinished() {
                Toast.makeText(context, "FINISHED", Toast.LENGTH_SHORT).show();
                mBinding.progressCircular.setPrefix("");
                mBinding.progressCircular.setSuffix("");
                mBinding.progressCircular.setText("FINISHED");
                mBinding.llButton.setVisibility(View.VISIBLE);

            }
        }, minute, TimeFormatEnum.SECONDS, 10);
        mBinding.progressCircular.startTimer();


        // mBinding.tvTitle.setText(title);
    }

    private void countDown(int daysInHours) {


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, daysInHours);

        if (calendar.getTimeInMillis() > System.currentTimeMillis()) {

            reverseTimer(calendar.getTimeInMillis() - System.currentTimeMillis());
        }

    }

    public void reverseTimer(final long min) {
        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(min, 1000) {

            public void onTick(long millisUntilFinished) {
                try {

                    int minute = (int) TimeUnit.MINUTES.toSeconds(millisUntilFinished);
                    mBinding.tvErrorMessage.setText("" + getHourFromMillis(millisUntilFinished));


                } catch (Exception ae) {
                    ae.printStackTrace();
                }
            }

            public void onFinish() {


            }
        }.start();
    }

    public String getHourFromMillis(long millis) {
        String hms = "0";
        try {
            hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return hms;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mBinding.tvOk.getId()) {
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
            dismiss();
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (timer != null) {
            timer.cancel();
        }
    }
}
