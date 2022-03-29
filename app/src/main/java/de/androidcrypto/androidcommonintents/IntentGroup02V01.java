package de.androidcrypto.androidcommonintents;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentGroup02V01 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    TextView tvG02;

    ImageView ivG02;

    static final int REQUEST_IMAGE_CAPTURE_THUMBNAIL = 3;
    static final int  REQUEST_IMAGE_CAPTURE_FULL_RESOLUTION = 4;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int  REQUEST_IMAGE_CAPTURE_FULL = 2;
    static final int  REQUEST_IMAGE_PICK = 5;
    public static final int CAMERA_IMAGE_PERM_CODE = 101;
    public static final int CAMERA_VIDEO_PERM_CODE = 102;
    String currentPhotoPath;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_group02);
        
        btn01 = findViewById(R.id.btnG02B01);
        btn02 = findViewById(R.id.btnG02B02);
        btn03 = findViewById(R.id.btnG02B03);
        btn04 = findViewById(R.id.btnG02B04);
        btn05 = findViewById(R.id.btnG02B05);
        btn06 = findViewById(R.id.btnG02B06);
        btn07 = findViewById(R.id.btnG02B07);
        btn08 = findViewById(R.id.btnG02B08);
        
        tvG02 = findViewById(R.id.tvG02);
        ivG02 = findViewById(R.id.ivG02);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capture a picture and return it
                // https://developer.android.com/guide/components/intents-common
                // https://developer.android.com/reference/android/provider/MediaStore#constants_1
                // https://developer.android.com/training/camera/photobasics
                /*
                <uses-feature android:name="android.hardware.camera"
                  android:required="true" />
                 */
                /*
                If your application uses, but does not require a camera in order to function,
                instead set android:required to false. In doing so, Google Play will allow
                devices without a camera to download your application. It's then your
                responsibility to check for the availability of the camera at runtime by
                calling hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY). If a camera is
                not available, you should then disable your camera features.
                 */
                /*
                <intent-filter>
        <action android:name="android.media.action.IMAGE_CAPTURE" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
                 */
                /*
                On Android 9 (API level 28) and lower, reading and writing to this directory
                requires the READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE permissions,
                respectively:
                <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                 */
                /*
                <provider
        android:name="androidx.core.content.FileProvider"
        android:authorities="de.androidcrypto.androidcommonintents.fileprovider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/file_paths"></meta-data>
    </provider>
                 */

                System.out.println("### 01 take a photo");
                // https://developer.android.com/training/camera/photobasics
                // private void dispatchTakePictureIntent() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    // deprecated startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_THUMBNAIL);
                    takePictureThumbnailActivityResultLauncher.launch(takePictureIntent);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }

                /*
                Context context = v.getContext();
                // extended version stores a photo
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        System.out.println("photo stored in: " + currentPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        System.out.println("*** ERROR: " + ex.toString());
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(context,
                                "de.androidcrypto.androidcommonintents.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
*/

                /* simple version
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    System.out.println("*** ERROR: " + e.toString());
                    // display error state to the user
                }
                */
                /*
                Context context = v.getContext();

                String file = "";
                Uri uri = Uri.fromFile(new File(file));
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                if (intent.resolveActivity(context.getPackageManager()) != null)
                {
                    context.startActivity(intent);
                }*/
            }
        });



        btn02.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                // Capture a video and return it
                // ACTION_IMAGE_CAPTURE_SECURE
                // https://developer.android.com/reference/android/provider/MediaStore#ACTION_IMAGE_CAPTURE_SECURE
                // https://developer.android.com/reference/android/os/Environment
                /*
                application
                    android:requestLegacyExternalStorage="true"
                 */
                System.out.println("### 02 take a photo full");
                dispatchTakePictureAppStorageIntent();
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                verifyPermissions();
            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                verifyPermissionsVideo();
            }
        });

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open the gallery
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open the gallery and pick a file
                // create an instance of the
                // intent of the type image
                Intent i = new Intent();
                // this is for images only
                i.setType("image/*");
                // this is for videos only
                //i.setType("video/*");
                i.setAction(Intent.ACTION_GET_CONTENT);

                // pass the constant to compare it
                // with the returned requestCode
                selectFileFromGalleryActivityResultLauncher.launch(i);
                // deprecated startActivityForResult(Intent.createChooser(i, "Select Picture"), REQUEST_IMAGE_PICK);
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

    // take a photo and show in gallery start
    private void verifyPermissions() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntentFullGallery();
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    CAMERA_IMAGE_PERM_CODE);
        }
    }

    // take a video and show in gallery start
    private void verifyPermissionsVideo() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED) {
            dispatchTakeVideoIntentFullGallery();
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    CAMERA_VIDEO_PERM_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_IMAGE_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntentFullGallery();
            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAMERA_VIDEO_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakeVideoIntentFullGallery();
            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFileFullGallery() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntentFullGallery() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFileFullGallery();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "de.androidcrypto.androidcommonintents.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                // deprecated startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                takePictureActivityResultLauncher.launch(takePictureIntent);
            }
        }
    }

    ActivityResultLauncher<Intent> takePictureActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        //Intent resultData = result.getData();
                        // and no resultData is given
                        File f = new File(currentPhotoPath);
                        ivG02.setImageURI(Uri.fromFile(f));
                        Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri contentUri = Uri.fromFile(f);
                        mediaScanIntent.setData(contentUri);
                        context.sendBroadcast(mediaScanIntent);
                    }
                }
            });

    // take a photo and show in gallery start END

    // takes a photo and shows it as thumbnail only
    ActivityResultLauncher<Intent> takePictureThumbnailActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent resultData = result.getData();
                        Bundle extras = resultData.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        ivG02.setImageBitmap(imageBitmap);
                        String info = "thumbnail height: " + imageBitmap.getHeight()
                                + " width: " + imageBitmap.getWidth();
                        tvG02.setText(info);
                    }
                }
            });

    ActivityResultLauncher<Intent> takePictureAppStorageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        setPic();
                        /*
                        Intent resultData = result.getData();
                        Bundle extras = resultData.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        ivG02.setImageBitmap(imageBitmap);
                        String info = "thumbnail height: " + imageBitmap.getHeight()
                                + " width: " + imageBitmap.getWidth();
                        tvG02.setText(info);*/
                    }
                }
            });
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE_THUMBNAIL && resultCode == RESULT_OK) {
            // Get the thumbnail
            // https://developer.android.com/training/camera/photobasics#TaskPhotoView
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivG02.setImageBitmap(imageBitmap);
            String info = "thumbnail height: " + imageBitmap.getHeight()
                    + " width: " + imageBitmap.getWidth();
            tvG02.setText(info);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_FULL_RESOLUTION && resultCode == RESULT_OK) {
            // Get the thumbnail
            // https://developer.android.com/training/camera/photobasics#TaskPhotoView
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //ivG02.setImageBitmap(imageBitmap);
            //String info = "fullresolution height: " + imageBitmap.getHeight() + " width: " + imageBitmap.getWidth();
            //tvG02.setText(info);
            System.out.println("REQUEST_IMAGE_CAPTURE_FULL_RESOLUTION - setPic");

            setPic();
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivG02.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_FULL && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //imageView.setImageBitmap(imageBitmap);
            setPic(); // show scaled image

        }
    }
*/

    private void dispatchTakePictureAppStorageIntent() {
        // https://developer.android.com/reference/android/provider/MediaStore.Images#TaskPath
        System.out.println("dispatchTakePictureIntentFullResolutionV2");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            System.out.println("takePictureIntent.resolveActivity(getPackageManager()) != null");
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFileAppStorage();
                System.out.println("photoFile: " + photoFile.getAbsolutePath());
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println("IOExeception: " + ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                System.out.println("photoFile created");
                Uri photoURI = FileProvider.getUriForFile(this,
                        "de.androidcrypto.androidcommonintents.fileprovider",
                        photoFile);
                System.out.println("uri: " + photoURI.toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    // deprecated startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_THUMBNAIL);
                    takePictureAppStorageActivityResultLauncher.launch(takePictureIntent);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_FULL_RESOLUTION);
            }
        }
    }

    private File createImageFileAppStorage() throws IOException {
        // https://developer.android.com/training/camera/photobasics#TaskPath
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // getExternalFilesDir = files stay private
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        System.out.println("createImageFileAppStorage currentPhotoPath: " + currentPhotoPath);
        return image;
    }

    // scale picture to imageView sizes
    private void setPic() {
        //galleryAddPic(context, currentPhotoPath);
        System.out.println("galleryAddPic currentPhotoPath: " + currentPhotoPath);
        Bitmap bm = BitmapFactory.decodeFile(currentPhotoPath);
        String info = "setPic height: "  + bm.getHeight() + " width: " + bm.getWidth();
        tvG02.setText(info);
        // Get the dimensions of the View
        int targetW = ivG02.getWidth();
        int targetH = ivG02.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        ivG02.setImageBitmap(bitmap);
    }

    // record a video and show in gallery
    private void dispatchTakeVideoIntentFullGallery() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                //photoFile = createImageFileFullGallery();
                photoFile = createVideoFileFullGallery();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "de.androidcrypto.androidcommonintents.fileprovider",
                        photoFile);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                // limit the video to 5 seconds
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3);
                // deprecated startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                takeVideoActivityResultLauncher.launch(takeVideoIntent);
            }
        }
    }

    ActivityResultLauncher<Intent> takeVideoActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        //Intent resultData = result.getData();
                        // and no resultData is given
                        File f = new File(currentPhotoPath);

                        //ivG02.setImageURI(Uri.fromFile(f));

                        Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));
                        System.out.println("*** Absolute Url of Image is " + Uri.fromFile(f));
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri contentUri = Uri.fromFile(f);
                        mediaScanIntent.setData(contentUri);
                        context.sendBroadcast(mediaScanIntent);
                        String info = "video file length: " + f.length();
                        tvG02.setText(info);
                    }
                }
            });

    private File createVideoFileFullGallery() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MP4_" + timeStamp + "_";
        // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // select a file from gallery
    // this function is triggered when user
    // selects the image from the imageChooser
    /*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == REQUEST_IMAGE_PICK) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    ivG02.setImageURI(selectedImageUri);
                    String info = "file name: " + selectedImageUri.toString();
                    tvG02.setText(info);
                }
            }
        }
    }*/


    ActivityResultLauncher<Intent> selectFileFromGalleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent resultData = result.getData();
                        Uri selectedImageUri = resultData.getData();
                        if (null != selectedImageUri) {
                            // update the preview image in the layout
                            ivG02.setImageURI(selectedImageUri);
                            String info = "file name: " + selectedImageUri.toString();
                            tvG02.setText(info);
                        }
                    }
                }
            });
}