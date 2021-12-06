package com.android.doral.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.android.doral.R;
import com.android.doral.databinding.PhotopickDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class PhotoSelectDialog extends BottomSheetDialogFragment {

    private PhotopickDialogBinding binding;
    private Context context;
    View.OnClickListener onClickListener;
    boolean isRemove;

    public PhotoSelectDialog(Context context, boolean isRemove, View.OnClickListener onClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.isRemove = isRemove;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.photopick_dialog, null, false);

        binding.tvRemove.setOnClickListener(onClickListener);
        binding.tvCamera.setOnClickListener(onClickListener);
        binding.tvGallery.setOnClickListener(onClickListener);
        binding.tvPdfdoc.setOnClickListener(onClickListener);
        if (isRemove) {
            binding.tvRemove.setVisibility(View.VISIBLE);
        } else {
            binding.tvRemove.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }


}