package com.android.doral.retrofit;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.doral.retrofit.model.MainCovidFormModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.Utils.AppClass;
import com.android.doral.Utils.Logger;
import com.android.doral.retrofit.model.AcceptRequest;
import com.android.doral.retrofit.model.LoginModel;
import com.android.doral.retrofit.model.WorkHistroyModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@SuppressWarnings("unchecked")
public class ApiTask {
    private ApiCallInterface callapi;
    Context context;

    public ApiTask(AppClass context) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        this.context = context;
        callapi = context.getRetrofitInstance().create(ApiCallInterface.class);

    }


    @SuppressLint("CheckResult")
    public void sendRequest(String body, HashMap<String, String> param, int reqCode, DisposableCallback apiCallback, String... documents) {

        if (param != null) {

            Logger.e(param.toString());

        }
        if (reqCode == ApiCallInterface.REGISTER) {
            callapi.register(new Gson().fromJson(body, UserModel.class)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.LOGIN) {
            callapi.login(new Gson().fromJson(body, LoginModel.class)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.USER_DETAILS) {
            callapi.getUserData().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }if (reqCode == ApiCallInterface.SELECTION_LIST) {
            callapi.getSelectionList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.SEND_OTP) {
            callapi.sendOtp(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.VERIFY_OTP) {
            callapi.verifyOtp(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.FORGOT_PASSWORD) {
            callapi.forgotPassword(new Gson().fromJson(body, LoginModel.class)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.CHANGE_PASSWORD) {
            callapi.changePassword(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.CANCEL_REASON) {
            callapi.getCancelReasons().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.CANCEL_APPOINTMENT) {
            callapi.cancelAppointment(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.UPDATE_LOCATION) {
            callapi.updateLocation(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.Complete_Request) {
            callapi.completerequest(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.categorylist) {
            callapi.Catlist(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.namelist) {
            callapi.namelist(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.Registerpass) {
            callapi.registerpass(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }


        if (reqCode == ApiCallInterface.Passcodelogin) {
            callapi.passcode(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.Fingerregister) {
            callapi.fingerregister(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

//        if (reqCode == ApiCallInterface.Upload_image) {
//            callapi.image(param).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
//        }

        if (reqCode == ApiCallInterface.Send_mesage) {
            callapi.sendmessage(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.Conversation) {
            callapi.getconversation(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.ADD_DIPOSIT_DETAILS) {
            callapi.addDipositDetails(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.ADD_APPLICANT) {
            callapi.addApplicant(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.ADD_COMPANY) {
            callapi.addCompany(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.DESIGNATION) {
            callapi.getDesignation().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.APPOITNMENT) {
            callapi.getAppointments().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.Patientlist) {
            callapi.getpatient().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }


        if (reqCode == ApiCallInterface.Notification) {
            callapi.notification().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

//        if (reqCode == ApiCallInterface.readNotification) {
//            callapi.readnotification().subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
//        }
        if (reqCode == ApiCallInterface.CHNAGE_AVAILABILTY) {
            callapi.changeOnline(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.APPOITNMENT_CANCELLED_LIST) {
            callapi.getCancelledAppointments().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.ACCPET_REQUEST) {
            callapi.acceptRequest(new Gson().fromJson(body, AcceptRequest.class)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.REQUEST_LIST) {
            callapi.requestList(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.STATES) {
            callapi.getStates().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.CITY) {
            callapi.getCity().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.EDUCATION) {
            callapi.educationSubmit(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.GET_CERTIFYING_BOARD) {
            callapi.getCertifyingBoard().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.GET_CERTIFYING_BOARD_STATUS) {
            callapi.getCertifyingBoardStatus().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.ADD_CERTIFICATE) {
            callapi.addCertificate(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.ADD_WORK_HISTORY) {
            callapi.addWorkHistory(new Gson().fromJson(body, WorkHistroyModel.class)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.SUBMIT_COVID_FORM) {
            ObjectMapper mapper = new ObjectMapper();

// Convert POJO to Map
            /*Map<String, Object> map =
                    mapper.convertValue(new Gson().fromJson(body, MainCovidFormModel.class), new TypeReference<Map<String, Object>>() {
                    });
            if (map != null) {
                Log.e("FORM DATA", map.toString());
            }*/

            callapi.submitCovidForm(new Gson().fromJson(body, MainCovidFormModel.class)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
           /* callapi.submitCovidForm(new Gson().fromJson(body, MainCovidFormModel.class), createMultipartBody("recipient_sign", documents[0]),
                    createMultipartBody("interpreter_sign", documents[1]),
                    createMultipartBody("vaccination_sign", documents[2])).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);*/
        }
        if (reqCode == ApiCallInterface.STORE_FORM_SIGNATURE) {
            callapi.storeSignature(param.get("id"), createMultipartBody("recipient_sign", documents[0]),
                    createMultipartBody("interpreter_sign", documents[1]),
                    createMultipartBody("vaccination_sign", documents[2])).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.readNotification) {
            callapi.readnotification(param.get("id")).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.STORE_FORM_SIGNATURE_1) {
            callapi.storeSignature(param.get("id"),
                    createMultipartBody("vaccination_sign", documents[0])).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.GET_COUNTRY) {
            callapi.getCountry().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.ROADL_PROCESS_REQUEST) {
            callapi.getRoadlProcessRequest(param.get("id")).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.ROADL_PROCESS_REQUEST_NEW) {
            callapi.getRoadlProcessRequestnew(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.GET_PARENT_ID) {
            callapi.getParentID(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.PATIENT_REQUEST) {
            callapi.patientRequest(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.GET_APPLICATION_DETAIL) {
            callapi.getNurseStoreDetail().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.GET_USER_APPLICATION_DETAIL) {
            callapi.getUserStoreDetail().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.VENDOR_LIST) {
            callapi.getVenderList(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.GET_COVID_FORMS) {
            callapi.getSubmitforms().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.UPLOAD_DOCUMENT) {
            callapi.uploadDocument(createMultipartBody("id_proof", documents[0]),
                    createMultipartBody("degree_proof", documents[1]),
                    createMultipartBody("medical_report", documents[2]),
                    createMultipartBody("insurance_report", documents[3])).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.Upload_image) {
            callapi.image(createMultipartBody("avatar", documents[0])).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.upload) {
            callapi.upload(createMultipartBody("document.19.w4document", documents[0])).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }
        if (reqCode == ApiCallInterface.UPLOAD_DOCUMENT_NURSE) {

            ArrayList<MultipartBody.Part> list = new ArrayList<>();
            if (documents[0] != "")
                list.add(prepareFilePart("document.5.socialSecurity", documents[0]));
            if (documents[2] != "")
                list.add(prepareFilePart("document.6.professionalReferrance", documents[1]));
            if (documents[3] != "")
                list.add(prepareFilePart("document.7.mainPracticeInsurance", documents[2]));
            if (documents[4] != "")
                list.add(prepareFilePart("document.8.nycNurseCertificate", documents[3]));
            if (documents[5] != "")
                list.add(prepareFilePart("document.9.CPR", documents[4]));
            if (documents[5] != "")
                list.add(prepareFilePart("document.10.physical", documents[5]));
            if (documents[6] != "")
                list.add(prepareFilePart("document.11.forensicDrugScreen", documents[6]));
            if (documents[7] != "")
                list.add(prepareFilePart("document.12.RubellaImmunization", documents[7]));
            if (documents[8] != "")
                list.add(prepareFilePart("document.13.RubellaMeasiesImmunization", documents[8]));
            if (documents[9] != "")
                list.add(prepareFilePart("document.16.annualPPD", documents[9]));
            if (documents[10] != "")
                list.add(prepareFilePart("document.15.flu", documents[10]));
            if (documents[11] != "")
                list.add(prepareFilePart("document.1.idProof", documents[11]));
            if (documents[14] != "")
                list.add(prepareFilePart("document.20.idProofBack", documents[14]));
            if (documents[15] != "")
                list.add(prepareFilePart("document.21.socialSecurityBack", documents[15]));
            if (documents[12] != "")
                list.add(prepareFilePart("document.17.chestXRay", documents[12]));
            if (documents[13] != "")
                list.add(prepareFilePart("document.18.annualTubeScreening", documents[13]));
            if (documents[16] != "")
                list.add(prepareFilePart("document.2.degreeProof", documents[16]));

            if (documents[17] != "")
                list.add(prepareFilePart("document.4.insuranceReport", documents[17]));

            if (documents[18] != "")
                list.add(prepareFilePart("document.3.medicalReport", documents[18]));

            if (documents[19] != "")
                list.add(prepareFilePart("document.22.pdfDoc", documents[19]));
            if (documents[20] != "")
                list.add(prepareFilePart("document.25.pictureIdentification", documents[20]));

            if (documents[21] != "")
                list.add(prepareFilePart("document.26.currentCV", documents[21]));
            if (documents[22] != "")
                list.add(prepareFilePart("document.27.ProfessionalLicense", documents[22]));
            if (documents[23] != "")
                list.add(prepareFilePart("document.28.StateRegistrationCertificate", documents[23]));
            if (documents[24] != "")
                list.add(prepareFilePart("document.29.DEALicense", documents[24]));
            if (documents[25] != "")
                list.add(prepareFilePart("document.30.ControlledSubstanceStateLicense", documents[25]));
            if (documents[26] != "")
                list.add(prepareFilePart("document.31.MalpracticeCertificateOfInsurance", documents[26]));
            if (documents[27] != "")
                list.add(prepareFilePart("document.32.ExplanationOfAllMalpractice", documents[27]));
            if (documents[28] != "")
                list.add(prepareFilePart("document.33.MedicalSchoolDiploma", documents[28]));
            if (documents[29] != "")
                list.add(prepareFilePart("document.34.ResidencyCertificate", documents[29]));
            if (documents[30] != "")
                list.add(prepareFilePart("document.35.FellowshipCertificate", documents[30]));
            if (documents[31] != "")
                list.add(prepareFilePart("document.36.IntershipCertificate", documents[31]));
            if (documents[32] != "")
                list.add(prepareFilePart("document.37.ECFMGCertificate", documents[32]));
            if (documents[33] != "")
                list.add(prepareFilePart("document.38.BoardCertificate(c)", documents[33]));
            if (documents[34] != "")
                list.add(prepareFilePart("document.39.HospitalAffiliationLetter", documents[34]));
            if (documents[35] != "")
                list.add(prepareFilePart("document.40.SanctionsQueries", documents[35]));
            if (documents[36] != "")
                list.add(prepareFilePart("document.41.MedicalWelcomeLetter", documents[36]));
            if (documents[37] != "")
                list.add(prepareFilePart("document.42.SignedW9", documents[37]));
            if (documents[38] != "")
                list.add(prepareFilePart("document.43.SignedESignatureForm", documents[38]));
            callapi.uploadNurseDocument(list)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.NURSE_SIGNATURE) {
            ArrayList<MultipartBody.Part> list = new ArrayList<>();
            list.add(prepareFilePart("signature", documents[0]));
            callapi.storeNurseSignature(
                    list).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

    }

    @SuppressLint("CheckResult")
    public void callAPI(String body, HashMap<String, Object> param, int reqCode, DisposableCallback apiCallback, String... documents) {
        if (reqCode == ApiCallInterface.STORE_APPLICANT_DETAIL) {
            callapi.storeApplicantDetail(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.PASSWORD_RESET) {
            callapi.resetPassword(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }


        if (reqCode == ApiCallInterface.UPDATE_TIME) {
            callapi.updatePreparationTime(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }

        if (reqCode == ApiCallInterface.Edit_USER_PROFILE) {
            callapi.editUserProfile(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }




    }

    @SuppressLint("CheckResult")
    public void sendRequest(HashMap<String, RequestBody> param, String image, int reqCode, DisposableCallback apiCallback) {
       /* if (reqCode == ApiCallInterface.EDIT_USER_PROFILE) {
            callapi.editProfile(param, createMultipartBody("image", image)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }*/
    }

    @SuppressLint("CheckResult")
    public void sendRequest(String body, int reqCode, DisposableCallback apiCallback) {
        /*if (reqCode == ApiCallInterface.CREATE_ORDER) {
            callapi.placeOrder(new Gson().fromJson(body, AddCartModel.class)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(apiCallback);
        }*/
    }

    public static RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    private MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        String path = new File(fileUri).getPath();
        String extension = path.substring(path.lastIndexOf("."));
        RequestBody requestFile = RequestBody.create(MediaType.parse(extension), new File(fileUri));
        return MultipartBody.Part.createFormData(partName, new File(fileUri).getName(), requestFile);
    }

    private MultipartBody.Part createMultipartBody(String paramName, String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            try {
                //File compressedImageFile = new Compressor(context).compressToFile(file);

                RequestBody requestBody = createRequestBody(file);
                return MultipartBody.Part.createFormData(paramName, file.getName(), requestBody);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private RequestBody createRequestBody(File file) {
        return RequestBody.create(MediaType.parse("image/*"), file);
    }


    private MultipartBody.Part[] createDynamicMultipartArrayBody(List<String> multipleFilePath, String key) {

        MultipartBody.Part[] imagesList = new MultipartBody.Part[multipleFilePath.size()];

        for (int index = 0; index < multipleFilePath.size(); index++) {
            if (!multipleFilePath.get(index).equals("")) {
                File file = new File(multipleFilePath.get(index));
                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                imagesList[index] = MultipartBody.Part.createFormData(key + "[" + index + "]", file.getName(), surveyBody);

            } else {
                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), "");
                imagesList[index] = MultipartBody.Part.createFormData(key + "[" + index + "]", "", surveyBody);

            }

        }
        return imagesList;
    }

    private MultipartBody.Part getImagePart(String name, File file) {
        RequestBody requestFile =
                RequestBody.create(MultipartBody.FORM, file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);

    }


}


//    createMultipartBody("document.5.socialSecurity", documents[0]),
//    createMultipartBody("document.6.professionalReferrance", documents[1]),
//    createMultipartBody("document.7.professionalReferrance", documents[2]),
//    createMultipartBody("document.8.nycNurseCertificate", documents[3]),
//    createMultipartBody("document.9.mainPracticeInsurance", documents[4]),
//    createMultipartBody("document.10.CPR", documents[5]),
//    createMultipartBody("document.11.physical", documents[6]),
//    createMultipartBody("document.12.forensicDrugScreen", documents[7]),
//    createMultipartBody("document.13.RubellaImmunization", documents[8]),
//    createMultipartBody("document.14.RubellaMeasiesImmunization", documents[9]),
//    createMultipartBody("document.15.flu", documents[10])

