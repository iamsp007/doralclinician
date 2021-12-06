package com.android.doral.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;

import com.android.doral.ImagePickerActivity;
import com.android.doral.databinding.CustomAlertDialogBinding;
import com.android.doral.databinding.SignatureDialogBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static androidx.core.content.FileProvider.getUriForFile;


public class SignatureDialog extends AppCompatDialog implements View.OnClickListener {
    private final Context context;

    private final OnsaveClickListener onClickListener;
    SignatureDialogBinding mBinding;


    public SignatureDialog(Context context, OnsaveClickListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = SignatureDialogBinding.inflate(LayoutInflater.from(context), null, false);
        setContentView(mBinding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.92);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        mBinding.tvOk.setOnClickListener(this);
        mBinding.tvClear.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mBinding.tvOk.getId()) {
            Dexter.withActivity((Activity) context)
                    .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {

                                if (mBinding.signatureView.getSignatureBitmap() != null) {
                                    saveTempBitmap(mBinding.signatureView.getSignatureBitmap());

                                }
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();


        }
        if (v.getId() == mBinding.tvClear.getId()) {

            mBinding.signatureView.clearCanvas();
        }

    }

    private Uri getCacheImagePath(String fileName) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(context, context.getPackageName() + ".provider", image);
    }

    public interface OnsaveClickListener {
        void onItemClick(String filepath);
    }

    public void saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    private void saveImage(Bitmap finalBitmap) {


        File file = getOutputMediaFile();
        if (file.exists()) file.delete();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            if (onClickListener != null) {
                onClickListener.onItemClick(file.getPath());
                dismiss();
            }
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }


    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = "" + System.currentTimeMillis();
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
