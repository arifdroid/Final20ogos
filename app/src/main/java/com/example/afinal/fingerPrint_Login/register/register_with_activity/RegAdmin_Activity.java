package com.example.afinal.fingerPrint_Login.register.register_with_activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.register.register_as_admin.register_as_admin_regAdmin.RegAdmin_AsAdmin_Activity;
import com.example.afinal.fingerPrint_Login.register.register_user_activity.RegUser_Activity;
import com.hanks.htextview.evaporate.EvaporateTextView;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_admin_);

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
        super.onDestroy();
        presenter.deleteObserver(this);
    }

    @Override
    public void onClick(View v) {



        switch (v.getId()){


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

                Log.i("22MayTest, ","3 name user: "+userNameIsAdmin + " phone user: "+userPhoneIsAdmin);

                checkValid = presenter.checkInputValid(userNameIsAdmin,userPhoneIsAdmin);
//
                if(checkValid){ //here we call

                    //updated 18 may

                    Log.i("22MayTest ","4 , check is valid");

                    userPhoneIsAdmin = RegAdmin_Presenter.phoneFinal; //finalise number

                    Log.i("22MayTest ","5 , normalized num "+ userNameIsAdmin);

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

//                    check_admin_database_for_user_registering_button.setVisibility(View.VISIBLE);

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

                    Log.i("22MayTest ","999 , not valid "+userPhoneIsAdmin);


                    return;
                }


                break;




            case R.id.regAdmin_button_user_id:

                textViewMessage_LogIn.setVisibility(View.INVISIBLE);


                statusnow = "wait..";
                textViewMessage.setText(statusnow);

                //fetch and save user name and phone

                String userName = user_editTextName.getText().toString();
                String userPhone = user_editTextPhone.getText().toString();

                checkValid = presenter.checkInputValid(userName,userPhone);

                if(checkValid){ //here we call

                    //updated 18 may

                    userPhone = RegAdmin_Presenter.phoneFinal;

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

                    adminPhone = RegAdmin_Presenter.phoneFinal;

                    globalAdminName = adminName;
                    globalAdminPhone =adminPhone;
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
