package com.android.doral.fragement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.doral.ApplicationActivity;
import com.android.doral.NurseApplicationDetailActivity;
import com.android.doral.R;
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseFragment;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.FragmentAddressDetailsBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.StateModel;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddressDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressDetailsFragment extends BaseFragment implements BaseViewInterface, View.OnClickListener {


    FragmentAddressDetailsBinding binding;
    BasePresenterInterface presenterInterface;
    private String state_code;
    private String city_code;
    private List<CityModel> cityList;
    List<StateModel> stateModelList;
    private static final int PLACE_REQUEST = 997;
    private static final int PLACE_REQUEST_PRIOR = 998;
    ViewPager viewPager;

    public AddressDetailsFragment(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static AddressDetailsFragment newInstance(ViewPager viewPager) {
        AddressDetailsFragment fragment = new AddressDetailsFragment(viewPager);

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
        binding = FragmentAddressDetailsBinding.inflate(inflater);

        presenterInterface = new Presenter(this);
        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), getString(R.string.google_api_key));
        }
        presenterInterface.sendRequest(getActivity(), null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(getActivity(), null, null, ApiCallInterface.CITY);
        binding.tvState.setOnClickListener(this);
        binding.tvCity.setOnClickListener(this);
        binding.tvNext.setOnClickListener(this);
        binding.tvPrevious.setOnClickListener(this);

        binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    state_code = ((StateModel) binding.spState.getSelectedItem()).getId();
                    binding.tvState.setText(((StateModel) binding.spState.getSelectedItem()).getState());
                    setCitySpinner(binding.spCity, getCityList(((StateModel) binding.spState.getSelectedItem()).getState_code()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    binding.tvCity.setText(((CityModel) binding.spCity.getSelectedItem()).getCity());
                    city_code = ((CityModel) binding.spCity.getSelectedItem()).getId();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.edtAddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                        .build(getActivity());
                startActivityForResult(intent, PLACE_REQUEST);
            }
        });
        return binding.getRoot();
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
            stateModelList = (List<StateModel>) success;
            if (stateModelList != null) {

                setStatteSpinner(binding.spState, stateModelList);
            }
        }
        if (requestCode == ApiCallInterface.CITY) {
            cityList = (List<CityModel>) success;

        }

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

    private void setCitySpinner(AppCompatSpinner spinner, List<CityModel> list) {

        CityModel spinnerModel = new CityModel("select");
        list.add(0, spinnerModel);

        ArrayAdapter<CityModel> arrayAdapter = new ArrayAdapter<CityModel>(getActivity(), R.layout.spinner_row, list) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
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

    private void setStatteSpinner(AppCompatSpinner spinner, List<StateModel> list) {

        StateModel spinnerModel = new StateModel("select");
        list.add(0, spinnerModel);

        ArrayAdapter<StateModel> arrayAdapter = new ArrayAdapter<StateModel>(getActivity(), R.layout.spinner_row, list) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvState) {
            binding.spState.performClick();
        }
        if (view.getId() == R.id.tvCity) {
            binding.spCity.performClick();
        }
        if (view.getId() == R.id.tv_next) {
            if (validate()) {
                ((ApplicationActivity) getActivity()).map.put("address_line_1", binding.edtAddress1.getText().toString());
                ((ApplicationActivity) getActivity()).map.put("address_line_2", binding.edtAddress1.getText().toString());
                ((ApplicationActivity) getActivity()).map.put("city", binding.tvCity.getText().toString());
                ((ApplicationActivity) getActivity()).map.put("state", binding.tvState.getText().toString());
                ((ApplicationActivity) getActivity()).map.put("zip", binding.edtZipCode.getText().toString());
                ((ApplicationActivity) getActivity()).map.put("address_life", "Within six months");
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }

        }
        if (view.getId() == R.id.tv_previous) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public boolean validate() {

        if (!StringUtils.isNotEmpty(binding.edtAddress1.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter address1");
            return false;
        }
//        else if (!StringUtils.isNotEmpty(binding.edtBuilding.getText().toString())) {
//            errorMessage(binding.getRoot(), "please enter building name");
//            return false;
//        }
        else if (!StringUtils.isNotEmpty(binding.tvState.getText().toString())) {
            errorMessage(binding.getRoot(), "please select state");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.tvCity.getText().toString())) {
            errorMessage(binding.getRoot(), "please select city");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtZipCode.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter zip code");
            return false;
        }
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PLACE_REQUEST) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.edtAddress1.setText(place.getAddress());
                    binding.tvCity.setText(Utility.getCity(getActivity(), place.getLatLng()));
                    binding.tvState.setText(Utility.getState(getActivity(), place.getLatLng()));
                    binding.edtZipCode.setText(Utility.getPostalCode(getActivity(), place.getLatLng()));

                    for (int i = 0; i < stateModelList.size(); i++) {
                        if (stateModelList.get(i).getState().equalsIgnoreCase(binding.tvState.getText().toString())) {
                            state_code = stateModelList.get(i).getId();
                        }
                    }
                    for (int i = 0; i < cityList.size(); i++) {
                        if (cityList.get(i).getCity().equalsIgnoreCase(binding.tvCity.getText().toString())) {
                            city_code = cityList.get(i).getId();
                        }
                    }

//                    Log.e("CIty ID", city_code);
//                    Log.e("STATE ID", state_code);
                    // do query with address

                }
            }

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // TODO: Handle the error.
            Status status = Autocomplete.getStatusFromIntent(data);

            Log.i("TAG", status.getStatusMessage());
        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
        }

    }
}