package com.android.doral.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.android.doral.GetRequestActivity;
import com.android.doral.R;
import com.android.doral.Utils.Logger;
import com.android.doral.Utils.StringUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String TAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null) {
            sendNotification(new JSONObject(remoteMessage.getData()));
        }

        if (remoteMessage.getData() != null)
            Logger.e("Notification Data" + remoteMessage.getData().toString());

    }


    private void sendNotification(JSONObject jsonObject) {
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, GetRequestActivity.class);

        try {
            Log.e("jsonObject", jsonObject.toString());
            intent.putExtra("lattitude", jsonObject.getString("latitude"));
            intent.putExtra("longitude", jsonObject.getString("longitude"));
            intent.putExtra("reason", jsonObject.getString("reason"));
            intent.putExtra("id", jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject detail = new JSONObject(jsonObject.getString("detail"));
            intent.putExtra("name", detail.getString("first_name") + " " + detail.getString("last_name"));
            intent.putExtra("email", detail.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "101";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("Doral Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

       /* String body = "";
        if (data.containsKey("message") && StringUtils.isNotEmpty(data.get("message"))) {
            body = data.get("message");
        } else {
            body = data.get("body");
        }*/


        NotificationCompat.Builder notificationBuilder = null;
        try {
            notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Request")
                    .setAutoCancel(true)
                    .setSound(defaultSound)
                    .setContentText("You have a new request")
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_MAX);
        } catch (Exception e) {
            e.printStackTrace();

        }
        notificationManager.notify(1, notificationBuilder.build());
    }


}