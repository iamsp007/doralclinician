package com.android.doral;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.viewpager.widget.ViewPager;

import com.android.CovidFormActivity;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityCovidStepTwoBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.CovidFormModel;
import com.android.doral.retrofit.model.StateModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Covid_Step_Two_Activity extends BaseFragment implements BaseViewInterface {
    ActivityCovidStepTwoBinding binding;
    private BasePresenterInterface presenterInterface;
    private String state_code;
    private String state;
    private String city_code;
    private List<CityModel> cityList;


    ViewPager viewPager;


    public Covid_Step_Two_Activity(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid_Step_Two_Activity newInstance(ViewPager viewPager) {
        Covid_Step_Two_Activity fragment = new Covid_Step_Two_Activity(viewPager);


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityCovidStepTwoBinding.inflate(getLayoutInflater());


        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(getActivity(), null, null, ApiCallInterface.STATES, false);
        presenterInterface.sendRequest(getActivity(), null, null, ApiCallInterface.CITY, false);
        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    String address = binding.edtBuilding.getText().toString() + "," + binding.edtAddress.getText().toString();
                     ((CovidFormActivity) getActivity()).formModel.setAddress(address);
                     ((CovidFormActivity) getActivity()).formModel.setZip(binding.edtZip.getText().toString());
                     ((CovidFormActivity) getActivity()).formModel.setState(state);
                     ((CovidFormActivity) getActivity()).formModel.setCity(city_code);

                     ((CovidFormActivity) getActivity()).formModel.setPreferred_language(binding.spLanguage.getSelectedItem().toString());
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    //startActivity(new Intent(Covid_Step_Two_Activity.this, Covid_Step_Three_Activity.class).putExtra("form",  ((CovidFormActivity) getActivity()).formModel));
                }
            }
        });
        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    state_code = ((StateModel) binding.spState.getSelectedItem()).getId();
                    state = ((StateModel) binding.spState.getSelectedItem()).getState();

                    ArrayAdapter<CityModel> adapter = new ArrayAdapter<CityModel>
                            (getActivity(), android.R.layout.select_dialog_item, getCityList(((StateModel) binding.spState.getSelectedItem()).getState_code()));
                    binding.spCity.setThreshold(1); //will start working from first character
                    binding.spCity.setAdapter(adapter);

                    //setCitySpinner(binding.spCity, getCityList(((StateModel) binding.spState.getSelectedItem()).getState_code()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CityModel cityModel = (CityModel) adapterView.getItemAtPosition(i);
                city_code = cityModel.getCity();
            }
        });
        /*binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    // binding.tvCity.setText(((CityModel) binding.spCity.getSelectedItem()).getCity());
                    city_code = binding.spCity.getText().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        return binding.getRoot();
    }



    private List<CityModel> getCityList(String state_code) {
        List<CityModel> modelList = new ArrayList<>();

        if (cityList != null) {
            for (int i = 0; i < cityList.size(); i++) {
                if (cityList.get(i).getState_code().equals(state_code)) {
                    modelList.add(cityList.get(i));
                }
            }
        }
        return modelList;
    }

    private boolean isValidate() {

        if (binding.spLanguage.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "Please select preferred language");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtAddress.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter address");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtBuilding.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter building");
            return false;
        } else if (!StringUtils.isNotEmpty(state_code)) {
            errorMessage(binding.getRoot(), "Please select state");
            return false;
        } else if (!StringUtils.isNotEmpty(city_code)) {
            errorMessage(binding.getRoot(), "Please select city");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtZip.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter zip");
            return false;
        }

        return true;
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        if (requestCode == ApiCallInterface.STATES) {
            List<StateModel> stateModelList = (List<StateModel>) success;
            if (stateModelList != null) {

                setStatteSpinner(binding.spState, stateModelList);
            }
        }
        if (requestCode == ApiCallInterface.CITY) {
            cityList = (List<CityModel>) success;

        }
    }

    private void setStatteSpinner(AppCompatSpinner spinner, List<StateModel> list) {

        StateModel spinnerModel = new StateModel("select");
        list.add(0, spinnerModel);

        ArrayAdapter<StateModel> arrayAdapter = new ArrayAdapter<StateModel>(getActivity(), R.layout.spinner_row, list) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View spinnerView = super.getDropDownView(position, convertView, parent);
                TextView spinnerTextV = (TextView) spinnerView;
                if (position == 0) {
                    spinnerTextV.setTextColor(Color.parseColor("#a7a7a6"));
                } else {
                    spinnerTextV.setTextColor(Color.parseColor("#b2000000"));
                }

                return spinnerView;
            }
        };

        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        spinner.setAdapter(arrayAdapter);

    }

    private void setCitySpinner(AppCompatSpinner spinner, List<CityModel> list) {

        CityModel spinnerModel = new CityModel("select");
        list.add(0, spinnerModel);

        ArrayAdapter<CityModel> arrayAdapter = new ArrayAdapter<CityModel>(context, R.layout.spinner_row, list) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View spinnerView = super.getDropDownView(position, convertView, parent);
                TextView spinnerTextV = (TextView) spinnerView;
                if (position == 0) {
                    spinnerTextV.setTextColor(Color.parseColor("#a7a7a6"));
                } else {
                    spinnerTextV.setTextColor(Color.parseColor("#b2000000"));
                }

                return spinnerView;
            }
        };

        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        spinner.setAdapter(arrayAdapter);

    }





}
