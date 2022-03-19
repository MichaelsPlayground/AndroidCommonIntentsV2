package de.androidcrypto.androidcommonintents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    // examples taken from here:
    // https://developer.android.com/guide/components/intents-common
    // https://guides.peruzal.com/v1/android-guides/common-intents/
    // https://guides.codepath.com/android/Common-Implicit-Intents


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}