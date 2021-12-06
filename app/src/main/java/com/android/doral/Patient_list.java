package com.android.doral;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.patient.RecyclerViewAdapter;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.Datum;
import com.android.doral.retrofit.model.PatientResponseModel;
import com.android.doral.retrofit.model.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Patient_list extends BaseActivity implements View.OnClickListener, BaseViewInterface {

    RecyclerView recyclerView;
  //  ArrayList<String> stringArrayList = new ArrayList<String>();
    CoordinatorLayout coordinatorLayout;
    private RecyclerViewAdapter appointmentAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private BasePresenterInterface presenterInterface;
    private  ImageView imageView;
    private List<Datum> dataArrayList;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        presenterInterface = new Presenter(this);
        recyclerView = findViewById(R.id.patientlist);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        imageView = findViewById(R.id.img_menu);
        imageView.setOnClickListener(this::onClick);
        drawerLayout =findViewById(R.id.drawelayout_homeactivity);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                shuffle();
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });

        populateRecyclerView();
    //    enableSwipeToDeleteAndUndo();
    }
    private void populateRecyclerView() {
        dataArrayList = new ArrayList<>();
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.Patientlist);
        appointmentAdapter = new RecyclerViewAdapter(this,dataArrayList);
       //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(appointmentAdapter);

    }
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.img_menu) {
          // drawerLayout.openDrawer(GravityCompat.START);
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
        if (requestCode == ApiCallInterface.Patientlist) {
            PatientResponseModel userModel = (PatientResponseModel) success;
            if (userModel.getData() != null) {
                appointmentAdapter.setData(userModel.getData());


            }
        }
    }
}
