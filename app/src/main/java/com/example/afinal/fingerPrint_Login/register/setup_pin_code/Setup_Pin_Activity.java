package com.example.afinal.fingerPrint_Login.register.setup_pin_code;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.fingerprint_login.FingerPrint_LogIn_Final_Activity;
import com.example.afinal.fingerPrint_Login.main_activity_fragment.Main_BottomNav_Activity;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;

public class Setup_Pin_Activity extends AppCompatActivity {

    //this activity should share for both admin and user,

    // one user/admin will only have one pin

    private EditText editText1, editText2, editText3, editText4;
    private TextView textView;

    private Button button;

   // private CircleImageView circleImageView;

    private DocumentReference documentReference;
    private StorageReference storageReference;

    Timer timer;

    //for automated move forward
    private Timer timer2;
    private boolean autoNext;

    private int countFlow;


    private String nameHere;
    private String phoneHere;
    private String adminName;
    private String adminPhone;
    private String checkAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup__pin_);
        autoNext = false;
        countFlow=0;
        editText1 = findViewById(R.id.regUser_editNumber_1_iD);
        editText2 = findViewById(R.id.regUser_editNumber_2_iD);
        editText3 = findViewById(R.id.regUser_editNumber_3_iD);
        editText4 = findViewById(R.id.regUser_editNumber_4_iD);

        //storageReference =



        textView = findViewById(R.id.regUser_pinCode_textViewiD);
//        textViewName = findViewById(R.id.regUser_pinCode_textView_NameID);
//        textViewPhone = findViewById(R.id.regUser_pinCode_textView_PhoneID);

       // circleImageView = findViewById(R.id.regUser_pinCode_circleImageViewiD);

        textView.setText("enter 4 pin password you prefer");


//        SharedPreferences prefs = getSharedPreferences("com.example.finalV8_punchCard", Context.MODE_PRIVATE);
//
//        final String nameHere = prefs.getString("final_User_Name","");
//        final String phoneHere = prefs.getString("final_User_Phone","");
//        String adminName = prefs.getString("final_Admin_Name","");
//        String adminPhone = prefs.getString("final_Admin_Phone","");
      //  String ref = prefs.getString("final_pth_user","");

            final Intent intent = getIntent();

            checkAdmin = intent.getStringExtra("checkadminOrUser");

            if(checkAdmin.equals("admin")){

                nameHere = intent.getStringExtra("sentAdminName");
                phoneHere= intent.getStringExtra("sentAdminPhone");

                adminName = nameHere;
                adminPhone = phoneHere;


            }else {

                nameHere = intent.getStringExtra("sentUserName");
                phoneHere = intent.getStringExtra("sentUserPhone");
                adminName = intent.getStringExtra("sentAdminName");
                adminPhone = intent.getStringExtra("sentAdminPhone");
            }
            if(adminName.equals("")|| adminName==null){

                adminName = nameHere; //we assume this is from admin page, register as admin, so admin and user has same detail.
                adminPhone=phoneHere;

            }

        Log.i("finalSharePreDataCheck","Setup_Pin_Activity 2,name: "+ nameHere+ ", phone: "+phoneHere+ ", adminName:"
                +adminName+" , adminPhone: "+adminPhone);


//
//        documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
//                .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
//                .document(nameHere+phoneHere+"doc");

        documentReference = FirebaseFirestore.getInstance().collection("users_top_detail").document(phoneHere+"imauser");

        Log.i("checkSharedPreferences ", "1");

        // storageReference
        storageReference = FirebaseStorage.getInstance().getReference().child(""+adminName+adminPhone+"doc").child(""+nameHere+phoneHere+"image");
        //FirebaseStorage.getInstance().getReference("" + adminName + adminPhone+"doc").child("" + userPhone + userName +"image")

//        textViewName.setText(nameHere);
//        textViewPhone.setText(phoneHere);

        timer = new Timer();
        timer2 = new Timer();


        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //every 1.5s check if data loaded,

                if(autoNext&& countFlow==1) {

                    Intent intent3 = new Intent(Setup_Pin_Activity.this,FingerPrint_LogIn_Final_Activity.class);

                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // clear ac
                    startActivity(intent3);

                    //we can still go back? after

                   //intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent3.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|intent.TASK);
                    //finish();

                    timer2.cancel();
                }
            }
        },500,1250);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                //if document updated, get the data.
                //

                if(documentSnapshot.exists()) {
                    Map<String, Object> remap = documentSnapshot.getData();

                    if(remap!=null) {
                        for (Map.Entry<String, Object> kk: remap.entrySet()) {

                            if(kk.getKey().equals("custom_pin")){

                                //assume is created, all data.
                                countFlow++;
                                //put delay a bit. then move intent automatically
                                autoNext=true;

                            }


                        }

                    }
                }

            }
        });

        //7 june , automatic edittext move


        editText1.requestFocus();

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(editText1.getText().toString().trim().length()==1){
                    editText1.clearFocus();
                    editText2.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(editText2.getText().toString().trim().length()==1){
                    editText2.clearFocus();
                    editText3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });

        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(editText2.getText().toString().trim().length()==1){
                    editText2.clearFocus();
                    editText4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });




        button = findViewById(R.id.regUser_pinCode_buttoniD);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number1 = editText1.getText().toString();
                String number2 = editText2.getText().toString();
                String number3 = editText3.getText().toString();
                String number4 = editText4.getText().toString();

                String number = number1+number2+number3+number4+"";

                //if(editText1!=null && editText2!=null && editText3!=null && editText4!=null){

                //12 june

                if(!number.isEmpty()){    //if edittext gets the typed number

                                //then can start intent here,
                                //if(timer.)
                                timer.cancel();


//                                String number1 = editText1.getText().toString();
//                                String number2 = editText2.getText().toString();
//                                String number3 = editText3.getText().toString();
//                                String number4 = editText4.getText().toString();

//                                String number = number1+number2+number3+number4+"";

                                //need to store to firestore as well.


                            //11june need to check first, admin count.

//                    File f_MainPool = new File("/data/data/com.example.afinal/shared_prefs/com.example.finalV8_punchCard.MAIN_POOL.xml");

//                    if(f_MainPool.exists()){ //meaning, //we assume exist, else, just sent error
//
//                        // check what previous activity registered.
//
//                        SharedPreferences prefs_Main_Pool = getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
//
//                        String countAdmin = prefs_Main_Pool.getString("count_admin","");
//
//                        //11june no need check since this case, admin is user, , so no need to pull phone number.
//
//                        if(countAdmin!=null || !countAdmin.equals("")) {
//
//                            if (Integer.valueOf(countAdmin) == 1) {  //this means only 1 admin exist.
//
//
//                                String phoneAdminPulled = prefs_Main_Pool.getString("final_Admin_Phone_MainPool_1","");
//
//
//
//                                //11june, since user is admin, sharedprefs will be created will look like this.
//                                //but still count_admin, can be 1 or 2.
//
//                                //update wrong, since this page will be shared.
//
//                                File fileHere = new File("/data/data/com.example.afinal/shared_prefs/" + phoneAdminPulled + ".xml"); //sharedPrefscheck == admin phone number
//
//                                //will read sharedprefs of "com.example.finalV8_punchCard.+60184670568" ,, admin phone?
//
//                                //SharedPreferences.Editor editor = prefs.edit();
//
//                                if(fileHere.exists()){ //just double check
//
//                                    SharedPreferences sharedPrefs_1 = getSharedPreferences("com.example.finalV8_punchCard."+phoneAdminPulled, Context.MODE_PRIVATE);
//
//                                    SharedPreferences.Editor editor_admin1 = sharedPrefs_1.edit();
//
//
//                                    editor_admin1.putString("final_User_Name",nameHere);
//                                    editor_admin1.putString("final_User_Phone",phoneHere);
//                                    editor_admin1.putString("final_Admin_Name",adminName);
//                                    editor_admin1.putString("final_Admin_Phone",adminPhone);
//
//                                    editor_admin1.commit();
//
//
//
//                                }else {// error
//
//
//
//                                }
//
//
//                            }else if(Integer.valueOf(countAdmin) == 2){
//
//
//                                String phoneAdminPulled = prefs_Main_Pool.getString("final_Admin_Phone_MainPool_2","");
//
//
//                            }else { //error
//
//
//                            }
 //                       }
//
//
//
//
//
//
//                    }else{ //error, somehow sharedprefs from previous activity not created.,,
//
//
//
//                    }
//
//
//                    SharedPreferences prefs = getSharedPreferences(
//                                        "com.example.finalV8_punchCard", Context.MODE_PRIVATE);
//
//                                SharedPreferences.Editor editor = prefs.edit();
//
//                                editor.putString("final_User_Pin",number);


                            //14 june

                    SharedPreferences prefs = getSharedPreferences(
                                        "com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("my_phone_number", phoneHere);

                    editor.commit();

                    if(documentReference!=null){

                                    Map<String,Object> map = new HashMap<>();

                                    map.put("custom_pin",number);

                                    documentReference.set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnCanceledListener(new OnCanceledListener() {
                                        @Override
                                        public void onCanceled() {

                                        }
                                    });
                                }


                                Toast.makeText(Setup_Pin_Activity.this,"pin : "+number+ " , is saved", Toast.LENGTH_LONG).show();
//
//                                Intent intent2= new Intent(Setup_Pin_Activity.this, FingerPrint_LogIn_Final_Activity.class);
//                                startActivity(intent2);
//                                finish();



                            }
                else {
                    Toast.makeText(Setup_Pin_Activity.this, "please enter 4 pin prefered password number", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Log.i("checkSharedPreferences ", "1");

        Log.i("checkSharedPreferences ", ", name: "+ nameHere + ", phone: "+ phoneHere + ", name admin: "+ adminName+ ", phone admin: "+ adminPhone );

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}
