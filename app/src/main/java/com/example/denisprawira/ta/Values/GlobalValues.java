package com.example.denisprawira.ta.Values;

public class GlobalValues {
    //shared Preference user
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";
    public static final String USER_ADDRESS = "address";
    public static final String USER_BLOOD_ID = "blood_type";
    public static final String USER_BLOOD_TYPE_NAME = "blood_type_name";
    public static final String USER_IMG = "img";
    public static final String USER_ROLE = "role";
    public static final String USER_STATUS = "status";
    public static final String USER_API_TOKEN = "api_token";
    public static final String USER_FIREBASE_TOKEN = "firebase_token";
    public static final String USER_FIREBASE_ID = "firebase_id";
    public static final String USER_LOG_STATUS = "log_status";

    //shared Preference user
    public static final String PMI_ID = "pmi_id";
    public static final String PMI_NAME = "pmi_name";
    public static final String PMI_ADDRESS = "pmi_address";
    public static final String PMI_LAT = "pmi_lat";
    public static final String PMI_LNG = "pmi_lng";
    public static final String PMI_IMG = "pmi_img";

    public static final String PMI_EMAIL = "email";
    public static final String PMI_PHONE = "phone";
    public static final String PMI_STATUS = "status";
    public static final String PMI_API_TOKEN = "api_token";
    public static final String PMI_FIREBASE_TOKEN = "firebase_token";
    public static final String PMI_FIREBASE_ID = "firebase_id";
    public static final String PMI_LOG_STATUS = "log_status";

    //token
    public static final String typeToken = "Bearer ";
    public static String tokenApi;
    public static String getTokenApi() {
        return tokenApi;
    }
    public static void setTokenApi(String tokenApi) {
        GlobalValues.tokenApi =typeToken + tokenApi;
    }

    //intent name
    public static final String EVENT_INTENT = "event_intent";
    public static final String EVENT_INTENT_TYPE = "event_intent_type";
    public static final String PMI_INTENT = "pmi_intent";

    //event status
    public static final String EVENT_APPROVED = "approved";
    public static final String EVENT_DISAPPROVED = "disapproved";
    public static final String EVENT_FINISHED = "finished";
    public static final String EVENT_PENDING = "pending";

    //help request
    public static final String HELP_APPROVED = "approved";
    public static final String HELP_DISAPPROVED = "disapproved";
    public static final String HELP_FINISHED = "finished";
    public static final String HELP_PENDING = "pending";



    //Notification Types
    public static final String NOTIFICATION_EVENT = "notificationRequestedEventPmi";
    public static final String NOTIFICATION_EVENT_ACCEPT = "notificationEventApproved";
    public static final String NOTIFICATION_EVENT_DISAPPROVE = "notificationEventDisapproved";
    public static final String NOTIFICATION_HELP_REQUEST = "notificationHelpRequested";

    //Notification Status



}
