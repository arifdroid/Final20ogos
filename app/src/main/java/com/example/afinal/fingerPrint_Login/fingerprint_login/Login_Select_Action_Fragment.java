package com.example.afinal.fingerPrint_Login.fingerprint_login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.afinal.fingerPrint_Login.TestActivity;
import com.example.afinal.fingerPrint_Login.register.register_as_admin.register_as_admin_regAdmin.RegAdmin_AsAdmin_Activity;
import com.example.afinal.fingerPrint_Login.register.register_as_admin_add_userList.Add_User_Activity;
import com.example.afinal.fingerPrint_Login.register.register_as_admin_setupProfile.RegAdmin_asAdmin_Profile_Activity;
import com.example.afinal.fingerPrint_Login.register.setup_pin_code.Setup_Pin_Activity;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.register.register_with_activity.RegAdmin_Activity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class Login_Select_Action_Fragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    private FloatingActionButton floatButton_Admin_1,floatButton_Admin_2, floatButton_Reg_Admin, floatButton_Reg_User,
                                floatButton_Note_MC, floatButton_Back;

    private TextView textViewAdmin_1, textViewAdmin_2, textView_RegUser, textView_RegAdmin, textView_Note;

    private FingerPrintFinal_Presenter presenter;
    private String nameHere;
    private String phoneHere;
    private String adminName;
    private String adminPhone;
    private String nameHere_2;
    private String phoneHere_2;
    private String adminName_2;
    private String adminPhone_2;

    //25 may memory leak

    private boolean nameHere_boolean;


    //14 june
    //make zero dependency on sharedprefs. go full online

    //problem is we need the user phone number ,, but we need to enter phone number each time,
    //so need to atleast store this user phone number , if empty, ask user to enter pin and phone number to retrieve
    //from log in phase.

    private CollectionReference cR_topUserCollection;

    private DocumentReference dR_topUserCollection;
    private String myphone_extracted;


    /// september 2019

    //this is data from current user.
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;

    private String userSSID;
    private String userBSSID;

    //boom menu test

//    private BoomMenuButton boomMenuButton;
//    private Timer timer;
//    private int countHere;
//    private boolean registerUserBoolean;
//    private boolean registerAdminBoolean;
//    private boolean popAdmin_1;
//    private boolean popAdmin_2;

    //15june we could set timer for loading top user collection, if after 10 seconds still not data pulled.
    //tell user to register if not registered yet.
    //if registered before please send report to developer.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //        return super.onCreateView(inflater, container, savedInstanceState);

        //14 june

        //initialize string to empty, or "", , for detail

        nameHere = "";
        nameHere_2="";
        adminName="";
        adminName_2="";
        phoneHere="";
        phoneHere_2="";
        adminPhone="";
        adminPhone_2="";
        myphone_extracted="";

        //for cases like, the document is not existed, this details shall remain "";


        View rootView = inflater.inflate(R.layout.floatingbutton_fragment_select, container,false);
        mContext = container.getContext();

        cR_topUserCollection = FirebaseFirestore.getInstance().collection("users_top_detail");

        nameHere_boolean = false;

        Log.i("checkNewNow 1", "fragment");
//
//        countHere =0;
//        boomMenuButton = rootView.findViewById(R.id.boomMenuiD);
//        assert  boomMenuButton!=null;
//        boomMenuButton.setButtonEnum(ButtonEnum.TextOutsideCircle);
//        boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_5_3);
//        boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_5_3);

        //test boom menu
//
//        registerUserBoolean =false;
//        registerAdminBoolean = false;
//
//        popAdmin_1 =false;
//        popAdmin_2 = false;
//
//
//
        floatButton_Admin_1 = rootView.findViewById(R.id.finall_fb_one_id);
        floatButton_Admin_2 = rootView.findViewById(R.id.final_fb_admin2);
        floatButton_Reg_User = rootView.findViewById(R.id.final_fb_register_id);
        //floatButton_Reg_Admin = rootView.findViewById(R.id.select_fragment_FloatButton_RegisterAdminiD);
        floatButton_Note_MC = rootView.findViewById(R.id.final_fb_leave_id);
        floatButton_Back = rootView.findViewById(R.id.final_fb_backbutton_id);

        textView_Note = rootView.findViewById(R.id.final_textView_fb_leave_id);
        textViewAdmin_1 = rootView.findViewById(R.id.final_textView_fb_admin1_id);
        textViewAdmin_2 = rootView.findViewById(R.id.final_textView_fb_admin2_id);
        textView_RegUser = rootView.findViewById(R.id.final_textView_fb_register_id);


//        check supplicant state first, while for api 28, need to get permission first,
//        then need to check how to trigger update without location enable


        wifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //this is getting the previous data. i suspect
        wifiInfo = wifiManager.getConnectionInfo();

        userSSID = wifiInfo.getSSID();
        Log.i("SSID_ni 1", "ssid : "+ userSSID);
        userSSID = userSSID.replace("\"","");
        userBSSID = wifiInfo.getBSSID();

//        textView_RegAdmin = rootView.findViewById(R.id.select_fragment_textView_regAdmin_1id);

        //we pull from shared preferences here once
        //then set text, this is the part, how we know to which admin do we pull from,
//
//        SharedPreferences prefs = getActivity().getSharedPreferences("com.example.finalV8_punchCard", Context.MODE_PRIVATE);
//
//        //SharedPreferences prefs = (SharedPreferences) getActivity().getPreferences("com.example.finalV8_punchCard", Context.MODE_PRIVATE);
//
//        nameHere = prefs.getString("final_User_Name","");
//        phoneHere = prefs.getString("final_User_Phone","");
//        adminName = prefs.getString("final_Admin_Name","");
//        adminPhone = prefs.getString("final_Admin_Phone","");
//
//        Log.i("finalSharePreDataCheck","Login_Select_Fragment 3,name: "+ nameHere+ ", phone: "+phoneHere + ", adminName:"
//                +adminName+" , adminPhone: "+adminPhone);

        //REAL PULL HERE., we dont add anything, we just check

        //check if exist first,


        //this file name Main_Pool

        //this is needed so that we can extract admin count,

        File f = new File("/data/data/com.example.afinal/shared_prefs/com.example.finalV8_punchCard.MAIN_POOL.xml");

        //this // note that after running these lines, we will saved all ndetails, hence there will never "", unless we kill the app, and
        //run again

        if(f.exists()){

            SharedPreferences prefs_Main_Pool = Objects.requireNonNull(getActivity()).getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);

            myphone_extracted = prefs_Main_Pool.getString("my_phone_number","");

            //problem is user might still extracting after button is pressed, so need to check.

            Log.i("checkNewNow 2", "file exist : "+myphone_extracted);

            if(!myphone_extracted.equals("") && myphone_extracted!=null&& !myphone_extracted.isEmpty()) {

                Log.i("checkNewNow 3", "file exist : "+myphone_extracted);

                dR_topUserCollection = cR_topUserCollection.document(myphone_extracted + "imauser");

                dR_topUserCollection.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.getResult().exists()){

                        if (task.isSuccessful()) {

//                            if(task.getResult().exists()){
//
//                            }

                            Map<String, Object> remap = Objects.requireNonNull(task.getResult()).getData();

                            for (Map.Entry<String, Object> mapHere : remap.entrySet()) {

                                //admin name, and admin phone. , relative user name, user phone.
                                //admin count,

                                //this is not needed since we have it in sharedprefs

                                if (mapHere.getKey().equals("user_name_1")) {
                                    nameHere = mapHere.getValue().toString();

                                    //handle null, or not registered, or wrong data input

                                    if (nameHere.isEmpty() || nameHere == null) {

                                        nameHere = "";
                                    }
                                }

//
//                                if (mapHere.getKey().equals("user_name_2")) {
//                                    nameHere_2 = mapHere.getValue().toString();
//
//
//                                    if (nameHere_2.isEmpty() || nameHere_2 == null) {
//
//                                        nameHere_2 = "";
//                                    }
//                                }


                                if (mapHere.getKey().equals("phone")) {
                                    phoneHere = mapHere.getValue().toString();


                                    if (phoneHere.isEmpty() || phoneHere == null) {

                                        phoneHere = "";
                                    }
                                }
//
//                                if(mapHere.getKey().equals("admin_count")){
//                                    admin_count = mapHere.getValue().toString();
//                                }
//
                                if (mapHere.getKey().equals("admin_name_1")) {
                                    adminName = mapHere.getValue().toString();


                                    if (adminName.isEmpty() || adminName == null) {

                                        adminName = "";
                                    }


                                }


//                                if (mapHere.getKey().equals("admin_name_2")) {
//                                    adminName_2 = mapHere.getValue().toString();
//
//
//                                    if (adminName_2.isEmpty() || adminName_2 == null) {
//
//                                        adminName_2 = "";
//                                    }
//                                }


                                if (mapHere.getKey().equals("admin_phone_1")) {
                                    adminPhone = mapHere.getValue().toString();


                                    if (adminPhone.isEmpty() || adminPhone == null) {

                                        adminPhone = "";
                                    }

                                }


//                                if (mapHere.getKey().equals("admin_phone_2")) {
//                                    adminPhone_2 = mapHere.getValue().toString();
//
//
//                                    if (adminPhone_2.isEmpty() || adminPhone_2 == null) {
//
//                                        adminPhone_2 = "";
//                                    }
//                                }

                            }

                            nameHere_boolean = true;

                            Toast.makeText(getContext(), "Success getting admin detail", Toast.LENGTH_SHORT).show();


                        } else {

                            nameHere_boolean = false;


                            Toast.makeText(getContext(), "Fail getting admin detail", Toast.LENGTH_SHORT).show();

                        }

                    }

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                        Toast.makeText(getContext(), "Fail getting admin detail", Toast.LENGTH_SHORT).show();

                    }
                });



            }



        }else { //this means it is either uninstalled after registration or never registered.

            //18 june, just to hack to check

            Log.i("checkNewNow 3", "file NOT exist " );

            Toast.makeText(getContext(),"succes HACK ", Toast.LENGTH_SHORT).show();


            //myphone_extracted = "+60184670568";



//
//            if(!myphone_extracted.equals("") && myphone_extracted!=null&& !myphone_extracted.isEmpty()) {
//
//
//                dR_topUserCollection = cR_topUserCollection.document(myphone_extracted + "imauser");
//
//                dR_topUserCollection.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                        if(task.getResult().exists()){
//
//                            if (task.isSuccessful()) {
//
////                            if(task.getResult().exists()){
////
////                            }
//
//                                Map<String, Object> remap = Objects.requireNonNull(task.getResult()).getData();
//
//                                for (Map.Entry<String, Object> mapHere : remap.entrySet()) {
//
//                                    //admin name, and admin phone. , relative user name, user phone.
//                                    //admin count,
//
//                                    //this is not needed since we have it in sharedprefs
//
//                                    if (mapHere.getKey().equals("user_name_1")) {
//                                        nameHere = mapHere.getValue().toString();
//
//                                        //handle null, or not registered, or wrong data input
//
//                                        if (nameHere.isEmpty() || nameHere == null) {
//
//                                            nameHere = "";
//                                        }
//                                    }
//
//
//                                    if (mapHere.getKey().equals("user_name_2")) {
//                                        nameHere_2 = mapHere.getValue().toString();
//
//
//                                        if (nameHere_2.isEmpty() || nameHere_2 == null) {
//
//                                            nameHere_2 = "";
//                                        }
//                                    }
//
//
//                                    if (mapHere.getKey().equals("phone")) {
//                                        phoneHere = mapHere.getValue().toString();
//
//
//                                        if (phoneHere.isEmpty() || phoneHere == null) {
//
//                                            phoneHere = "";
//                                        }
//                                    }
////
////                                if(mapHere.getKey().equals("admin_count")){
////                                    admin_count = mapHere.getValue().toString();
////                                }
////
//                                    if (mapHere.getKey().equals("admin_name_1")) {
//                                        adminName = mapHere.getValue().toString();
//
//
//                                        if (adminName.isEmpty() || adminName == null) {
//
//                                            adminName = "";
//                                        }
//
//
//                                    }
//
//
//                                    if (mapHere.getKey().equals("admin_name_2")) {
//                                        adminName_2 = mapHere.getValue().toString();
//
//
//                                        if (adminName_2.isEmpty() || adminName_2 == null) {
//
//                                            adminName_2 = "";
//                                        }
//                                    }
//
//
//                                    if (mapHere.getKey().equals("admin_phone_1")) {
//                                        adminPhone = mapHere.getValue().toString();
//
//
//                                        if (adminPhone.isEmpty() || adminPhone == null) {
//
//                                            adminPhone = "";
//                                        }
//
//                                    }
//
//
//                                    if (mapHere.getKey().equals("admin_phone_2")) {
//                                        adminPhone_2 = mapHere.getValue().toString();
//
//
//                                        if (adminPhone_2.isEmpty() || adminPhone_2 == null) {
//
//                                            adminPhone_2 = "";
//                                        }
//                                    }
//
//                                }
//
//                                nameHere_boolean = true;
//
//                                Toast.makeText(getContext(), "Success getting admin detail HACK", Toast.LENGTH_SHORT).show();
//
//
//                            } else {
//
//                                nameHere_boolean = false;
//
//
//                                Toast.makeText(getContext(), "Fail getting admin detail HACK", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        }
//
//                    }
//                }).addOnCanceledListener(new OnCanceledListener() {
//                    @Override
//                    public void onCanceled() {
//
//                        Toast.makeText(getContext(), "Fail getting admin detail HACK", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//
//
//            }




        }





        floatButton_Admin_1.setOnClickListener(this);
        floatButton_Admin_2.setOnClickListener(this);
        floatButton_Reg_User.setOnClickListener(this);
//        floatButton_Reg_Admin.setOnClickListener(this);
        floatButton_Back.setOnClickListener(this);
        floatButton_Note_MC.setOnClickListener(this);




        return rootView;
    }

    // HANDLE RESOURCES

    public static int getResId(String variableName, Class<?> с) {

        Field field = null;
        int resId = 0;
        try {
            field = с.getField(variableName);
            try {
                resId = field.getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        ((FingerPrint_LogIn_Final_Activity)getActivity()).backColor.setAlpha(1f);

        //data we want to sent back


    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.final_fb_backbutton_id:


                getFragmentManager().popBackStack();

                break;

            case R.id.finall_fb_one_id:

                //to disable register user, save data to shared preferences,
                //then pull data, if data exist, dont allow for register, show toast
//                String test = nameHere;
                if (nameHere_boolean) {

                    if (!nameHere.equals("") && !nameHere.isEmpty() && nameHere != null) {


                        FingerPrint_LogIn_Final_Activity.booleanResultExtracted = true;

                        FingerPrint_LogIn_Final_Activity.timeFragmentBoolean = true;


                        ((FingerPrint_LogIn_Final_Activity) Objects.requireNonNull(getActivity())).nameUser = nameHere; //
                        ((FingerPrint_LogIn_Final_Activity) getActivity()).phoneUser = phoneHere; //
                        ((FingerPrint_LogIn_Final_Activity) getActivity()).globalAdminNameHere = adminName; //
                        ((FingerPrint_LogIn_Final_Activity) getActivity()).globalAdminPhoneHere = adminPhone; //

                        Toast.makeText(getContext(), "admin connected 1) [ " + nameHere + " ]", Toast.LENGTH_SHORT).show();

                        Log.i("checkFinal_18June ", "Login_Select_Fragment , before return,name: "
                                + nameHere + ", phone: " + phoneHere + ", adminName:"
                                + adminName + " , adminPhone: " + adminPhone);

                        Objects.requireNonNull(getFragmentManager()).popBackStack();

                    } else {
                        Log.i("checkFinal_18June", "admin 1 problem popstack");
                    }
                }

                //25 May

                else {


                    Toast.makeText(getContext(), "please ensure you are connected to internet", Toast.LENGTH_SHORT).show();


                }
//
//                //this is we setup shared prefe
                //  getFragmentManager().popBackStack();

                break;
            case R.id.final_fb_admin2:

//                if (nameHere_boolean) { //we dont even need this, if check for zero or not.
//
//                    if (!nameHere_2.equals("") && !nameHere_2.isEmpty() && nameHere_2 != null) {
//
//
//                        FingerPrint_LogIn_Final_Activity.timeFragmentBoolean = true;
//                        ((FingerPrint_LogIn_Final_Activity) Objects.requireNonNull(getActivity())).nameUser = nameHere_2; //
//                        ((FingerPrint_LogIn_Final_Activity) getActivity()).phoneUser = phoneHere_2; //
//                        ((FingerPrint_LogIn_Final_Activity) getActivity()).globalAdminNameHere = adminName_2; //
//                        ((FingerPrint_LogIn_Final_Activity) getActivity()).globalAdminPhoneHere = adminPhone_2; //
//
//                        Toast.makeText(getContext(), "admin connected 2) [ " + nameHere_2 + " ]", Toast.LENGTH_SHORT).show();
//
//                        Log.i("checkFinal_18June ", "Login_Select_Fragment , before return,name: "
//                                + nameHere_2 + ", phone: " + phoneHere_2 + ", adminName:"
//                                + adminName_2 + " , adminPhone: " + adminPhone_2);
//
//                        Objects.requireNonNull(getFragmentManager()).popBackStack();
//
//                    }   else {
//                        Log.i("checkFinal_18June", "admin 2 problem popstack");
//                    }
//
//                } else {

                    Toast.makeText(getContext(), "feature not released yet", Toast.LENGTH_SHORT).show();


             //   }



                //getFragmentManager().popBackStack();



                break;

            case R.id.final_fb_register_id:

                // september update, ensure wifi admin is connected.


                //ensure location is connected

                if(userSSID==""||userSSID==null||userSSID.equals("<unknown ssid>")){

                    Log.i("SSID_ni 2", "ssid : "+ userSSID);

                    Toast.makeText(getContext(), "please connect to admin wifi first", Toast.LENGTH_LONG).show();

                }else {


                    if (nameHere_boolean) {

                        Toast.makeText(getContext(), "already registered", Toast.LENGTH_SHORT).show();


                    } else {



                    Intent intent = new Intent(getActivity(), RegAdmin_Activity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //creating new task for registering,
                    //but we dont want user to be able to back.
                    startActivity(intent);

                      }
                }




                break;
//
//            case R.id.select_fragment_FloatButton_RegisterAdminiD:
//
//            Intent intent2 = new Intent(getActivity(), RegAdmin_AsAdmin_Activity.class);
//            startActivity(intent2);
//            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //creating new task for registering,
//                //but we dont want user to be able to back.
//
//
//                break;

            case R.id.final_fb_leave_id:

                Toast.makeText(getContext(), "feature not released yet", Toast.LENGTH_SHORT).show();



//                Intent intent1 = new Intent(getActivity(), Setup_Pin_Activity.class);
//
//                intent1.putExtra("sentAdminName","arifhaniftest");
//
//                intent1.putExtra("sentAdminPhone","+60184670568");
////
////                intentSaved.putExtra("sentAdminName", user_name_asAdmin);
////                intentSaved.putExtra("sentAdminPhone", user_phone_asAdmin);
//                intent1.putExtra("checkadminOrUser","admin");
//
////                startActivity(intentSaved);
//
//
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                startActivity(intent1);

                break;





        }


    }




}
