package de.androidcrypto.androidcommonintents;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentGroup02 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    TextView tvG02;

    ImageView ivG02;

    static final int REQUEST_IMAGE_CAPTURE_THUMBNAIL = 3;
    static final int  REQUEST_IMAGE_CAPTURE_FULL_RESOLUTION = 4;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int  REQUEST_IMAGE_CAPTURE_FULL = 2;
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
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_THUMBNAIL);
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
                context = v.getContext();
                dispatchTakePictureIntentFullResolutionV2();


            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();

                //int height = photo.getHeight(); // 160 in Emulator
                //int width = photo.getWidth(); // 120 in Emulator
                //Toast.makeText(v.getContext(), "Photo height:  " + height + " width: " + width, Toast.LENGTH_SHORT).show();
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
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);*/
            setPic(); // show scaled image

        }
    }

    private void dispatchTakePictureIntentFullResolutionV2() {
        // https://developer.android.com/reference/android/provider/MediaStore.Images#TaskPath
        System.out.println("dispatchTakePictureIntentFullResolutionV2");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            System.out.println("takePictureIntent.resolveActivity(getPackageManager()) != null");
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
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
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_FULL_RESOLUTION);
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    private File createImageFile() throws IOException {
        // https://developer.android.com/training/camera/photobasics#TaskPath
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // getExternalFilesDir = files stay private
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // getExternalStoragePublicDirectory = files get shared
        //boolean isExternalStorageLegacy = Environment.isExternalStorageLegacy(getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        //System.out.println("isExternalStoreLegacy: " + isExternalStorageLegacy);
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        galleryAddPic(context, currentPhotoPath);
        notifyMediaStoreScanner(context, image);
        return image;
    }

    //added the photo file to gallery
    private static void galleryAddPic(Context context, String imagePath) {
        System.out.println("galleryAddPic");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        //Uri contentUri = Uri.fromFile(f);
        Uri contentUri = FileProvider.getUriForFile(context,
                "de.androidcrypto.androidcommonintents.fileprovider",
                f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public final void notifyMediaStoreScanner(Context context, final File file) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
            context.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void dispatchTakePictureIntentFullResolution() {
        System.out.println("*** dispatchTakePictureIntentFullResolution");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        // Create the File where the photo should go
        System.out.println("*** create a foto");
        File photoFile = null;
        try {
            photoFile = createImageFile();
            System.out.println("photoFile: " + photoFile.getPath());
        } catch (IOException ex) {
            // Error occurred while creating the File
            //Log.e("CameraIntent2", "error", ex);
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "de.androidcrypto.androidcommonintents.fileprovider",
                    photoFile);
            System.out.println("photo URI: " + photoURI);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_FULL);
        }
        // I/System.out: photoFile: /storage/emulated/0/Android/data/de.androidcrypto.cameraintent2/files/Pictures/JPEG_20210913_221700_1210935483649964327.jpg
        // I/System.out: photo URI: content://de.androidcrypto.cameraintent2.fileprovider/my_images/JPEG_20210913_221700_1210935483649964327.jpg
        //}
    }

    // scale picture to imageView sizes
    private void setPic() {
        //galleryAddPic(context, currentPhotoPath);
        System.out.println("galleryAddPic");
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

}