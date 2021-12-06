package com.android.doral.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.android.doral.ApplicationActivity;
import com.android.doral.R;
import com.android.doral.adapter.ReferanceAdapter;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.FragmentReferenceBinding;
import com.android.doral.retrofit.model.ReferenceModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReferenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReferenceFragment extends BaseFragment implements View.OnClickListener {

    FragmentReferenceBinding binding;
    ReferanceAdapter adapter;
    ViewPager viewPager;

    public ReferenceFragment(ViewPager viewPager) {
        this.viewPager = viewPager;
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReferenceFragment newInstance(ViewPager viewPager) {
        ReferenceFragment fragment = new ReferenceFragment(viewPager);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReferenceBinding.inflate(inflater);
        setRefrecseList();
        return binding.getRoot();
    }


    private void setRefrecseList() {
        adapter = new ReferanceAdapter(getActivity());
        binding.rvReference.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvReference.setAdapter(adapter);
        binding.addReference.setOnClickListener(this);
        binding.tvNext.setOnClickListener(this);
        binding.tvPrevious.setOnClickListener(this);
        addrefrence();

    }


    private void addrefrence() {
        ReferenceModel referenceModel = new ReferenceModel("", "", "", "");
        adapter.setData(referenceModel);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_reference) {
            if (isValidate()) {
                addrefrence();
            }
        }
        if (view.getId() == R.id.tv_next) {
            if (isValidate()) {
                ((ApplicationActivity) getActivity()).map.put("reference", adapter.getDataInString());
                ((ApplicationActivity) getActivity()).map.put("bonded", binding.rgBonded.getCheckedRadioButtonId() == R.id.rb_bonded_yes ? "1" : "0");
                ((ApplicationActivity) getActivity()).map.put("refused_bond", binding.rgRefused.getCheckedRadioButtonId() == R.id.rb_refused_yes ? "1" : "0");
                ((ApplicationActivity) getActivity()).map.put("convicted_crime", binding.rgCrime.getCheckedRadioButtonId() == R.id.rb_crime_yes ? "1" : "0");
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }

        }
        if (view.getId() == R.id.tv_previous) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }

    }

    private boolean isValidate() {
        if (adapter.getData().get(adapter.getData().size() - 1).getReferance_name().equalsIgnoreCase("") || adapter.getData().get(adapter.getData().size() - 1).getReference_address().equalsIgnoreCase("") || adapter.getData().get(adapter.getData().size() - 1).getReference_phone().equalsIgnoreCase("") || adapter.getData().get(adapter.getData().size() - 1).getReference_relationship().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter above data");
            return false;
        }

        return true;
    }
}