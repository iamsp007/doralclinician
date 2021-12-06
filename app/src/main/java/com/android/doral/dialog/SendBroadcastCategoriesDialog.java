package com.android.doral.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import com.android.doral.Cat;
import com.android.doral.Example;
import com.android.doral.LoginActivity;
import com.android.doral.Namelist;
import com.android.doral.R;
import com.android.doral.Test;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.Utility;
import com.android.doral.adapter.Category;
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


public class SendBroadcastCategoriesDialog extends AppCompatDialog implements View.OnClickListener, BaseViewInterface {
    private final Context context;
    private final RoadLRequestAdapter.SendBroadListener onClickListener;
    SendBroadcastDialogBinding mBinding;
    private BasePresenterInterface presenterInterface;
    String selectedId;
    List<Cat> categoriesList;
    List<Test> subCategoriesList;
    List<String> catList = new ArrayList<>();
    List<String> selectedCatList = new ArrayList<>();

    public SendBroadcastCategoriesDialog(Context context, String selectedId, RoadLRequestAdapter.SendBroadListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;
        this.selectedId = selectedId;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        presenterInterface = new Presenter(this);
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
        mBinding.tvBroadCast.setText("Next");
        mBinding.tvOk.setOnClickListener(this);
        mBinding.tvBroadCast.setOnClickListener(this);
        mBinding.imgClose.setOnClickListener(this);

        getCategoriesList();

        //List<String> wordList = new ArrayList<String>(catList);

        mBinding.tvValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBinding.llSuggetion.removeAllTags();
                for (int j = 0; j < catList.size(); j++) {

                    if (catList.get(j).contains(charSequence.toString())) {

                        mBinding.llSuggetion.addTag(catList.get(j));
                        categoriesList.get(j).setSelected(true);

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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
                categoriesList.get(position).setSelected(false);
            }
        });

        mBinding.llSuggetion.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                mBinding.llMain.addTag(text);
                categoriesList.get(position).setSelected(true);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {
                categoriesList.get(position).setSelected(false);
            }
        });


    }

    public void getCategoriesList() {

        mBinding.progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("type_id", selectedId);
        presenterInterface.sendRequest(context, null, map, ApiCallInterface.categorylist);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mBinding.tvBroadCast.getId()) {

            if (onClickListener != null) {

                if (mBinding.llMain.getTags() != null) {

                    // onClickListener.onSelect(TextUtils.join(",", mBinding.llMain.getTags()));
                    selectedCatList.clear();
                    for (int i = 0; i < categoriesList.size(); i++) {

                        if (categoriesList.get(i).isSelected()) {

                            selectedCatList.add("" + categoriesList.get(i).getId());

                        }

                    }

                    //onClickListener.onSelect(TextUtils.join(",", mBinding.llMain.getTags()));
                    Utility.CATEGORIES_ID = TextUtils.join(",", selectedCatList);
                    Utility.SUBCATEGORIES_ID = "";
                    Utility.SUBCATEGORIES_NAME = "";
                    getSubCategoriesList(TextUtils.join(",", selectedCatList));

                }

            }

        }

       /* if (v.getId() == mBinding.tvCancel.getId()) {

            dismiss();
        }*/
        if (v.getId() == mBinding.imgClose.getId()) {

            dismiss();
        }
    }

    public void getSubCategoriesList(String selectedCatId) {
        mBinding.progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("category_id", selectedCatId);
        presenterInterface.sendRequest(context, null, map, ApiCallInterface.namelist);

    }


    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

        if (requestCode == ApiCallInterface.categorylist) {
            mBinding.progressBar.setVisibility(View.GONE);
            Example baseModel = (Example) success;

            if (baseModel.getData() != null) {

                categoriesList = baseModel.getData();
                if (categoriesList.size()>0){

                    catList.clear();
                    selectedCatList.clear();

                    for (int i = 0; i < categoriesList.size(); i++) {

                        catList.add(categoriesList.get(i).getName());

                    }

                    mBinding.llSuggetion.setTags(catList);

                }else {

                    dismiss();
                    Toast.makeText(context,"Categorise not available",Toast.LENGTH_SHORT).show();

                }


            }else {

                dismiss();
                Toast.makeText(context,"Categorise not available",Toast.LENGTH_SHORT).show();

            }

        }
        if (requestCode == ApiCallInterface.namelist) {

            mBinding.progressBar.setVisibility(View.GONE);
            Namelist baseModel = (Namelist) success;

            if (baseModel.getData() != null) {

                subCategoriesList = baseModel.getData();

                if (subCategoriesList.size() > 0) {

                    Utility.IS_SUBCATEGORIES_Available=true;
                    onClickListener.onSelect(TextUtils.join(",", mBinding.llMain.getTags()));
                    dismiss();

                } else {

                    Utility.IS_SUBCATEGORIES_Available=false;
                    showBroadCast();

                }

            }else {

                Utility.IS_SUBCATEGORIES_Available=false;
                showBroadCast();

            }

        }

    }

    public void showBroadCast(){
        new CustomAlertDialog(context, "are you sure want \n" + "to start Broadcast?", "Logout", "Yes", "No", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onClickListener != null) {

                    if (mBinding.llMain.getTags() != null) {

                        // onClickListener.onSelect(TextUtils.join(",", mBinding.llMain.getTags()));
                        selectedCatList.clear();
                        if (categoriesList != null) {

                            for (int i = 0; i < categoriesList.size(); i++) {

                                if (categoriesList.get(i).isSelected()) {

                                    selectedCatList.add("" + categoriesList.get(i).getId());

                                }

                            }

                        }

                        //onClickListener.onSelect(TextUtils.join(",", selectedCatList));
                        Utility.CATEGORIES_ID = TextUtils.join(",", selectedCatList);
                        Utility.SUBCATEGORIES_ID = "";
                        onClickListener.onSelect(TextUtils.join(",", mBinding.llMain.getTags()));

                    }

                }

                dismiss();

            }
        }).show();
    }
}

