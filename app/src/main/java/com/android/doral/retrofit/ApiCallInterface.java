package com.android.doral.retrofit;



import com.android.doral.Example;
import com.android.doral.Namelist;
import com.android.doral.retrofit.model.Conversation1;
import com.android.doral.retrofit.model.FormModel;
import com.android.doral.retrofit.model.MainCovidFormModel;
import com.android.doral.Notification.Model.Notification;
import com.android.doral.retrofit.model.ParentIdModel;
import com.android.doral.retrofit.model.PatientResponseModel;
import com.android.doral.retrofit.model.RoadRequestModel;
import com.android.doral.retrofit.model.SelectionListModel.SelectionListModel;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.retrofit.model.AcceptRequest;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.CertifyingModel;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.CountryModel;
import com.android.doral.retrofit.model.DesignationModel;
import com.android.doral.retrofit.model.LoginModel;
import com.android.doral.retrofit.model.OtpResponseModel;
import com.android.doral.retrofit.model.ReasonModel;
import com.android.doral.retrofit.model.RequestModel;
import com.android.doral.retrofit.model.StateModel;
import com.android.doral.retrofit.model.VenderModel;
import com.android.doral.retrofit.model.WorkHistroyModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface ApiCallInterface {
    int TOKEN_EXPIRED = 401;
    int REGISTER = 1;
    int LOGIN = 2;
    int ACCPET_REQUEST = 3;
    int REQUEST_LIST = 4;
    int FORGOT_PASSWORD = 5;
    int DESIGNATION = 6;
    int STATES = 7;
    int CITY = 8;
    int SEND_OTP = 9;
    int VERIFY_OTP = 10;
    int APPOITNMENT = 11;
    int CHANGE_PASSWORD = 12;
    int EDUCATION = 13;
    int UPLOAD_DOCUMENT = 14;
    int CANCEL_REASON = 15;
    int CANCEL_APPOINTMENT = 16;
    int UPDATE_LOCATION = 17;
    int APPOITNMENT_CANCELLED_LIST = 18;
    int CHNAGE_AVAILABILTY = 19;
    int USER_DETAILS = 20;
    int ADD_DIPOSIT_DETAILS = 21;
    int ADD_APPLICANT = 22;
    int ADD_COMPANY = 23;
    int GET_CERTIFYING_BOARD = 24;
    int GET_CERTIFYING_BOARD_STATUS = 25;
    int ADD_CERTIFICATE = 26;
    int GET_COUNTRY = 27;
    int ADD_WORK_HISTORY = 28;
    int ROADL_PROCESS_REQUEST = 29;
    int ROADL_PROCESS_REQUEST_NEW = 30;
    int VENDOR_LIST = 31;
    int PATIENT_REQUEST = 32;
    int GET_PARENT_ID = 33;
    int SUBMIT_COVID_FORM = 34;
    int STORE_FORM_SIGNATURE = 35;
    int STORE_FORM_SIGNATURE_1 = 36;
    int GET_COVID_FORMS = 37;
    int STORE_APPLICANT_DETAIL = 38;
    int UPLOAD_DOCUMENT_NURSE = 39;
    int GET_APPLICATION_DETAIL = 40;
    int USER_DETAILS_DATA = 41;
    int PASSWORD_RESET = 42;
    int Edit_USER_PROFILE = 43;
    int NURSE_SIGNATURE = 44;
    int Complete_Request=45;
    int UPDATE_TIME=46;
    int Patientlist=47;
    int Send_mesage=48;
    int Conversation=49;
    int Notification=50;
    int readNotification=51;
    int upload=52;
    int categorylist =53;
    int namelist =54;
    int GET_USER_APPLICATION_DETAIL = 55;
    int SELECTION_LIST = 56;
    int Upload_image = 57;
    int Registerpass = 58;
    int Passcodelogin = 59;
    int Fingerregister = 60;
    @POST(WebAPI.REGISTER)
    Observable<BaseModel> register(@Body UserModel userModel);

    @GET(WebAPI.STATES)
    Observable<List<StateModel>> getStates();

    @GET(WebAPI.CITY)
    Observable<List<CityModel>> getCity();

    @GET(WebAPI.selectionList)
    Observable<SelectionListModel> getSelectionList();

    @POST(WebAPI.LOGIN)
    Observable<UserModel> login(@Body LoginModel userModel);

    @GET(WebAPI.USER_DETAILS)
    Observable<UserModel> getUserDetails();

    @GET(WebAPI.USER_DETAILS)
    Observable<UserModel> getUserData();

    @POST(WebAPI.SEND_OTP)
    @FormUrlEncoded
    Observable<OtpResponseModel> sendOtp(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.VERIFY_OTP)
    @FormUrlEncoded
    Observable<UserModel> verifyOtp(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.ACCPET_REQUEST)
    Observable<BaseModel> acceptRequest(@Body AcceptRequest map);

    @POST(WebAPI.FORGOT_PASSWORD)
    Observable<BaseModel> forgotPassword(@Body LoginModel map);

    @POST(WebAPI.CHANGE_PASSWORD)
    @FormUrlEncoded
    Observable<BaseModel> changePassword(@FieldMap HashMap<String, String> map);

    @GET(WebAPI.CANCEL_REASON)
    Observable<ReasonModel> getCancelReasons();

    @POST(WebAPI.CANCEL_APPOINTMENT)
    @FormUrlEncoded
    Observable<BaseModel> cancelAppointment(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.UPDATE_LOCATION)
    @FormUrlEncoded
    Observable<BaseModel> updateLocation(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.ADD_DIPOSIT_DETAILS)
    @FormUrlEncoded
    Observable<BaseModel> addDipositDetails(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.ADD_APPLICANT)
    @FormUrlEncoded
    Observable<BaseModel> addApplicant(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.ADD_COMPANY)
    @FormUrlEncoded
    Observable<BaseModel> addCompany(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.UPLOAD_DOCUMENT)
    @Multipart
    Observable<BaseModel> uploadDocument(@Part MultipartBody.Part... multipart);



    @Multipart
    @POST(WebAPI.UPLOAD_DOCUMENT)
    Observable<BaseModel> uploadNurseDocument(@Part ArrayList<MultipartBody.Part> file);

    @GET(WebAPI.DESIGNATION)
    Observable<DesignationModel> getDesignation();

    @GET(WebAPI.APPOITNMENT)
    Observable<AppontmentModel> getAppointments();

    @POST(WebAPI.CHNAGE_AVAILABILTY)
    @FormUrlEncoded
    Observable<UserModel> changeOnline(@FieldMap HashMap<String, String> map);

    @GET(WebAPI.APPOITNMENT_CANCELLED_LIST)
    Observable<AppontmentModel> getCancelledAppointments();

    @POST(WebAPI.REQUEST_LIST)
    @FormUrlEncoded
    Observable<RequestModel> requestList(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.EDUCATION)
    @FormUrlEncoded
    Observable<BaseModel> educationSubmit(@FieldMap HashMap<String, String> map);

    @GET(WebAPI.CERTIFYING_BOARD)
    Observable<CertifyingModel> getCertifyingBoard();

    @GET(WebAPI.CERTIFYING_BOARD_STATUS)
    Observable<CertifyingModel> getCertifyingBoardStatus();

    @POST(WebAPI.CERTIFICATES)
    @FormUrlEncoded
    Observable<BaseModel> addCertificate(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.ROADL_PROCESS_REQUEST_NEW)
    @FormUrlEncoded
    Observable<RoadRequestModel> getRoadlProcessRequestnew(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.GET_PARENT_ID)
    @FormUrlEncoded
    Observable<ParentIdModel> getParentID(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.PATIENT_REQUEST)
    @FormUrlEncoded
    Observable<BaseModel> patientRequest(@FieldMap HashMap<String, String> map);

    @GET(WebAPI.VENDOR_LIST)
    Observable<VenderModel> getVenderList(@QueryMap HashMap<String, String> map);

    @POST(WebAPI.ADD_WORK_HISTORY)
    Observable<BaseModel> addWorkHistory(@Body WorkHistroyModel map);

    @POST(WebAPI.SUBMIT_COVID)
    Observable<MainCovidFormModel> submitCovidForm(@Body MainCovidFormModel map);

    @GET(WebAPI.GET_COVID_FORMS)
    Observable<FormModel> getSubmitforms();

    @GET(WebAPI.COUNTRIES)
    Observable<CountryModel> getCountry();

    @GET(WebAPI.GET_APPLICANT_DETAILS)
    Observable<ApplicationDetailsModel> getNurseStoreDetail();

    @GET(WebAPI.GET_APPLICANT_DETAILS)
    Observable<UserApplicationDetailsModel> getUserStoreDetail();

    @GET(WebAPI.ROADL_PROCESS_REQUEST)
    Observable<RoadRequestModel> getRoadlProcessRequest(@Path("id") String id);

    @POST(WebAPI.STORE_FORM_SIGNATURE)
    @Multipart
    Observable<BaseModel> storeSignature(@Path("id") String id, @Part MultipartBody.Part... multipart);

    @POST(WebAPI.NURSE_STORE_FORM_SIGNATURE)
    @Multipart
    Observable<BaseModel> storeNurseSignature(@Part ArrayList<MultipartBody.Part> file);

    @POST(WebAPI.STORE_APPLICANT_DETAIL)
    Observable<BaseModel> storeApplicantDetail(@Body HashMap<String, Object> map);

    @POST(WebAPI.PASSWORD_RESET)
    Observable<BaseModel> resetPassword(@Body HashMap<String, Object> map);

    @POST(WebAPI.UPDATE_TIME)
    Observable<BaseModel> updatePreparationTime(@Body HashMap<String, Object> map);

    @POST(WebAPI.EDIT_PROIFLE)
    Observable<BaseModel> editUserProfile(@Body HashMap<String, Object> map);


//    @POST(WebAPI.Complete_Request)
//    Observable<BaseModel> completerequest(@Body HashMap<String, Object> map);


    @POST(WebAPI.Complete_Request)
    @FormUrlEncoded
    Observable<BaseModel> completerequest(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.Send_mesage)
    @FormUrlEncoded
    Observable<SendModel> sendmessage(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.Conversation)
    @FormUrlEncoded
    Observable<Conversation1> getconversation(@FieldMap HashMap<String, String> map);

    @GET(WebAPI.Patientlist)
    Observable<PatientResponseModel> getpatient();

    @GET(WebAPI.Notification)
    Observable<Notification> notification();

    @GET(WebAPI.readNotification)
    Observable<BaseModel> readnotification(@Path("id") String id);

    @POST(WebAPI.upload)
    @Multipart
    Observable<BaseModel> upload(@Part MultipartBody.Part... multipart);


    @POST(WebAPI.categorylist)
    @FormUrlEncoded
    Observable<Example> Catlist(@FieldMap HashMap<String, String> map);

//    @POST(WebAPI.Upload_image)
//    @FormUrlEncoded
//    Observable<BaseModel> image(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.Upload_image)
    @Multipart
    Observable<BaseModel> image(@Part MultipartBody.Part... multipart);

    @POST(WebAPI.namelist)
    @FormUrlEncoded
    Observable<Namelist> namelist(@FieldMap HashMap<String, String> map);


    @POST(WebAPI.Registerpass)
    @FormUrlEncoded
    Observable<BaseModel> registerpass(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.Passcodelogin)
    @FormUrlEncoded
    Observable<BaseModel> passcode(@FieldMap HashMap<String, String> map);

    @POST(WebAPI.Fingerregister)
    @FormUrlEncoded
    Observable<BaseModel> fingerregister(@FieldMap HashMap<String, String> map);
}
