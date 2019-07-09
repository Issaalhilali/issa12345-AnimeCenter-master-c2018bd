package com.example.animecenter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.animecenter.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AboutActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MobileAds.initialize(this,getString(R.string.ads_banner));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.ads_Interstitials));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
