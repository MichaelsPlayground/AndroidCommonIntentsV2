package de.androidcrypto.androidcommonintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentGroup06 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    EditText etG06E01, etG06E02, etG06E03, etG06E04, etG06E05;
    TextView tvG06;
    String timeStamp;

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

        etG06E01 = findViewById(R.id.etG06E01);
        etG06E02 = findViewById(R.id.etG06E02);
        etG06E03 = findViewById(R.id.etG06E03);
        etG06E04 = findViewById(R.id.etG06E04);
        etG06E05 = findViewById(R.id.etG06E05);
        tvG06 = findViewById(R.id.tvG06);

        timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
        etG06E05.setText("body from " + timeStamp);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("group 06 send an email");
                String[] addresses = etG06E01.getText().toString().split(",");
                String[] ccAddresses = etG06E02.getText().toString().split(",");
                String[] bcAddresses = etG06E03.getText().toString().split(",");
                String subject = etG06E04.getText().toString();
                String body = etG06E05.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_CC, ccAddresses);
                intent.putExtra(Intent.EXTRA_BCC, bcAddresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("group 06 send an email to email clients");
                String[] addresses = etG06E01.getText().toString().split(",");
                String[] ccAddresses = etG06E02.getText().toString().split(",");
                String[] bcAddresses = etG06E03.getText().toString().split(",");
                String subject = etG06E04.getText().toString();
                String body = etG06E05.getText().toString();
                Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                selectorIntent.setData(Uri.parse("mailto:"));
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_CC, ccAddresses);
                intent.putExtra(Intent.EXTRA_BCC, bcAddresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                intent.setSelector(selectorIntent);
                startActivity(Intent.createChooser(intent, "Send email..."));
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