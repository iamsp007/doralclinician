package com.android.doral.fragement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.doral.ReasonActivity;
import com.android.doral.RoadlRequestActivity;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.adapter.AppointmentAdapter1;
import com.android.doral.base.BaseFragment;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.FragmentAppointmentBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.BaseModel;

import java.util.HashMap;


public class AppointmentFragment extends BaseFragment implements BaseViewInterface {

    private FragmentAppointmentBinding binding;
    BasePresenterInterface presenterInterface;
    AppointmentAdapter1 appointmentAdapter;
    int selectPos = -1;

    public AppointmentFragment() {

    }


    public static AppointmentFragment newInstance(String type) {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentBinding.inflate(inflater);
        presenterInterface = new Presenter(this);
        binding.rvAppointment.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        appointmentAdapter = new AppointmentAdapter1(getActivity());
        binding.rvAppointment.setAdapter(appointmentAdapter);

        if (getArguments().getString("type").equalsIgnoreCase("1")) {
            presenterInterface.sendRequest(getActivity(), null, null, ApiCallInterface.APPOITNMENT);
        } else {
            presenterInterface.sendRequest(getActivity(), null, null, ApiCallInterface.APPOITNMENT_CANCELLED_LIST);
        }

        appointmentAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                selectPos = position;
                if (which == 1) {

                    startActivityForResult(new Intent(getActivity(), ReasonActivity.class).putExtra("id", ((AppontmentModel) object).getId()), 2005);
                }
                if (which == 2) {
                    startActivity(new Intent(getActivity(), RoadlRequestActivity.class));
                }

            }
        });

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipe.setRefreshing(true);
                if (getArguments().getString("type").equalsIgnoreCase("1")) {
                    presenterInterface.sendRequest(getActivity(), null, null, ApiCallInterface.APPOITNMENT);
                } else {
                    presenterInterface.sendRequest(getActivity(), null, null, ApiCallInterface.APPOITNMENT_CANCELLED_LIST);
                }

            }
        });

        return binding.getRoot();
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {
        errorMessage(binding.getRoot(), errorMsg);
    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

        if (requestCode == ApiCallInterface.APPOITNMENT || requestCode == ApiCallInterface.APPOITNMENT_CANCELLED_LIST) {
            binding.swipe.setRefreshing(false);
            AppontmentModel appontmentModel = (AppontmentModel) success;
            if (appontmentModel.getData() != null) {
                appointmentAdapter.setData(appontmentModel.getData());
            }
        }
        if (requestCode == ApiCallInterface.CANCEL_APPOINTMENT) {
            appointmentAdapter.remove(selectPos);
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
                new CustomAlertDialog(getActivity(), baseModel.getMessage(), "Appointment Cancelled", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                }).show();


            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 2005 && resultCode == Activity.RESULT_OK) {

            HashMap<String, String> map = new HashMap<>();
            map.put("appointment_id", appointmentAdapter.getData(selectPos).getId());
            map.put("reason_id", data.getStringExtra("reason_id"));
            map.put("reason_notes", data.getStringExtra("reason"));
            map.put("cancel_user", new MyPref(getActivity()).getUserData().getId());

            presenterInterface.sendRequest(getActivity(), null, map, ApiCallInterface.CANCEL_APPOINTMENT);

        }
    }
}