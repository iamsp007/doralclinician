package com.android.doral.retrofit;

public class WebAPI {

//  Live
  public static final String DOMAIN = "https://api.doralhealthconnect.com";
  //Test
  //public static final String DOMAIN = "http://doralapi.hcbspro.com";
    public static final String BASE_URL = DOMAIN + "/api/";


    static final String REGISTER = "auth/register";
    static final String LOGIN = "auth/login";
    static final String USER_DETAILS = "auth/user";
    static final String DESIGNATION = "auth/designation";
    static final String FORGOT_PASSWORD = "auth/forgot";
    static final String STATES = "auth/states";
    static final String CITY = "auth/cities";
    static final String SEND_OTP = "auth/nexmo-send";
    static final String VERIFY_OTP = "auth/nexmo-verify";
    static final String APPOITNMENT = "auth/appointment";
    static final String APPOITNMENT_CANCELLED_LIST = "get-cancel-appoiment-list";
    static final String CHNAGE_AVAILABILTY = "auth/change-availability";
    static final String CHANGE_PASSWORD = "auth/password/reset";
    static final String CANCEL_REASON = "auth/appointment/cancel-appointment-reasons";
    static final String CANCEL_APPOINTMENT = "appointment/cancel-appointment";
    static final String UPLOAD_DOCUMENT = "auth/document-verification";
    static final String UPDATE_LOCATION = "roadl-information";
    static final String ADD_DIPOSIT_DETAILS = "auth/bank-account";
    static final String ADD_APPLICANT = "auth/applicants/steps";
    static final String ADD_COMPANY = "auth/work-history";
    static final String CERTIFYING_BOARD = "auth/certifying-board";
    static final String CERTIFYING_BOARD_STATUS = "auth/certifying-board-status";
    static final String CERTIFICATES = "auth/certificates";
    static final String SUBMIT_COVID = "auth/covid-19/store";
    static final String GET_COVID_FORMS = "auth/get-covid-19-patient-list";
    static final String COUNTRIES = "auth/countries";
    static final String ADD_WORK_HISTORY = "auth/work-history";
    static final String ROADL_PROCESS_REQUEST_NEW = "get-roadl-proccess-new";
    static final String GET_PARENT_ID = "get-parent-id-using-patient-id";
    static final String PATIENT_REQUEST = "patient-request";
    static final String VENDOR_LIST = "vendor-list";
    static final String ROADL_PROCESS_REQUEST = "get-roadl-proccess/{id}";
    static final String STORE_FORM_SIGNATURE = "auth/covid-19/{id}/store-signatures";
    static final String NURSE_STORE_FORM_SIGNATURE = "auth/clinician/store-signatures";
    static final String STORE_APPLICANT_DETAIL="auth/store-applicant-detail";
    static final String ACCPET_REQUEST = "clinician-request-accept";
    static final String REQUEST_LIST = "clinician-patient-request-list";
    static final String EDUCATION = "auth/education";
    static final String GET_APPLICANT_DETAILS="auth/get-applicant-details";
    static final String PASSWORD_RESET = "auth/password/reset";
    static final String EDIT_PROIFLE = "auth/user_update";
    static final String UPDATE_TIME = "update-preparation-time";

    static final String Complete_Request = "update-patient-request-status";
    static final String Send_mesage = "auth/send-mesage";
    static final String Conversation = "auth/conversation";
    static final String Patientlist = "auth/patient-list";
    static final String Notification = "auth/notification-history";
    static final String readNotification = "auth/read-notification/{id}";
    static final String upload = "auth/document-verification";
    static final String categorylist = "category-list";
    static final String namelist = "name-list";
    static final String selectionList = "selection-list";
  static final String Upload_image = "update-profile";
  static final String Registerpass = "auth/passcode-register";
  static final String Passcodelogin = "auth/finger-print-login";
  static final String Fingerregister = "auth/finger-print-register";
}
