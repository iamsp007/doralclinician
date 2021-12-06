package com.android.doral.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.doral.Cat;
import com.android.doral.Example;
import com.android.doral.Notification.Model.Datum2;
import com.android.doral.Notification.Model.Notification;
import com.android.doral.Notification.NotificationAdapter;
import com.android.doral.R;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.dialog.SendbroadcastDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Category extends BaseActivity implements View.OnClickListener, BaseViewInterface{
    RecyclerView recyclerView;
    CategoryAdapter mAdapter;
    List<Cat> data;
    private BasePresenterInterface presenterInterface;
    Button button;
    String parent_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        presenterInterface = new Presenter( this);
        recyclerView = findViewById(R.id.rv_data);
        button = findViewById(R.id.btn_show_status);



//        ImageView imageView = findViewById(R.id.img_back);
//        imageView.setVisibility(View.VISIBLE);
//        imageView.setOnClickListener(this::onClick);
//        presenterInterface.sendRequest(context, null, null, ApiCallInterface.categorylist);
        data = new ArrayList<>();
        mAdapter = new CategoryAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        HashMap<String, String> map = new HashMap<>();
        if (getIntent().hasExtra("type_id")) {
            parent_id = (String) getIntent().getStringExtra("type_id");
        }
        map.put("type_id", parent_id);
        presenterInterface.sendRequest(Category.this, null, map, ApiCallInterface.categorylist);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new SendbroadcastDialog(context, new RoadLRequestAdapter.SendBroadListener() {
                        @Override
                        public void onSelect(String testName) {
//
//                            holder.binding.imgSelect.setImageResource(R.drawable.ic_selected);
//                            requestModels.get(holder.getAdapterPosition()).setChecked(true);
//                            requestModels.get(holder.getAdapterPosition()).setTest_name(testName);
//                            if (itemClickListener != null) {
//                                itemClickListener.onItemClick(holder.getAdapterPosition(), 1, requestModels.get(holder.getAdapterPosition()));
//                            }
                        }
                    }).show();
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

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
            Example baseModel = (Example) success;

            if (baseModel.getData() != null) {
                mAdapter.setData(baseModel.getData());


            }


        }
    }
}