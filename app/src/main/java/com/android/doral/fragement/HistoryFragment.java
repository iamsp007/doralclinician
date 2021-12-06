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

import com.android.doral.GetRequestActivity;
import com.android.doral.ReasonActivity;
import com.android.doral.RoadlRequestActivity;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.adapter.AppointmentAdapter1;
import com.android.doral.adapter.HistoryAdapter;
import com.android.doral.base.BaseFragment;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.FragmentAppointmentBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.RequestModel;

import java.util.HashMap;

public class HistoryFragment extends BaseFragment implements BaseViewInterface {

    private FragmentAppointmentBinding binding;
    BasePresenterInterface presenterInterface;
    HistoryAdapter historyAdapter;
    int selectPos = -1;
    String type = "4";

    public HistoryFragment() {

    }


    public static HistoryFragment newInstance(String type) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void getRequestList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        presenterInterface.sendRequest(getActivity(), "", map, ApiCallInterface.REQUEST_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentBinding.inflate(inflater);
        presenterInterface = new Presenter(this);
        binding.rvAppointment.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        historyAdapter = new HistoryAdapter(getActivity());
        binding.rvAppointment.setAdapter(historyAdapter);

        if (getArguments().getString("type").equalsIgnoreCase("4")) {
            type = "4";
            getRequestList();
        } else {
            type = "5";
            getRequestList();
        }

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

        if (ApiCallInterface.REQUEST_LIST == requestCode) {

            RequestModel baseModel = (RequestModel) success;
            if (baseModel.isStatus().equals("true")) {

                if (baseModel.getData() != null && baseModel.getData().size() > 0) {

                    binding.rvAppointment.setVisibility(View.VISIBLE);
                    binding.tvNoData.setVisibility(View.GONE);
                    historyAdapter.setData(baseModel.getData());

                } else {

                    binding.rvAppointment.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);

                }

            }else {

                binding.rvAppointment.setVisibility(View.GONE);
                binding.tvNoData.setVisibility(View.VISIBLE);

            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}