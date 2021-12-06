package com.android.doral.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.doral.ApplicationActivity;
import com.android.doral.R;
import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseFragment;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.FragmentEmergencyContactBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmergencyContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergencyContactFragment extends BaseFragment implements View.OnClickListener, BaseViewInterface {
    FragmentEmergencyContactBinding binding;
    ViewPager viewPager;
    BasePresenterInterface presenterInterface;

    public EmergencyContactFragment(ViewPager viewPager) {
        this.viewPager = viewPager;
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static EmergencyContactFragment newInstance(ViewPager viewPager) {
        EmergencyContactFragment fragment = new EmergencyContactFragment(viewPager);

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
        binding = FragmentEmergencyContactBinding.inflate(inflater);
        binding.tvNext.setOnClickListener(this);
        binding.edtRelation.setOnClickListener(this);
        presenterInterface = new Presenter(this);
        binding.edtPhoneNumber.addTextChangedListener(new PhoneTextFormatter(binding.edtPhoneNumber, "+1 (###) ###-####"));

        binding.spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    binding.edtRelation.setText(binding.spRelation.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return binding.getRoot();
    }

    public boolean validate() {

        if (!StringUtils.isNotEmpty(binding.edtName.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter name");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtAddress.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter address");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtPhoneNumber.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter phone number");
            return false;
        } else if (binding.spRelation.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "please select relation");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_next) {
            if (validate()) {

                ((ApplicationActivity) getActivity()).map.put("emergency_name", binding.edtName.getText().toString());
                ((ApplicationActivity) getActivity()).map.put("emergency_address", binding.edtAddress.getText().toString());
                ((ApplicationActivity) getActivity()).map.put("emergency_phone", binding.edtPhoneNumber.getText().toString());
                ((ApplicationActivity) getActivity()).map.put("emergency_relationship", binding.edtRelation.getText().toString());
                presenterInterface.sendRequest(getActivity(), null, ((ApplicationActivity) getActivity()).map, ApiCallInterface.ADD_APPLICANT, true);
            }
        }
        if (view.getId() == R.id.edt_relation) {
            binding.spRelation.performClick();
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

    }
}