package com.example.afinal.fingerPrint_Login.sample_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class Sample_Only_Activity extends AppCompatActivity {


    private CircleImageView imageView1, imageView2, imageView3;
    private int READ_REQUEST_CODE=44;
    private Uri uriImageHere;
    private File imageFile;
    private boolean imageSetupHere;

    private StorageReference storageReference;
    private boolean imageUploaded_boolean;
    private Uri uriResult;

    private TextView textView1;
    private TextView textView2, textView3;
    private Bitmap imageBitmap;
    private String sizeString;
    private String sizeString2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample__only_);

        imageUploaded_boolean =false;

        imageSetupHere = false;

        imageView1 = findViewById(R.id.circleImageViewTestid);
        imageView2 = findViewById(R.id.circleImageViewID2);
        imageView3 = findViewById(R.id.circleImageViewID3);

        textView1 = findViewById(R.id.textViewImageTest1);

        textView2 = findViewById(R.id.textViewImageTest2);

        textView3 = findViewById(R.id.textviewimagetest3id);




        storageReference = FirebaseStorage.getInstance().getReference().child("upload_test").child("111");

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent,READ_REQUEST_CODE);




            }
        });


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageUploaded_boolean && imageSetupHere){


                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            if(task.isSuccessful()){

                                uriResult = task.getResult();

                                //imageView2.setImageURI(uriResult);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                        if(uriResult!=null) {
                                            Picasso.with(Sample_Only_Activity.this).load(uriResult).into(imageView2);

                                            Toast.makeText(Sample_Only_Activity.this,"success download image", Toast.LENGTH_SHORT).show();

                                           // File fileHere = new File(uriResult.getPath());

                                            try {
                                                File fileHere2 = FileUtil.from(Sample_Only_Activity.this,uriImageHere);




                                                long size = fileHere2.length();

                                                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)

                                                long fileSizeInKB = size / 1024;


                                                // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                                                long fileSizeInMB = fileSizeInKB / 1024;

                                                if(fileSizeInMB<=1) {

                                                    sizeString2 = String.valueOf(fileSizeInKB);

//                                                runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {

                                                    textView2.setText(sizeString2 + " Kb");
//                                                    }
//                                                });
//



                                                }else {

                                                    sizeString2 = String.valueOf(fileSizeInMB);
//                                                runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {

                                                    textView2.setText(sizeString2 + " Mb");
//                                                    }
//                                                });

                                                }




                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

//
//                                            long size = fileHere.length();
//
//                                            // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
//
//                                            long fileSizeInKB = size / 1024;
//
//
//                                            // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
//                                            long fileSizeInMB = fileSizeInKB / 1024;
//
//                                            if(fileSizeInMB<=1) {
//
//                                                sizeString2 = String.valueOf(fileSizeInKB);
//
////                                                runOnUiThread(new Runnable() {
////                                                    @Override
////                                                    public void run() {
//
//                                                        textView2.setText(sizeString2 + " Kb");
////                                                    }
////                                                });
////
//
//
//
//                                            }else {
//
//                                                sizeString2 = String.valueOf(fileSizeInMB);
////                                                runOnUiThread(new Runnable() {
////                                                    @Override
////                                                    public void run() {
//
//                                                        textView2.setText(sizeString2 + " Mb");
////                                                    }
////                                                });
//
//                                            }





                                        }else {

                                            Toast.makeText(Sample_Only_Activity.this,"problem download image", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });



                            }else {

                                Toast.makeText(Sample_Only_Activity.this,"problem download image ELSE", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {

                            Toast.makeText(Sample_Only_Activity.this,"problem download image CANCEL", Toast.LENGTH_SHORT).show();

                        }
                    });




                }else {


                    Toast.makeText(Sample_Only_Activity.this,"please upload picture first",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if(data!=null){

                uriImageHere = data.getData();


                imageView3.setImageURI(uriImageHere);


                try {
                    imageFile = FileUtil.from(Sample_Only_Activity.this,uriImageHere);

                    long size = imageFile.length();

                    // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)

                    long fileSizeInKB = size / 1024;


                    // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                    long fileSizeInMB = fileSizeInKB / 1024;

                    final String sizeString = String.valueOf(fileSizeInMB);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            textView3.setText("before compressed:"+ sizeString + " Mb");
                        }
                    });

                    Log.i("testimagecompressed","00 :"+imageFile.toString());


                } catch (IOException e) {
                    e.printStackTrace();

                    Log.i("testimagecompressed","01 :"+e.getMessage());
                }

                try {
                    imageFile = new Compressor(this).compressToFile(imageFile);

                    uriImageHere = Uri.fromFile(imageFile);

                    Log.i("testimagecompressed","2 :"+uriImageHere.toString());

                    showImage(uriImageHere);

                    imageSetupHere = true;

                } catch (IOException e) {
                    e.printStackTrace();

                    Log.i("testimagecompressed","3 :"+imageFile.toString()+" >>"+e.getMessage());
                    imageSetupHere=false;
                }


            }else{

                imageSetupHere=false;
            }


        }
    }

    private void showImage(Uri uriImageHere) {

        imageView1.setImageURI(uriImageHere);


        //upload here.

        storageReference.putFile(uriImageHere).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){

                    Toast.makeText(Sample_Only_Activity.this,"success upload",Toast.LENGTH_SHORT).show();


                    long size = imageFile.length();

                    // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)

                    long fileSizeInKB = size / 1024;


                    // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                    long fileSizeInMB = fileSizeInKB / 1024;

                    if(fileSizeInMB<=1){

                        sizeString = String.valueOf(fileSizeInKB);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                textView1.setText("after compressed :"+sizeString + " Kb");
                            }
                        });

                    }else {

                        sizeString = String.valueOf(fileSizeInMB);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                textView1.setText("after compressed :"+sizeString + " Mb");
                            }
                        });

                    }



                    imageUploaded_boolean = true;


                }else {

                    imageUploaded_boolean = false;

                    Toast.makeText(Sample_Only_Activity.this,"failed upload",Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

                imageUploaded_boolean = false;

                Toast.makeText(Sample_Only_Activity.this,"failed upload",Toast.LENGTH_SHORT).show();

            }
        });


    }
}

