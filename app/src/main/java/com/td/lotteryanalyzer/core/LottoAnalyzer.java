package com.td.lotteryanalyzer.core;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.td.lotteryanalyzer.R;
import com.td.lotteryanalyzer.others.Analyzer;
import com.td.lotteryanalyzer.others.DrawTrend;
import com.td.lotteryanalyzer.others.WinningSearch;

public class LottoAnalyzer extends AppCompatActivity {

    public String dbName;
    public SQLiteDatabase db;
    private int IdCheck;
    private TextView _n1,_n2,_n3,_n4,_n5,_n6,_n7,_nb;
    private Spinner spinner;
    private int drawType;
    public static String[] nbs = new String[7];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotto_analyzer);
        openDB();
        createTB();
        //checkTB();
        _n1=(TextView)findViewById(R.id.txt_n1);
        _n2=(TextView)findViewById(R.id.txt_n2);
        _n3=(TextView)findViewById(R.id.txt_n3);
        _n4=(TextView)findViewById(R.id.txt_n4);
        _n5=(TextView)findViewById(R.id.txt_n5);
        _n6=(TextView)findViewById(R.id.txt_n6);
        _n7=(TextView)findViewById(R.id.txt_n7);
        _nb=(TextView)findViewById(R.id.txt_nb);
        spinner=(Spinner)findViewById(R.id.spinner);
        drawType=0;
        showDraw();
        //Data source:0=MAX, 1=6/49, 2=Daily Ground
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
                    + " (drawDate varchar(10) PRIMARY KEY ,n1 varchar(2), n2 varchar(2), n3 varchar(2), " +
                    "n4 varchar(2), n5 varchar(2), n6 varchar(2), n7 varchar(2), nb varchar(2), " +
                    "lottoType varchar(1));");
            //-------LOTTO MAX---------
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-12-01','26','28','33','38','42','43','47','16','0');");
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-11-24','05','11','20','22','35','38','46','45','0');");
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-11-17','03','15','22','23','33','47','49','06','0');");
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-11-10','05','12','18','26','29','31','37','40','0');");
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-11-03','16','17','18','20','40','42','43','15','0');");
            //---------6/49--------------
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-12-02','10','12','29','33','47','48','00','24','1');");
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-11-29','11','18','27','33','37','43','00','20','1');");
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-11-25','16','17','21','31','39','49','00','29','1');");
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-11-22','26','28','31','36','38','45','00','18','1');");
            db.execSQL("INSERT INTO LottoResult (drawDate , n1,n2,n3,n4,n5,n6,n7,nb,lottoType) VALUES ('2017-11-18','09','16','18','22','23','40','00','13','1');");
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

    public void showDraw(){
        //Data source:0=MAX, 1=6/49, 2=Daily Ground
        String[] mItems;
        if(drawType==0){
            mItems=getResources().getStringArray(R.array.dateMax_array);
        }else if(drawType==1){
            mItems=getResources().getStringArray(R.array.date649_array);
        }else mItems=null;
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] sdate;
                if(drawType==0) sdate=getResources().getStringArray(R.array.dateMax_array);
                else if(drawType==1) sdate=getResources().getStringArray(R.array.date649_array);
                else sdate=null;
                //Toast.makeText(LottoAnalyzer.this, "date: "+sdate[pos], Toast.LENGTH_SHORT).show();
                try{
                    Cursor dbCursor = db.query( "LottoResult", null, null, null, null, null, null);
                    int dDateCol = dbCursor.getColumnIndex("drawDate");
                    int n1Col = dbCursor.getColumnIndex("n1");
                    int n2Col = dbCursor.getColumnIndex("n2");
                    int n3Col = dbCursor.getColumnIndex("n3");
                    int n4Col = dbCursor.getColumnIndex("n4");
                    int n5Col = dbCursor.getColumnIndex("n5");
                    int n6Col = dbCursor.getColumnIndex("n6");
                    int n7Col = dbCursor.getColumnIndex("n7");
                    int nbCol = dbCursor.getColumnIndex("nb");
                    int typeCol = dbCursor.getColumnIndex("lottoType");
                    if (dbCursor != null) {
                        dbCursor.moveToFirst();
                        if (dbCursor.getCount() != 0) {
                            int i = 0; //int index=1;
                            do {
                                i++;
                                String dDate = dbCursor.getString(dDateCol);
                                String n1 = dbCursor.getString(n1Col);
                                String n2 = dbCursor.getString(n2Col);
                                String n3 = dbCursor.getString(n3Col);
                                String n4 = dbCursor.getString(n4Col);
                                String n5 = dbCursor.getString(n5Col);
                                String n6 = dbCursor.getString(n6Col);
                                String n7 = dbCursor.getString(n7Col);
                                String nb = dbCursor.getString(nbCol);
                                String type=dbCursor.getString(typeCol);
                                //Toast.makeText(this, id + "  " + tday+" "+tkey, Toast.LENGTH_SHORT).show();
                                //String cheak=sdate[pos];
                                //if(dDate==null)  tkey="";
                                if(dDate.equals(sdate[pos])){
                                    _n1.setText(n1);
                                    _n2.setText(n2);
                                    _n3.setText(n3);
                                    _n4.setText(n4);
                                    _n5.setText(n5);
                                    _n6.setText(n6);
                                    _n7.setText(n7);
                                    _nb.setText(nb);
                                }
                            } while (dbCursor.moveToNext());
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public void to_649(View view){
        drawType=1;
        _n7.setVisibility(View.INVISIBLE);
        showDraw();
    }
    public void to_Max(View view){
        drawType=0;
        _n7.setVisibility(View.VISIBLE);
        showDraw();
    }
    public void to_DrawTrend(View view){
        Intent intent1= new Intent(LottoAnalyzer.this, DrawTrend.class);
        startActivity(intent1);
    }
    public void to_Analyzer(View view){
        Intent intent1= new Intent(LottoAnalyzer.this, Analyzer.class);
        startActivity(intent1);
    }
    public void to_WinningSearch(View view){
        Intent intent1= new Intent(LottoAnalyzer.this, WinningSearch.class);
        EditText n1=(EditText)findViewById(R.id.txt_w1);
        EditText n2=(EditText)findViewById(R.id.txt_w2);
        EditText n3=(EditText)findViewById(R.id.txt_w3);
        EditText n4=(EditText)findViewById(R.id.txt_w4);
        EditText n5=(EditText)findViewById(R.id.txt_w5);
        EditText n6=(EditText)findViewById(R.id.txt_w6);
        EditText n7=(EditText)findViewById(R.id.txt_w7);
        nbs=new String[7];
        nbs[0] = n1.getText().toString();
        nbs[1] = n2.getText().toString();
        nbs[2] = n3.getText().toString();
        nbs[3] = n4.getText().toString();
        nbs[4] = n5.getText().toString();
        nbs[5] = n6.getText().toString();
        nbs[6] = n7.getText().toString();
        /*for(int i=0;i<7;i++){
            intent1.putExtra(EXTRA_MESSAGE[i], nbs[i]);
        }*/
        startActivity(intent1);
    }

    /*private void cleanTxt (){

    }*/
}
