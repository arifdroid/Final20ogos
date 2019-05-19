package com.example.afinal.fingerPrint_Login.register.register_with_activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.register.register_user_activity.RegUser_Activity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_admin_);

        user_editTextName = findViewById(R.id.regFinal_EditText_UserName_iD);
        user_editTextPhone = findViewById(R.id.regFinal_EditText_User_Phone_iD);
        textViewMessage = findViewById(R.id.regAdmin_TextView_ID);


        admin_editTextName = findViewById(R.id.regFinal_EditText_Admin_Name_iD);
        admin_editTextPhone = findViewById(R.id.regFinal_EditText_Admin_Phone_iD);



        //18 may update
        //, problem user, will enter their name first.
        register_as_user_button = findViewById(R.id.regAdmin_button_user_id); //this want to register as admin, so can proceed to next activity.


        //this can move forward if input valid. go next activity
        register_as_admin_button = findViewById(R.id.regAdmin_button_admin_id);

        //button for checking only, one button

        check_admin_database_for_user_registering_button = findViewById(R.id.regAdmin_Check_ID);





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


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deleteObserver(this);
    }

    @Override
    public void onClick(View v) {



        switch (v.getId()){

            case R.id.regAdmin_button_user_id:


                statusnow = "wait..";
                textViewMessage.setText(statusnow);
                String userName = user_editTextName.getText().toString();
                String userPhone = user_editTextPhone.getText().toString();

                checkValid = presenter.checkInputValid(userName,userPhone);

                if(checkValid){ //here we call

                    //updated 18 may


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

                String adminName = user_editTextName.getText().toString();
                String adminPhone = user_editTextPhone.getText().toString();

                checkValid = presenter.checkInputValid(adminName,adminPhone);

                if(checkValid){ //here we call

                    //updated 18 may

                    //this will be done, after received new input.

            globalAdminName = adminName;
            globalAdminPhone =adminPhone;
          boolean finalStatus = presenter.checkFromFirebaseSimulation(adminName,adminPhone);
//
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

            if(checkHere==true){



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
