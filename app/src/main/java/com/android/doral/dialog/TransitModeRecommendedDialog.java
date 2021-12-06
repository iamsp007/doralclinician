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

import com.android.doral.ClinicianTrackActivity;
import com.android.doral.adapter.TransitModeRecommendedAdapter;
import com.android.doral.databinding.TransitModeRecommendedDialogBinding;
import com.android.doral.model.StepsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class TransitModeRecommendedDialog extends AppCompatDialog implements View.OnClickListener {
    private final Context context;
    private final View.OnClickListener onClickListener;
    TransitModeRecommendedDialogBinding mBinding;
    TransitModeRecommendedAdapter adapter;
    JSONArray rootsArray;
    ArrayList<JSONObject> rootList;
    TransitModeDetailsDialog transitModeDetailsDialog;

    public TransitModeRecommendedDialog(JSONArray rootsArray, Context context, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;
        this.rootsArray = rootsArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = TransitModeRecommendedDialogBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(mBinding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.92);
        lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.70);
        lp.gravity = Gravity.BOTTOM;
        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        mBinding.linRecommended.setVisibility(View.VISIBLE);
        mBinding.linDetails.setVisibility(View.GONE);

        mBinding.imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.linRecommended.setVisibility(View.VISIBLE);
                mBinding.linDetails.setVisibility(View.GONE);
            }
        });
        rootList=new ArrayList<>();
        for (int i = 0; i < rootsArray.length(); i++) {
            try {
                rootList.add((JSONObject) rootsArray.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter = new TransitModeRecommendedAdapter(TransitModeRecommendedDialog.this,context,rootList);
        mBinding.rvTransitRecommended.setLayoutManager(new LinearLayoutManager(context));
        mBinding.rvTransitRecommended.setAdapter(adapter);

    }

    public void showDetails(JSONObject rootObject){

        transitModeDetailsDialog= new TransitModeDetailsDialog(rootObject, context, new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        transitModeDetailsDialog.show();

    }

    @Override
    public void onClick(View v) {

    }
}
