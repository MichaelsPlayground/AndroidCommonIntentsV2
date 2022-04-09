package de.androidcrypto.androidcommonintents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import java.io.File;

public class IntentGroup07 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    TextView tvG07;
    EditText etG07E03, etG07E04, etG07E05;

    final int PICK_AUDIO_REQUEST = 11;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_group07);

        btn01 = findViewById(R.id.btnG07B01);
        btn02 = findViewById(R.id.btnG07B02);
        btn03 = findViewById(R.id.btnG07B03);
        btn04 = findViewById(R.id.btnG07B04);
        btn05 = findViewById(R.id.btnG07B05);
        btn06 = findViewById(R.id.btnG07B06);
        btn07 = findViewById(R.id.btnG07B07);
        btn08 = findViewById(R.id.btnG07B08);

        tvG07 = findViewById(R.id.tvG07);
        etG07E03 = findViewById(R.id.etG07E03);
        etG07E04 = findViewById(R.id.etG07E04);
        etG07E05 = findViewById(R.id.etG07E05);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pick an audio file and play it
                System.out.println("*** group 07: pick an audio file and play it");
                tvG07.setText("");
                context = v.getContext();
                Intent audioIntent = new Intent();
                audioIntent.setType("audio/*");
                //audioIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                audioIntent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(audioIntent,PICK_AUDIO_REQUEST); // deprecated
                pickAudioFileAndPlayItViaIntentActivityResultLauncher.launch(audioIntent);
/*                // check that an audio player is available
                if (audioIntent.resolveActivity(getPackageManager()) != null) {
                    pickAudioFileAndPlayItViaIntentActivityResultLauncher.launch(audioIntent);
                    //startActivityForResult(audioIntent,PICK_AUDIO_REQUEST); // deprecated
                } else {
                    tvG07.setText("ERROR: no audio player available on phone");
                }

 */
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pick a video file and play it
                System.out.println("*** group 07: pick a video file and play it");
                tvG07.setText("");
                context = v.getContext();
                Intent videoIntent = new Intent();
                videoIntent.setType("video/*");
                //videoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                videoIntent.setAction(Intent.ACTION_GET_CONTENT);
                pickVideoFileAndPlayItViaIntentActivityResultLauncher.launch(videoIntent);
                /*// check that a video player is available
                if (videoIntent.resolveActivity(getPackageManager()) != null) {
                    pickVideoFileAndPlayItViaIntentActivityResultLauncher.launch(videoIntent);
                } else {
                    tvG07.setText("ERROR: no video player available on phone");
                }*/
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enter an url and play it
/*
<!-- Permissions of internet -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
                // sample data: https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3
                tvG07.setText("");
                String url = etG07E03.getText().toString();
                if (url.equals("")) {
                    tvG07.setText("ERROR: paste an audio URL to play");
                    return;
                }
                Uri uri = Uri.parse(url);
                Intent viewMediaIntent = new Intent();
                viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);
                viewMediaIntent.setDataAndType(uri, "audio/*");
                viewMediaIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(viewMediaIntent);
                if (viewMediaIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewMediaIntent);
                } else {
                    tvG07.setText("ERROR: something got wrong (e.g. false URL ? no internet ?)");
                }

            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// enter an url and play it
/*
<!-- Permissions of internet -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
                // sample data: https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/360/Big_Buck_Bunny_360_10s_2MB.mp4
                tvG07.setText("");
                String url = etG07E04.getText().toString();
                if (url.equals("")) {
                    tvG07.setText("ERROR: paste a video URL to play");
                    return;
                }
                Uri uri = Uri.parse(url);
                Intent viewMediaIntent = new Intent();
                viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);
                viewMediaIntent.setDataAndType(uri, "video/*");
                viewMediaIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(viewMediaIntent);
                if (viewMediaIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewMediaIntent);
                } else {
                    tvG07.setText("ERROR: something got wrong (e.g. false URL ? no internet ?)");
                }
            }
        });

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// enter an url and play it
/*
<!-- Permissions of internet -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
                // sample data: https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/360/Big_Buck_Bunny_360_10s_2MB.mp4
                tvG07.setText("");
                String url = etG07E05.getText().toString();
                if (url.equals("")) {
                    tvG07.setText("ERROR: paste a video stream URL to play");
                    return;
                }
                Uri uri = Uri.parse(url);
                Intent viewMediaIntent = new Intent();
                viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);
                viewMediaIntent.setDataAndType(uri, "video/*");
                viewMediaIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(viewMediaIntent);
                if (viewMediaIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewMediaIntent);
                } else {
                    tvG07.setText("ERROR: something got wrong (e.g. false URL ? no internet ?)");
                }
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

    ActivityResultLauncher<Intent> pickAudioFileAndPlayItViaIntentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        System.out.println("pickAudioFileAndPlayItViaIntentActivityResultLauncher");
                        // There are no request codes
                        Intent resultData = result.getData();
                        Uri uri= resultData.getData();
                        Intent viewMediaIntent = new Intent();
                        viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);
                        viewMediaIntent.setDataAndType(uri, "audio/*");
                        viewMediaIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(viewMediaIntent);
                    }
                }
            });

    ActivityResultLauncher<Intent> pickVideoFileAndPlayItViaIntentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        System.out.println("pickVideoFileAndPlayItViaIntentActivityResultLauncher");
                        // There are no request codes
                        Intent resultData = result.getData();
                        Uri uri= resultData.getData();
                        Intent viewMediaIntent = new Intent();
                        viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);
                        viewMediaIntent.setDataAndType(uri, "video/*");
                        viewMediaIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(viewMediaIntent);
                    }
                }
            });


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null || data.getData() == null) {
            // error
            return;
        }
        if (requestCode == PICK_AUDIO_REQUEST) {
            try {
                System.out.println("*** PICK_AUDIO_REQUEST");
                Uri uri= data.getData();
                Intent viewMediaIntent = new Intent();
                viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);
                viewMediaIntent.setDataAndType(uri, "audio/*");
                viewMediaIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(viewMediaIntent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }








    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}