package com.example.denisprawira.ta.User.Helper;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GlobalHelper {

    //////////////////////////////////////////variabel Uri//////////////////////////////////////////
    public static final String url="192.168.1.68";
    public static final String baseUrl = "http://" +url + "/donor_darah/index.php/api/";
    public static final String fcmUrl = "https://fcm.googleapis.com/";

    ////////////////////////////////////////variabel notification type//////////////////////////////
    public static final String SERVICE_HELP ="service_help";
    public static final String SERVICE_CHAT = "service_chat";
    public static final String SERVICE_EVENT = "service_event";

    //variabel sharedpreference
    public static final String USER_ID="id";
    public static final String USER_NAME="nama";
    public static final String USER_PHONE="telp";
    public static final String USER_BLOOD="goldarah";
    public static final String USER_GENDER="gender";
    public static final String USER_EMAIL="email";
    public static final String USER_PHOTO="photo";
    public static final String USER_TOKEN="token";
    public static final String LOG_STATUS = "logStatus";
    public static final String USER_ID_CHAT= "chatId";



    //TYPE EVENT
    public static final String TYPE_EVENT_APPROVE   = "Approved";
    public static final String TYPE_EVENT_PENDING   = "Pending";
    public static final String TYPE_EVENT_UNAPPROVE = "Unapproved";
    public static final String TYPE_EVENT_COMPLETE  = "Complete";



    //TYPE NOTIFICATION
    public static final String TYPE_EVENTAPPROVAL       = "eventApproval";
    public static final String TYPE_EVENTREJECT         = "eventReject";
    public static final String TYPE_NEWEVENT            = "newEvent";
    public static final String TYPE_REQUESTDONOR        = "requestDonor";
    public static final String TYPE_ANNOUNCEMENT        = "announcement";
    public static final String TYPE_DONORREMINDER       = "donorReminder";

    public static final String TYPE_CANCELREQUESTDONOR  = "cancelRequestDonor";
    public static final String TYPE_CHAT                = "chat";


    //TYPE USER STATUS
    public static final String TYPE_USER_CLIENT         = "user";
    public static final String TYPE_USER_UTD            = "utd";


    //TITLE NOTIFICATION
    public static final String TITLE_EVENTAPPROVAL  = "Persetujuan Event";
    public static final String TITLE_NEWEVENT       = "Event Baru";
    public static final String TITLE_REQUESTDONOR   = "Permintaaan RequestDonor";
    public static final String TITLE_CHAT           = "Chat Baru";
    public static final String TITLE_ANNOUNCEMENT   = "Pemberitahuan Baru";


    //MESSAGE NOTIFICATION
    public static final String MSG_EVENTAPPROVAL    = "Mengajukan Sebuah Event";
    public static final String MSG_NEWEVENT         = "Terdapat Event Baru";
    public static final String MSG_REQUESTDONOR     = "meminta bantuan";
    public static final String MSG_CHAT             = "message Chat";
    public static final String MSG_ANNOUNCEMENT     = "message announcement";



    public static String lat;
    public static String lng;

    public static String countEvent;


    public static String convertDate(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertTime(String textTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertDay(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertMonth(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }


    public static String convertDateFromDateTime(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy ", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertTimeFromDateTime(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }


    public static String convertDayMonth(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertToYear(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertToDayOfWeek(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convert24HoursTo12Hours(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static int convertToIntegerYear(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format);
    }

    public static int convertToIntegerDay(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format);
    }

    public static int convertToIntegerMonth(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format)-1;
    }

    public static Integer convertToIntegerHour(String textTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        Date date = null;
        try {
            date = sdf.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format);
    }

    public static Integer convertToIntegerMinute(String textTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        Date date = null;
        try {
            date = sdf.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format);
    }

    public static String convertDateTimeToDay(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertDateTimeToMonth(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertDateTimeToTime(String textTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }


    public static int toPixels(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * (metrics.densityDpi / 160f));
    }

    public static float toDips(int px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }


}
