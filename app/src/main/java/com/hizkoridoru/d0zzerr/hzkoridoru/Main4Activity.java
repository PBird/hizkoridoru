package com.hizkoridoru.d0zzerr.hzkoridoru;


import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.hizkoridoru.sergen.hzkoridoru.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {


    private final static int[] AracHizSinirlari = { 120,100,110,100,95,90,100,80,60,40};
    private int girisId,cikisId,secilenarac,secilenotoyol;
    private TextView otoyol,giris,cikis,ucretler,t_mesafe,t_hizsiniri,counterDown;
    private ImageView im_arac ;
    private Button baslat,sifirla;
    private int sinif=0;

    private int notificationId = 1;
    NotificationManager notificationManager;
    NotificationCompat.Builder mBuilder=null;

    Intent counterIntent;


    private ArrayList<String> gisearasi;
    private int hizsiniri;
    private Double time;
    private Double mesafe;
    private CountDownTimer countDownTimer;
    private boolean runningtimer,isPaused,isCanceled ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        setHomeIcon();



        Bundle extras = getIntent().getExtras();
        secilenarac= extras.getInt("secilenarac");
        secilenotoyol= extras.getInt("secilenotoyol");
        girisId= extras.getInt("girisId");
        cikisId= extras.getInt("cikisId");
        runningtimer = false;

        otoyol = (TextView) findViewById(R.id.otoyol);
        giris = (TextView) findViewById(R.id.giris_gisesi);
        cikis = (TextView) findViewById(R.id.cikis_gisesi);
        ucretler = (TextView) findViewById(R.id.ucretler);
        t_mesafe = (TextView) findViewById(R.id.mesafe);
        t_hizsiniri = (TextView) findViewById(R.id.hizsiniri);
        baslat = (Button) findViewById(R.id.b_baslat);
        sifirla = (Button) findViewById(R.id.b_sifirla);
        im_arac = (ImageView) findViewById(R.id.im_arac);
        counterDown = (TextView) findViewById(R.id.counterDown);

        sifirla.setEnabled(false);

        switch (secilenarac) {
            case 0: im_arac.setImageResource(R.drawable.otomobil);break;
            case 1: im_arac.setImageResource(R.drawable.minubusotobus);break;
            case 2: im_arac.setImageResource(R.drawable.panelvan);break;
            case 3: im_arac.setImageResource(R.drawable.kamyonet);break;
            case 4: im_arac.setImageResource(R.drawable.kamyon);break;
            case 5: im_arac.setImageResource(R.drawable.motosikletl3);break;
            case 6: im_arac.setImageResource(R.drawable.motosikletl457);break;
            case 7: im_arac.setImageResource(R.drawable.tehlikeli);break;
            case 8: im_arac.setImageResource(R.drawable.araccekici);break;

        }


        DatabaseAccess database = DatabaseAccess.getInstance(getApplicationContext());
        database.open();

        String girisgiseismi = database.giseismi(girisId,true);
        String cikisgiseismi = database.giseismi(cikisId,false);

        gisearasi = database.giselerArasi(girisgiseismi,cikisgiseismi);


        String otoyolismi = database.otoyolismi(secilenotoyol);


        otoyol.setText(otoyolismi);
        giris.setText(girisgiseismi);
        cikis.setText(cikisgiseismi);
        String ucrets = new String() ;
        for(int i=1;i<6;i++)
            ucrets += gisearasi.get(i) +"tl  -  ";
        ucrets = ucrets.substring(0,ucrets.length()-3);
        ucretler.setText(ucrets);

        mesafe = Double.parseDouble(gisearasi.get(0));
        hizsiniri = AracHizSinirlari[secilenarac];

        t_mesafe.setText("Mesafe : "+mesafe + "km");
        t_hizsiniri.setText("Hız Sınırı : "+hizsiniri+"km/saat");



        setCounterDown();

        baslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!runningtimer)
                {
                    counterIntent = new Intent(getApplicationContext(),CounterDownService.class);
                    counterIntent.putExtra("mesafe",mesafe);
                    counterIntent.putExtra("hizsiniri",hizsiniri);
                    startService(counterIntent );
                    isCanceled = false;
                    start(v);
                    baslat.setEnabled(false);
                    sifirla.setEnabled(true);
                }
                else {

                    System.out.println(" runnintimer true ");
                }
            }
        });

        sifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(runningtimer)
                {
                    isCanceled = true;
                    setCounterDown();
                    baslat.setEnabled(true);
                    sifirla.setEnabled(false);
                    stopService(counterIntent);

                }
                else {


                }
            }
        });


    }

    private void start(View v) {

//        Toast.makeText(Main4Activity.this, "Hours : " + hours + "  minute : " + minute + "  sec : " + second , Toast.LENGTH_SHORT).show();
        long milliseconds = (long) (time * 3600000) ;

        System.out.println("milliseconds : " + milliseconds );

         countDownTimer = new CountDownTimer(milliseconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(isCanceled) {
                    cancel();
                    runningtimer = false;
                    stopService(counterIntent);
                }
                else{

                    System.out.println("millisUntilFinished : " + millisUntilFinished );
                    int hours =(int) (millisUntilFinished /(3600000.0));
                    int minutes = (int)(((millisUntilFinished /(3600000.0)) - hours ) * 60);
                    int seconds = (int) (((((millisUntilFinished /(3600000.0)) - hours ) * 60) - minutes) * 60);


                    DecimalFormat format = new DecimalFormat("00");
                    String timeStamp = format.format(hours) + ":" + format.format(minutes)+":"+format.format(seconds);
                    counterDown.setText(timeStamp);
//                    if(mBuilder !=null)
//                    {
//                        mBuilder.setContentText(timeStamp);
//                        notificationManager.notify(notificationId,mBuilder.build());
//                    }
                    runningtimer = true;

                }

            }

            @Override
            public void onFinish() {
                if(!isCanceled)
                    counterDown.setText(" Bitti ");
            }

        };
        countDownTimer.start();

    }
    public void setHomeIcon(){
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void setCounterDown(){

        time = mesafe / hizsiniri ;
        int hours = time.intValue();
        int minutes = (int)((time.doubleValue()-hours) *60);
        int seconds = (int)((((time.doubleValue()-hours) *60) - minutes)*60);

        DecimalFormat format = new DecimalFormat("00");
        counterDown.setText(format.format(hours) + ":" + format.format(minutes)+":"+format.format(seconds) );

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus){
            if(runningtimer){
//                System.out.println(" Timer çalışırken program alta alındı");
//                mBuilder = new NotificationCompat.Builder(Main4Activity.this)
//                        .setSmallIcon(android.R.drawable.ic_dialog_info)
//                        .setContentTitle("Kalan Süre")
//                        .setContentText("Bilmem Kaç dakika ");
//
//                // Obtain NotificationManager system service in order to show the notification
//                notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                notificationManager.notify(notificationId, mBuilder.build());
            }
        }
        else {
            if(runningtimer){
//                notificationManager.cancel(1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isCanceled=true;
        if(counterIntent != null)
            stopService(counterIntent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(counterIntent != null)
            stopService(counterIntent);

    }
}
