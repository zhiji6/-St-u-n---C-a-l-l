package com.tonyapps.stuncall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends Activity {
    EditText inputName, inputPhone;
    Button btncall;
    SharedPreferences preferences;
    private AdView mAdView,mAdView1;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    AdRequest adRequest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = (EditText) findViewById(R.id.input_name);
        inputPhone = (EditText) findViewById(R.id.input_phone);
        btncall = (Button) findViewById(R.id.btn_call);
        preferences=getSharedPreferences("call", Context.MODE_PRIVATE);
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String MobilePattern = "[0-9]{10}";
                final String fname = inputName.getText().toString();
                final String phone = inputPhone.getText().toString();
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("NAME", fname);//key and value pair
                editor.putString("PHONE", phone);
                editor.commit();

                if (fname.length() == 0) {
                    inputName.requestFocus();
                    inputName.setError("Field Can't Be Empty");

                } else if (phone.length() == 0) {
                    inputPhone.requestFocus();
                    inputPhone.setError("Field Can't Be Empty");

                } else if (!fname.matches("[a-zA-Z]+")) {
                    inputName.requestFocus();
                    inputName.setError("Enter only Alphabets");
                }
                else if(!phone.matches(MobilePattern)) {
                    inputPhone.requestFocus();
                    inputPhone.setError("Enter 10 digit phone number");

                }
                else{
                    Intent i=new Intent(MainActivity.this,DialingPage.class);
                    startActivity(i);
                }
            }
        });



        //===========================
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView1 = (AdView) findViewById(R.id.adView1);
        adRequest= new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        mAdView1.loadAd(adRequest);

        //==================================
        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

       adRequest1 = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest1);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

    }
    @Override
    public void onBackPressed() {
        mInterstitialAd.loadAd(adRequest1);
    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}




