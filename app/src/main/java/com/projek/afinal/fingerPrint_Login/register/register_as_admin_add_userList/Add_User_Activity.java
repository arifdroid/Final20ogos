package com.projek.afinal.fingerPrint_Login.register.register_as_admin_add_userList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.projek.afinal.R;
import com.projek.afinal.fingerPrint_Login.register.setup_pin_code.Setup_Pin_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Add_User_Activity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton floatingActionButton;

    private RecyclerView recyclerView;

    private ArrayList<UserFromAdmin> userList;

    // 6 june add,

    private ArrayList<String> userListNumberOnly;

    //private ArrayList<UserFromAdmin> u

    private RecyclerViewAdapter_UserList recyclerViewAdapter_UserList;
    private Timer timer;
    private FloatingActionButton buttonNext;
    private String user_name_asAdmin;
    private String user_phone_asAdmin;
    private Map<String, Object> addUserMap;

    //28 may

    private TextView adduserText;

    private FloatingActionButton buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__user_);

        adduserText = findViewById(R.id.textview_adduser_id);

        userListNumberOnly = new ArrayList<>();

        buttonAdd = findViewById(R.id.add_user_buttoniD);

        buttonNext = findViewById(R.id.add_User_FloatButtoniD);
        //floatingActionButton = findViewById(R.id.add_User_FloatButtoniD);
        recyclerView = findViewById(R.id.add_User_RecycleriD);

        buttonAdd.setOnClickListener(this);

        // 21 May
        addUserMap = new HashMap<>();

        Intent intent = getIntent();

        user_name_asAdmin = intent.getStringExtra("adminName_asAdmin");
        user_phone_asAdmin = intent.getStringExtra("adminPhone_asAdmin");

        Log.i("checkADDtak", "0, name:"+user_name_asAdmin+" , number:"+user_phone_asAdmin);

        initRecycler();

       // recyclerView.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        ObjectAnimator animator = ObjectAnimator.ofFloat(buttonAdd,"translationY",-250f);
                        buttonAdd.animate()
                                .alpha(1f)
                                .setDuration(200)
                                .setListener(null);
                        animator.setDuration(200);
                        animator.start();

                        ObjectAnimator animator_text = ObjectAnimator.ofFloat(adduserText, "translationY", -250f);

                        adduserText.animate()
                                .alpha(1f)
                                .setDuration(200)
                                .setListener(null);
                        animator_text.setDuration(200);
                        animator_text.start();

                        Animation fadeIn = AnimationUtils.loadAnimation(Add_User_Activity.this,R.anim.fadein);
                        Animation fadeInText = AnimationUtils.loadAnimation(Add_User_Activity.this,R.anim.fadein);
                        adduserText.startAnimation(fadeInText);
                        buttonAdd.startAnimation(fadeIn);


                        timer.cancel();


                    }

                });



            }
        },1500,10);

    }

    private void initRecycler() {

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    userList = new ArrayList<>();

    //userList.add(new UserFromAdmin("click here to add",""));

    Log.i("checkAddingUser, ","1 ");



    recyclerViewAdapter_UserList = new RecyclerViewAdapter_UserList(this, userList);

        Log.i("checkAddingUser, ","3 ");
    recyclerView.setAdapter(recyclerViewAdapter_UserList);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add_User_FloatButtoniD:

                //save to online database

                Log.i("checkADDtak", "1, name:"+user_name_asAdmin+" , number:"+user_phone_asAdmin);

                Toast.makeText(Add_User_Activity.this,"check ADD", Toast.LENGTH_SHORT).show();

                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("all_admins_collections")
                        .document(user_name_asAdmin+user_phone_asAdmin+"doc"); //check either doc or oollection


//
//                Intent intentSaved = new Intent(Add_User_Activity.this, Setup_Pin_Activity.class);
//
//                                    intentSaved.putExtra("sentAdminName", user_name_asAdmin);
//                                    intentSaved.putExtra("sentAdminPhone", user_phone_asAdmin);
//                                    intentSaved.putExtra("checkadminOrUser","admin");
//
//                                    startActivity(intentSaved);

                //documentReference.set(addUserMap)

                //documentReference.set()

                //addUserMap.put("makan", "dah makan");

               // Arrays s = userListNumberOnly.toArray().;

                String[] item = userListNumberOnly.toArray(new String[userListNumberOnly.size()]);

                addUserMap.put("employee_this_admin", Arrays.asList(item));
//
                documentReference.set(addUserMap, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("checkADDtak", "2");
                        if(task.isSuccessful()){



                                    Log.i("checkADDtak", "3");

                            Toast.makeText(Add_User_Activity.this,"users array added SUCCESS "+ userList.size()  , Toast.LENGTH_SHORT).show();


                                    Intent intentSaved = new Intent(Add_User_Activity.this, Setup_Pin_Activity.class);

                                    intentSaved.putExtra("sentAdminName", user_name_asAdmin);
                                    intentSaved.putExtra("sentAdminPhone", user_phone_asAdmin);
                                    intentSaved.putExtra("checkadminOrUser","admin");

                                    startActivity(intentSaved);



                        }else {

                            Toast.makeText(Add_User_Activity.this,"users contact not added, size user list: "+ userList.size()  , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //this is we set the array





            break;

            case R.id.add_user_buttoniD:



                Log.i("checkAddingUser, ", "4 button click ");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);

            break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==1){

                Log.i("checkAddingUser, ","5 request code");

                if(resultCode==RESULT_OK){

                    Log.i("checkAddingUser, ","5 result code");

                    Uri contactData = data.getData();

                    Cursor cursor = managedQuery(contactData,null,null,null,null);

                    cursor.moveToFirst();

                    Toast.makeText(this,"pick contact you wish to add to your user list",Toast.LENGTH_LONG).show();

                    String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY));

                    if(name.length()>=12){

                        name = name.substring(0,11);
                    }

//                    if(number.contains(" ")){
//
//                        number = number.replace(" ","");
//                    }
//
//                    if(number.contains("-")){
//                        number = number.replace("-","");
//                    }
//
//                    if(!(number.indexOf(0)=='+')){
//
//                        if(!(number.indexOf(1)=='6')){
//
//                            number = "+6"+number;
//                        }
//
//                        if((number.indexOf(1)=='6')) {
//
//                            number = "+"+number;
//                        }
//                    }


                    ///////////// >>>>>>>>>>>>>

                            String numberR = number;

                            String numberFinal ="";
//
//                            if(i==0){
//
//                                numberR = numberOne;
//                            }
//
//
//                            if(i==1){
//
//                                numberR = numberTwo;
//                            }
//
//
//                            if(i==2){
//
//                                numberR = numberThree;
//                                }
//
//
//                            if(i==3){
//
//                                numberR = numberFour;
//                            }
//
//
//                            if(i==4){
//
//                                numberR = numberFive;
//                            }
//



                            if(numberR.contains(" ")){

                                numberR = numberR.replace(" ","");


                            }


                            if(numberR.contains("-")){

                                numberR = numberR.replace("-","");
                            }

                            numberFinal = numberR;

            //                System.out.println("numberFinal i>"+i+" :"+ numberFinal);

                            //6018

                            if((numberFinal.charAt(0))=='0'){

                                String subCheck = numberFinal.substring(0,2);

                                if(subCheck.equals("01")){

                                    numberFinal = "+6"+numberFinal;
                                }

                                else {
                                    numberFinal = "error";
                                }

                            }

                            else if((numberFinal.charAt(0))=='6'){

                                String subCheck = numberFinal.substring(0,3);

                                if(subCheck.equals("601")){

                                    numberFinal = "+"+numberFinal;
                                }else {
                                    numberFinal = "error";
                                }

                            }

                            //finalize

                                //+6018
                                String subCheck = numberFinal.substring(0,4);

                                if(subCheck.equals("+601")){

                                    //userList.add(name,numberFinal);

                                    userList.add(new UserFromAdmin(name,numberFinal));
                                    userListNumberOnly.add(numberFinal);

                                    recyclerViewAdapter_UserList.notifyDataSetChanged();
                                }else {


                                    //toast error message

                                    Toast.makeText(Add_User_Activity.this,"number not added, due to error in format (+601)", Toast.LENGTH_SHORT).show();
                                }




                }





                    ////////////>>>>>>>>>>

                    //one more case, first letter is '+', but not followed by 6

//                    else { //first letter is '+'
//
//                        if(!(number.indexOf(1)=='6')){
//
//                            number = "6"+number;
//                        }else{ //2nd letter is + but
//
//
//
//                        }
//
//                    }

                //    Log.i("checkAddingUser, ","6 request code, name:" + name+" , phone:"+number);

//                    if(!number.equals("")||number!=null) {
//
//                        Log.i("checkAddingUser, ","7 request code");
//
//                        userList.add(new UserFromAdmin(name,number));
//
//                        userListNumberOnly.add(number);
//
//                        Log.i("checkADDtak ", ""+userListNumberOnly.size());
//
//                        //21May
//                      //  addUserMap.put("employee_this_admin",userList.get(userList.size()).getPhone()); // need to check
//
//
//
//
//
//
//                        recyclerViewAdapter_UserList.notifyDataSetChanged();
//                        Log.i("checkAddingUser, ","8 before setadpater again");
//
//
//                       // recyclerView.setAdapter(recyclerViewAdapter_UserList);
//
//                        Log.i("checkAddingUser, ","9 before setadpater again");
//
//                    }

                }

            }

    }
