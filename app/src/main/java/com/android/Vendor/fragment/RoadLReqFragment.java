package com.android.Vendor.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.Vendor.Adapter.OnTrackAdapter;
import com.android.Vendor.Adapter.RoadLReqAdapter;
import com.android.doral.base.BaseFragment;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.FragmentRoadlReqBinding;
import com.android.doral.register.BasePresenterInterface;

public class RoadLReqFragment extends BaseFragment implements BaseViewInterface {

    private FragmentRoadlReqBinding binding;
    BasePresenterInterface presenterInterface;
    RoadLReqAdapter roadLReqAdapter;
    OnTrackAdapter onTrackAdapter;

    public RoadLReqFragment() {

    }

    public static RoadLReqFragment newInstance(String type) {
        RoadLReqFragment fragment = new RoadLReqFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRoadlReqBinding.inflate(inflater);

        if (getArguments().getString("type").equalsIgnoreCase("1")) {

            binding.rvAppointment.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            roadLReqAdapter = new RoadLReqAdapter(getActivity());
            binding.rvAppointment.setAdapter(roadLReqAdapter);

        } else {

            binding.rvAppointment.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            onTrackAdapter = new OnTrackAdapter(getActivity());
            binding.rvAppointment.setAdapter(onTrackAdapter);

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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}