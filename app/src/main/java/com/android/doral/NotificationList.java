package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.doral.Notification.Model.Datum2;
import com.android.doral.Notification.NotificationAdapter;
import com.android.doral.R;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.Notification.Model.Notification;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.Datum1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationList extends BaseActivity implements View.OnClickListener, BaseViewInterface {

    RecyclerView recyclerView;
    NotificationAdapter mAdapter;
    private List<Datum2> data;

    String id;
    private BasePresenterInterface presenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        presenterInterface = new Presenter((BaseViewInterface) this);
        recyclerView = findViewById(R.id.notificationlist);

        ImageView imageView = findViewById(R.id.img_back);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(this::onClick);


        presenterInterface.sendRequest(context, null, null, ApiCallInterface.Notification);


        //
        data = new ArrayList<>();
        mAdapter = new NotificationAdapter(this, data);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {

                HashMap<String, String> map = new HashMap<>();
                map.put("id", ((Datum2) object).getRequest().getId());
                presenterInterface.sendRequest(context, null, map, ApiCallInterface.readNotification);
            }
        });

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {


            onBackPressed();
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
        if (requestCode == ApiCallInterface.Notification) {
            Notification baseModel = (Notification) success;

            if (baseModel.getData() != null) {
                mAdapter.setData(baseModel.getData());


            }


        }

        if (requestCode == ApiCallInterface.readNotification) {
            BaseModel baseModel = (BaseModel) success;

            if (baseModel.isStatus().equals("true")) {
               mAdapter.notifyDataSetChanged();


            }


        }
    }
}