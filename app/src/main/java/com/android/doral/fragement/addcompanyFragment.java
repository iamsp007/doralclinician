package com.android.doral.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.doral.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addcompanyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addcompanyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addcompanyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static addcompanyFragment newInstance() {
        addcompanyFragment fragment = new addcompanyFragment();

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
        return inflater.inflate(R.layout.add_company, container, false);
    }
}