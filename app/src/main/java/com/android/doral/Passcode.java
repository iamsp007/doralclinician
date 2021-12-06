package com.android.doral;

import android.animation.ObjectAnimator;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityPasscodeBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.ParentIdModel;

import java.util.HashMap;

public class Passcode extends BaseActivity implements BaseViewInterface, View.OnClickListener {

    ActivityPasscodeBinding binding;
    BasePresenterInterface presenterInterface;
    private String str = "";
    private String strPasslock = "", strRePasslock = "", passcode,email, f_name = "", l_name = "", user_id = "", SETTING = "logout";
    private ImageView imgPass1, imgPass2, imgPass3, imgPass4;
    private boolean isRePass, isNew;
    private RelativeLayout btnDalet;

    private CancellationSignal cancellationSignal = null;

    // create an authenticationCallback
    private BiometricPrompt.AuthenticationCallback authenticationCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasscodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);


        imgPass1 = (ImageView) findViewById(R.id.img1);
        imgPass2 = (ImageView) findViewById(R.id.img2);
        imgPass3 = (ImageView) findViewById(R.id.img3);
        imgPass4 = (ImageView) findViewById(R.id.img4);


        if (isNew) {
            binding.activityForgotPasswordTvForgotTitle.setText("Set Passcode");
        } else {
            binding.activityForgotPasswordTvForgotTitle.setText("Enter Passcode");
            binding.passcodeLogout.setVisibility(View.VISIBLE);

        }

//        if (getIntent().hasExtra("email")) {
//            email = (String) getIntent().getStringExtra("email");
//            Log.e("passcode", email);
//        }



        binding.btnOne.setOnClickListener(this);
        binding.btnTwo.setOnClickListener(this);
        binding.btnThree.setOnClickListener(this);
        binding.btnFour.setOnClickListener(this);
        binding.btnFive.setOnClickListener(this);
        binding.btnSix.setOnClickListener(this);
        binding.btnSeven.setOnClickListener(this);
        binding.btnEight.setOnClickListener(this);
        binding.btnNine.setOnClickListener(this);
        binding.btnZero.setOnClickListener(this);
//        btnDalet.setOnClickListener(this);
        binding.imgBackDelete.setOnClickListener(this);
        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.passcodeLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Passcode.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.changepasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Passcode.this, ChangePasscode.class);
                startActivity(intent);
            }
        });

        binding.finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }


    private CancellationSignal getCancellationSignal()
    {
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(
                new CancellationSignal.OnCancelListener() {
                    @Override public void onCancel()
                    {
                        notifyUser("Authentication was Cancelled by the user");
                    }
                });
        return cancellationSignal;
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private Boolean checkBiometricSupport()
    {
        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            notifyUser("Fingerprint authentication has not been enabled in settings");
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled");
            return false;
        }
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true;
        }
        else
            return true;
    }

    // this is a toast method which is responsible for
    // showing toast it takes a string as parameter
    private void notifyUser(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        if (requestCode == ApiCallInterface.Registerpass) {
            BaseModel baseModel = (BaseModel) success;

            if (baseModel.isStatus().equals("true")) {

                new CustomAlertDialog(context, "can you use fingerprint ?", "Confirm your email address", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BiometricPrompt biometricPrompt = new BiometricPrompt
                                .Builder(getApplicationContext())
                                .setTitle("Title of Prompt")
                                .setSubtitle("Subtitle")
                                .setDescription("Uses FP")
                                .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void
                                    onClick(DialogInterface dialogInterface, int i)
                                    {
                                        notifyUser("Authentication Cancelled");
                                    }
                                }).build();

                        // start the authenticationCallback in
                        // mainExecutor
                        biometricPrompt.authenticate(
                                getCancellationSignal(),
                                getMainExecutor(),
                                authenticationCallback);

                    }
                }).show();





//                Intent intent = new Intent(Passcode.this, LoginActivity.class);
//                startActivity(intent);
//                if (StringUtils.isNotEmpty(baseModel.getData().getParent_id())) {
//                    Intent intent = new Intent(NewDashboardActivity.this, RoadLStatusActivity.class);
//                    intent.putExtra("parent_id", baseModel.getData().getParent_id());
//                    startActivity(intent);
//                } else {
//                    startActivity(new Intent(NewDashboardActivity.this, RoadlRequestActivity.class).putExtra("patient_id", appontmentModel.getPatient_id()));
//                }
//
//            } else {
//                startActivity(new Intent(NewDashboardActivity.this, RoadlRequestActivity.class).putExtra("patient_id", appontmentModel.getPatient_id()));
            }

            authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                // here we need to implement two methods
                // onAuthenticationError and
                // onAuthenticationSucceeded If the
                // fingerprint is not recognized by the
                // app it will call onAuthenticationError
                // and show a toast
                @Override
                public void onAuthenticationError(
                        int errorCode, CharSequence errString)
                {
                    super.onAuthenticationError(errorCode, errString);
                    notifyUser("Authentication Error : " + errString);
                }
                // If the fingerprint is recognized by the
                // app then it will call
                // onAuthenticationSucceeded and show a
                // toast that Authentication has Succeed
                // Here you can also start a new activity
                // after that
                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result)
                {
                    super.onAuthenticationSucceeded(result);
                    notifyUser("Authentication Succeeded");
                    // or start a new Activity
//                    Intent intent = new Intent(Passcode.this, NewDashboardActivity.class);
//                    startActivity(intent);
                    registerfinger();

                }
            };
//
            checkBiometricSupport();
         }

        if (requestCode == ApiCallInterface.Passcodelogin) {
            BaseModel baseModel = (BaseModel) success;

            if (baseModel.isStatus().equals("true")) {
                Intent intent = new Intent(Passcode.this, NewDashboardActivity.class);
                startActivity(intent);
//                if (StringUtils.isNotEmpty(baseModel.getData().getParent_id())) {
//                    Intent intent = new Intent(NewDashboardActivity.this, RoadLStatusActivity.class);
//                    intent.putExtra("parent_id", baseModel.getData().getParent_id());
//                    startActivity(intent);
//                } else {
//                    startActivity(new Intent(NewDashboardActivity.this, RoadlRequestActivity.class).putExtra("patient_id", appontmentModel.getPatient_id()));
//                }
//
//            } else {
//                startActivity(new Intent(NewDashboardActivity.this, RoadlRequestActivity.class).putExtra("patient_id", appontmentModel.getPatient_id()));
            }
//
        }

        if (requestCode == ApiCallInterface.Fingerregister) {
            BaseModel baseModel = (BaseModel) success;

            if (baseModel.isStatus().equals("true")) {
                Intent intent = new Intent(Passcode.this, NewDashboardActivity.class);
                startActivity(intent);
//                if (StringUtils.isNotEmpty(baseModel.getData().getParent_id())) {
//                    Intent intent = new Intent(NewDashboardActivity.this, RoadLStatusActivity.class);
//                    intent.putExtra("parent_id", baseModel.getData().getParent_id());
//                    startActivity(intent);
//                } else {
//                    startActivity(new Intent(NewDashboardActivity.this, RoadlRequestActivity.class).putExtra("patient_id", appontmentModel.getPatient_id()));
//                }
//
//            } else {
//                startActivity(new Intent(NewDashboardActivity.this, RoadlRequestActivity.class).putExtra("patient_id", appontmentModel.getPatient_id()));
            }
//
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOne:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                            "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnOne.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnOne.getText().toString());
                        setPointView();
                    }
                }
                break;
            case R.id.btnTwo:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                            "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnTwo.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnTwo.getText().toString());
                        setPointView();
                    }
                }
                break;
            case R.id.btnThree:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                            "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnThree.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnThree.getText().toString());
                        setPointView();
                    }
                }
                break;
            case R.id.btnFour:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                            "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnFour.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnFour.getText().toString());
                        setPointView();
                    }
                }
                break;
            case R.id.btnFive:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                            "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnFive.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnFive.getText().toString());
                        setPointView();
                    }
                }
                break;
            case R.id.btnSix:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                            "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnSix.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnSix.getText().toString());
                        setPointView();
                    }
                }
                break;
            case R.id.btnSeven:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                            "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnSeven.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnSeven.getText().toString());
                        setPointView();
                    }
                }
                break;
            case R.id.btnEight:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                            "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnEight.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnEight.getText().toString());
                        setPointView();
                    }
                }
                break;
            case R.id.btnNine:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                      "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnNine.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnNine.getText().toString());
                        setPointView();
                    }
                }
                break;
            case R.id.btnZero:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !checkPermission()) {
//                    Functions.showAlertMessageWithOK(PasscodeActivity.this, "",
//                            "Permission allows us to access Dashboard. \nPlease allow in App Settings for additional functionality.");
                    if (str.length() < 4) {
                        str = str.concat(binding.btnZero.getText().toString());
                        setPointView();
                    }
                } else {
                    if (str.length() < 4) {
                        str = str.concat(binding.btnZero.getText().toString());
                        setPointView();
                    }
                }
                break;
//            case R.id.btnDalet:
//                if (str.length() > 0) {
//                    str = str.substring(0, str.length() - 1);
//                    setPointView();
//                }
//                break;
            case R.id.imgBackDelete:
                if (str.length() > 0) {
                    str = str.substring(0, str.length() - 1);
                    setPointView();
                }
                break;
//            case R.id.passcode_logout:
//                Logout_API(sh.getString(SharedPreferences_String_Name.API_KEY, ""));
//                break;
            default:
                break;
        }
      //  Functions.printLog("strpassCode", strPasslock);
    }

    private boolean checkPermission() {
        return false;
    }

     void setPointView() {

        imgPass1.setImageResource(R.drawable.empty_dot);
        imgPass2.setImageResource(R.drawable.empty_dot);
        imgPass3.setImageResource(R.drawable.empty_dot);
        imgPass4.setImageResource(R.drawable.empty_dot);
        for (int i = 0; i < str.length(); i++) {
            if (i == 0) {
                imgPass1.setImageResource(R.drawable.passcode_blue);
            } else if (i == 1) {
                imgPass2.setImageResource(R.drawable.passcode_blue);
            } else if (i == 2) {
                imgPass3.setImageResource(R.drawable.passcode_blue);
            } else if (i == 3) {
                imgPass4.setImageResource(R.drawable.passcode_blue);
            }
        }


        if (str.length() == 4) {
//            Functions.printLog("TAG", isNew + "");
            if (!isNew) {
                if (!isRePass) {
                    isRePass = true;
                    strRePasslock = str;
                    str = "";
                    binding.activityForgotPasswordTvForgotTitle.setText("Verify Passcode");
                    setPointView();
                } else {
                    if (str.equals(strRePasslock)) {
//                        if (Functions.inBackground == true) {
//                            Functions.inBackground = false;
//                            finish();
//                        } else {
                        Log.e("strPasslock", str);
//                        if (!new MyPref(context).getData(MyPref.Keys.IsLogin, false)) {
////                            Intent intent = new Intent(Passcode.this, NewDashboardActivity.class);
////                            startActivity(intent);
//
//                            passcodelogin();
//                        }else{
//                            registerpasscode();
//                        }

                        if (getIntent().hasExtra("passcode")) {
                            passcode = (String) getIntent().getStringExtra("passcode");
                            Log.e("passcode",passcode);
                            passcodelogin();
                        }else{
                            registerpasscode();
                        }
                      //  registerpasscode();
//                        }
//
                    } else {
                        isRePass = false;
                        binding.activityForgotPasswordTvForgotTitle.setVisibility(View.VISIBLE);
                        binding.activityForgotPasswordTvForgotTitle.setText("Passcode do not match.\nPlease try again.");
                        binding.activityForgotPasswordTvForgotTitle.setText("Set Passcode");
                        ObjectAnimator.ofFloat(binding.mainLayout, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                                .setDuration(500).start();
                        str = "";
                        setPointView();
                    }
                }
            } else {
//                if (str.equals(passcode)) {
////                    if (Functions.inBackground == true) {
////                        Functions.inBackground = false;
////                        finish();
////                    } else {
//                    Intent i = new Intent(Passcode.this, NewDashboardActivity.class);
////                    i.putExtra("Home", "Dashboard");
////                    i.putExtra("m_deviceId", getIntent().getStringExtra("m_deviceId"));
////                    i.putExtra("fcm_id", getIntent().getStringExtra("fcm_id"));
////                    if (!page.isEmpty()) {
////                        i.putExtra("page", page);
////                    }
//                    startActivity(i);
////                    Functions.printLog("Here is", "2");
//                    finish();
////                    }
//                } else {
//                    binding.activityForgotPasswordTvForgotTitle.setVisibility(View.VISIBLE);
//                    binding.activityForgotPasswordTvForgotTitle.setText("Wrong Passcode");
//                    ObjectAnimator.ofFloat(binding.mainLayout, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
//                            .setDuration(500).start();
//                    str = "";
//                    setPointView();
//                }
            }
        }
    }

    private void passcodelogin() {
        HashMap<String, String> map = new HashMap<>();
        map.put(   "device_type","1");
        map.put(   "login_type","passcode");
        map.put(  "passcode",str);
        presenterInterface.sendRequest(context, null, map, ApiCallInterface.Passcodelogin);
    }

    private void registerpasscode() {
        HashMap<String, String> map = new HashMap<>();
        map.put(   "device_type","1");
        map.put(   "type","clinician");
        map.put(  "email","dishank2696@gmail.com");
        map.put(  "passcode",str);
        presenterInterface.sendRequest(context, null, map, ApiCallInterface.Registerpass);
    }

    private void registerfinger() {
        HashMap<String, String> map = new HashMap<>();
        map.put(   "device_type","1");
        map.put(   "type","clinician");
        map.put(  "email","dishank2696@gmail.com");
        map.put(  "finger_print","1");
        presenterInterface.sendRequest(context, null, map, ApiCallInterface.Fingerregister);
    }
}