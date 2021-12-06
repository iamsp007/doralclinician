package com.android.doral;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.Vendor.Activity.VendorDashboardActivity;
import com.android.doral.Utils.BottomSheetAskPermission;
import com.android.doral.Utils.GpsUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.SingleShotLocationProvider;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.databinding.ActivityLoginBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.Presenter;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.RegisterViewInterface;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.LoginModel;
import com.android.doral.retrofit.model.OtpResponseModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.service.GeoService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends BaseActivity implements RegisterViewInterface {
    ActivityLoginBinding binding;
    BasePresenterInterface presenterInterface;
    String newToken = "";
    UserModel userModel;
    private int passwordNotVisible=1;
    private CancellationSignal cancellationSignal = null;

    // create an authenticationCallback
    private BiometricPrompt.AuthenticationCallback authenticationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setStatusBarColor(R.color.white);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
        }
        init();
    }

    private void init() {
        presenterInterface = new Presenter(this);

        if (StringUtils.isNotEmpty(new MyPref(context).getData(MyPref.Keys.USER_NAME))) {
            binding.cbRemember.setChecked(true);
            binding.activityLoginEtEmail.setText(new MyPref(context).getData(MyPref.Keys.USER_NAME));
            binding.activityLoginEtPassword.setText(new MyPref(context).getData(MyPref.Keys.PASSWORD));
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        newToken = task.getResult();

                        // Log and toast

                    }
                });


        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        binding.tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        binding.pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Passcode.class);
                                intent.putExtra("passcode", "passcode");
                              //  Log.e("passcode",userModel.getPasscode());
                                startActivity(intent);
            }
        });

        binding.finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, Passcode.class);
//                intent.putExtra("fin", "fin");
//                  Log.e("passcode","1");
//                startActivity(intent);
                new CustomAlertDialog(context, "can you use fingerprint ?", "Confirm your email address", "OK", "Skip", new View.OnClickListener() {
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
                  //  registerfinger();

                }


            };
//
                checkBiometricSupport();

            }
        });

        ImageView imagepass = (ImageView) findViewById(R.id.imagepassword);
        imagepass.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (passwordNotVisible == 1) {
                    binding.activityLoginEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    imagepass.setImageResource(R.drawable.ic_baseline_visibility_24);
                    passwordNotVisible = 0;
                } else {

                    binding.activityLoginEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imagepass.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    passwordNotVisible = 1;
                }

                binding.activityLoginEtPassword.setSelection(binding.activityLoginEtPassword.length());


            }

        });



//        ImageView imagepass = (ImageView) findViewById(R.id.imagepassword);
//        imagepass .setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        binding.activityLoginEtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
//                        imagepass.setImageResource(R.drawable.ic_baseline_visibility_off_24);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        binding.activityLoginEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                        imagepass.setImageResource(R.drawable.ic_baseline_visibility_24);
//                        break;
//                }
//                return true;
//            }
//        });



        binding.activityLoginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {

                    LoginModel userModel = new LoginModel();
                    userModel.setUsername(binding.activityLoginEtEmail.getText().toString());
                    userModel.setPassword(binding.activityLoginEtPassword.getText().toString());
                    userModel.setDevice_token(newToken);
                    userModel.setDevice_type("1");
                    userModel.setLatitude(""+new MyPref(LoginActivity.this).getData(MyPref.Keys.LAT));
                    userModel.setLongitude(""+new MyPref(LoginActivity.this).getData(MyPref.Keys.LAG));
                    /*Intent intent = new Intent(LoginActivity.this, OtpVerificationActivity.class);
                    startActivity(intent);
                    finish();*/
                    presenterInterface.sendRequest(context, new Gson().toJson(userModel), null, ApiCallInterface.LOGIN);

                }
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
    private boolean validate() {

        if (binding.activityLoginEtEmail.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter email or phone number");
            return false;
        } else if (binding.activityLoginEtPassword.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter password");
            return false;
        }
        return true;
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
        if (requestCode == ApiCallInterface.LOGIN) {
            UserModel basemodel = (UserModel) success;
            if (basemodel.isStatus().equals("true")) {
                userModel = basemodel.getData().getUser();
                if (binding.cbRemember.isChecked()) {
                    new MyPref(context).setData(MyPref.Keys.USER_NAME, binding.activityLoginEtEmail.getText().toString().trim());
                    new MyPref(context).setData(MyPref.Keys.PASSWORD, binding.activityLoginEtPassword.getText().toString().trim());
                } else {
                    new MyPref(context).setData(MyPref.Keys.USER_NAME, "");
                    new MyPref(context).setData(MyPref.Keys.PASSWORD, "");
                }
                if (userModel != null) {

                    if (userModel.isMobileVerified()) {
                        new MyPref(context).setUserData(userModel);
                        new MyPref(context).setData(MyPref.Keys.token, basemodel.getData().getAccess_token());
                        new MyPref(context).setData(MyPref.Keys.IsLogin, true);
                        if (userModel.isStatus().equals("0")){

//                            if (userModel.getDesignation_id().equals("2")) {
//                                startActivity(new Intent(LoginActivity.this, NurseProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                                finish();
//                            } else {
//                                startActivity(new Intent(LoginActivity.this, CreateProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                                finish();
//                            }
                            startActivity(new Intent(LoginActivity.this, NurseProfileActivity.class));

                        }else {
                           // startActivity(new Intent(LoginActivity.this, NewDashboardActivity.class));

                            if (new MyPref(context).getUserData().getRoles().get(new MyPref(context).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

                                startActivity(new Intent(LoginActivity.this, NewDashboardActivity.class));

//                                Intent intent = new Intent(LoginActivity.this, Passcode.class);
//                                intent.putExtra("passcode", userModel.getPasscode());
//                                Log.e("passcode",userModel.getPasscode());
//                                startActivity(intent);

                            } else {
                                startActivity(new Intent(LoginActivity.this, VendorDashboardActivity.class));
                            }
                        }
//                        if (userModel.getRoles().get(userModel.getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {
//                            startActivity(new Intent(LoginActivity.this, NewDashboardActivity.class));
//
//                        } else {
//                            startActivity(new Intent(LoginActivity.this, VendorDashboardActivity.class));
//                        }


                        finish();


                    } else {

                        new MyPref(context).setUserData(userModel);
                        new MyPref(context).setData(MyPref.Keys.token, basemodel.getData().getAccess_token());
                        new MyPref(context).setData(MyPref.Keys.IsLogin, false);


                        if (userModel.getPhone() != null) {

                            sendOtp();

                        } else {

                            startActivityForResult(new Intent(LoginActivity.this, UpdateMobileNumberActivity.class).putExtra("mobile", userModel.getPhone()).putExtra("email", userModel.getEmail()), 2003);
                            finish();
                            Toast.makeText(context, "Mobile number dose not exits", Toast.LENGTH_SHORT).show();

                        }

                    }
                }

            } else {

                errorMessage(binding.getRoot(), basemodel.getMessage());

            }
        }
        if (requestCode == ApiCallInterface.SEND_OTP) {

            OtpResponseModel baseModel = (OtpResponseModel) success;
            if (baseModel.isStatus().equals("true")) {

                startActivityForResult(new Intent(LoginActivity.this, OtpVerificationActivity.class).putExtra("mobile", userModel.getPhone()).putExtra("email", userModel.getEmail()).putExtra("data", baseModel), 2003);
                finish();

            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }

        }

    }

    private void sendOtp() {

        HashMap<String, String> map = new HashMap<>();
        map.put("email", userModel.getEmail());
        map.put("phone", userModel.getPhone());
        presenterInterface.sendRequest(context, "", map, ApiCallInterface.SEND_OTP);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2003 && resultCode == RESULT_OK) {

            new MyPref(context).setUserData(userModel);
            new MyPref(context).setData(MyPref.Keys.token, userModel.getAccess_token());
            Intent intent = new Intent(LoginActivity.this, NewDashboardActivity.class);
            startActivity(intent);
            finish();

        } if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case GpsUtils.GPS_REQUEST:

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(new Intent(LoginActivity.this, GeoService.class));
                    } else {
                        startService(new Intent(LoginActivity.this, GeoService.class));
                    }
                    SingleShotLocationProvider.requestSingleUpdate(LoginActivity.this, new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                            new MyPref(LoginActivity.this).setData(MyPref.Keys.LAT, location.latitude + "");
                            new MyPref(LoginActivity.this).setData(MyPref.Keys.LAG, location.longitude + "");

                        }
                    });

                    break;

            }

        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case GpsUtils.GPS_REQUEST:
                    onGPS();
                    break;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        askPermission();
    }

    private void askPermission() {
        new BottomSheetAskPermission(this, new BottomSheetAskPermission.PermissionResultListener() {
            @Override
            public void onAllPermissionAllow() {

                onGPS();

            }

            @Override
            public void onPermissionDeny() {


            }
        }, BottomSheetAskPermission.ACCESS_COARSE_LOCATION, BottomSheetAskPermission.ACCESS_FINE_LOCATION
        ).show(getSupportFragmentManager(), "");
    }

    private void onGPS() {
        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {

                if (isGPSEnable) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(new Intent(LoginActivity.this, GeoService.class));
                    } else {
                        startService(new Intent(LoginActivity.this, GeoService.class));
                    }
                    SingleShotLocationProvider.requestSingleUpdate(LoginActivity.this, new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {

                            new MyPref(LoginActivity.this).setData(MyPref.Keys.LAT, location.latitude + "");
                            new MyPref(LoginActivity.this).setData(MyPref.Keys.LAG, location.longitude + "");

                        }
                    });

                }

            }
        });
    }

}