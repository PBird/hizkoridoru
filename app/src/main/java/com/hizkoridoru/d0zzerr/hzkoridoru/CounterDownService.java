package com.hizkoridoru.d0zzerr.hzkoridoru;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.hizkoridoru.sergen.hzkoridoru.R;

import java.text.DecimalFormat;

/**
 * Created by sergen on 25.04.2018.
 */

public class CounterDownService extends Service{



    private static final String TAG = "CounterDownService";
    private boolean isRunning = false,isCanceled;

    private int hizsiniri;
    private Double time;
    private Double mesafe;


    private CountDownTimer countDownTimer;

    private int notificationId = 1;
    NotificationManager notificationManager;
    NotificationCompat.Builder mBuilder=null;



    @Override
    public void onCreate() {
        super.onCreate();


        mBuilder = new NotificationCompat.Builder(CounterDownService.this)
                .setSmallIcon(R.drawable.hizkoridoruapplogo)
                .setContentTitle("Kalan Süre")
                .setContentText("");

        // Obtain NotificationManager system service in order to show the notification
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, mBuilder.build());
        Log.i(TAG,"Service Started");

        isRunning = true;
        isCanceled = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent !=null){
            Bundle extras = intent.getExtras();
            mesafe = (double)extras.get("mesafe");
            hizsiniri =(int) extras.get("hizsiniri");
        }
        else {
            BroadcastReceiver mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    mesafe = (double)extras.get("mesafe");
                    hizsiniri =(int) extras.get("hizsiniri");
                }
            };
        }


        Log.i(TAG, "Service onStartCommand");


               setCounterDown();
               start();


        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        isCanceled = true;
        notificationManager.cancel(notificationId);
        Log.i(TAG,"Service onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       return  null;

    }

    private void setCounterDown(){

        time = mesafe / hizsiniri ;
        int hours = time.intValue();
        int minutes = (int)((time.doubleValue()-hours) *60);
        int seconds = (int)((((time.doubleValue()-hours) *60) - minutes)*60);

        DecimalFormat format = new DecimalFormat("00");

    }

    private void start() {

//        Toast.makeText(Main4Activity.this, "Hours : " + hours + "  minute : " + minute + "  sec : " + second , Toast.LENGTH_SHORT).show();

        long milliseconds = (long) (time * 3600000) ;

        System.out.println("milliseconds : " + milliseconds );

        countDownTimer = new CountDownTimer(milliseconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(isCanceled) {
                    cancel();
                    notificationManager.cancel(notificationId);


                }
                else {
                    System.out.println("millisUntilFinished : " + millisUntilFinished );
                    int hours =(int) (millisUntilFinished /(3600000.0));
                    int minutes = (int)(((millisUntilFinished /(3600000.0)) - hours ) * 60);
                    int seconds = (int) (((((millisUntilFinished /(3600000.0)) - hours ) * 60) - minutes) * 60);


                    DecimalFormat format = new DecimalFormat("00");
                    String timeStamp = format.format(hours) + ":" + format.format(minutes)+":"+format.format(seconds);

                    mBuilder.setContentText(timeStamp);
                    notificationManager.notify(notificationId,mBuilder.build());

                }



            }

            @Override
            public void onFinish() {
                mBuilder.setContentText("Çıkış Gişesinden ceza yeme riski olmadan rahatlıkla çıkabilirsiniz");
                mBuilder.setContentTitle("SÜRE BİTTİ");
                notificationManager.notify(notificationId,mBuilder.build());
            }

        };
        countDownTimer.start();

    }

}
