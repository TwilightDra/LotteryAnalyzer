package com.td.lotteryanalyzer.core;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.td.lotteryanalyzer.R;

public class LottoAnalyzer extends AppCompatActivity {

    public String dbName;
    public SQLiteDatabase db;
    private int IdCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotto_analyzer);
        openDB();
        createTB();
        checkTB();
    }
    public void openDB(){
        dbName = "LottoAnalyzer.db";
        db = openOrCreateDatabase(dbName, 0, null);

        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }
    public void createTB(){
        db.beginTransaction();
        try{

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "LottoResult"
                    + " (ID integer PRIMARY KEY, Image BINARY, TagDate varchar(10), TagKeyword varchar(20));");

            db.execSQL("INSERT INTO ImageData (ID , Image, TagDate) VALUES (0,'DummyTest123456789','2017-09-26');");
            //db.execSQL("DROP TABLE PhoneBook;");
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
        }
        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }
    public void checkTB(){
        Cursor dbCursor = db.query( "ImageData", null, null, null, null, null, null);
        int idCol = dbCursor.getColumnIndex("ID");
        if(dbCursor.getCount()!=0){
            dbCursor.moveToLast();
            IdCheck=dbCursor.getInt(idCol);
            //Toast.makeText(this, "IDcheck:"+IdCheck, Toast.LENGTH_SHORT).show();
        }else{
            IdCheck=0;
        }
    }
}
