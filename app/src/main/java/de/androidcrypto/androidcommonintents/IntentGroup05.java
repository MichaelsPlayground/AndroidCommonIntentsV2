package de.androidcrypto.androidcommonintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntentGroup05 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    TextView tvG05;
    EditText etG05B01, etG05B04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_group05);

        btn01 = findViewById(R.id.btnG05B01);
        btn02 = findViewById(R.id.btnG05B02);
        btn03 = findViewById(R.id.btnG05B03);
        btn04 = findViewById(R.id.btnG05B04);
        btn05 = findViewById(R.id.btnG05B05);
        btn06 = findViewById(R.id.btnG05B06);
        btn07 = findViewById(R.id.btnG05B07);
        btn08 = findViewById(R.id.btnG05B08);

        tvG05 = findViewById(R.id.tvG05);
        etG05B01 = findViewById(R.id.etG05B01);
        etG05B04 = findViewById(R.id.etG05B04);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <data android:scheme="geo" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>

    <queries>
             <package android:name="com.google.android.apps.maps" />
    </queries>
                 */
                System.out.println("01 show a map by coordinates");
                String coordinates = etG05B01.getText().toString();
                String geoCoordinates = "geo:" + coordinates;
                Uri gmmIntentUri = Uri.parse(geoCoordinates);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    System.out.println("no application found");
                    tvG05.setText("no application found");
                }
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("02 show a map by coordinates with title");
                String coordinates = etG05B01.getText().toString();
                String searchString = "wire pass"; // searching for this
                String geoCoordinates = "geo:" + coordinates + "?q=";
                Uri gmmIntentUri = Uri.parse(geoCoordinates + Uri.encode(searchString));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    System.out.println("no application found");
                    tvG05.setText("no application found");
                }
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("03 show a map by coordinates zoom level");
                String coordinates = etG05B01.getText().toString();
                String zoomLevel = "10"; // zooming to this
                String geoCoordinates = "geo:" + coordinates + "?z=";
                Uri gmmIntentUri = Uri.parse(geoCoordinates + Uri.encode(zoomLevel));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    System.out.println("no application found");
                    tvG05.setText("no application found");
                }
            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("04 show a map by plus code");
                String plusCode = etG05B04.getText().toString();
                String geoCoordinates = "http://plus.codes/" + plusCode;
                Uri gmmIntentUri = Uri.parse(geoCoordinates);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    System.out.println("no application found");
                    tvG05.setText("no application found");
                }
            }
        });

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigation
                Uri gmmIntentUri = Uri.parse("google.navigation:q=Airport+Las Vegas+Nevada");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // street view
                // Displays an image of the Bell Rock and Courthouse Rock in Sedona, Arizona
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=34.795528769957734,-111.76493307319492&cbp=0,30,0,0,-15");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}