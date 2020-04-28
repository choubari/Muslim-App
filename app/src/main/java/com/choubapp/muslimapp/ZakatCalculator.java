package com.choubapp.muslimapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ZakatCalculator extends AppCompatActivity {
    TextView ToUnderline;
    EditText GoldPrice,TotalPrice;
    TextView Result1,Result2;
    float InputGold, InputTotal;
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat_calculator);
        ToUnderline= findViewById(R.id.underlinelink);
        ToUnderline.setPaintFlags(ToUnderline.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
    }

    public void LocalPriceGold(View v) {
        Intent intent = new Intent(this, GoldPrice.class);
        startActivity(intent);
    }

    public void ZakatInfo(View v){
        k=1;
        setContentView(R.layout.zakatinfo);
    }

    @SuppressLint("SetTextI18n")
    public  void CalculateZakat(View v){
        GoldPrice=findViewById(R.id.inputgoldprice);
        TotalPrice=findViewById(R.id.inputtotalprice);
        Result1=findViewById(R.id.zakatresult1);
        Result2=findViewById(R.id.zakatresult2);
        if (GoldPrice.getText().toString().equals("") || TotalPrice.getText().toString().equals("")) return;
        InputGold=Float.parseFloat(GoldPrice.getText().toString());
        InputTotal=Float.parseFloat(TotalPrice.getText().toString());

        double nissab = InputGold*85.05;
        String result = getString(R.string.nissab) +" "+ nissab;
        String zakat ;
        if (nissab>InputTotal){
            zakat =getString(R.string.nozakat);
            Result1.setText(zakat);
            Result2.setText(result);
        }else{
            float zakatprice = (float) (InputTotal*0.025);
            zakat= getString(R.string.zakatprice) +" "+ zakatprice ;
            Result1.setText(zakat);
            Result2.setText(result);
        }
    }

    @Override
    public void onBackPressed() {
        if (k == 1) {
            k = 0;
            //super.onBackPressed();
            setContentView(R.layout.activity_zakat_calculator);

        } else {
            super.onBackPressed();
        }
    }
}
