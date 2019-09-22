package com.projek.afinal.fingerPrint_Login.register.register_as_admin.register_as_admin_regAdmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projek.afinal.R;
import com.projek.afinal.fingerPrint_Login.register.register_as_admin_setupProfile.RegAdmin_asAdmin_Profile_Activity;
import com.projek.afinal.fingerPrint_Login.sample_test.FileUtil;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hanks.htextview.evaporate.EvaporateTextView;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class RegAdmin_AsAdmin_Activity extends AppCompatActivity implements Observer, View.OnClickListener {

    //private EditText editTextName, editTextPhone, editTextCode;
    //private Button buttonLogin, buttonGetCode;

   // private ConstraintLayout constraintLayout;

    private TextView textViewMessage;
    private Timer timer;
    private int count;

    private String userName;
    private String userPhone;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;

    private boolean allowCreateAdmin;
    private Presenter_RegAdmin_AsAdmin_Activity presenter;
    private String codeFromFirebase;
    private String codeUserAdminEnter;
    private PhoneAuthCredential credenttial;
    private int countForAnimateButton;
    private int copyadminCreated;

    //testing phase

    private Button buttonTestHere;
    private String admin_Label;


    // we try pull if there is any data in shared preferences


    //20 may
    private TextView textViewName, textViewPhone, textViewMessageCode;
    private FloatingActionButton buttonGetCode_next;
    private CardView cardView_regAsAdmin;
    private CircleImageView circleImageView_regAsAdmin;
    private FloatingActionButton buttonGetCode_next2;
    private EvaporateTextView evaporate_textView;
    private EditText editTextCode_1,editTextCode_2, editTextCode_3, editTextCode_4, editTextCode_5, editTextCode_6;


    //static final for image

    private static final int READ_REQUEST_CODE = 42;
    private boolean imageSetupTrue;


    private StorageReference storageReference;
    private Uri uriImage;
    private StorageReference this_image_ref;

    //24 may

    private Button buttonTest;

    //28 may
  //  private Button button28may;

    //14 june
    private String admin_count;
    private boolean boolean_admin_count;
    private Bitmap btMapImage;
    private File image_File;

    private String image_download_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_admin__as_admin_);

        image_download_url= "";

        boolean_admin_count=false;

        Log.i("checkFlowDestroy", "2 reg_admin");
//
        //buttonTest = findViewById(R.id.reg_admin_asAdmin_buttonTest);

       // button28may = findViewById(R.id.buttontest28mayid);

        //buttonTest.setOnClickListener(this);

//
//        buttonTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////
////                Intent intent = new Intent(RegAdmin_AsAdmin_Activity.this, RegAdmin_asAdmin_Profile_Activity.class);
////
////                intent.putExtra("adminName_asAdmin",userName);
////                intent.putExtra("adminPhone_asAdmin",userPhone);
////
////                startActivity(intent);
//
//
//            }
//        });

        copyadminCreated=0;
        countForAnimateButton=0;
        credenttial=null;

        storageReference = FirebaseStorage.getInstance().getReference("uploads");


        textViewName = findViewById(R.id.reg_Admin_asAdmin_textView_name_id);
        textViewPhone= findViewById(R.id.reg_Admin_asAdmin_textView_phone_id);

        Intent intent = getIntent();

        userName = intent.getStringExtra("adminName_asAdmin");
        userPhone = intent.getStringExtra("adminPhone_asAdmin");

//        CollectionReference cR_topUser = FirebaseFirestore.getInstance()
//                .collection("users_top_detail");
//
//        DocumentReference dR_topUser = cR_topUser.document(userPhone+"imauser");
//
//        dR_topUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                //16 june check back flow
//
//                if(task.isSuccessful()){
//
//
//                if(Objects.requireNonNull(task.getResult()).exists()) {
//
//                    Map<String, Object> remap = task.getResult().getData();
//
//
//                    if (task.getResult().getData().isEmpty()) {
//                        //meaning no recorded yet, it is zero
//
//                        admin_count = "0";
//                    }
//
//
//                    if (remap != null) {
//                        for (Map.Entry<String, Object> remapHere : remap.entrySet()) {
//
//                            if (remapHere.getKey().equals("admin_count")) {
//
//                                admin_count = remapHere.getValue().toString();
//                            }
//
//                        }
//                    }
//
//
//                    boolean_admin_count = true;
//
//
//                } //assume if exist.
//
//                }else {
//
//
//                }
//
//
//
//            }
//        }).addOnCanceledListener(new OnCanceledListener() {
//            @Override
//            public void onCanceled() {
//
//
//
//            }
//        });

        textViewName.setText(userName);
        textViewPhone.setText(userPhone);

        editTextCode_1 = findViewById(R.id.reg_Admin_asAdmin_editText_ring1_id);
        editTextCode_2 = findViewById(R.id.reg_Admin_asAdmin_editText_ring2_id);
        editTextCode_3 = findViewById(R.id.reg_Admin_asAdmin_editText_ring3_id);
        editTextCode_4 = findViewById(R.id.reg_Admin_asAdmin_editText_ring4_id);
        editTextCode_5 = findViewById(R.id.reg_Admin_asAdmin_editText_ring5_id);
        editTextCode_6 = findViewById(R.id.reg_Admin_asAdmin_editText_ring6_id);



       // editTextCode = findViewById(R.id.regAdmin_asAdmin_editText_CodeiD);
        //constraintLayout = findViewById(R.id.regAdmin_constraint);

       // buttonTestHere = findViewById(R.id.buttonaksdkasjklda);

        allowCreateAdmin=false;

        //get code change to next button

        buttonGetCode_next = findViewById(R.id.reg_Admin_asAdmin_floatingActionButton_Next_id);

        buttonGetCode_next2 = findViewById(R.id.reg_Admin_asAdmin_floatingActionButton_Next2_id);

        textViewMessageCode = findViewById(R.id.reg_Admin_asAdmin_6pin_code_id);

        evaporate_textView = findViewById(R.id.reg_Admin_asAdmin_EvaporateText_Code_ID);

       // buttonLogin = findViewById(R.id.regAdmin_asAdmin_buttonLoginiD);


        cardView_regAsAdmin = findViewById(R.id.reg_Admin_asAdmin_cardView_id);
        circleImageView_regAsAdmin = findViewById(R.id.reg_Admin_asAdmin_circlerImageView);

        //setting up image
        circleImageView_regAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent,READ_REQUEST_CODE);

            }
        });


        //textViewMessage = findViewById(R.id.regAdmin_asAdmin_textViewMessageiD);

        presenter = new Presenter_RegAdmin_AsAdmin_Activity(this);

        presenter.addObserver(this);


//
//        buttonTestHere.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String userName = editTextName.getText().toString();
//                String userPhone = editTextPhone.getText().toString();
//
//                Intent intent = new Intent(RegAdmin_AsAdmin_Activity.this,RegAdmin_asAdmin_Profile_Activity.class);
//                intent.putExtra("adminName_asAdmin",userName);
//                intent.putExtra("adminPhone_asAdmin",userPhone);
//
//                startActivity(intent);
//            }
//        });


        //testing animation view

        timer = new Timer();

        count=0;


//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                codeUserAdminEnter = editTextCode.getText().toString();
//                if(checkInput(codeUserAdminEnter)){
//
//                    //send method verify
//
//                    checkCredential(codeUserAdminEnter,codeFromFirebase);
//
//                }else {
//
//
//
//                }
//
//            }
//        });

        buttonGetCode_next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
  //              if(checkNumberOfAdminRegisteredTo()) {

                    if(imageSetupTrue) {
//                    userName = textViewName.getText().toString();
//                    userPhone = textViewPhone.getText().toString();

//                    textViewMessage.setText("check input..");
//                    if (checkInput(userName, userPhone)) {
//                        //if input true
                        //      textViewMessage.setText("getting phone verification..");

                        cardView_regAsAdmin.setVisibility(View.GONE);
                        circleImageView_regAsAdmin.setVisibility(View.GONE);

                        buttonGetCode_next.setVisibility(View.GONE);


                        buttonGetCode_next2.setVisibility(View.VISIBLE);

                        textViewMessageCode.setVisibility(View.VISIBLE);

                        evaporate_textView.setVisibility(View.VISIBLE);

                        editTextCode_1.setVisibility(View.VISIBLE);
                        editTextCode_2.setVisibility(View.VISIBLE);
                        editTextCode_3.setVisibility(View.VISIBLE);
                        editTextCode_4.setVisibility(View.VISIBLE);
                        editTextCode_5.setVisibility(View.VISIBLE);
                        editTextCode_6.setVisibility(View.VISIBLE);


                        getCallBack(userPhone);

//                    } else {
//
//                        //if input faulty
//
//
//                    }

                    }else {
                        Toast.makeText(RegAdmin_AsAdmin_Activity.this, "please set image", Toast.LENGTH_SHORT).show();
                    }

//
//                }else { //if FALSE,
//
//                    //intent go back, disallow register more than one user//or admin, since registering admin, means registering user.
//
//                    Intent intentBack = new Intent(RegAdmin_AsAdmin_Activity.this, FingerPrint_LogIn_Final_Activity.class);
//                    startActivity(intentBack);
//                    finish();
//                }
            }
        });



        buttonGetCode_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String code1 = editTextCode_1.getText().toString();
                String code2 = editTextCode_2.getText().toString();
                String code3 = editTextCode_3.getText().toString();
                String code4 = editTextCode_4.getText().toString();
                String code5 = editTextCode_5.getText().toString();
                String code6 = editTextCode_6.getText().toString();

                String codeEntered = code1+code2+code3+code4+code5+code6;

            if(checkInput(codeEntered)){

                Log.i("check_custom_auth","1 credential, ourcode : "+ codeEntered +" , codefirebase : "+codeFromFirebase);
                checkCredential(codeEntered,codeFromFirebase);

            }else {

                Toast.makeText(RegAdmin_AsAdmin_Activity.this,"please enter code received",Toast.LENGTH_SHORT).show();
            }

            }
        });


        //28 may

        // button28may.setOnClickListener(this);


        //phone auth call back

        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(final PhoneAuthCredential phoneAuthCredential) {

                Toast.makeText(RegAdmin_AsAdmin_Activity.this,"verified, please wait, attempt automatically...",Toast.LENGTH_LONG).show();

                //checkPhoneCredential(phoneAuthCredential);

                //textViewMessage.setText("phone verified, try automatically...");

                textViewMessageCode.setText("try log in automatically..");

                checkPhoneCredential(phoneAuthCredential);


//
//                timer.scheduleAtFixedRate(new TimerTask() {
//                    @Override
//                    public void run() {
//                        countForAnimateButton++;
//                        //here after got code, we animate button, to visible and move
//                        //after 1 second, after 3 secound
//
//                        if(countForAnimateButton==10 && copyadminCreated!=2){
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    editTextCode.setHint("please enter code here");
//                                }
//                            });
//
//
//                        }
//
//                        if(countForAnimateButton==1){
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    // https://www.youtube.com/watch?v=IECLEh98HnE
//                                    // https://stackoverflow.com/questions/30535304/android-pop-in-animation
//                                    // https://stackoverflow.com/questions/9448732/shaking-wobble-view-animation-in-android
//
//
//
//                                    ObjectAnimator animator = ObjectAnimator.ofFloat(buttonGetCode,"translationY",-70f);
//                                    buttonGetCode.animate()
//                                            .alpha(0f)
//                                            .setDuration(200)
//                                            .setListener(null);
//                                    animator.setDuration(200);
//                                    animator.start();
//
//
//                                    Animation fadeIn = AnimationUtils.loadAnimation(RegAdmin_AsAdmin_Activity.this,R.anim.fadein);
//                                    buttonLogin.startAnimation(fadeIn);
//
//
//                                    ObjectAnimator moveUpGroup = ObjectAnimator.ofFloat(constraintLayout,"translationY",-180f);
//                                    moveUpGroup.setDuration(200);
//                                    moveUpGroup.start();
//                                    editTextCode.startAnimation(fadeIn);
//                                    //editTextCode.setText("try to log in automatically..");
//                                    editTextCode.setHint("auto registering attempt..");
//
//                                    checkPhoneCredential(phoneAuthCredential);
////                                buttonLogin.animate()
////                                        .alpha(1f)
////                                        .setDuration(400)
////                                        .setListener(null);
//
//
//
//                                }
//                            });
//
//                        }
//
//                    }
//                },500,1000);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i("fail_verify",": "+e.getMessage());
                textViewMessageCode.setText("fail verify");


            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeFromFirebase=s;
                Log.i("checkCode", codeFromFirebase);

                Toast.makeText(RegAdmin_AsAdmin_Activity.this,"please enter code now",Toast.LENGTH_LONG).show();
                //textViewMessageCode.setText("checking in credential, please wait.. "+codeFromFirebase);

                // we should put timer here,

            }
        };

        //problem is when this admin want to register as user for other admin.
        //so we need to put label, then pull according to corresponding lable.

        //two tag,
        // tag ONE , mean user was admin, and added user under its tree
        // tag TWO , mean user was user , and want to become admin.
        //must be done at first phase.



//        buttonTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.i("checktest ","asda");
//
//            }
//        });

    }

    //for image loader.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //data.getData().



        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){

             uriImage = null;

            if(data!=null){

                uriImage = data.getData();

                //FileUtil.

                try {
                    image_File = FileUtil.from(RegAdmin_AsAdmin_Activity.this,uriImage);

                    image_File = new Compressor(this).compressToFile(image_File);

                    uriImage = Uri.fromFile(image_File);

                    showImage(uriImage);

                    imageSetupTrue=true;

                } catch (IOException e) {
                    e.printStackTrace();
                    imageSetupTrue=false;
                }



            }else {
                imageSetupTrue=false;
            }
        }

    }

    private void showImage(Uri uri) {

        ///circleImageView_regAsAdmin.setImageURI(uri);

        circleImageView_regAsAdmin.setImageURI(uri);

        Toast.makeText(RegAdmin_AsAdmin_Activity.this,"image setup", Toast.LENGTH_SHORT).show();

    }

    //check shared preferences, if already 2, dont allow to register to admin

    // 12 june no longer used

    private boolean checkNumberOfAdminRegisteredTo(){

        File f_MainPool = new File("/data/data/com.example.afinal/shared_prefs/com.example.finalV8_punchCard.MAIN_POOL.xml");

        if(f_MainPool.exists()){ //if exist, should

            //if exist, is already user to other admin, so counter should read 1.
            //read count first if 2 or higher, send error.

            SharedPreferences prefs_Main_Pool = this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);

            String count_admin = prefs_Main_Pool.getString("count_admin","");

            if(Integer.valueOf(count_admin)==1){


                return true; //return can create new admmin, since within 2 user max.

//                SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
//
//                editor_Main_Pool.putString("count_admin","2"); //here we update the count. //we could just check earlier.
//                editor_Main_Pool.putString("final_Admin_Phone_MainPool_2",userPhone);
//                //editor_Main_Pool.putString("")
//
//                editor_Main_Pool.commit();


            }if((Integer.valueOf(count_admin)>=2)){


                return false;
                //this should be error. somehow, should not happen to have register to 2 admin.


            }else {



                return false;// somehow something weird data pulled
            }






        }else {


            return true; //not exist yet, so can add more user. //another user

            //here we create, since it is not exist yet.
//
//            SharedPreferences prefs_Main_Pool = this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
//
//            SharedPreferences.Editor editor_Main_Pool = prefs_Main_Pool.edit();
//
//            editor_Main_Pool.putString("count_admin","1");
//            editor_Main_Pool.putString("final_Admin_Phone_MainPool",userPhone);
//            //editor_Main_Pool.putString("")
//
//            editor_Main_Pool.commit();

        }
    }


    private void checkCredential(String codeUserAdminEnter, String codeFromFirebase) {

        Log.i("check_custom_auth"," 2 credential");
        presenter.getCredentialWithUpdates(codeUserAdminEnter,codeFromFirebase);
    }

    private void checkPhoneCredential(PhoneAuthCredential phoneAuthCredential) {

        //if(userName!=null&&userPhone!=null) {

        Log.i("check_custom_auth","6 credential");


        textViewMessageCode.setText("logging in automatically with credential");

            //presenter.checkCredentialWithUpdates2(phoneAuthCredential, userName, userPhone);

            //12 june

         presenter.checkCredentialWithUpdates_NewStructure(phoneAuthCredential,userName,userPhone);
       // }


    }

    @Override
    protected void onDestroy() {


        Log.i("checkFlowDestroy", "3 reg_admin");

        presenter.deleteObserver(this);

        super.onDestroy();

        Log.i("checkFlowDestroy", "4 reg_admin");
    }

    private void getCallBack(String userPhone) {

      PhoneAuthProvider.getInstance().verifyPhoneNumber(userPhone,
                60,
                TimeUnit.SECONDS,
                RegAdmin_AsAdmin_Activity.this,
                mCallBack);



    }

    private boolean checkInput(String code){
        if(code.equals("")|| code==null){

            return false;
        }
        else {


            return true;
        }


    }

    private boolean checkInput(String userName,String userPhone) {

        if(userName.equals("")||userPhone.equals("")||userName==null||userPhone==null){


            return false;
        }
        else {

            int countInput = userName.length();

            if(countInput<=3){

                Toast.makeText(this,"please enter name more than 3 characters",Toast.LENGTH_SHORT).show();

                return false;
            }

            //normalize phone number and name
            String uncheckName = userName;
            String uncheckPhone = userPhone;

            userName = userName.trim();
            userPhone = userPhone.replace(" ","");
            userPhone = userPhone.replace("-","");
            if(userPhone.charAt(0)==0){
                userPhone="+6"+userPhone; //test if input is 017
            }
            if(userPhone.charAt(0)==6){
                userPhone="+"+userPhone;
            }

            this.userName =userName;
            this.userPhone=userPhone;
            return true;
        }
    }


    @Override
    public void update(Observable observable, Object o) {
        Log.i("checkFlowAsAdmin", "5");

        if(observable instanceof Presenter_RegAdmin_AsAdmin_Activity){

        Log.i("checkFlowAsAdmin", "4");
        // 0 = initial , 1 = success , 2 = fail
        int adminDocumentCreated = ((Presenter_RegAdmin_AsAdmin_Activity) observable).getIfDocumentCreated();

        int count_admin_asAdmin = ((Presenter_RegAdmin_AsAdmin_Activity) observable).getCount_admin_RegAsAdmin(); // by default = 1,

        copyadminCreated=adminDocumentCreated;


        if(adminDocumentCreated==1){ //mean we can create



            //28 may

            uploadFile(); //this might not finished in time.

            // 11june

//            Intent intent = new Intent(RegAdmin_AsAdmin_Activity.this,RegAdmin_asAdmin_Profile_Activity.class);
//            intent.putExtra("adminName_asAdmin",userName);
//            intent.putExtra("adminPhone_asAdmin",userPhone);
//            intent.putExtra("image_ref_asAdmin",this_image_ref.toString());
//
//            intent.putExtra("admin_count", "1");
//
//
//
//            startActivity(intent);

        }

            if(adminDocumentCreated==2){ //mean we can create

                uploadFile_2(); //this might not finished in time.

                // 11june

//                Intent intent = new Intent(RegAdmin_AsAdmin_Activity.this,RegAdmin_asAdmin_Profile_Activity.class);
//                intent.putExtra("adminName_asAdmin",userName);
//                intent.putExtra("adminPhone_asAdmin",userPhone);
//                intent.putExtra("image_ref_asAdmin",this_image_ref.toString());
//
//                intent.putExtra("admin_count", "2");
////                if(admin_count.equals("0")) {
////
////                    intent.putExtra("admin_count", "1");
////                }else if(admin_count.equals("1")){
////
////                    intent.putExtra("admin_count", "2");
////                }
//
//                //intent.addFlags(Intent.)
//                startActivity(intent);

            }
        if(adminDocumentCreated==0){

            timer.cancel();
            Log.i("checkFlowAsAdmin", "2");
            //textViewMessage.setText("please try again");
            Toast.makeText(this,"please try again",Toast.LENGTH_SHORT).show();


        }
        if(adminDocumentCreated==3){

            timer.cancel();
         //   textViewMessage.setText("please try again");
            //here can intent to next.

            Log.i("checkFlowAsAdmin", "1");
            Toast.makeText(this,"please try again",Toast.LENGTH_SHORT).show();




        }
        if(adminDocumentCreated==4){ //already exist.

        //    textViewMessage.setText("please try again");
            Toast.makeText(this,"phone already registered",Toast.LENGTH_SHORT).show();

        }

        credenttial = ((Presenter_RegAdmin_AsAdmin_Activity) observable).getCredential();

        if(credenttial!=null){


            Log.i("check_custom_auth","5 credential");
            checkPhoneCredential(credenttial);


        }

    }



    }


    // 22 may

    //upload image

    private String getFileExtentsion(Uri uri){

        ContentResolver cr = getContentResolver();

        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));

    }

    private void uploadFile(){

        if(imageSetupTrue){


            // uploads.adminnamephone.jpg etc.
            //StorageReference this_image_ref = storageReference.child("admin"+userName+userPhone+"."+getFileExtentsion(uriImage));

            //so it will looks like, uploads/admin/admin+6018467

           this_image_ref = storageReference.child("picture"+userName+userPhone);

            this_image_ref.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){

                        //if success save picture inside storage, the we save the referenece inside admin, i think better
                        //create user profile for this admin.

//                        DocumentReference reference = FirebaseFirestore.getInstance()
//                                .collection("employees_to_offices")
//                                .document(userName+userPhone+"document");
//
//                        Map<String, Object> imm = new HashMap<>();
//
//                        imm.put("image",this_image_ref);
//
//                        reference.set(imm, SetOptions.merge());

                        this_image_ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                if(task.isSuccessful()){

                                    image_download_url = task.getResult().toString();


                                    if(!image_download_url.equals("")){


                                        Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture successfully uploaded",Toast.LENGTH_SHORT).show();

                                        //11june

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                //sharedprefs.

                                                Intent intent = new Intent(RegAdmin_AsAdmin_Activity.this,RegAdmin_asAdmin_Profile_Activity.class);
                                                intent.putExtra("adminName_asAdmin",userName);
                                                intent.putExtra("adminPhone_asAdmin",userPhone);
                                                intent.putExtra("image_ref_asAdmin",this_image_ref.toString());
                                                intent.putExtra("image_url",image_download_url);
                                                intent.putExtra("admin_count", "1");
                                                //intent.addFlags(Intent.)

                                                startActivity(intent);

                                            }
                                        });



                                    }else {     // task getting download url not success

                                        Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture successfully uploaded but problem getting url",Toast.LENGTH_SHORT).show();



                                    }




                                }else {

                                    Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture successfully uploaded but problem getting url",Toast.LENGTH_SHORT).show();



                                }
                            }
                        }).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {

                                Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture successfully uploaded but problem getting url",Toast.LENGTH_SHORT).show();


                            }
                        });



                    }else {


                        Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture failed to upload",Toast.LENGTH_SHORT).show();
                    }

                }
            });





        }else {

            Toast.makeText(RegAdmin_AsAdmin_Activity.this,"please setup image", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadFile_2(){

        if(imageSetupTrue){


            // uploads.adminnamephone.jpg etc.
            //StorageReference this_image_ref = storageReference.child("admin"+userName+userPhone+"."+getFileExtentsion(uriImage));

            //so it will looks like, uploads/admin/admin+6018467

            this_image_ref = storageReference.child("picture"+userName+userPhone);

            this_image_ref.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){

                        //if success save picture inside storage, the we save the referenece inside admin, i think better
                        //create user profile for this admin.

//                        DocumentReference reference = FirebaseFirestore.getInstance()
//                                .collection("employees_to_offices")
//                                .document(userName+userPhone+"document");
//
//                        Map<String, Object> imm = new HashMap<>();
//
//                        imm.put("image",this_image_ref);
//
//                        reference.set(imm, SetOptions.merge());

                        this_image_ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                if(task.isSuccessful()){

                                    image_download_url = task.getResult().toString();


                                    if(!image_download_url.equals("")){


                                        Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture successfully uploaded",Toast.LENGTH_SHORT).show();

                                        //11june

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                //sharedprefs.

                                                Intent intent = new Intent(RegAdmin_AsAdmin_Activity.this,RegAdmin_asAdmin_Profile_Activity.class);
                                                intent.putExtra("adminName_asAdmin",userName);
                                                intent.putExtra("adminPhone_asAdmin",userPhone);
                                                intent.putExtra("image_ref_asAdmin",this_image_ref.toString());
                                                intent.putExtra("image_url",image_download_url);
                                                intent.putExtra("admin_count", "2");
                                                //intent.addFlags(Intent.)

                                                startActivity(intent);

                                            }
                                        });



                                    }else {     // task getting download url not success

                                        Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture successfully uploaded but problem getting url",Toast.LENGTH_SHORT).show();



                                    }




                                }else {

                                    Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture successfully uploaded but problem getting url",Toast.LENGTH_SHORT).show();



                                }
                            }
                        }).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {

                                Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture successfully uploaded but problem getting url",Toast.LENGTH_SHORT).show();


                            }
                        });



                    }else {


                        Toast.makeText(RegAdmin_AsAdmin_Activity.this,"picture failed to upload",Toast.LENGTH_SHORT).show();
                    }

                }
            });





        }else {

            Toast.makeText(RegAdmin_AsAdmin_Activity.this,"please setup image", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

//            case R.id.buttontest28mayid:
//
//                uploadFile();
//
//                Intent intent = new Intent(RegAdmin_AsAdmin_Activity.this,RegAdmin_asAdmin_Profile_Activity.class);
//                intent.putExtra("adminName_asAdmin",userName);
//                intent.putExtra("adminPhone_asAdmin",userPhone);
//                //intent.putExtra("image_ref_asAdmin",this_image_ref.toString());
//
//                startActivity(intent);
//
//
//                Log.i("checkClickWhy", "1");
//
//
//                break;
        }



    }
}
