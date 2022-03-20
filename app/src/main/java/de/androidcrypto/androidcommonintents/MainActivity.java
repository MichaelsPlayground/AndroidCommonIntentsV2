package de.androidcrypto.androidcommonintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // examples taken from here:
    // https://developer.android.com/guide/components/intents-common
    // https://guides.peruzal.com/v1/android-guides/common-intents/
    // https://guides.codepath.com/android/Common-Implicit-Intents

    Button btnMain01, btnMain02, btnMain03, btnMain04, btnMain05, btnMain06, btnMain07;
    Intent group01Intent, group02Intent, group03Intent, group04Intent, group05Intent,
            group06Intent, group07Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMain01 = findViewById(R.id.btnMain01);
        btnMain02 = findViewById(R.id.btnMain02);
        btnMain03 = findViewById(R.id.btnMain03);
        btnMain04 = findViewById(R.id.btnMain04);
        btnMain05 = findViewById(R.id.btnMain05);
        btnMain06 = findViewById(R.id.btnMain06);
        btnMain07 = findViewById(R.id.btnMain07);

        group01Intent = new Intent(MainActivity.this, IntentGroup01.class);
        group02Intent = new Intent(MainActivity.this, IntentGroup02.class);
        group03Intent = new Intent(MainActivity.this, IntentGroup03.class);
        group04Intent = new Intent(MainActivity.this, IntentGroup04.class);
        group05Intent = new Intent(MainActivity.this, IntentGroup05.class);
        group06Intent = new Intent(MainActivity.this, IntentGroup06.class);
        group07Intent = new Intent(MainActivity.this, IntentGroup07.class);

        btnMain01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(group01Intent);
            }
        });

        btnMain02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(group02Intent);
            }
        });

        btnMain03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(group03Intent);
            }
        });

        btnMain04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(group04Intent);
            }
        });

        btnMain05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(group05Intent);
            }
        });

        btnMain06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(group06Intent);
            }
        });

        btnMain07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(group07Intent);
            }
        });


    }
}