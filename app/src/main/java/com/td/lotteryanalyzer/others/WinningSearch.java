package com.td.lotteryanalyzer.others;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.td.lotteryanalyzer.R;
import com.td.lotteryanalyzer.core.LottoAnalyzer;

public class WinningSearch extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_search);
        Toast.makeText(this,LottoAnalyzer.nbs[0].toString(),Toast.LENGTH_SHORT).show();

    }
}
