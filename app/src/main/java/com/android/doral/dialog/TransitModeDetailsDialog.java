package com.android.doral.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.doral.adapter.TransitModeDetailsAdapter;
import com.android.doral.adapter.TransitModeRecommendedAdapter;
import com.android.doral.databinding.TransitModeDetailsDialogBinding;
import com.android.doral.databinding.TransitModeRecommendedDialogBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TransitModeDetailsDialog extends AppCompatDialog implements View.OnClickListener {
    private final Context context;
    private final View.OnClickListener onClickListener;
    TransitModeDetailsDialogBinding mBinding;
    TransitModeDetailsAdapter adapter;
    ArrayList<JSONObject> stepsList;
    JSONObject rootObject;

    public TransitModeDetailsDialog(JSONObject rootObject, Context context, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;
        this.rootObject = rootObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = TransitModeDetailsDialogBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(mBinding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.92);
        lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.70);
        lp.gravity = Gravity.BOTTOM;
        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);

        mBinding.imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        stepsList=new ArrayList<>();
        try {
            JSONArray jLegs = ((JSONObject) rootObject).getJSONArray("legs");
            for (int j = 0; j < jLegs.length(); j++) {

                JSONArray stepsJsonArray=((JSONObject) jLegs.get(j)).getJSONArray("steps");
                for (int k = 0; k <stepsJsonArray.length() ; k++) {

                    stepsList.add((JSONObject) stepsJsonArray.getJSONObject(k));
                   /* String html_instructions = ((JSONObject) stepsJsonArray.get(0)).getString("html_instructions");
                    JSONObject durationSteps = ((JSONObject) stepsJsonArray.get(0)).getJSONObject("duration");
                    mBinding.tvStartAddress.setText(html_instructions+" ("+durationSteps.getString("text")+")");*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new TransitModeDetailsAdapter(TransitModeDetailsDialog.this,context,stepsList);
        mBinding.rvTransitAddress.setLayoutManager(new LinearLayoutManager(context));
        mBinding.rvTransitAddress.setAdapter(adapter);

    }

    public void showDetails(int pos){


    }

    @Override
    public void onClick(View v) {

    }
}
