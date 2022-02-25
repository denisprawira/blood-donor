package com.example.denisprawira.ta.User.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.denisprawira.ta.User.Helper.GlobalHelper;
import com.example.denisprawira.ta.User.Helper.SessionManager;
import com.example.denisprawira.ta.User.Model.Notif;
import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.User.UI.CallUserActivity;
import com.example.denisprawira.ta.User.UI.Event.EventRequestUnapprovedActivity;
import com.example.denisprawira.ta.User.UI.Event.EventDetailActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class FirebaseServices extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String idSender, idReceiver,senderName, senderPhoto, idType, message, status, type, receiverStatus, senderStatus;
    String title,golDarah,utd, lat, lng,address,distance;


    SessionManager sessionManager ;
    String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sessionManager  = new SessionManager(getApplicationContext());
        if(remoteMessage.getData().get("type").equals(GlobalHelper.TYPE_REQUESTDONOR)){

            idSender    = remoteMessage.getData().get("idSender");
            senderName  = remoteMessage.getData().get("senderName");
            type        = remoteMessage.getData().get("type");
            title       = remoteMessage.getData().get("title");
            idType      = remoteMessage.getData().get("idType");
            message     = remoteMessage.getData().get("message");
            golDarah    = remoteMessage.getData().get("golDarah");
            utd         = remoteMessage.getData().get("utd");
            senderPhoto = remoteMessage.getData().get("senderPhoto");
            lat         = remoteMessage.getData().get("lat");
            lng         = remoteMessage.getData().get("lng");
            address     = remoteMessage.getData().get("address");
            distance    = remoteMessage.getData().get("distance");
            senderStatus = remoteMessage.getData().get("senderStatus");

            addNotification(idType, idSender, message, senderPhoto, senderName, senderStatus,type);

            Intent intent =  new Intent(getBaseContext(),CallUserActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("idSender",idSender);
            intent.putExtra("sender",senderName);
            intent.putExtra("type",type);
            intent.putExtra("title",title);
            intent.putExtra("idType",idType);
            intent.putExtra("message",message);
            intent.putExtra("golDarah",golDarah);
            intent.putExtra("utd",utd);
            intent.putExtra("senderImage",senderPhoto);
            intent.putExtra("lat",lat);
            intent.putExtra("lng",lng);
            intent.putExtra("address",address);
            intent.putExtra("distance",distance);

            startActivity(intent);

        }else if(remoteMessage.getData().get("type").equals(GlobalHelper.TYPE_EVENTAPPROVAL)|| remoteMessage.getData().get("type").equals(GlobalHelper.TYPE_EVENTREJECT)){
            idSender        = remoteMessage.getData().get("idSender");
            idReceiver      = remoteMessage.getData().get("idReceiver");
            senderName      = remoteMessage.getData().get("senderName");
            senderPhoto     = remoteMessage.getData().get("senderPhoto");
            idType          = remoteMessage.getData().get("idType");
            message         = remoteMessage.getData().get("message");
            status          = remoteMessage.getData().get("status");
            type            = remoteMessage.getData().get("type");
            receiverStatus  = remoteMessage.getData().get("receiverStatus");
            senderStatus    = remoteMessage.getData().get("senderStatus");
//
            addNotification(idType, idSender, message, senderPhoto, senderName, senderStatus,type);
             //sendNotficationChat(remoteMessage.getData().get("senderName"),remoteMessage.getData().get("message"),remoteMessage.getData().get("senderPhoto"));
            if(remoteMessage.getData().get("type").equals(GlobalHelper.TYPE_EVENTAPPROVAL)){
                sendNotificationEvent(remoteMessage.getData().get("senderName"),remoteMessage.getData().get("message"),remoteMessage.getData().get("senderPhoto"));
            }else if(remoteMessage.getData().get("type").equals(GlobalHelper.TYPE_EVENTREJECT)){
                sendNotificationEventUnapproved(remoteMessage.getData().get("senderName"),remoteMessage.getData().get("message"),remoteMessage.getData().get("senderPhoto"));
            }

        }
        else if(remoteMessage.getData().get("type").equals(GlobalHelper.TYPE_CHAT)){
            sendNotficationChat(remoteMessage.getData().get("senderName"),remoteMessage.getData().get("message"),remoteMessage.getData().get("senderPhoto"));
        }else{
            sendNotficationChat(remoteMessage.getData().get("senderName"),"ini adalah defaul message",remoteMessage.getData().get("senderPhoto"));
        }

    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        Log.d(TAG, s+" "+" "+e);
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.d(TAG, s);
    }

    private void sendNotificationEvent(String title,String message,String uri) {
        Intent intent = new Intent(this,EventDetailActivity.class);
        intent.putExtra("id",idType);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = null;
        try {
            largeIcon =  Glide.with(this).asBitmap().load(uri).submit().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String CHANNEL_ID="Channel Services";
        int randomdint = new Random().nextInt(9999-1)+1;
        Notification notification = new Notification();
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(getCircleBitmap(largeIcon))
                .setChannelId(CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.svg_pmi);

        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(randomdint,builder.build());
    }

    private void sendNotificationEventUnapproved(String title,String message,String uri) {
        Intent intent = new Intent(this, EventRequestUnapprovedActivity.class);
        intent.putExtra("id",idType);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = null;
        try {
            largeIcon =  Glide.with(this).asBitmap().load(uri).submit().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String CHANNEL_ID="Channel Services";
        int randomdint = new Random().nextInt(9999-1)+1;
        Notification notification = new Notification();
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(getCircleBitmap(largeIcon))
                .setChannelId(CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.svg_pmi);

        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(randomdint,builder.build());
    }



    //notification for chat
    private void sendNotficationChat(String title,String message,String uri) {
        String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";
        Bitmap largeIcon = null;
        try {
            largeIcon =  Glide.with(this).asBitmap().load(uri).submit().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String CHANNEL_ID="Channel Services";
        int randomdint = new Random().nextInt(9999-1)+1;
        Notification notification = new Notification();
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(getCircleBitmap(largeIcon))
                .setChannelId(CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.svg_pmi);

        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(randomdint,builder.build());
    }





    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output;
        Rect srcRect, dstRect;
        float r;
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        if (width > height){
            output = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
            int left = (width - height) / 2;
            int right = left + height;
            srcRect = new Rect(left, 0, right, height);
            dstRect = new Rect(0, 0, height, height);
            r = height / 2;
        }else{
            output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            int top = (height - width)/2;
            int bottom = top + width;
            srcRect = new Rect(0, top, width, bottom);
            dstRect = new Rect(0, 0, width, width);
            r = width / 2;
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

      //  bitmap.recycle();

        return output;
    }


    public void addNotification(String idType, String idUser, String msg, String senderPhoto, String senderName,String senderStatus, String type){
        // sebelumnya karena memakai CurrentUser.currentUser.getId(); error maka gunakan sessionManager.getId(); karena begitu aplikasi keluar CurrentUser akan di set ke null
        final CollectionReference notifs = db.collection("Notifs");
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        Notif newNotif = new Notif();
        newNotif.setIdSender(idUser);
        newNotif.setIdReceiver(sessionManager.getId());
        newNotif.setSenderName(senderName);
        newNotif.setSenderPhoto(senderPhoto);
        newNotif.setIdType(idType);
        newNotif.setMsg(msg);
        newNotif.setStatus("0");
        newNotif.setType(type);
        newNotif.setReceiverStatus(GlobalHelper.TYPE_USER_CLIENT);
        newNotif.setSenderStatus(senderStatus);
        newNotif.setTimeMilis(String.valueOf(currentTime));
        notifs.add(newNotif).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    DocumentReference docRef = task.getResult();
                    String key = docRef.getId();
                    notifs.document(key).update("id",key).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.e("insert to firebase","berhasil gan");
                        }
                    });
                }
            }
        });
    }



}
