package com.igordubrovin.tfsmsg.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.activities.MessagesActivity;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

import java.util.concurrent.atomic.AtomicBoolean;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Игорь on 12.04.2017.
 */

public class ReceiveMessageService extends Service {

    private AtomicBoolean activityConnected;
    private AnswerBinder binder;
    private PendingIntent pendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new AnswerBinder();
        activityConnected = new AtomicBoolean();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pendingIntent = intent.getParcelableExtra(MessagesActivity.PENDING_INTENT);
        ThreadLoadMessage loader = new ThreadLoadMessage(pendingIntent);
        loader.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        setActivityConnected(true);
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        setActivityConnected(true);
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        setActivityConnected(false);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void sendAnswer(){
        ThreadLoadMessage loader = new ThreadLoadMessage(pendingIntent);
        loader.start();
    }

    private void setActivityConnected(boolean state){
        activityConnected.set(state);
    }

    private boolean isActivityConnected(){
        return activityConnected.get();
    }

    private class ThreadLoadMessage extends Thread{

        PendingIntent pendingIntent;

        public ThreadLoadMessage(PendingIntent pendingIntent){
            this.pendingIntent = pendingIntent;
        }

        @Override
        public void run() {
            super.run();
            String sender = "test test test test test test test test test";
            String message = "Test Test Test Test Test Test Test Test Test Test";
            try {
                Thread.sleep(3000);
                messageCome(sender, message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void messageCome(String sender, String message){
            Intent result;
            Context context = ReceiveMessageService.this;
            if (isActivityConnected()) {
                result = new Intent().putExtra(MessagesActivity.EXTRA_SUCCESS, false)
                        .putExtra(ProjectConstants.SERVICE_SENDER_MESSAGE, sender)
                        .putExtra(ProjectConstants.SERVICE_MESSAGE, message);
                try {
                    pendingIntent.send(context, RESULT_OK, result);
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            } else {
                result = new Intent(context, MessagesActivity.class);
                pendingIntent = PendingIntent.getActivity(context, 0, result, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder notificationBuilder = new Notification.Builder((context))
                        .setContentTitle("Входящее сообщение")
                        .setContentText("от " + sender)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true)
                        .setSmallIcon(R.mipmap.ic_launcher);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notificationManager.notify(ProjectConstants.ID_NOTIFICATION_RECEIVE_MESSAGE, notificationBuilder.build());
                } else {
                    notificationManager.notify(ProjectConstants.ID_NOTIFICATION_RECEIVE_MESSAGE, notificationBuilder.getNotification());
                }
            }
        }
    }

    public class AnswerBinder extends Binder{
        public ReceiveMessageService gerService(){
            return ReceiveMessageService.this;
        }
    }
}
