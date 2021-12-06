package com.android.doral.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Cat;
import com.android.doral.Example;
import com.android.doral.LoginActivity;
import com.android.doral.Namelist;
import com.android.doral.R;
import com.android.doral.Test;
import com.android.doral.Utils.MyPref;
import com.android.doral.adapter.Category;
import com.android.doral.adapter.CategoryAdapter;
import com.android.doral.adapter.RoadLRequestAdapter;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.CustomAlertDialogBinding;
import com.android.doral.databinding.SendBroadcastDialogBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.robertlevonyan.views.chip.Chip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import co.lujun.androidtagview.TagView;


public class SendbroadcastDialog extends AppCompatDialog implements View.OnClickListener,BaseViewInterface {
    private final Context context;
    private List<Test> data;
    private BasePresenterInterface presenterInterface;

    private final RoadLRequestAdapter.SendBroadListener onClickListener;
    SendBroadcastDialogBinding mBinding;
    RecyclerView recyclerView;
    TestAdapter mAdapter;


    public SendbroadcastDialog(Context context, RoadLRequestAdapter.SendBroadListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = SendBroadcastDialogBinding.inflate(LayoutInflater.from(context), null, false);
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
        //mBinding.tvCancel.setOnClickListener(this);
        mBinding.imgClose.setOnClickListener(this);
        presenterInterface = new Presenter((BaseViewInterface) context);
        recyclerView = findViewById(R.id.rv_data);
        data = new ArrayList<>();
        mAdapter = new TestAdapter(context, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        HashMap<String, String> map = new HashMap<>();
//        if (getIntent().hasExtra("type_id")) {
//
//        }

        map.put("category_id", "1");
        presenterInterface.sendRequest(context, null, map, ApiCallInterface.namelist);

        List<Test> wordList = new ArrayList<>();
        mBinding.llSuggetion.setTags(String.valueOf(wordList));
        mBinding.tvValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBinding.llSuggetion.removeAllTags();
                for (int j = 0; j < wordList.size(); j++) {
//                    if (wordList.get(j).contains(charSequence.toString())) {
//
//
//                    }
                    mBinding.llSuggetion.addTag(String.valueOf(wordList.size()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


      /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (context, android.R.layout.select_dialog_item, context.getResources().getStringArray(R.array.referell_request));*/
        //Getting the instance of AutoCompleteTextView

      /*  mBinding.tvValue.setThreshold(1);//will start working from first character
        mBinding.tvValue.setAdapter(adapter);


        mBinding.tvValue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mBinding.llMain.addTag(mBinding.tvValue.getText().toString());
                mBinding.tvValue.setText("");
            }
        });*/
        mBinding.llMain.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {
                mBinding.llMain.removeTag(position);
            }
        });

        mBinding.llSuggetion.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                mBinding.llMain.addTag(text);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mBinding.tvOk.getId()) {

            new CustomAlertDialog(context, "are you sure want \n" + "to start Broadcast?", "Logout", "Yes", "No", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (onClickListener != null) {
                        if (mBinding.llMain.getTags() != null) {
                            onClickListener.onSelect(TextUtils.join(",", mBinding.llMain.getTags()));
                        }
                    }
                    dismiss();

                }
            }).show();

        }
       /* if (v.getId() == mBinding.tvCancel.getId()) {

            dismiss();
        }*/
        if (v.getId() == mBinding.imgClose.getId()) {

            dismiss();
        }
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        Toast.makeText(getContext(),"calllll",Toast.LENGTH_SHORT).show();
        if (requestCode == ApiCallInterface.namelist) {
            Namelist baseModel = (Namelist) success;

            if (baseModel.getData() != null) {
                mAdapter.setData(baseModel.getData());
                mAdapter.notifyDataSetChanged();
            }

        }
    }
}
