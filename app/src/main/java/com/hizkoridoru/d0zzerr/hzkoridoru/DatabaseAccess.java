package com.hizkoridoru.d0zzerr.hzkoridoru;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by sergen on 03.01.2018.
 */

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public ArrayList<String>[] getGirisGiseleri(int oto_id) {
        ArrayList<String> list[]= new ArrayList[2];
        list[0] = new ArrayList<String>();
        list[1] = new ArrayList<String>();
        Cursor cursor = database.rawQuery("Select * from giris_giseler where oto_id="+oto_id,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            list[0].add(cursor.getString(0)); // id
            list[1].add(cursor.getString(1)); // name
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<String>[] getCikisGiseleri(int oto_id) {
        ArrayList<String> list[]= new ArrayList[2];
        list[0] = new ArrayList<String>();
        list[1] = new ArrayList<String>();

        Cursor cursor = database.rawQuery("Select * from cikis_giseler where oto_id="+oto_id,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            list[0].add(cursor.getString(0)); // id
            list[1].add(cursor.getString(1)); // name
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> giselerArasi(String giris,String cikis)
    {
        ArrayList<String> list = new ArrayList<String>();

        Cursor cursor = database.rawQuery("select mesafe,ucret1,ucret2,ucret3,ucret4,ucret5 from giseler_arasi g  inner join ucretler u on g.id = u.giseler_arasi_id  where (giris_gise= ? AND cikis_gise= ?) OR (giris_gise=? and cikis_gise= ?)",new String[]{giris,cikis,cikis,giris});


        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {

            list.add(cursor.getString(0)); // mesafe
            list.add(cursor.getString(1)); // ucret1
            list.add(cursor.getString(2)); // ucret2
            list.add(cursor.getString(3)); // ucret3
            list.add(cursor.getString(4)); // ucret4
            list.add(cursor.getString(5)); // ucret5

            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public String otoyolismi(int id){
        String name= new String();

        Cursor cursor = database.rawQuery("select name from Otoyollar where id=?",new String[]{id+""});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            name = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return name;

    }
    public String giseismi(int id,boolean a){ // a 1 ise giris 0 ise çıkış gişesinden
        String name= new String();
        Cursor cursor;
        if(a)
            cursor = database.rawQuery("select name from giris_giseler where id=?",new String[]{id+""});
        else
            cursor = database.rawQuery("select name from cikis_giseler where id=?",new String[]{id+""});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            name = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return name;

    }



}
