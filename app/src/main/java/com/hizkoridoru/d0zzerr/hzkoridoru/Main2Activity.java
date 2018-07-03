package com.hizkoridoru.d0zzerr.hzkoridoru;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hizkoridoru.sergen.hzkoridoru.R;

public class Main2Activity extends AppCompatActivity {

    LinearLayout otoyollar[];

    int secilenotoyol;
    int secilenarac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setHomeIcon();

        Bundle extras = getIntent().getExtras();

        secilenarac = extras.getInt("secilen");

        otoyollar = new LinearLayout[6];

        otoyollar[0] = (LinearLayout) findViewById(R.id.v_anadolu);
        otoyollar[1] = (LinearLayout) findViewById(R.id.v_cesme);
        otoyollar[2] = (LinearLayout) findViewById(R.id.v_aydin);
        otoyollar[3] = (LinearLayout) findViewById(R.id.v_urfa);
        otoyollar[4] = (LinearLayout) findViewById(R.id.v_mersin);
        otoyollar[5] = (LinearLayout) findViewById(R.id.v_avrupa);


        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (v.getId()){
                    case R.id.v_anadolu:secilenotoyol=1;break;
                    case R.id.v_cesme:secilenotoyol=2;break;
                    case R.id.v_aydin:secilenotoyol=3;break;
                    case R.id.v_urfa:secilenotoyol=4;break;
                    case R.id.v_mersin:secilenotoyol=5;break;
                    case R.id.v_avrupa:secilenotoyol=6;break;

                }

                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                intent.putExtra("secilenarac",secilenarac);
                intent.putExtra("secilenotoyol",secilenotoyol);

                startActivity(intent);


            }
        };

        for (int i=0;i<6;i++){
            otoyollar[i].setOnClickListener(v);
        }





    }
    public void setHomeIcon(){
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
}
