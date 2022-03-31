package de.androidcrypto.androidcommonintents;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class IntentGroup04 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    EditText etE01, etE02, etE03;
    TextView tvG04;

    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 100;
    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 101;
    Context contextSave; // wird für write & read a file from uri benötigt



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
                    return;
                }
                if (filename.equals("")) {
                    tvG04.setText("no filename to save");
                    return;
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
                    return;
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
                // write a file to external shared storage
                contextSave = v.getContext();
                tvG04.setText(""); // clear the info
                verifyPermissionsWriteString();
            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read a file from external shared storage
                contextSave = v.getContext();
                tvG04.setText(""); // clear the info
                // clear edittext
                etE02.setText("");
                verifyPermissionsReadString();
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

    // section external storage permission check
    private void verifyPermissionsWriteString() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED) {
            writeStringToExternalSharedStorage();
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        }
    }

    private void verifyPermissionsReadString() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED) {
            readStringFromExternalSharedStorage();
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                writeStringToExternalSharedStorage();
            } else {
                Toast.makeText(this, "Grant Storage Permission is Required to use this function.", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readStringFromExternalSharedStorage();
            } else {
                Toast.makeText(this, "CGrant Storage Permission is Required to use this function.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void writeStringToExternalSharedStorage() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        //boolean pickerInitialUri = false;
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        // get filename from edittext
        String filename = etE03.getText().toString();
        // sanity check
        if (filename.equals("")) {
            tvG04.setText("no filename to load");
            return;
        }
        intent.putExtra(Intent.EXTRA_TITLE, filename);
        fileSaverActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> fileSaverActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent resultData = result.getData();
                        // The result data contains a URI for the document or directory that
                        // the user selected.
                        Uri uri = null;
                        if (resultData != null) {
                            uri = resultData.getData();
                            // Perform operations on the document using its URI.
                            try {
                                // get file content from edittext
                                String fileContent = etE01.getText().toString();
                                writeTextToUri(uri, fileContent);
                                String message = "file written to external shared storage: " + uri.toString();
                                tvG04.setText(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                                tvG04.setText("ERROR: " + e.toString());
                                return;
                            }
                        }
                    }
                }
            });

    private void writeTextToUri(Uri uri, String data) throws IOException {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(contextSave.getContentResolver().openOutputStream(uri));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            System.out.println("Exception File write failed: " + e.toString());
        }
    }

    private void readStringFromExternalSharedStorage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        boolean pickerInitialUri = false;
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        fileLoaderActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> fileLoaderActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent resultData = result.getData();
                        // The result data contains a URI for the document or directory that
                        // the user selected.
                        Uri uri = null;
                        if (resultData != null) {
                            uri = resultData.getData();
                            // Perform operations on the document using its URI.
                            try {
                                String fileContent = readTextFromUri(uri);
                                etE02.setText(fileContent);
                                String message = "file loaded from external storage" + uri.toString();
                                tvG04.setText(message);
                                // get metadata from uri
                                dumpImageMetaData(uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                                tvG04.setText("ERROR: " + e.toString());
                                return;
                            }
                        }
                    }
                }
            });

    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        //try (InputStream inputStream = getContentResolver().openInputStream(uri);
        // achtung: context1 muss gefüllt sein !
        try (InputStream inputStream = contextSave.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        }
        return stringBuilder.toString();
    }

    public void dumpImageMetaData(Uri uri) {
        String TAG = "### LOG ";
        // The query, because it only applies to a single document, returns only
        // one row. There's no need to filter, sort, or select fields,
        // because we want all fields for one document.
        String message = tvG04.getText().toString();
        Cursor cursor = contextSave.getContentResolver()
                .query(uri, null, null, null, null, null);

        try {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {
                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                @SuppressLint("Range") String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i(TAG, "Display Name: " + displayName);
                message = message + "\nDisplay Name: " + displayName;
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                // If the size is unknown, the value stored is null. But because an
                // int can't be null, the behavior is implementation-specific,
                // and unpredictable. So as
                // a rule, check if it's null before assigning to an int. This will
                // happen often: The storage API allows for remote files, whose
                // size might not be locally known.
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    size = cursor.getString(sizeIndex);
                } else {
                    size = "Unknown";
                }
                Log.i(TAG, "Size: " + size + " bytes");
                message = message + "\nSize: " + size + " bytes";
                tvG04.setText(message);
            }
        } finally {
            cursor.close();
        }
    }

}