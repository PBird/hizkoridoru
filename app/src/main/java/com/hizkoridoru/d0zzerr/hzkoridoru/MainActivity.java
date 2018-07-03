package com.hizkoridoru.d0zzerr.hzkoridoru;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hizkoridoru.sergen.hzkoridoru.R;

public class MainActivity extends AppCompatActivity {


    LinearLayout[] araclar;
    int secilen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setHomeIcon();

        araclar = new LinearLayout[9];

        araclar[0] = (LinearLayout) findViewById(R.id.im_otomobil);
        araclar[1] = (LinearLayout) findViewById(R.id.im_minubus_otobus);
        araclar[2] = (LinearLayout) findViewById(R.id.im_panelvan);
        araclar[3] = (LinearLayout) findViewById(R.id.im_kamyonet);
        araclar[4] = (LinearLayout) findViewById(R.id.im_kamyon);
        araclar[5] = (LinearLayout) findViewById(R.id.im_motosikletl3);
        araclar[6] = (LinearLayout) findViewById(R.id.im_motosikletl457);
        araclar[7] = (LinearLayout) findViewById(R.id.im_tehlikeli);
        araclar[8] = (LinearLayout) findViewById(R.id.im_araccekici);


        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (v.getId()){
                    case R.id.im_otomobil:secilen=0;break;
                    case R.id.im_minubus_otobus:secilen=1;break;
                    case R.id.im_panelvan:secilen=2;break;
                    case R.id.im_kamyonet:secilen=3;break;
                    case R.id.im_kamyon:secilen=4;break;
                    case R.id.im_motosikletl3:secilen=5;break;
                    case R.id.im_motosikletl457:secilen=6;break;
                    case R.id.im_tehlikeli:secilen=7;break;
                    case R.id.im_araccekici:secilen=8;break;
                }

                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("secilen",secilen);
                startActivity(intent);


            }
        };

        for (int i=0;i<8;i++){
            araclar[i].setOnClickListener(v);
        }
    }
    public void setHomeIcon(){
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
}
