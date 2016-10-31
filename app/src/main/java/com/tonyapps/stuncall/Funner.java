package com.tonyapps.stuncall;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Funner extends Activity {
    private static int RETURN_TIME_OUT = 6000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funner);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        },RETURN_TIME_OUT);
    }
}

