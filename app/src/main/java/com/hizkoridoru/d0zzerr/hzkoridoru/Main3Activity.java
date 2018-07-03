package com.hizkoridoru.d0zzerr.hzkoridoru;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.hizkoridoru.sergen.hzkoridoru.R;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {


    Spinner giris, cikis;
    Button b_Hesapla;
    int girisId,cikisId,secilenarac,secilenotoyol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        setHomeIcon();

        giris = (Spinner) findViewById(R.id.sp_giris);
        cikis = (Spinner) findViewById(R.id.sp_cikis);
        b_Hesapla = (Button) findViewById(R.id.b_hesapla);

        Bundle extras = getIntent().getExtras();

        secilenarac= extras.getInt("secilenarac");
        secilenotoyol= extras.getInt("secilenotoyol");

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this.getApplicationContext());
        databaseAccess.open();

        final ArrayList<String>[] array_giris = databaseAccess.getGirisGiseleri(secilenotoyol);
        final ArrayList<String>[] array_cikis = databaseAccess.getCikisGiseleri(secilenotoyol);

        databaseAccess.close();


//select * from giseler_arasi g  inner join ucretler u on g.id = u.giseler_arasi_id where (giris_gise_id = 1 and cikis_gise_id=2 )or (giris_gise_id = 2 and cikis_gise_id=1)
// select * from giseler_arasi where (giris_gise = "SEFERİHİSAR" AND cikis_gise= "KARABURUN") OR (giris_gise="KARABURUN" and cikis_gise = "SEFERİHİSAR")

        ArrayAdapter<String> girisAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,array_giris[1]);
        ArrayAdapter<String> cikisAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,array_cikis[1]);

        girisAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        giris.setAdapter(girisAdapter);
        cikis.setAdapter(cikisAdapter);


        giris.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                girisId =  Integer.parseInt(array_giris[0].get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cikis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cikisId = Integer.parseInt(array_cikis[0].get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        b_Hesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess database = DatabaseAccess.getInstance(getApplicationContext());
                database.open();

                String girisgiseismi = database.giseismi(girisId,true);
                String cikisgiseismi = database.giseismi(cikisId,false);

                ArrayList<String> gisearasi = database.giselerArasi(girisgiseismi,cikisgiseismi);
                if(gisearasi.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Lütfen giriş - çıkış gişelerini doğru seçtiğinizden emin olun", Toast.LENGTH_SHORT).show();
                    return;

                }



                Intent intent = new Intent(getApplicationContext(),Main4Activity.class);
                intent.putExtra("girisId",girisId);
                intent.putExtra("cikisId",cikisId);
                intent.putExtra("secilenotoyol",secilenotoyol);
                intent.putExtra("secilenarac",secilenarac);
                startActivity(intent);
            }
        });



    }

    public void setHomeIcon(){
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
}
