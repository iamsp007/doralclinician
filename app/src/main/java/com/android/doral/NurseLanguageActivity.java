package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.RowLangugesBinding;
import com.android.doral.retrofit.model.NurseLanguageModel;
import com.google.gson.Gson;

public class NurseLanguageActivity extends BaseActivity implements BaseViewInterface {


    RowLangugesBinding binding;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= RowLangugesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        binding.toolbar.tvTitle.setText("Add Language Detail");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!StringUtils.isNotEmpty(binding.edtLanuageName.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter language");
                }
                else {



                NurseLanguageModel model= new NurseLanguageModel(binding.edtLanuageName.getText().toString(),
                        binding.cbMinimal.isChecked(),binding.cbFluent.isChecked(),binding.cbRead.isChecked(),
                        binding.cbWrite.isChecked());

                Intent intent=new Intent();
                intent.putExtra("data",new Gson().toJson(model));
                setResult(RESULT_OK,intent);
                finish();
            }
            }

        });

    }
    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

    }
}
