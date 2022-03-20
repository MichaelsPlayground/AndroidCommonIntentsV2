package de.androidcrypto.androidcommonintents;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntentGroup06 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    TextView tvG06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_group06);

        btn01 = findViewById(R.id.btnG06B01);
        btn02 = findViewById(R.id.btnG06B02);
        btn03 = findViewById(R.id.btnG06B03);
        btn04 = findViewById(R.id.btnG06B04);
        btn05 = findViewById(R.id.btnG06B05);
        btn06 = findViewById(R.id.btnG06B06);
        btn07 = findViewById(R.id.btnG06B07);
        btn08 = findViewById(R.id.btnG06B08);

        tvG06 = findViewById(R.id.tvG06);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}