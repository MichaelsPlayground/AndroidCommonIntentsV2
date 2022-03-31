package de.androidcrypto.androidcommonintents;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class IntentGroup04 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    EditText etE01, etE02, etE03;
    TextView tvG04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_group04);

        btn01 = findViewById(R.id.btnG04B01);
        btn02 = findViewById(R.id.btnG04B02);
        btn03 = findViewById(R.id.btnG04B03);
        btn04 = findViewById(R.id.btnG04B04);
        btn05 = findViewById(R.id.btnG04B05);
        btn06 = findViewById(R.id.btnG04B06);
        btn07 = findViewById(R.id.btnG04B07);
        btn08 = findViewById(R.id.btnG04B08);

        etE01 = findViewById(R.id.etG04E01);
        etE02 = findViewById(R.id.etG04E02);
        etE03 = findViewById(R.id.etG04E03);
        tvG04 = findViewById(R.id.tvG04);

        // create some random content
        RandomString gen = new RandomString(5);
        String randomString = "A " + gen.nextString() + " B " + gen.nextString();
        etE01.setText(randomString);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store a file in internal storage
                String dataToWrite = etE01.getText().toString();
                String filename = etE03.getText().toString();
                tvG04.setText(""); // clear the info
                // sanity check
                if (dataToWrite.equals("")) {
                    tvG04.setText("no data to save");
                }
                if (filename.equals("")) {
                    tvG04.setText("no filename to save");
                }
                FileWriter writer = null;
                try {
                    File file = new File(v.getContext().getFilesDir(), filename);
                    writer = new FileWriter(file);
                    writer.append(dataToWrite);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    tvG04.setText("ERROR: " + e.toString());
                    return;
                }
                String message = "file written to internal storage: " + filename;
                tvG04.setText(message);
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read a file from internal storage
                String dataToLoad = "";
                etE02.setText(dataToLoad); // clear the edittext
                tvG04.setText(""); // clear the info
                String filename = etE03.getText().toString();
                // sanity check
                if (filename.equals("")) {
                    tvG04.setText("no filename to load");
                }
                File file = new File(v.getContext().getFilesDir(), filename);
                // check that file is existing
                if (!file.exists()) {
                    String errorMessage = "file not existing: " + filename;
                    tvG04.setText(errorMessage);
                    return;
                }
                int length = (int) file.length();
                byte[] bytes = new byte[length];
                FileInputStream in = null;
                try {
                    in = new FileInputStream(file);
                    in.read(bytes);
                    in.close();
                    dataToLoad = new String(bytes);
                    etE02.setText(dataToLoad);
                } catch (IOException e) {
                    e.printStackTrace();
                    tvG04.setText("ERROR: " + e.toString());
                    return;
                }
                String message = "file loaded from internal storage: " + filename;
                tvG04.setText(message);

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