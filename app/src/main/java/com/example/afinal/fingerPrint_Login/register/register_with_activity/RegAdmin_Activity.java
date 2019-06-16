package com.example.afinal.fingerPrint_Login.register.register_with_activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.TestActivity;
import com.example.afinal.fingerPrint_Login.fingerprint_login.Login_Select_Action_Fragment;
import com.example.afinal.fingerPrint_Login.register.register_as_admin.register_as_admin_regAdmin.RegAdmin_AsAdmin_Activity;
import com.example.afinal.fingerPrint_Login.register.register_user_activity.RegUser_Activity;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hanks.htextview.evaporate.EvaporateTextView;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

// 15 june,
//this page must handle if phone number entered is not valid,
//must follow +601* **** , without space, or dash '-'

//1st ask user, whether they want to register as user, or admin


public class RegAdmin_Activity extends AppCompatActivity implements View.OnClickListener, RegAdminViewInterface, Observer {

    //18 may, some functionality change, page can serve
    // to register both admin, and user.
    //originally, this is for user. so.


    private TextView textViewMessage;
    private EditText user_editTextName, user_editTextPhone;

    //18 may updated.
    private Button register_as_user_button;

    private RegAdmin_Presenter presenter;

    private String globalAdminName;
    private String globalAdminPhone;

    private boolean checkValid;
    private Timer timer;
    private String statusnow;
    private int count;
    private Button movenextbutton;

    //18 may updated

    private Button register_as_admin_button;
    private String globalUserName;
    private String globalUserPhone;
    private EditText admin_editTextName;
    private EditText admin_editTextPhone;
    private Button check_admin_database_for_user_registering_button;

    //20 may update

    private EvaporateTextView evaporateTextView;
    private TextView textViewMessage_LogIn;
    private Button button_yes;
    private Button button_no;

    //the purpose for this reg_admin, check whether
    // user want to register as another user, or as admin.

    // if select as user, proceed with current flow

    //if want to select as admin, move to next activity, as admin flow.

    //22 may

    private TextView textViewSureAsAdmin;


    //24 may

    private Button buttonTest;

    //27 may
    private Button why_buttonTest;

    //9 june

    private Button button_login_user;

    private TextView textViewMessageLogin_INFO;

    //this is to reuse button
    private int login_first;
    private String myphone_extracted;

    //14june
    private CollectionReference cR_topUserCollection;
    private DocumentReference dR_topUserCollection;
    private String pinHere;
    private String phoneHere;

    private String adminCountHere;


    //private String myphone_extracted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_admin_);

        cR_topUserCollection = FirebaseFirestore.getInstance().collection("users_top_detail");

        adminCountHere="";

        pinHere ="";

        login_first =0;

        //9 June

        textViewMessageLogin_INFO = findViewById(R.id.reg_admin_textView_infoMessageiD);

        //textViewMessageLogin_INFO.setText("please enter your name, and phone number to register");

        button_login_user = findViewById(R.id.reg_admin_log_in_buttonID);

        button_login_user.setOnClickListener(this);

        //buttonTest = findViewById(R.id.reg_admin_buttonTest);

        why_buttonTest = findViewById(R.id.why_button_test_id);

        why_buttonTest.setOnClickListener(this);

        //buttonTest.setOnClickListener(this);

//        buttonTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent intent = new Intent(RegAdmin_Activity.this, RegAdmin_AsAdmin_Activity.class);
//
//                intent.putExtra("adminName_asAdmin_2", "arifhaniftest");
//                intent.putExtra("adminPhone_asAdmin_2", "+60184670568");
//
//                startActivity(intent);
//
//            }
//        });


//        File f = new File("/data/data/com.example.afinal/shared_prefs/com.example.finalV8_punchCard.MAIN_POOL.xml");
//
//        if(f.exists()){
//
//            SharedPreferences prefs_Main_Pool = this.getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);
//
//            myphone_extracted = prefs_Main_Pool.getString("my_phone_number","");
//
//            //problem is user might still extracting after button is pressed, so need to check.
//
//            if(!myphone_extracted.equals("") && myphone_extracted!=null&& !myphone_extracted.isEmpty()){
//
//
//                dR_topUserCollection = cR_topUserCollection.document(myphone_extracted+"imauser");
//
//                dR_topUserCollection.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                        if(task.isSuccessful()){
//
//
//                            Map<String, Object> remap = Objects.requireNonNull(task.getResult()).getData();
//
//                            for(Map.Entry<String, Object> mapHere : remap.entrySet()){
//
//                                //admin name, and admin phone. , relative user name, user phone.
//                                //admin count,
//
//                                //this is not needed since we have it in sharedprefs
//
//                                if(mapHere.getKey().equals("user_name_1")){
//                                    nameHere = mapHere.getValue().toString();
//
//                                    //handle null, or not registered, or wrong data input
//
//                                    if(nameHere.isEmpty()|| nameHere==null ){
//
//                                        nameHere = "";
//                                    }
//                                }
//
//
//                                if(mapHere.getKey().equals("user_name_2")){
//                                    nameHere_2 = mapHere.getValue().toString();
//
//
//                                    if(nameHere_2.isEmpty()|| nameHere_2==null ){
//
//                                        nameHere_2 = "";
//                                    }
//                                }
//
//
//                                if(mapHere.getKey().equals("phone")){
//                                    phoneHere = mapHere.getValue().toString();
//
//
//                                    if(phoneHere.isEmpty()|| phoneHere==null ){
//
//                                        phoneHere = "";
//                                    }
//                                }
////
////                                if(mapHere.getKey().equals("admin_count")){
////                                    admin_count = mapHere.getValue().toString();
////                                }
////
//                                if(mapHere.getKey().equals("admin_name_1")){
//                                    adminName= mapHere.getValue().toString();
//
//
//                                    if(adminName.isEmpty()|| adminName==null ){
//
//                                        adminName= "";
//                                    }
//
//
//                                }
//
//
//
//
//
//                                if(mapHere.getKey().equals("admin_name_2")){
//                                    adminName_2= mapHere.getValue().toString();
//
//
//                                    if(adminName_2.isEmpty()|| adminName_2==null ){
//
//                                        adminName_2 = "";
//                                    }
//                                }
//
//
//                                if(mapHere.getKey().equals("admin_phone_1")){
//                                    adminPhone= mapHere.getValue().toString();
//
//
//                                    if(adminPhone.isEmpty()|| adminPhone==null ){
//
//                                        adminPhone = "";
//                                    }
//
//                                }
//
//
//                                if(mapHere.getKey().equals("admin_phone_2")){
//                                    adminPhone_2 = mapHere.getValue().toString();
//
//
//                                    if(adminPhone_2.isEmpty()|| adminPhone_2==null ){
//
//                                        adminPhone_2 = "";
//                                    }
//                                }
//
//                            }
//
//                            nameHere_boolean =true;
//
//                            Toast.makeText(getContext(),"Success getting admin detail", Toast.LENGTH_SHORT).show();
//
//
//                        }else{
//
//                            Toast.makeText(getContext(),"Fail getting admin detail", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//                }).addOnCanceledListener(new OnCanceledListener() {
//                    @Override
//                    public void onCanceled() {
//
//                        Toast.makeText(getContext(),"Fail getting admin detail", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//
//
//            }
//
//
//
//        }else { //this means it is either uninstalled after registration or never registered.
//
//            Toast.makeText(getContext(),"Fail getting admin detail", Toast.LENGTH_SHORT).show();
//
//
//        }

        user_editTextName = findViewById(R.id.regFinal_EditText_UserName_iD);
        user_editTextPhone = findViewById(R.id.regFinal_EditText_User_Phone_iD);

        textViewMessage = findViewById(R.id.regAdmin_TextView_ID);
        textViewMessage_LogIn = findViewById(R.id.reg_admin_textView_login_id);


        admin_editTextName = findViewById(R.id.regFinal_EditText_Admin_Name_iD);
        admin_editTextPhone = findViewById(R.id.regFinal_EditText_Admin_Phone_iD);



        Log.i("22MayTest, ","1 ");

        //18 may update
        //, problem user, will enter their name first.
        register_as_user_button = findViewById(R.id.regAdmin_button_user_id); //this want to register as admin, so can proceed to next activity.


        //this can move forward if input valid. go next activity
        register_as_admin_button = findViewById(R.id.regAdmin_button_admin_id);

        //button for checking only, one button

        check_admin_database_for_user_registering_button = findViewById(R.id.regAdmin_Check_ID);

        button_yes = findViewById(R.id.reg_admin_button_yes_id);
        button_no = findViewById(R.id.reg_admin_button_no_id);


        textViewSureAsAdmin = findViewById(R.id.reg_admin_textView_sure_as_admin_id);


        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegAdmin_Activity.this, RegAdmin_AsAdmin_Activity.class);
//                intent.putExtra("adminName_asAdmin", globalAdminName); //this just pass intent.
//                intent.putExtra("adminPhone_asAdmin", globalAdminPhone);
                //18 May , put extra intent.
                intent.putExtra("adminName_asAdmin", globalUserName);
                intent.putExtra("adminPhone_asAdmin", globalUserPhone);

                startActivity(intent);

            }
        });

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //15 june,
                globalAdminName = "";
                globalAdminPhone = "";
                globalUserName="";
                globalUserPhone="";

                //clear view

                button_yes.setVisibility(View.GONE);
                button_no.setVisibility(View.GONE);
                textViewSureAsAdmin.setText(View.GONE);



             //   evaporateTextView.setVisibility(View.VISIBLE);
                textViewMessage.setVisibility(View.VISIBLE);
                textViewMessage_LogIn.setText(View.VISIBLE);



                //set animation
                user_editTextName.setVisibility(View.VISIBLE);
                user_editTextPhone.setVisibility(View.VISIBLE);

//                    admin_editTextName.setVisibility(View.VISIBLE);
//                    admin_editTextPhone.setVisibility(View.VISIBLE);



                //then animate button. to change.
                register_as_admin_button.setVisibility(View.VISIBLE);
                register_as_user_button.setVisibility(View.VISIBLE);


               // button_no.setVisibility(View.INVISIBLE);
            }
        });


        //20 May

        evaporateTextView = findViewById(R.id.reg_admin_evaporate_registring_id);

        textViewMessage_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //9 june,

                //button user and admin, gone.

                register_as_user_button.setVisibility(View.GONE);
                register_as_admin_button.setVisibility(View.GONE);

                //admin_editTextName.setVisibility(View.GONE);
                admin_editTextName.setHint("4 pin code");


                button_login_user.setVisibility(View.VISIBLE);

                textViewMessageLogin_INFO.setText("please enter your phone number, and 4 pin log in");

                login_first =1;


            }
        });


        //15 May for testing layout

//        movenextbutton = findViewById(R.id.regAdmin_button_user_id);
//
//        movenextbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(RegAdmin_Activity.this, RegAdmin_asAdmin_Profile_Activity.class);
//                startActivity(intent);
//
//
//            }
//        });


        checkValid=false;



//        textViewMessage.setText("please enter admin name, phone");

        presenter = new RegAdmin_Presenter(this);
        presenter.addObserver(this);



        register_as_user_button.setOnClickListener(this);
        register_as_admin_button.setOnClickListener(this);


    }

    @Override
    protected void onDestroy() {

        presenter.deleteObserver(this);

        super.onDestroy();

    //    presenter.deleteObserver(this);


        Log.i("checkDestroy", "reg_admin");
    }

    @Override
    public void onClick(View v) {



        switch (v.getId()){

            //9 june

            case R.id.reg_admin_log_in_buttonID:


                final String userPinInput = user_editTextName.getText().toString();
                final String userPhoneLogin = user_editTextPhone.getText().toString();


                checkValid = presenter.checkInputValid(userPinInput, userPhoneLogin);


                //

                if(checkValid){

                dR_topUserCollection = cR_topUserCollection.document(myphone_extracted+"imauser");

                dR_topUserCollection.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){


                            Map<String, Object> remap = Objects.requireNonNull(task.getResult()).getData();

                            for(Map.Entry<String, Object> mapHere : remap.entrySet()){

                                //admin name, and admin phone. , relative user name, user phone.
                                //admin count,

                                //this is not needed since we have it in sharedprefs

                                if(mapHere.getKey().equals("custom_pin")){

                                    pinHere = mapHere.getValue().toString();
                                }

                                if(mapHere.getKey().equals("phone")){

                                    phoneHere = mapHere.getValue().toString();
                                }

//                                if(mapHere.getKey().equals("user_name_1")){
//                                    nameHere = mapHere.getValue().toString();
//
//                                    //handle null, or not registered, or wrong data input
//
//                                    if(nameHere.isEmpty()|| nameHere==null ){
//
//                                        nameHere = "";
//                                    }
//                                }
//
//
//                                if(mapHere.getKey().equals("user_name_2")){
//                                    nameHere_2 = mapHere.getValue().toString();
//
//
//                                    if(nameHere_2.isEmpty()|| nameHere_2==null ){
//
//                                        nameHere_2 = "";
//                                    }
//                                }
//
//
//                                if(mapHere.getKey().equals("phone")){
//                                    phoneHere = mapHere.getValue().toString();
//
//
//                                    if(phoneHere.isEmpty()|| phoneHere==null ){
//
//                                        phoneHere = "";
//                                    }
//                                }
////
////                                if(mapHere.getKey().equals("admin_count")){
////                                    admin_count = mapHere.getValue().toString();
////                                }
////
//                                if(mapHere.getKey().equals("admin_name_1")){
//                                    adminName= mapHere.getValue().toString();
//
//
//                                    if(adminName.isEmpty()|| adminName==null ){
//
//                                        adminName= "";
//                                    }
//
//
//                                }
//
//
//
//
//
//                                if(mapHere.getKey().equals("admin_name_2")){
//                                    adminName_2= mapHere.getValue().toString();
//
//
//                                    if(adminName_2.isEmpty()|| adminName_2==null ){
//
//                                        adminName_2 = "";
//                                    }
//                                }
//
//
//                                if(mapHere.getKey().equals("admin_phone_1")){
//                                    adminPhone= mapHere.getValue().toString();
//
//
//                                    if(adminPhone.isEmpty()|| adminPhone==null ){
//
//                                        adminPhone = "";
//                                    }
//
//                                }
//
//
//                                if(mapHere.getKey().equals("admin_phone_2")){
//                                    adminPhone_2 = mapHere.getValue().toString();
//
//
//                                    if(adminPhone_2.isEmpty()|| adminPhone_2==null ){
//
//                                        adminPhone_2 = "";
//                                    }
//                                }

                            }

                        //    nameHere_boolean =true;




                            if(pinHere.equals(userPinInput) && phoneHere.equals(userPhoneLogin)) {

                                Toast.makeText(RegAdmin_Activity.this, "Success getting admin detail", Toast.LENGTH_SHORT).show();

                                SharedPreferences prefs = getSharedPreferences(
                                        "com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = prefs.edit();

                                editor.putString("my_phone_number", userPhoneLogin);

                                editor.commit();


                                //14 june, suppose to work if app is uninstall, then reinstall, but already registered.
                                //we recover with phone number and 4 pin code registered before.

                                Intent intentHere = new Intent(RegAdmin_Activity.this, Login_Select_Action_Fragment.class);
                                intentHere.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(intentHere);





                            }else {

                                Toast.makeText(RegAdmin_Activity.this,"Wrong phone number or input pin", Toast.LENGTH_SHORT).show();

                            }


                        }else{

                            Toast.makeText(RegAdmin_Activity.this,"Fail getting admin detail", Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                        Toast.makeText(RegAdmin_Activity.this,"Fail getting admin detail", Toast.LENGTH_SHORT).show();

                    }
                });



                }



//
//
//                if(login_first==1) {
//
//                    //set reg admin to invisible. / gone
//
//                    //fetch and save user name and phone.
//
//                    String userNameLogin = user_editTextName.getText().toString();
//                    String userPhoneLogin = user_editTextPhone.getText().toString();
//
//
//                    checkValid = presenter.checkInputValid(userNameLogin, userPhoneLogin);
////
//                    if (checkValid) { //here we call
//
//                        //updated 18 may
//
//                        Log.i("22MayTest ", "4 , check is valid");
//
//                        userPhoneLogin = RegAdmin_Presenter.phoneFinal; //finalise number
//
//                        //Log.i("22MayTest ","5 , normalized num "+ userNameIsAdmin);
//
//                        globalUserName = userNameLogin;       //this will be used in next activity.
//                        globalUserPhone = userPhoneLogin;
//                        //since this person will become admin
////                    globalAdminName=userNameIsAdmin;
////                    globalAdminPhone = userPhoneIsAdmin;
//
//
//                        //
////
//                        textViewMessageLogin_INFO.setText("please enter admin name and admin phone registered to, then press log in");
//
//                        button_login_user.setText("log in");
//
//                        //set animation
//                        user_editTextName.setVisibility(View.GONE);
//                        user_editTextPhone.setVisibility(View.GONE);
//
////                    admin_editTextName.setVisibility(View.VISIBLE);
////                    admin_editTextPhone.setVisibility(View.VISIBLE);
//
//
//                        //then animate button. to change.
////
//                        //button_login_user.setVisibility(View.GONE);
//
//                        login_first=2;
//
////                    check_admin_database_for_user_registering_button.setVisibility(View.VISIBLE);
//
//                        //textViewSureAsAdmin.setVisibility(View.VISIBLE);
////
////                    button_yes.setVisibility(View.VISIBLE);
////                    button_no.setVisibility(View.VISIBLE);
//
//                        admin_editTextName.setVisibility(View.VISIBLE);
//                        admin_editTextPhone.setVisibility(View.VISIBLE);
//
//
//                        //this will be done, after received new input.
//
////            globalAdminName = userName;
////            globalAdminPhone =userPhone;
////          boolean finalStatus = presenter.checkFromFirebaseSimulation(userName,userPhone);
////
////            if(finalStatus){
////                //success
////
////               result(true);
////
////            }else {
////
////                result(false);
////            }
//
//
//                    } else {
//
//                        Log.i("22MayTest ", "999 , not valid " + userPhoneLogin);
//
//                        textViewMessageLogin_INFO.setText("please enter valid name and phone");
//
//
//
//                        return;
//                    }
//
//
//                }else if(login_first==2){
//
//
//                    String adminNameLogin = admin_editTextName.getText().toString();
//                    String adminPhoneLogin = admin_editTextPhone.getText().toString();
//
//                    checkValid = presenter.checkInputValid(adminNameLogin, adminPhoneLogin);
//
//                    if(checkValid){
//
//                        //extract admin info
//
//                        aaa
//
//
//
//
//
//
//                    }else {
//
//                        login_first=1;
//
//                        textViewMessageLogin_INFO.setText("please enter valid name and phone");
//                    }
//
//
//                }

                break;

            case R.id.why_button_test_id:

                // maybe presenter not destroyed. since activity is not destroyed, it is put in stack only.
                //28 may test.

                presenter.deleteObserver(this);

                //

                Log.i("checkFlowDestroy", "1 reg_admin");

                Intent intent = new Intent(RegAdmin_Activity.this, RegAdmin_AsAdmin_Activity.class);

                intent.putExtra("adminName_asAdmin_2", "arifzamri");
                intent.putExtra("adminPhone_asAdmin_2", "+60184670568");

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);



                break;


            case R.id.regAdmin_button_admin_id:

                Log.i("22MayTest, ","2 ");
//
//                statusnow = "wait..";
//                textViewMessage.setText(statusnow);

                textViewMessage.setVisibility(View.INVISIBLE);

                textViewMessage_LogIn.setVisibility(View.INVISIBLE);

                //fetch and save user name and phone.

                String userNameIsAdmin = user_editTextName.getText().toString();
                String userPhoneIsAdmin = user_editTextPhone.getText().toString();

                checkValid = presenter.checkInputValid(userNameIsAdmin,userPhoneIsAdmin);
//
                if(checkValid){ //here we call

                    //updated 18 may

                    //userPhoneIsAdmin = RegAdmin_Presenter.phoneFinal; //finalise number ,,

                    userPhoneIsAdmin = presenter.getPhoneFinal();

                    globalUserName= userNameIsAdmin;       //this will be used in next activity.
                    globalUserPhone = userPhoneIsAdmin;
                                                            //since this person will become admin
                    globalAdminName=userNameIsAdmin;
                    globalAdminPhone = userPhoneIsAdmin;

//

                    //set animation
                    user_editTextName.setVisibility(View.GONE);
                    user_editTextPhone.setVisibility(View.GONE);

//                    admin_editTextName.setVisibility(View.VISIBLE);
//                    admin_editTextPhone.setVisibility(View.VISIBLE);



                    //then animate button. to change.
                    register_as_admin_button.setVisibility(View.GONE);
                    register_as_user_button.setVisibility(View.GONE);

//                  check_admin_database_for_user_registering_button.setVisibility(View.VISIBLE);

                    textViewSureAsAdmin.setVisibility(View.VISIBLE);

                    button_yes.setVisibility(View.VISIBLE);
                    button_no.setVisibility(View.VISIBLE);


                    //this will be done, after received new input.

//            globalAdminName = userName;
//            globalAdminPhone =userPhone;
//          boolean finalStatus = presenter.checkFromFirebaseSimulation(userName,userPhone);
//
//            if(finalStatus){
//                //success
//
//               result(true);
//
//            }else {
//
//                result(false);
//            }


                }else {

                    return;
                }


                break;




            case R.id.regAdmin_button_user_id:

                textViewMessage_LogIn.setVisibility(View.INVISIBLE);

                textViewMessage.setVisibility(View.INVISIBLE);


                statusnow = "wait..";
                textViewMessageLogin_INFO.setText(statusnow);

                //fetch and save user name and phone

                String userName = user_editTextName.getText().toString();
                String userPhone = user_editTextPhone.getText().toString();

                checkValid = presenter.checkInputValid(userName,userPhone);

                if(checkValid){ //here we call

                    //updated 18 may

                    //userPhone = RegAdmin_Presenter.phoneFinal;

                    userPhone = presenter.getPhoneFinal();

                    globalUserName= userName;       //this will be used in next activity.
                    globalUserPhone = userPhone;

//

                    //set animation
                    user_editTextName.setVisibility(View.GONE);
                    user_editTextPhone.setVisibility(View.GONE);

                    admin_editTextName.setVisibility(View.VISIBLE);
                    admin_editTextPhone.setVisibility(View.VISIBLE);



                    //then animate button. to change.
                    register_as_admin_button.setVisibility(View.GONE);
                    register_as_user_button.setVisibility(View.GONE);

                    check_admin_database_for_user_registering_button.setVisibility(View.VISIBLE);



                    //14 june
//
//                    dR_topUserCollection = cR_topUserCollection.document(myphone_extracted+"imauser");
//
//
//                    dR_topUserCollection.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                            if(task.isSuccessful()){
//
//                                Map<String, Object> remap = Objects.requireNonNull(task.getResult()).getData();
//
//                                for(Map.Entry<String, Object> mapHere : remap.entrySet()){
//
//                                    //admin name, and admin phone. , relative user name, user phone.
//                                    //admin count,
//
//                                    //this is not needed since we have it in sharedprefs
//
//                                    if(mapHere.getKey().equals("admin_count")){
//
//                                        adminCountHere = mapHere.getValue().toString();
//                                    }
//
//
//                                }
//
//
//                                if(adminCountHere.isEmpty() || adminCountHere.equals("") || adminCountHere!=null){
//
//                                    adminCountHere="1";
//
//                                }
//
//
//
//
//                            }else {
//
//                                //try again.
//
//                            }
//
//
//                        }
//                    }).addOnCanceledListener(new OnCanceledListener() {
//                        @Override
//                        public void onCanceled() {
//
//
//
//
//
//                        }
//                    });




                    //this will be done, after received new input.

//            globalAdminName = userName;
//            globalAdminPhone =userPhone;
//          boolean finalStatus = presenter.checkFromFirebaseSimulation(userName,userPhone);
//
//            if(finalStatus){
//                //success
//
//               result(true);
//
//            }else {
//
//                result(false);
//            }


                }else {

                    return;
                }


                break;

            case R.id.regAdmin_Check_ID:

                //here we want to get admin name and phone from user input.

                String adminName = admin_editTextName.getText().toString();
                String adminPhone = admin_editTextPhone.getText().toString();

                checkValid = presenter.checkInputValid(adminName,adminPhone);

                if(checkValid){ //here we call

                    //updated 18 may

                    //this will be done, after received new input.

                    //adminPhone = RegAdmin_Presenter.phoneFinal;

                    adminPhone = presenter.getPhoneFinal();

                    globalAdminName = adminName;
                    globalAdminPhone =adminPhone;

                    //this will only check if admin exist. thats it.


                    boolean finalStatus = presenter.checkFromFirebaseSimulation(adminName,adminPhone);
//

            // 22 May, this variable will be updated, after model finish checking with database.

            if(finalStatus){
                //success

               result(true);

            }else {

                result(false);
            }


                }else {

                    return;
                }








                break;


        }




    }

//    private void onReturn() {
//        Toast.makeText(this, "please try again", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, FingerPrint_LogIn_Final_Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
//    }
//

    @Override
    public void update(Observable o, Object arg) {

        //here we set valid. back to true

        if(o instanceof RegAdmin_Presenter){

            boolean checkHere = ((RegAdmin_Presenter) o).checkFinalFromFirebase(); //maybe because we never return false.

            if(checkHere){



                textViewMessage.setText("success log in");
                result(checkHere);


            }else {
                //if not return, never update again, since,
                //return; //or we just handle here.
                //update will be called if theres an update, should always have update

                textViewMessage.setText("not success, try again");

              //  return;
                //onReturn();



            }
        }

    }

    @Override
    public void result(boolean check) {

        if(check){

            Toast.makeText(this,"success log in",Toast.LENGTH_LONG).show();

            // passing intent move to next activity if successful

            Intent intent = new Intent(RegAdmin_Activity.this, RegUser_Activity.class);





            intent.putExtra("admin_name", globalAdminName); //this just pass intent.
            intent.putExtra("admin_phone", globalAdminPhone);
            //18 May , put extra intent.

            intent.putExtra("user_here_name", globalUserName);
            intent.putExtra("user_here_phone", globalUserPhone);


            startActivity(intent);

        }
        else {

            //check if cancel after few secs

            Toast.makeText(this,"please wait",Toast.LENGTH_SHORT).show();

            //then ask try again.

        }



    }
}
