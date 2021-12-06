package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.android.CovidFormActivity;
import com.android.doral.Utils.StringUtils;
import com.android.doral.adapter.QuetionAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.ActivityCovidStepSevenBinding;
import com.android.doral.retrofit.model.CovidFormModel;
import com.android.doral.retrofit.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class Covid_Step_Seven_Activity extends BaseFragment {
    ActivityCovidStepSevenBinding binding;
    List<QuestionModel> list = new ArrayList<>();

    QuetionAdapter quetionAdapter;

    ViewPager viewPager;


    public Covid_Step_Seven_Activity(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid_Step_Seven_Activity newInstance(ViewPager viewPager) {
        Covid_Step_Seven_Activity fragment = new Covid_Step_Seven_Activity(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityCovidStepSevenBinding.inflate(getLayoutInflater());

        list.add(new QuestionModel("Are you feeling sick today?"));
        list.add(new QuestionModel("In the last 10 days, have you had a COVID-19 test because you had symptoms and are still\n" +
                "awaiting your test results or been told by a health care provider or health department to\n" +
                "isolate or quarantine at home due to COVID-19 infection, exposure or travel?"));
        list.add(new QuestionModel("Have you been treated with antibody therapy or convalescent plasma for COVID-19 in the past\n" +
                "90 days (3 months)? If yes, when did you receive the last dose?"));
        list.add(new QuestionModel("Have you ever had an immediate allergic reaction (e.g., hives,facial swelling, difficulty breathing,\n" +
                "anaphylaxis)to any vaccine, injection, or shot or to any component of the COVID-19 vaccine, or a\n" +
                "severe allergic reaction (anaphylaxis) to anything?"));
        list.add(new QuestionModel("Have you had any vaccines in the past 14 days (2 weeks) including flu shot?\n" +
                "If yes, how long ago was your most recent vaccine?"));
        list.add(new QuestionModel("Are you pregnant or considering becoming pregnant?"));
        list.add(new QuestionModel("Do you have cancer, leukemia, HIV/AIDS, a history of  autoimmune disease or any other condition\n" +
                "that weakens the immune system?"));
        list.add(new QuestionModel("Do you take any medications that affect your immune system, such as cortisone, prednisone or\n" +
                "other steroids, anticancer drugs, or have you had any radiation treatments?\n"));
        list.add(new QuestionModel("Do you have a bleeding disorder or are you taking a blood thinner?"));
        list.add(new QuestionModel("Have you received a previous dose of the COVID-19 vaccine? If yes, which vaccine?"));

        binding.rvQuestion.setLayoutManager(new LinearLayoutManager(getActivity()));
        quetionAdapter = new QuetionAdapter(getActivity(), list);
        binding.rvQuestion.setAdapter(quetionAdapter);
        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidate()) {
                    ((CovidFormActivity) getActivity()).formModel.setQuestionnaire(quetionAdapter.getData());
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }
            }
        });

        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        return binding.getRoot();
    }

    private boolean isValidate() {
        if (quetionAdapter != null) {

            for (int i = 0; i < quetionAdapter.getData().size(); i++) {

                if (!StringUtils.isNotEmpty(quetionAdapter.getData().get(i).getAns())) {
                    errorMessage(binding.getRoot(), "please give all answer");
                    return false;
                }

            }

        }
        return true;
    }
}
