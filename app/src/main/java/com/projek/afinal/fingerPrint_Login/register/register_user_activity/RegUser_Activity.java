package com.projek.afinal.fingerPrint_Login.register.register_user_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projek.afinal.R;
import com.projek.afinal.fingerPrint_Login.register.register_with_activity.RegAdmin_Activity;
import com.projek.afinal.fingerPrint_Login.register.setup_pin_code.Setup_Pin_Activity;
import com.projek.afinal.fingerPrint_Login.sample_test.FileUtil;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hanks.htextview.evaporate.EvaporateTextView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class RegUser_Activity extends AppCompatActivity implements View.OnClickListener, RegUserView_Interface, Observer {

    private static final int READ_REQUEST_CODE = 42;
    private Button buttonLogin, buttonGetCode;

//    private EditText editTextName, editTextPhone, editTextCode;

    //private TextView textViewMessage;

    private RegUser_Presenter presenter;

    private boolean inputValid;
    private boolean inputValid_2;

    //if register as admin,
    //just declare, admin name, and admin phone,
    //no need to check employee, instead, check if admin is existed.
    //then jump another activity to set the ssid , bssid etc.

    private String codeFromFirebase;

    private String adminName;
    private String adminPhone;
    private String userName,userPhone;

    private String statusnow="";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;

    //Timer to start while checking doc process
    //if doc method not updated, we stop observable, and clear data, ask user to try again.

    Timer timer;

    private CircleImageView circleImageView;

    ////// storage

    private StorageReference storageReference;
    private Uri mImageuri;
    private Timer timer2;
    private int count_adminGlobal;
    private boolean imageSetup;

    //20 May 2019

    private CardView cardView_user_detail;

    private TextView textView_userName, textView_userPhone, textView_adminName, textView_adminPhone;

    private FloatingActionButton fButton_Next;

    private EvaporateTextView evaporateTextView;

    private EditText editText_ring1 , editText_ring2, editText_ring3, editText_ring4, editText_ring5, editText_ring6;

    private TextView textView6pin;
    private FloatingActionButton fButton_Next2;


    // 7 june

    //private Button testbutton;

    // 14 june

    private String admin_count_extracted;
    private boolean boolean_admin_count;
    private CollectionReference cR_AllUser;
    private DocumentReference dR_User_Top;
    private File image_File;
    private String image_url_downloaded;
    private boolean boolean_image_setup;
    private DocumentReference documentReference_intoUser_Doc;
    private DocumentReference documentReference_intoUserDoc_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user);

        boolean_image_setup = false;

        image_url_downloaded = "";

        boolean_admin_count = false;

        admin_count_extracted = "";

        //testbutton = findViewById(R.id.reg_user_buttonTest);

        //testbutton.setOnClickListener(this);

        imageSetup =false;


        //       buttonGetCode = findViewById(R.id.regUser_Button_GetCodeID);
        //buttonLogin = findViewById(R.id.regUser_Button_LogInID);

        //20 may update
//
//        editTextName = findViewById(R.id.regUser_editText_NameID);
//        editTextPhone = findViewById(R.id.regUser_editText_PhoneID);
//        editTextCode = findViewById(R.id.regUser_editText_CodeID);

        textView_userName = findViewById(R.id.reg_User_textView_name_id);
        textView_userPhone = findViewById(R.id.reg_User_textView_phone_id);
        textView_adminName = findViewById(R.id.reg_User_textView_nameAdmin_id);
        textView_adminPhone = findViewById(R.id.reg_User_textView_adminPhone_id);

        //first page
        fButton_Next = findViewById(R.id.reg_User_floatingActionButton_Next_id);


        //2nd page
        fButton_Next2 = findViewById(R.id.reg_User_floatingActionButton_Next2_id);

        evaporateTextView = findViewById(R.id.reg_User_EvaporateText_Code_ID);

        editText_ring1 = findViewById(R.id.reg_User_editText_ring1_id);
        editText_ring2 = findViewById(R.id.reg_User_editText_ring2_id);
        editText_ring3 = findViewById(R.id.reg_User_editText_ring3_id);
        editText_ring4 = findViewById(R.id.reg_User_editText_ring4_id);
        editText_ring5 = findViewById(R.id.reg_User_editText_ring5_id);
        editText_ring6 = findViewById(R.id.reg_User_editText_ring6_id);

        textView6pin = findViewById(R.id.reg_User_TextView_6pin_code_id);

        //

        fButton_Next.setOnClickListener(this);

        fButton_Next2.setOnClickListener(this);





        ////

       // textViewMessage = findViewById(R.id.regUser_textViewID);

       // textViewMessage.setText("enter your name and phone");

        cardView_user_detail = findViewById(R.id.regUser_CardView_id);



        circleImageView = findViewById(R.id.reg_User_circlerImageView);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent,READ_REQUEST_CODE);

            }
        });

        Log.i("checkUserReg Flow: ", "[Activity] , 1 ");

        final Intent intent =getIntent();

        adminName = intent.getStringExtra("admin_name"); //pulling data
        adminPhone = intent.getStringExtra("admin_phone");

        userName = intent.getStringExtra("user_here_name");
        userPhone = intent.getStringExtra("user_here_phone"); //this data should be formatted,


        Toast.makeText(this, "user: "+userPhone + ", admin name: "+adminName,Toast.LENGTH_SHORT).show();


        textView_userName.setText(userName);
        textView_userPhone.setText(userPhone);
        textView_adminPhone.setText(adminPhone);
        textView_adminName.setText(adminName);

        //15 june , setup user check


         cR_AllUser = FirebaseFirestore.getInstance().collection("users_top_detail");


         dR_User_Top = cR_AllUser.document(userPhone+"imauser");


        dR_User_Top.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()) {

                    //need to recheck


                    boolean user_registered = Objects.requireNonNull(task.getResult()).exists(); //this is wrong.

                    if (user_registered) { //we need to check count, if one or two.


                        //21 sep, one phone user or admin only

//                        Map<String, Object> remap = task.getResult().getData();
//
//                        for (Map.Entry<String, Object> remapHere : remap.entrySet()) {
//
//                            if (remapHere.getKey().equals("admin_count")) {
//
//                                //we could extract 1 or 2 ,
//
//                                admin_count_extracted = remapHere.getValue().toString();
//                            }
//
//                        }
//
//                        if(admin_count_extracted.equals("1")) {
//                            boolean_admin_count = true;
//                        }else {
//
//
//                            Toast.makeText(RegUser_Activity.this,"maximum number of 2 users registered",Toast.LENGTH_SHORT).show();
//                            boolean_admin_count=false;
//                        }

                        boolean_admin_count=false;


                    } else { //if never existed we registered count as one.

                        //confused

                        admin_count_extracted = "0"; //register as 1, first timer.,, if 0 will need to


                        boolean_admin_count = true;
                    }



                }else {


                    boolean_admin_count=false;


                }

            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

                boolean_admin_count =false;

            }
        });



        inputValid=false;

        timer = new Timer();

        presenter = new RegUser_Presenter(this);
        presenter.addObserver(this);


        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

              //  textViewMessage.setText("got credential");

                Log.i("checkUserReg Flow: ", "[Activity] , 2 , verification completed");

                //change view visible to user.
                cardView_user_detail.setVisibility(View.GONE);
                circleImageView.setVisibility(View.GONE);

                fButton_Next.setVisibility(View.GONE);


                evaporateTextView.setVisibility(View.VISIBLE);
                editText_ring1.setVisibility(View.VISIBLE);
                editText_ring2.setVisibility(View.VISIBLE);
                editText_ring3.setVisibility(View.VISIBLE);
                editText_ring4.setVisibility(View.VISIBLE);
                editText_ring5.setVisibility(View.VISIBLE);
                editText_ring6.setVisibility(View.VISIBLE);

                fButton_Next2.setVisibility(View.VISIBLE);
                textView6pin.setVisibility(View.VISIBLE);

                verifyCredential(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
             //   textViewMessage.setText("verification failed");
                Log.i("checkUserReg Flow: ", "[Activity] , 3 , verification failed ");
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeFromFirebase=s;
                Log.i("checkUserReg Flow: ", "[Activity] , 4 , codesent, codeReceived: " +codeFromFirebase);

                //checkDocResult("received code, enter code now");
            }
        };


    }



    //handle image


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==42 && resultCode== Activity.RESULT_OK){

            mImageuri = null;

            if(data!=null){

                mImageuri = data.getData();

                try {
                    image_File = FileUtil.from(RegUser_Activity.this,mImageuri);

                    image_File = new Compressor(this).compressToFile(image_File);

                    mImageuri = Uri.fromFile(image_File);


                    imageSetup =true;

                    showImage(mImageuri);

                } catch (IOException e) {
                    e.printStackTrace();

                    imageSetup=false;
                }


            }else {

                imageSetup = false;
            }

        }
    }

    private void showImage(Uri uri) {

        circleImageView.setImageURI(uri);


    }

    private void verifyCredential(PhoneAuthCredential phoneAuthCredential) {


        statusnow= "wait..";
      //  textViewMessage.setText(statusnow);
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                //    textViewMessage.setText("credential verified, wait..");

                    //then check with firebase.


                    //get the reference to admin collection,
                    final CollectionReference cR_ifRegistered = FirebaseFirestore.getInstance().collection("all_admins_collections");

                   // final CollectionReference cR_ifRegistered = FirebaseFirestore.getInstance().collection(userPhone+"imauser");

                    //check array of field "employee_this_admin", if contain UserCheckIn phone number
                    Query query_ifRegistered = cR_ifRegistered.whereArrayContains("employee_this_admin",userPhone);

                    query_ifRegistered.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()) {

                                //17 june
                                //there could also be another

                             // boolean_exist_doc_here = task.getResult().e


                                int documentSnapshotSize = task.getResult().getDocuments().size();

                                if (documentSnapshotSize == 1 || documentSnapshotSize == 2) { //if size is one, user is registered by one admin.

                                    //then here we can log in

                                    registrationProcessFireStore();



                                } else {

                                    //return as UserCheckIn is not verified by any admin,
                                    //sign him out from firebase authentication page.
                                    //or somehow he already registered to other admin // this is supposed to be admin functions.



                                    logOutNow();
                                }

                            }
                            else {

                                logOutNow();
                            }
                        }
                    });


                    // 14 june


                }


                }


        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {


                logOutNow();
            }
        });






    }

    private void onStartOur() {

        Log.i("checkUserReg Flow: ", "[Activity] , 12 ,stop  ");


        Toast.makeText(this,"please try register again", Toast.LENGTH_SHORT).show();
      //  textViewMessage.setText("press back, and try again");
        //onStart();

        Intent intent = new Intent(RegUser_Activity.this, RegAdmin_Activity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // clear other activity on top of old instance of the activity,
        startActivity(intent);
        //finish();
    }

    private void logOutNow() {

        Log.i("checkUserReg Flow: ", "[Activity] , 13 , logoutNow  ");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){

            FirebaseAuth.getInstance().signOut();
        }

        onStartOur();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deleteObserver(this);
    }

    private void registrationProcessFireStore() {

    //check first if alreadt exist.

        Log.i("checkUserReg Flow: ", "[Activity] , 14 ,go to presenter  ");

        presenter.checkUserDoc(userName,userPhone,adminName,adminPhone);

        Log.i("checkUserReg Flow: ", "[Activity] , 15 ,back from presenter  ");

    }


    @Override
    public void onClick(View v) {

        Log.i("checkk UserReg: ", "tt 1");

//        String name = textView_userName.getText().toString();
//        String phone = textView_userPhone.getText().toString();
//
//



       // inputValid = presenter.checkInputValid(name,phone);
       // inputValid_2 = presenter.checkInputValid(name,phone,code);

//        Log.i("checkk UserReg: ", "tt 2 " + name);
//            Log.i("checkk UserReg: ", "tt 3");
//            userName = name;
//            userPhone = phone;

            if(imageSetup){

            switch (v.getId()) {

                //7 june


//                case R.id.reg_user_buttonTest:
//
//                    break;

                //case R.id.regUser_Button_GetCodeID:

                case R.id.reg_User_floatingActionButton_Next_id:
//
//                    String name = textView_userName.getText().toString();
//                    String phone = textView_userPhone.getText().toString();
//
//                    userName = name;
//                    userPhone = phone;

                    String booleanadmincount = String.valueOf(boolean_admin_count);

                    Toast.makeText(RegUser_Activity.this,"admin count" + booleanadmincount, Toast.LENGTH_SHORT).show();

                    //we can create boolean check, if user is maxed here.

                    if (boolean_admin_count) {

                        if(admin_count_extracted.equals("0")) {


                            //  textViewMessage.setText("getting code..");

                            Log.i("checkk UserReg: ", "tt 4");

                            //presenter.phonecallBack();
                            getCallBack(userPhone);

                        }else if(admin_count_extracted.equals("2")){

                            Toast.makeText(RegUser_Activity.this,"our are only allowed to be registered to 2 admins",Toast.LENGTH_SHORT).show();

                            //something wrong when extracting.

                        }


                    } else {

                        //something wrong with


                        Toast.makeText(RegUser_Activity.this, "please wait 5 seconds and try again", Toast.LENGTH_SHORT).show();

//                        Intent intentHereGoBack_MaxAlready = new Intent(RegUser_Activity.this, FingerPrint_LogIn_Final_Activity.class);
//                        startActivity(intentHereGoBack_MaxAlready);
//                        finish();
                    }

                    break;

                case R.id.reg_User_floatingActionButton_Next2_id: //this is after code, maybe no need to push t

                    String number_1 = editText_ring1.getText().toString();



                    String number_2 = editText_ring2.getText().toString();
                    String number_3 = editText_ring3.getText().toString();
                    String number_4 = editText_ring4.getText().toString();
                    String number_5 = editText_ring5.getText().toString();
                    String number_6 = editText_ring6.getText().toString();

                    String code = number_1+number_2+number_3+number_4+number_5+number_6;


                    Log.i("checkk UserReg: ", "tt 5");

                    checkCredential(code, codeFromFirebase);

                    Log.i("checkk UserReg: ", "tt 6, after check credential compare ourCode: " + code + " , codeFirebase: " + codeFromFirebase);


                    break;

            }

        }else {

               // textViewMessage.setText("please click picture, and set profile picture");

                Toast.makeText(RegUser_Activity.this,"please click and choose your image", Toast.LENGTH_SHORT).show();
            }



    }

    //check if max user is registered.
    private boolean checkNumberOfAdminRegisteredTo_nope() {

        //fix here

        File f_MainPool = new File("/data/data/com.example.afinal/shared_prefs/com.example.finalV8_punchCard.MAIN_POOL.xml");

        if(f_MainPool.exists()){ //if exist, should

            //if exist, is already user to other admin, so counter should read 1.
            //read count first if 2 or higher, send error.

            SharedPreferences prefs_Main_Pool = this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);

            String count_admin = prefs_Main_Pool.getString("count_admin","");

            if(Integer.valueOf(count_admin)==1){


                count_adminGlobal =1;

                return true; //



            }if((Integer.valueOf(count_admin)>=2)){

                count_adminGlobal =2;
                return false;
                //this should be error. somehow, should not happen to have register to 2 admin.


            }else {

                return false;// somehow something weird data pulled
            }


        }else {

            count_adminGlobal =0;
            return true; //not exist yet, so can add more user. //another user



        }


    }

    private void checkCredential(String code, String codeFromFirebase) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code,codeFromFirebase);

        Log.i("checkUserReg Flow: ", "[Activity] , 16 ,check credential  ");

        verifyCredential(credential);

    }

    private void getCallBack(String phone) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,
                45,
                TimeUnit.SECONDS,
                RegUser_Activity.this,
                mCallBack);


    }

    @Override
    public void checkDocResult(String status) {
        //textViewMessage.setText(status);

        //here we create, but what if constant fail.
        //after 60 seconds, observable do not return update. we need to delete all operation,

        if(status.equals("doc created")){

            //here we also need to create referrel

            //14 june >> we want to update admin here, since it is verified that this user can be created under provided condition

            Map<String,Object> adminMapCount = new HashMap<>();

            if(admin_count_extracted.equals("0")){

                adminMapCount.put("admin_count","1");

                admin_count_extracted ="1";

                adminMapCount.put("phone", userPhone);

                adminMapCount.put("admin_phone_1",adminPhone);

                adminMapCount.put("admin_name_1",adminName);

                adminMapCount.put("user_name_1",userName);


                dR_User_Top.set(adminMapCount).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        // copied here.

                        if(task.isSuccessful()){

                             documentReference_intoUser_Doc = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                                    .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
                                    .document(userName+userPhone+"doc");

                            Map<Object,String> userprofile_data = new HashMap<>();

                            userprofile_data.put("name",userName);
                            userprofile_data.put("phone",userPhone);
                          //  userprofile_data.put("image",documentReference.toString());


                            userprofile_data.put("mon_date","");
                            userprofile_data.put("tue_date","");
                            userprofile_data.put("wed_date","");
                            userprofile_data.put("thu_date","");
                            userprofile_data.put("fri_date","");

                            userprofile_data.put("ts_mon_morning","");
                            userprofile_data.put("ts_tue_morning","");
                            userprofile_data.put("ts_wed_morning","");
                            userprofile_data.put("ts_thu_morning","");
                            userprofile_data.put("ts_fri_morning","");

                            userprofile_data.put("ts_mon_evening","");
                            userprofile_data.put("ts_tue_evening","");
                            userprofile_data.put("ts_wed_evening","");
                            userprofile_data.put("ts_thu_evening","");
                            userprofile_data.put("ts_fri_evening","");


                            userprofile_data.put("status","user");


                            //userprofile_data.put("");

                            //   textViewMessage.setText("success.. setting up account");

                            //documentReference

                            documentReference_intoUser_Doc.set(userprofile_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){


                                        if(imageSetup) {

                                            storageReference = FirebaseStorage.getInstance().getReference().child("uploads").child("picture"+ userName+userPhone);
                                            storageReference.putFile(mImageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                                    if(task.isSuccessful()){
                                                      //      image boolean


                                                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Uri> task) {

                                                                if(task.isSuccessful()){

                                                                    image_url_downloaded = task.getResult().toString();



                                                                    if(!image_url_downloaded.equals("")){

                                                                        Toast.makeText(RegUser_Activity.this,"your image is successfully uploaded", Toast.LENGTH_SHORT).show();

                                                                        Map<String,Object> putIntoUserDoc = new HashMap<>();

                                                                        putIntoUserDoc.put("image_url",image_url_downloaded);

                                                                        documentReference_intoUser_Doc.set(putIntoUserDoc,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                            }
                                                                        });

                                                                    }else {


                                                                        Toast.makeText(RegUser_Activity.this,"image is uploaded but url not created", Toast.LENGTH_SHORT).show();

                                                                    }



                                                                }else {


                                                                    Toast.makeText(RegUser_Activity.this,"image is uploaded but url not created", Toast.LENGTH_SHORT).show();


                                                                }

                                                            }
                                                        }).addOnCanceledListener(new OnCanceledListener() {
                                                            @Override
                                                            public void onCanceled() {

                                                                Toast.makeText(RegUser_Activity.this,"image is uploaded but url not created", Toast.LENGTH_SHORT).show();


                                                            }
                                                        });



                                                        boolean_image_setup=true;



                                                    }else {
                                                        //task not successful

                                                        Toast.makeText(RegUser_Activity.this,"image failed to upload", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }).addOnCanceledListener(new OnCanceledListener() {
                                                @Override
                                                public void onCanceled() {


                                                    Toast.makeText(RegUser_Activity.this,"image failed to upload", Toast.LENGTH_SHORT).show();


                                                }
                                            });

                                        }

                                        //should we check,

                                        //here we saved all in sharedpreferences //create 4 pin id.

                                        //check sharedpref into the right admin. ..first check pool, where to create.



//                        if(count_adminGlobal==0){ //here we can create the whole.
//
//                            //create 2 file2,
//
//                            SharedPreferences prefs_Main_Pool = RegUser_Activity.this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
//
//                            editor_Main_Pool.putString("count_admin","1");
//                            editor_Main_Pool.putString("final_Admin_Phone_MainPool",userPhone);
//
//                            editor_Main_Pool.commit();
//
//                            //here for direct
//
//
//                            SharedPreferences prefs = getSharedPreferences(
//                                    "com.example.finalV8_punchCard."+adminPhone, Context.MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = prefs.edit(); // we need to know, which preferences belong to which admin,
//                            //if user registered to another admin.
//
//
//                            editor.putString("final_User_Name",userName);
//                            editor.putString("final_User_Phone",userPhone);
//                            editor.putString("final_Admin_Phone",adminPhone);
//                            editor.putString("final_Admin_Name", adminName);
//
//                            editor.putString("final_User_Picture", storageReference.toString());
//
//                            Log.i("finalSharePreDataCheck","Reg_User_Activity 1,name: "+ userName+ ", phone: "+userPhone + ", adminName:"
//                                    +adminName+" , adminPhone: "+adminPhone);
//
//
//                            //FingerPrint_LogIn_Final_Activity.userCount++;
//
//                            editor.commit();
//
//                        }if(count_adminGlobal==1){
//
//                            //create file
//
//                            SharedPreferences prefs_Main_Pool = RegUser_Activity.this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
//
//                            editor_Main_Pool.putString("count_admin","2");
//                            editor_Main_Pool.putString("final_Admin_Phone_MainPool_2",userPhone);
//
//                            editor_Main_Pool.commit();
//
//
//                            SharedPreferences prefs = getSharedPreferences(
//                                    "com.example.finalV8_punchCard."+adminPhone, Context.MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = prefs.edit(); // we need to know, which preferences belong to which admin,
//                            //if user registered to another admin.
//
//
//                            editor.putString("final_User_Name",userName);
//                            editor.putString("final_User_Phone",userPhone);
//                            editor.putString("final_Admin_Phone",adminPhone);
//                            editor.putString("final_Admin_Name", adminName);
//
//                            editor.putString("final_User_Picture", storageReference.toString());
//
//                            Log.i("finalSharePreDataCheck","Reg_User_Activity 1,name: "+ userName+ ", phone: "+userPhone + ", adminName:"
//                                    +adminName+" , adminPhone: "+adminPhone);
//
//
//                            //FingerPrint_LogIn_Final_Activity.userCount++;
//
//                            editor.commit();
//
//
//
//                        }if(count_adminGlobal>=2){
//
//                            //cancel create, should not create here.,, not create anything.
//
//                        }

                                        //we skipped this?

//                        Intent intent = new Intent(RegUser_Activity.this, Setup_Pin_Activity.class);
//
//                        startActivity(intent);

                                        //intent.addFlags()

                                        //   finish();


                                    }else {

                                        Log.i("documentSet ", "2, task failed");


                                    }
                                }


                            });




                        }else {



                        }

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                    }
                });




            }else if(admin_count_extracted.equals("1")){

                adminMapCount.put("admin_count","2");


                adminMapCount.put("phone", userPhone);

                adminMapCount.put("admin_phone_2",adminPhone);

                adminMapCount.put("admin_name_2",adminName);

                adminMapCount.put("user_name_2",userName);

                admin_count_extracted ="2";


                dR_User_Top.set(adminMapCount, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        // copied here.

                        if(task.isSuccessful()){

                            documentReference_intoUserDoc_2 = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                                    .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
                                    .document(userName+userPhone+"doc");

                            Map<Object,String> userprofile_data = new HashMap<>();

                            userprofile_data.put("name",userName);
                            userprofile_data.put("phone",userPhone);

                            //24 june

                            userprofile_data.put("image_url",image_url_downloaded);
                            //userprofile_data.put("");

                            //   textViewMessage.setText("success.. setting up account");

                            //documentReference

                            documentReference_intoUserDoc_2.set(userprofile_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Log.i("documentSet ", "1");


                                        if(mImageuri!=null) {

                                            storageReference = FirebaseStorage.getInstance().getReference("" + adminName + adminPhone+"doc").child("" + userName + userPhone +"image");
                                            storageReference.putFile(mImageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                                    if(task.isSuccessful()){


                                                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Uri> task) {

                                                                if(task.isSuccessful()){

                                                                    image_url_downloaded = task.getResult().toString();



                                                                    if(!image_url_downloaded.equals("")){

                                                                        Toast.makeText(RegUser_Activity.this,"your image is successfully uploaded", Toast.LENGTH_SHORT).show();


                                                                        Map<String,Object> putIntoUserDoc = new HashMap<>();

                                                                        putIntoUserDoc.put("image_url",image_url_downloaded);

                                                                        documentReference_intoUserDoc_2.set(putIntoUserDoc,SetOptions.merge());

                                                                    }else {


                                                                        Toast.makeText(RegUser_Activity.this,"image is uploaded but url not created", Toast.LENGTH_SHORT).show();

                                                                    }



                                                                }else {


                                                                    Toast.makeText(RegUser_Activity.this,"image is uploaded but url not created", Toast.LENGTH_SHORT).show();


                                                                }

                                                            }
                                                        }).addOnCanceledListener(new OnCanceledListener() {
                                                            @Override
                                                            public void onCanceled() {

                                                                Toast.makeText(RegUser_Activity.this,"image is uploaded but url not created", Toast.LENGTH_SHORT).show();


                                                            }
                                                        });



                                                        boolean_image_setup=true;


                                                    }else {
                                                        //task not successful

                                                        Log.i("checkImageUploaded", "2");

                                                        Log.i("checkSharedPreferences ", "before image upload task fail");

                                                    }

                                                }
                                            }).addOnCanceledListener(new OnCanceledListener() {
                                                @Override
                                                public void onCanceled() {

                                                    Log.i("checkImageUploaded", "3");
                                                }
                                            });

                                        }

                                        //should we check,

                                        //here we saved all in sharedpreferences //create 4 pin id.

                                        //check sharedpref into the right admin. ..first check pool, where to create.



//                        if(count_adminGlobal==0){ //here we can create the whole.
//
//                            //create 2 file2,
//
//                            SharedPreferences prefs_Main_Pool = RegUser_Activity.this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
//
//                            editor_Main_Pool.putString("count_admin","1");
//                            editor_Main_Pool.putString("final_Admin_Phone_MainPool",userPhone);
//
//                            editor_Main_Pool.commit();
//
//                            //here for direct
//
//
//                            SharedPreferences prefs = getSharedPreferences(
//                                    "com.example.finalV8_punchCard."+adminPhone, Context.MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = prefs.edit(); // we need to know, which preferences belong to which admin,
//                            //if user registered to another admin.
//
//
//                            editor.putString("final_User_Name",userName);
//                            editor.putString("final_User_Phone",userPhone);
//                            editor.putString("final_Admin_Phone",adminPhone);
//                            editor.putString("final_Admin_Name", adminName);
//
//                            editor.putString("final_User_Picture", storageReference.toString());
//
//                            Log.i("finalSharePreDataCheck","Reg_User_Activity 1,name: "+ userName+ ", phone: "+userPhone + ", adminName:"
//                                    +adminName+" , adminPhone: "+adminPhone);
//
//
//                            //FingerPrint_LogIn_Final_Activity.userCount++;
//
//                            editor.commit();
//
//                        }if(count_adminGlobal==1){
//
//                            //create file
//
//                            SharedPreferences prefs_Main_Pool = RegUser_Activity.this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
//
//                            editor_Main_Pool.putString("count_admin","2");
//                            editor_Main_Pool.putString("final_Admin_Phone_MainPool_2",userPhone);
//
//                            editor_Main_Pool.commit();
//
//
//                            SharedPreferences prefs = getSharedPreferences(
//                                    "com.example.finalV8_punchCard."+adminPhone, Context.MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = prefs.edit(); // we need to know, which preferences belong to which admin,
//                            //if user registered to another admin.
//
//
//                            editor.putString("final_User_Name",userName);
//                            editor.putString("final_User_Phone",userPhone);
//                            editor.putString("final_Admin_Phone",adminPhone);
//                            editor.putString("final_Admin_Name", adminName);
//
//                            editor.putString("final_User_Picture", storageReference.toString());
//
//                            Log.i("finalSharePreDataCheck","Reg_User_Activity 1,name: "+ userName+ ", phone: "+userPhone + ", adminName:"
//                                    +adminName+" , adminPhone: "+adminPhone);
//
//
//                            //FingerPrint_LogIn_Final_Activity.userCount++;
//
//                            editor.commit();
//
//
//
//                        }if(count_adminGlobal>=2){
//
//                            //cancel create, should not create here.,, not create anything.
//
//                        }



                                        //we skipped this?

                                        Toast.makeText(RegUser_Activity.this,"user succesfully created", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(RegUser_Activity.this, Setup_Pin_Activity.class);
//
//                        startActivity(intent);

                                        //intent.addFlags()

                                        //   finish();


                                    }else {

                                        Log.i("documentSet ", "2, task failed");


                                    }
                                }


                            });




                        }else {



                        }

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                    }
                });



            }

//
//            dR_User_Top.set(adminMapCount, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                    // copied here.
//
//                if(task.isSuccessful()){
//
//                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
//                            .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
//                            .document(userName+userPhone+"doc");
//
//                    Map<Object,String> userprofile_data = new HashMap<>();
//
//                    userprofile_data.put("name",userName);
//                    userprofile_data.put("phone",userPhone);
//                    userprofile_data.put("image",documentReference.toString());
//                    //userprofile_data.put("");
//
//                    //   textViewMessage.setText("success.. setting up account");
//
//                    //documentReference
//
//                    documentReference.set(userprofile_data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//
//                                Log.i("documentSet ", "1");
//
//
//                                if(mImageuri!=null) {
//
//                                    storageReference = FirebaseStorage.getInstance().getReference("" + adminName + adminPhone+"doc").child("" + userName + userPhone +"image");
//                                    storageReference.putFile(mImageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//                                            if(task.isSuccessful()){
//
//                                                Log.i("checkImageUploaded", "1");
//
//                                                Log.i("checkSharedPreferences ", "before image upload success");
//
//                                            }else {
//                                                //task not successful
//
//                                                Log.i("checkImageUploaded", "2");
//
//                                                Log.i("checkSharedPreferences ", "before image upload task fail");
//
//                                            }
//
//                                        }
//                                    }).addOnCanceledListener(new OnCanceledListener() {
//                                        @Override
//                                        public void onCanceled() {
//
//                                            Log.i("checkImageUploaded", "3");
//                                        }
//                                    });
//
//                                }
//
//                                //should we check,
//
//                                //here we saved all in sharedpreferences //create 4 pin id.
//
//                                //check sharedpref into the right admin. ..first check pool, where to create.
//
//
//
////                        if(count_adminGlobal==0){ //here we can create the whole.
////
////                            //create 2 file2,
////
////                            SharedPreferences prefs_Main_Pool = RegUser_Activity.this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
////
////                            SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
////
////                            editor_Main_Pool.putString("count_admin","1");
////                            editor_Main_Pool.putString("final_Admin_Phone_MainPool",userPhone);
////
////                            editor_Main_Pool.commit();
////
////                            //here for direct
////
////
////                            SharedPreferences prefs = getSharedPreferences(
////                                    "com.example.finalV8_punchCard."+adminPhone, Context.MODE_PRIVATE);
////
////                            SharedPreferences.Editor editor = prefs.edit(); // we need to know, which preferences belong to which admin,
////                            //if user registered to another admin.
////
////
////                            editor.putString("final_User_Name",userName);
////                            editor.putString("final_User_Phone",userPhone);
////                            editor.putString("final_Admin_Phone",adminPhone);
////                            editor.putString("final_Admin_Name", adminName);
////
////                            editor.putString("final_User_Picture", storageReference.toString());
////
////                            Log.i("finalSharePreDataCheck","Reg_User_Activity 1,name: "+ userName+ ", phone: "+userPhone + ", adminName:"
////                                    +adminName+" , adminPhone: "+adminPhone);
////
////
////                            //FingerPrint_LogIn_Final_Activity.userCount++;
////
////                            editor.commit();
////
////                        }if(count_adminGlobal==1){
////
////                            //create file
////
////                            SharedPreferences prefs_Main_Pool = RegUser_Activity.this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
////
////                            SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
////
////                            editor_Main_Pool.putString("count_admin","2");
////                            editor_Main_Pool.putString("final_Admin_Phone_MainPool_2",userPhone);
////
////                            editor_Main_Pool.commit();
////
////
////                            SharedPreferences prefs = getSharedPreferences(
////                                    "com.example.finalV8_punchCard."+adminPhone, Context.MODE_PRIVATE);
////
////                            SharedPreferences.Editor editor = prefs.edit(); // we need to know, which preferences belong to which admin,
////                            //if user registered to another admin.
////
////
////                            editor.putString("final_User_Name",userName);
////                            editor.putString("final_User_Phone",userPhone);
////                            editor.putString("final_Admin_Phone",adminPhone);
////                            editor.putString("final_Admin_Name", adminName);
////
////                            editor.putString("final_User_Picture", storageReference.toString());
////
////                            Log.i("finalSharePreDataCheck","Reg_User_Activity 1,name: "+ userName+ ", phone: "+userPhone + ", adminName:"
////                                    +adminName+" , adminPhone: "+adminPhone);
////
////
////                            //FingerPrint_LogIn_Final_Activity.userCount++;
////
////                            editor.commit();
////
////
////
////                        }if(count_adminGlobal>=2){
////
////                            //cancel create, should not create here.,, not create anything.
////
////                        }
//
//
//
//                                //we skipped this?
//
//                                Toast.makeText(RegUser_Activity.this,"user succesfully created", Toast.LENGTH_SHORT).show();
//
////                        Intent intent = new Intent(RegUser_Activity.this, Setup_Pin_Activity.class);
////
////                        startActivity(intent);
//
//                                //intent.addFlags()
//
//                                //   finish();
//
//
//                            }else {
//
//                                Log.i("documentSet ", "2, task failed");
//
//
//                            }
//                        }
//
//
//                    });
//
//
//
//
//                }else {
//
//
//
//                }
//
//                }
//            }).addOnCanceledListener(new OnCanceledListener() {
//                @Override
//                public void onCanceled() {
//
//                }
//            });
//



            //creating document here.
            //then after finish, move to next.

//            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
//                    .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
//                    .document(userName+userPhone+"doc");
//
//            Map<Object,String> userprofile_data = new HashMap<>();
//
//            userprofile_data.put("name",userName);
//            userprofile_data.put("phone",userPhone);
//            userprofile_data.put("rating","2.5");
//            userprofile_data.put("image",documentReference.toString());
//            //userprofile_data.put("");
//
//         //   textViewMessage.setText("success.. setting up account");
//
//            //documentReference
//
//            documentReference.set(userprofile_data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//
//                        Log.i("documentSet ", "1");
//
//
//                        if(mImageuri!=null) {
//
//                            storageReference = FirebaseStorage.getInstance().getReference("" + adminName + adminPhone+"doc").child("" + userName + userPhone +"image");
//                            storageReference.putFile(mImageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//                                    if(task.isSuccessful()){
//
//                                        Log.i("checkImageUploaded", "1");
//
//                                        Log.i("checkSharedPreferences ", "before image upload success");
//
//                                    }else {
//                                        //task not successful
//
//                                        Log.i("checkImageUploaded", "2");
//
//                                        Log.i("checkSharedPreferences ", "before image upload task fail");
//
//                                    }
//
//                                }
//                            }).addOnCanceledListener(new OnCanceledListener() {
//                                @Override
//                                public void onCanceled() {
//
//                                    Log.i("checkImageUploaded", "3");
//                                }
//                            });
//
//                        }
//
//                        //should we check,
//
//                        //here we saved all in sharedpreferences //create 4 pin id.
//
//                        //check sharedpref into the right admin. ..first check pool, where to create.
//
//
//
////                        if(count_adminGlobal==0){ //here we can create the whole.
////
////                            //create 2 file2,
////
////                            SharedPreferences prefs_Main_Pool = RegUser_Activity.this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
////
////                            SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
////
////                            editor_Main_Pool.putString("count_admin","1");
////                            editor_Main_Pool.putString("final_Admin_Phone_MainPool",userPhone);
////
////                            editor_Main_Pool.commit();
////
////                            //here for direct
////
////
////                            SharedPreferences prefs = getSharedPreferences(
////                                    "com.example.finalV8_punchCard."+adminPhone, Context.MODE_PRIVATE);
////
////                            SharedPreferences.Editor editor = prefs.edit(); // we need to know, which preferences belong to which admin,
////                            //if user registered to another admin.
////
////
////                            editor.putString("final_User_Name",userName);
////                            editor.putString("final_User_Phone",userPhone);
////                            editor.putString("final_Admin_Phone",adminPhone);
////                            editor.putString("final_Admin_Name", adminName);
////
////                            editor.putString("final_User_Picture", storageReference.toString());
////
////                            Log.i("finalSharePreDataCheck","Reg_User_Activity 1,name: "+ userName+ ", phone: "+userPhone + ", adminName:"
////                                    +adminName+" , adminPhone: "+adminPhone);
////
////
////                            //FingerPrint_LogIn_Final_Activity.userCount++;
////
////                            editor.commit();
////
////                        }if(count_adminGlobal==1){
////
////                            //create file
////
////                            SharedPreferences prefs_Main_Pool = RegUser_Activity.this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
////
////                            SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
////
////                            editor_Main_Pool.putString("count_admin","2");
////                            editor_Main_Pool.putString("final_Admin_Phone_MainPool_2",userPhone);
////
////                            editor_Main_Pool.commit();
////
////
////                            SharedPreferences prefs = getSharedPreferences(
////                                    "com.example.finalV8_punchCard."+adminPhone, Context.MODE_PRIVATE);
////
////                            SharedPreferences.Editor editor = prefs.edit(); // we need to know, which preferences belong to which admin,
////                            //if user registered to another admin.
////
////
////                            editor.putString("final_User_Name",userName);
////                            editor.putString("final_User_Phone",userPhone);
////                            editor.putString("final_Admin_Phone",adminPhone);
////                            editor.putString("final_Admin_Name", adminName);
////
////                            editor.putString("final_User_Picture", storageReference.toString());
////
////                            Log.i("finalSharePreDataCheck","Reg_User_Activity 1,name: "+ userName+ ", phone: "+userPhone + ", adminName:"
////                                    +adminName+" , adminPhone: "+adminPhone);
////
////
////                            //FingerPrint_LogIn_Final_Activity.userCount++;
////
////                            editor.commit();
////
////
////
////                        }if(count_adminGlobal>=2){
////
////                            //cancel create, should not create here.,, not create anything.
////
////                        }
//
//
//
//                        //we skipped this?
//
//                        Toast.makeText(RegUser_Activity.this,"user succesfully created", Toast.LENGTH_SHORT).show();
//
////                        Intent intent = new Intent(RegUser_Activity.this, Setup_Pin_Activity.class);
////
////                        startActivity(intent);
//
//                        //intent.addFlags()
//
//                     //   finish();
//
//
//                    }else {
//
//                        Log.i("documentSet ", "2, task failed");
//
//
//                    }
//                }
//
//
//            });

           //add listener

            CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections").
                    document(adminName + adminPhone + "doc").collection("all_employee_thisAdmin_collection");

            Query query1 = collectionReference.whereEqualTo("name",userName);   //check if it is written.


            //24 june, change to check url

            query1.addSnapshotListener(new EventListener<QuerySnapshot>() {

                @Override
                public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                    Log.i("checkSnapShotListener", "1 start");
                    if(e!=null){

                        return;
                    }

                    if(queryDocumentSnapshots!=null ){
                        int size =  queryDocumentSnapshots.size();
                        Log.i("checkSnapShotListener", "2 not null");
//                                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){

//                                }
                        String testurl = "";

                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()){



                            for(Map.Entry<String,Object> remap : documentSnapshot.getData().entrySet()){

                                if(remap.getKey().equals("image_url")){

                                    testurl = remap.getValue().toString();

                                }

                            }

                        }

//
                        if (!testurl.equals("")){

                            //move to next activity?

                            Log.i("checkSnapShotListener", " 3 exist");

                            Intent intent1 = new Intent(RegUser_Activity.this,Setup_Pin_Activity.class);
                            intent1.putExtra("sentUserName", userName);
                            intent1.putExtra("sentUserPhone", userPhone);
                            intent1.putExtra("sentAdminName", adminName);
                            intent1.putExtra("sentAdminPhone", adminPhone);
                            intent1.putExtra("checkadminOrUser","user");

                            //intent1.addFlags(Intent.)
                            startActivity(intent1);
                            //finish();

                            //need to disable back button manually.
                        }

                    }
                }
            });
            //               }



        }

        if(status.equals("please contact admin")){

            //this will be called, if false return from check document,
            //document existed
         //   textViewMessage.setText("registration failed");
            logOutNow();
        }


    }

    @Override
    public void update(Observable o, Object arg) {

        if(o instanceof RegUser_Presenter){

            Log.i("checkUserReg Flow: ", "[Activity] , 18 ,update , o is: "+((RegUser_Presenter) o).getReturnStatus());


            //String resultHere = ((RegUser_Presenter) o).getReturnStatus();

            Boolean resultBoolean = ((RegUser_Presenter) o).getFinally();

            if(resultBoolean){
            //if(resultHere.equals("doc created")){

                checkDocResult("doc created");
            }

            else {

            //if(resultHere.equals("please contact admin")){

                checkDocResult("please contact admin");
            }
            //


        }


    }
}
