package com.example.afinal.fingerPrint_Login.fingerprint_login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable2;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.view.ViewGroup;
import android.view.animation.Animation;

//september update.




import com.example.afinal.fingerPrint_Login.oop.OnServerTime_Interface;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yy.mobile.rollingtextview.RollingTextView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

// https://stackoverflow.com/questions/8077728/how-to-prevent-the-activity-from-loading-twice-on-pressing-the-button

//introduce fingerprint id here,
//need observer to watch, if string pull from fragment(sharedpreferences) exist.
//first instantiate to null
//update text view as well.

public class FingerPrint_LogIn_Final_Activity extends AppCompatActivity implements FingerPrintFinal_View_Interface, View.OnClickListener, Observer, OnServerTime_Interface {

    //september update

    public Handler handler_counter;

    //private int iik;

    private int counterUpdateCheck;

    public Runnable runnableCode_counterFlow= new Runnable() {
        @Override
        public void run() {

            //i++;
            //textView.setText('a');
            //Log.i("checkSecond :", "i : "+i);

            counterFlowHere++;

            Log.i("counterflowhere ", "counterFlowHere : "+counterFlowHere);
            Log.i("counterflowhere2 ", "counterFlowHere2 : "+counterFlowHere2);
            Log.i("counterflowhere3 ", "counterFlowHere3 : "+counterFlowHere3);

            if(counterFlowHere==300){

                Log.i("checksampai300","sampai");

                wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                Log.i("SSID_ni_main 99 ","wifimanage : "+wifiManager);

                wifiInfo = wifiManager.getConnectionInfo();
                Log.i("SSID_ni_main 99.1 ","wifiinfo : "+wifiInfo);

                userSSID = wifiInfo.getSSID();
                Log.i("SSID_ni_main 99.2 ","ssid : "+userSSID);
            }

            handler.postDelayed(runnableCode_counterFlow,50);


        }
    };

    private Handler handler_to_count;
    //private

    public static int userCount;
    public static boolean booleanResultExtracted;

    private FloatingActionButton floatButtonGetAction;
   // private TextView textView;
    private ImageView imageView;

    private Login_Select_Action_Fragment fragment;

    private FingerPrintFinal_Presenter presenter;

    private boolean dataPulled;

    public String nameUser;
    public String phoneUser;

    ConstraintLayout backColor;



    //after log in register user data timestamp directly.

    public String globalAdminNameHere; //"ariff";
    public String globalAdminPhoneHere;//"+60190";

    private FirebaseFirestore instance = FirebaseFirestore.getInstance();

    private CollectionReference collectionReferenceRating
            = instance.collection("all_admin_doc_collections")
            .document(globalAdminNameHere + globalAdminPhoneHere + "_doc").collection("all_employee_thisAdmin_collection");


    //cloud function with time value

    private String dayNow;
    //private String dateNow;

    private long timeStampthis;

    private OnServerTime_Interface onServerTime_interface;
    private String dateAndTimeNow;

    /////////// constraint by admin

    private String bssidConstraint;
    private String locationConstraint;
    private String morningConstraint;
    private String eveningConstraint;
    private String ssidConstraint;
    private String latitudeConstraint;
    private String longitudeConstraint;
    private String streetConstraint;

    private String phoneAdminConstraint;

    //this is data from current user.
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;

    private String userSSID;
    private String userBSSID;

    private String userLatitude;
    private String userLongitude;
    private LocationManager mLocationManager;

    private String user_StreetName;

    //time here current.
    private String timeCurrent;
    private String timeCurrent2;
    private String dateCurrent;
    private boolean checkLocationProcess;
    private boolean checkAdminConstraintProcess;
    Method showsb;
    private int statusBarWeSet;
    private int counterFlowHere;
    private int counterFlowHere2;
    private int counterFlowHere3;

    //test program flow.

    //private TextView textViewFinalData;
    private int countFinalFlow;

   // private TextView textViewFinalData2;
    private int countFinalFlow2;

    //private TextView textViewDataLocation;
    private DocumentReference documentReference;
    private int countUserverified;

    //ensuring location not tampered

    private String lastLocationRecorded;
    private String dateNow;
    private boolean setEveningTimeStamp;
    private boolean setMorningTimeStamp;


    public static boolean timeFragmentBoolean;
    private String lastSSIDrecorded;

    //// navigation icon

    private BottomNavigationView bottomNavigationView;

    private CardView cardViewAdminIcon, cardViewEveningIcon;
    private ConstraintLayout constraintLayoutMorningIcon, constraintLayoutLocationIcon, constraintLayoutWifiIcon;

    private ConstraintLayout barMorning, barEvening,barAdmin, barLocation, barWifi;

    private ImageView imageViewAdminIcon, imageViewEveningIcon, imageViewMorningIcon, imageViewLocationIcon, imageViewWifiIcon;

    //bottom navigation listener

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Do something ...

            Log.i("ada_ke_permission", "request granted");
        }else {

            Log.i("ada_ke_permission", "request not granted");
        }
    }


    // SEPTEMBER-19
//    public boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }


    //17 june

    private TextView nameDisplay, phoneDisplay, dateDisplay, hourDisplay, amOrpmDisplay, outMessageDisplay, boxMessageDisplay;

    private TextView wifiDisplay, locationDisplay, morningDisplay,eveningDisplay, adminDisplay;

    public static String globalUserName, globalUserPhone,globalAdminName,globalAdminPhone;


    //22 may
    private int lastCheckId;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.nav_wifi:

                    if(lastCheckId==R.id.nav_admin) {

                        imageViewAdminIcon.setVisibility(View.INVISIBLE);
                        cardViewAdminIcon.setVisibility(View.INVISIBLE);
                        barAdmin.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_evening) {

                        imageViewEveningIcon.setVisibility(View.INVISIBLE);
                        cardViewEveningIcon.setVisibility(View.INVISIBLE);
                        barEvening.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_morning) {
                        imageViewMorningIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutMorningIcon.setVisibility(View.INVISIBLE);
                        barMorning.setVisibility(View.INVISIBLE);
                    }
                    if(lastCheckId==R.id.nav_location) {
                        imageViewLocationIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutLocationIcon.setVisibility(View.INVISIBLE);
                        barLocation.setVisibility(View.INVISIBLE);
                    }
//
//                    if(lastCheckId==R.id.nav_wifi){
//                        imageViewWifiIcon.setVisibility(View.INVISIBLE);
//                        constraintLayoutWifiIcon.setVisibility(View.INVISIBLE);
//                        barWifi.setVisibility(View.INVISIBLE);
//                    }

                    imageViewWifiIcon.setVisibility(View.VISIBLE);
                    constraintLayoutWifiIcon.setVisibility(View.VISIBLE);
                    barWifi.setVisibility(View.VISIBLE);

                    lastCheckId = R.id.nav_wifi ;






                    break;
                case R.id.nav_location:



                    if(lastCheckId==R.id.nav_admin) {

                        imageViewAdminIcon.setVisibility(View.INVISIBLE);
                        cardViewAdminIcon.setVisibility(View.INVISIBLE);
                        barAdmin.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_evening) {

                        imageViewEveningIcon.setVisibility(View.INVISIBLE);
                        cardViewEveningIcon.setVisibility(View.INVISIBLE);
                        barEvening.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_morning) {
                        imageViewMorningIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutMorningIcon.setVisibility(View.INVISIBLE);
                        barMorning.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_wifi) {
                        imageViewWifiIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutWifiIcon.setVisibility(View.INVISIBLE);
                        barWifi.setVisibility(View.INVISIBLE);
                    }

//
                    imageViewLocationIcon.setVisibility(View.VISIBLE);
                    constraintLayoutLocationIcon.setVisibility(View.VISIBLE);
                    barLocation.setVisibility(View.VISIBLE);

                    lastCheckId=R.id.nav_location;








                    break;
                case R.id.nav_morning:



                    if(lastCheckId==R.id.nav_admin) {

                        imageViewAdminIcon.setVisibility(View.INVISIBLE);
                        cardViewAdminIcon.setVisibility(View.INVISIBLE);
                        barAdmin.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_evening) {

                        imageViewEveningIcon.setVisibility(View.INVISIBLE);
                        cardViewEveningIcon.setVisibility(View.INVISIBLE);
                        barEvening.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_location) {
                        imageViewLocationIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutLocationIcon.setVisibility(View.INVISIBLE);
                        barLocation.setVisibility(View.INVISIBLE);

                    }

                    if(lastCheckId==R.id.nav_wifi) {
                        imageViewWifiIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutWifiIcon.setVisibility(View.INVISIBLE);
                        barWifi.setVisibility(View.INVISIBLE);
                    }

                    imageViewMorningIcon.setVisibility(View.VISIBLE);
                    constraintLayoutMorningIcon.setVisibility(View.VISIBLE);
                    barMorning.setVisibility(View.VISIBLE);

                    lastCheckId=R.id.nav_morning;


                    break;
                case R.id.nav_evening:



                    if(lastCheckId==R.id.nav_admin) {

                        imageViewAdminIcon.setVisibility(View.INVISIBLE);
                        cardViewAdminIcon.setVisibility(View.INVISIBLE);
                        barAdmin.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_location) {


                        imageViewLocationIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutLocationIcon.setVisibility(View.INVISIBLE);
                        barLocation.setVisibility(View.INVISIBLE);

                    }

                    if(lastCheckId==R.id.nav_morning) {
                        imageViewMorningIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutMorningIcon.setVisibility(View.INVISIBLE);
                        barMorning.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_wifi) {
                        imageViewWifiIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutWifiIcon.setVisibility(View.INVISIBLE);
                        barWifi.setVisibility(View.INVISIBLE);
                    }

//
                    imageViewEveningIcon.setVisibility(View.VISIBLE);
                    cardViewEveningIcon.setVisibility(View.VISIBLE);
                    barEvening.setVisibility(View.VISIBLE);

                    lastCheckId=R.id.nav_evening;

//



                    break;
                case R.id.nav_admin:


                    if(lastCheckId==R.id.nav_evening) {

                        imageViewEveningIcon.setVisibility(View.INVISIBLE);
                        cardViewEveningIcon.setVisibility(View.INVISIBLE);
                        barEvening.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_location) {


                        imageViewLocationIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutLocationIcon.setVisibility(View.INVISIBLE);
                        barLocation.setVisibility(View.INVISIBLE);

                    }

                    if(lastCheckId==R.id.nav_morning) {
                        imageViewMorningIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutMorningIcon.setVisibility(View.INVISIBLE);
                        barMorning.setVisibility(View.INVISIBLE);
                    }

                    if(lastCheckId==R.id.nav_wifi) {
                        imageViewWifiIcon.setVisibility(View.INVISIBLE);
                        constraintLayoutWifiIcon.setVisibility(View.INVISIBLE);
                        barWifi.setVisibility(View.INVISIBLE);
                    }


                    imageViewAdminIcon.setVisibility(View.VISIBLE);
                    cardViewAdminIcon.setVisibility(View.VISIBLE);
                    barAdmin.setVisibility(View.VISIBLE);

                    lastCheckId=R.id.nav_admin;




                    break;



            }
            return true;
        }
    };
    private Handler handler = new Handler();
    private String seconds_fromPhone = "";
    private String timeFromPhone = "";
    private String timeHour="";
    private String dateFromPhone="";
    private String amOrPmFromPhone="";

    //26 june
    private ImageView fingerprint_imageView;
    private boolean boolean_fingerprint_animate;
    private Timer timer_finger_animate;
    private Handler timer_handler;
    private int fingerCounter;
    private ConstraintLayout constrainBox;
    private TextView textInBox;
    private ConstraintLayout box_success2;
    private ConstraintLayout constrainBoxError;
    private boolean boolean_fingerprint_first;
    private int fingerprint_count;
    private boolean animation_fingerprint_ended_boolean;
    private int counter_to_start_timer;
    private Timer timer_async;
    private AnimatedVectorDrawableCompat avd1;
    private AnimatedVectorDrawable avd;

    //27 june
    private ConstraintLayout constraintbaccck;
    private FloatingActionButton firsttime_floatButton;

    //7 july, only if location is used, when user out of location 50m from admin
    private String streetname;
    private String myphone_extracted;

    //7 july, check if admin, display button to get report.

    private String nameHere_ifadmin;
    private String nameHere_2_ifadmin;
    private String phoneHere_ifadmin;

    private String adminName_ifadmin;
    private String adminName_2_ifadmin;
    private String adminPhone_ifadmin;
    private String adminPhone_2_ifadmin;

    //7 july
    private FloatingActionButton buttonGetReport;
    private TextView textGetReport;
    private String s;
    private float distanceOffset;


    //19 june

    //september 20

    private TextView checklocationText, checkConstraintText, checkCounterFlow;


    //


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);


        outState.putFloat("distanceOffset",distanceOffset);


        outState.putString("bssidConstraint",bssidConstraint);
        outState.putString("fingerprint_result",s);
        outState.putString("morningConstraint",morningConstraint);
        outState.putString("eveningConstraint",eveningConstraint);
        outState.putString("eveningConstraint",ssidConstraint);
        outState.putString("locationConstraint",locationConstraint);
        outState.putString("globalAdminNameHere",globalAdminNameHere);
        outState.putString("globalAdminPhoneHere",globalAdminPhoneHere);

        outState.putString("timeCurrent",timeCurrent);
        outState.putString("dateNow",dateNow);

        outState.putString("dayNow",dayNow);





        outState.putString("globalUserName", globalUserName);
        outState.putString("globalUserPhone", globalUserPhone);

        outState.putString("userSSID",userSSID);
        outState.putString("userBSSID",userBSSID);



        outState.putString("lastSSIDrecorded",lastSSIDrecorded);



        outState.putString("lastLocationRecorded",lastLocationRecorded);
        outState.putString("userLatitude",userLatitude);
        outState.putString("userLongitude",userLongitude);

        outState.putString("nameUser",nameUser);
        outState.putString("phoneUser",phoneUser);




        outState.putString("dateAndTimeNow",dateAndTimeNow);



        outState.putInt("counterFlowHere", counterFlowHere);
        outState.putInt("counterFlowHere2", counterFlowHere2);
        outState.putInt("fingerprint_count", fingerprint_count);

        outState.putInt("countUserverified",countUserverified);

        outState.putInt("statusBarWeSet", statusBarWeSet);

        outState.putBoolean("booleanResultExtracted",booleanResultExtracted);
        outState.putBoolean("boolean_fingerprint_first",boolean_fingerprint_first);
        outState.putBoolean("checkLocationProcess",checkLocationProcess);
        outState.putBoolean("checkAdminConstraintProcess",checkAdminConstraintProcess);







        //outState.putBundle("dateAndTIme", dateAndTimeNow);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print__log_in__final_);

        handler_counter = new Handler();

        counterUpdateCheck = 0;
        handler_counter.post(runnableCode_counterFlow);

        globalUserPhone="";

        checklocationText = findViewById(R.id.textViewMain_location_latitude_id); // 20 sep
        checkConstraintText = findViewById(R.id.textViewMain_checkConstraint_id); //20 sep
        checkCounterFlow = findViewById(R.id.textViewMain_checkCounterFlow_id); //20 sep


        if (savedInstanceState != null) {


            //outState.putFloat("distanceOffset",distanceOffset);
            distanceOffset = savedInstanceState.getFloat("distanceOffset");
            s = savedInstanceState.getString("fingerprint_result");
            //outState.putString("fingerprint_result",s);
            morningConstraint = savedInstanceState.getString("morningConstraint");
            eveningConstraint = savedInstanceState.getString("eveningConstraint");
            ssidConstraint = savedInstanceState.getString("ssidConstraint");
            locationConstraint = savedInstanceState.getString("locationConstraint");
            bssidConstraint = savedInstanceState.getString("bssidConstraint");
//
//            outState.putString("morningConstraint",morningConstraint);
//            outState.putString("eveningConstraint",eveningConstraint);
//            outState.putString("eveningConstraint",ssidConstraint);
//            outState.putString("locationConstraint",locationConstraint);

            globalAdminNameHere = savedInstanceState.getString("globalAdminNameHere");
            globalAdminPhoneHere = savedInstanceState.getString("globalAdminPhoneHere");
            //outState.putString("globalAdminNameHere",globalAdminNameHere);
            //outState.putString("globalAdminPhoneHere",globalAdminPhoneHere);

            timeCurrent = savedInstanceState.getString("timeCurrent");
            dateNow = savedInstanceState.getString("dateNow");
            dayNow = savedInstanceState.getString("dayNow");
//
//            outState.putString("timeCurrent",timeCurrent);
//            outState.putString("dateNow",dateNow);

            //outState.putString("dayNow",dayNow);


            globalUserName = savedInstanceState.getString("globalUserName");
            globalUserPhone = savedInstanceState.getString("globalUserPhone");
            userSSID = savedInstanceState.getString("userSSID");
            userBSSID = savedInstanceState.getString("userBSSID");

//
//            outState.putString("globalUserName", globalUserName);
//            outState.putString("globalUserPhone", globalUserPhone);
//
//            outState.putString("userSSID",userSSID);
//            outState.putString("userBSSID",userBSSID);
//

            lastSSIDrecorded = savedInstanceState.getString("lastSSIDrecorded");
            //outState.putString("lastSSIDrecorded",lastSSIDrecorded);


            globalUserName = savedInstanceState.getString("globalUserName");
            globalUserPhone = savedInstanceState.getString("globalUserPhone");
            userSSID = savedInstanceState.getString("userSSID");
            userBSSID = savedInstanceState.getString("userBSSID");


            lastLocationRecorded = savedInstanceState.getString("lastLocationRecorded");
            userLatitude = savedInstanceState.getString("userLatitude");
            userLongitude = savedInstanceState.getString("userLongitude");


            nameUser = savedInstanceState.getString("nameUser");
            phoneUser = savedInstanceState.getString("phoneUser");

            dateAndTimeNow = savedInstanceState.getString("dateAndTimeNow");


            //outState.putString("lastLocationRecorded",lastLocationRecorded);
//            outState.putString("userLatitude",userLatitude);
//            outState.putString("userLongitude",userLongitude);
//
//            outState.putString("nameUser",nameUser);
//            outState.putString("phoneUser",phoneUser);


            //outState.putString("dateAndTimeNow",dateAndTimeNow);


            counterFlowHere = savedInstanceState.getInt("counterFlowHere");

            counterFlowHere2 = savedInstanceState.getInt("counterFlowHere2");
            fingerprint_count = savedInstanceState.getInt("fingerprint_count");
            countUserverified = savedInstanceState.getInt("countUserverified");
            statusBarWeSet = savedInstanceState.getInt("statusBarWeSet");


            //outState.putInt("counterFlowHere", counterFlowHere);
//            outState.putInt("counterFlowHere2", counterFlowHere2);
//            outState.putInt("fingerprint_count", fingerprint_count);
//            outState.putInt("countUserverified",countUserverified);
//            outState.putInt("statusBarWeSet", statusBarWeSet);

            booleanResultExtracted = savedInstanceState.getBoolean("booleanResultExtracted");
            boolean_fingerprint_first = savedInstanceState.getBoolean("boolean_fingerprint_first");
            checkLocationProcess = savedInstanceState.getBoolean("checkLocationProcess");
            checkAdminConstraintProcess = savedInstanceState.getBoolean("checkAdminConstraintProcess");

//            outState.putBoolean("booleanResultExtracted",booleanResultExtracted);
//            outState.putBoolean("boolean_fingerprint_first",boolean_fingerprint_first);
//            outState.putBoolean("checkLocationProcess",checkLocationProcess);
//            outState.putBoolean("checkAdminConstraintProcess",checkAdminConstraintProcess);


        }

        constraintbaccck = findViewById(R.id.constraint_baccckkkID);
        backColor = findViewById(R.id.backLayoutColourID);


        buttonGetReport = findViewById(R.id.getreportButton_id);
        textGetReport = findViewById(R.id.textView_getReportID);

/////////////////////////////////////////////////// 9 july

        //check network is presence

        boolean networkavai = false;  //isNetworkAvailable();

        if(networkavai) {

            CollectionReference cR_topUserCollection2 = FirebaseFirestore.getInstance().collection("users_top_detail");

            if ( globalUserPhone.equals(null)|| globalUserPhone.equals("")) {

            }else{
            DocumentReference dR_topUserCollection2 = cR_topUserCollection2.document(globalUserPhone + "imauser");

            dR_topUserCollection2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.getResult().exists()) {

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
                                    nameHere_ifadmin = mapHere.getValue().toString();

                                    //handle null, or not registered, or wrong data input

                                    if (nameHere_ifadmin.isEmpty() || nameHere_ifadmin == null) {

                                        nameHere_ifadmin = "";
                                    }
                                }


                                if (mapHere.getKey().equals("user_name_2")) {
                                    nameHere_2_ifadmin = mapHere.getValue().toString();


                                    if (nameHere_2_ifadmin.isEmpty() || nameHere_2_ifadmin == null) {

                                        nameHere_2_ifadmin = "";
                                    }
                                }


                                if (mapHere.getKey().equals("phone")) {
                                    phoneHere_ifadmin = mapHere.getValue().toString();


                                    if (phoneHere_ifadmin.isEmpty() || phoneHere_ifadmin == null) {

                                        phoneHere_ifadmin = "";
                                    }
                                }
//
//                                if(mapHere.getKey().equals("admin_count")){
//                                    admin_count = mapHere.getValue().toString();
//                                }
//
                                if (mapHere.getKey().equals("admin_name_1")) {
                                    adminName_ifadmin = mapHere.getValue().toString();


                                    if (adminName_ifadmin.isEmpty() || adminName_ifadmin == null) {

                                        adminName_ifadmin = "";
                                    }


                                }


                                if (mapHere.getKey().equals("admin_name_2")) {
                                    adminName_2_ifadmin = mapHere.getValue().toString();


                                    if (adminName_2_ifadmin.isEmpty() || adminName_2_ifadmin == null) {

                                        adminName_2_ifadmin = "";
                                    }
                                }


                                if (mapHere.getKey().equals("admin_phone_1")) {
                                    adminPhone_ifadmin = mapHere.getValue().toString();


                                    if (adminPhone_ifadmin.isEmpty() || adminPhone_ifadmin == null) {

                                        adminPhone_ifadmin = "";
                                    }

                                }


                                if (mapHere.getKey().equals("admin_phone_2")) {
                                    adminPhone_2_ifadmin = mapHere.getValue().toString();


                                    if (adminPhone_2_ifadmin.isEmpty() || adminPhone_2_ifadmin == null) {

                                        adminPhone_2_ifadmin = "";
                                    }
                                }

                            }

//                                nameHere_boolean = true;
//
//                                Toast.makeText(getContext(), "Success getting admin detail", Toast.LENGTH_SHORT).show();


                            //buttonGetReport.setVisibility(View.VISIBLE);

                            //20 september HALT

                            if (phoneHere_ifadmin.equals(adminPhone_ifadmin) || phoneHere_ifadmin.equals(adminPhone_2_ifadmin)) {

                                runOnUiThread(new Runnable() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void run() {

                                        buttonGetReport.setVisibility(View.VISIBLE);
                                        textGetReport.setVisibility(View.VISIBLE);

                                        buttonGetReport.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Toast.makeText(FingerPrint_LogIn_Final_Activity.this,"feature not availabe yet", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                //DONT DELETE HERE
//                                        buttonGetReport.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View view) {
//
//
//                                                if (phoneHere_ifadmin.equals(adminPhone_ifadmin)) {
//                                                    //it belongs to admin 1
//
//                                                    Intent getReportIntent = new Intent(FingerPrint_LogIn_Final_Activity.this, Report_Activity.class);
//
//                                                    getReportIntent.putExtra("phone", phoneHere_ifadmin);
//                                                    getReportIntent.putExtra("name", nameHere_ifadmin);
//                                                    getReportIntent.putExtra("adminname", adminName_ifadmin);
//
//                                                    startActivity(getReportIntent);
//
//
//                                                } else if (phoneHere_ifadmin.equals(adminPhone_2_ifadmin)) {
//                                                    //it belongs to admin 2
//
//                                                    Intent getReportIntent2 = new Intent(FingerPrint_LogIn_Final_Activity.this, Report_Activity.class);
//
//
//                                                    getReportIntent2.putExtra("phone", phoneHere_ifadmin);
//                                                    getReportIntent2.putExtra("name", nameHere_ifadmin);
//                                                    getReportIntent2.putExtra("adminname", adminName_2_ifadmin);
//
//                                                    startActivity(getReportIntent2);
//
//                                                }
//
//
//                                            }
//                                        });

                                    }
                                });

                            }


                        } else {

                            //  nameHere_boolean = false;


                            //  Toast.makeText(getContext(), "Fail getting admin detail", Toast.LENGTH_SHORT).show();

                        }

                    }

                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {

                    //Toast.makeText(getContext(), "Fail getting admin detail", Toast.LENGTH_SHORT).show();

                }
            });

        }//check ada ke global user phone

        }//check ada internet tak

////////////////////////////////


        File f = new File("/data/data/com.example.afinal/shared_prefs/com.example.finalV8_punchCard.MAIN_POOL.xml");


        //9 july, test get report list.





        if(f.exists()){

            //7 july

            constraintbaccck.setVisibility(View.GONE);


            //here we fetch if user is admin. ,

            SharedPreferences prefs_Main_Pool = Objects.requireNonNull(FingerPrint_LogIn_Final_Activity.this).getSharedPreferences("com.example.finalV8_punchCard.MAIN_POOL", Context.MODE_PRIVATE);

            myphone_extracted = prefs_Main_Pool.getString("my_phone_number","");

            if(!myphone_extracted.equals("") && myphone_extracted!=null&& !myphone_extracted.isEmpty()) {


                CollectionReference cR_topUserCollection =  FirebaseFirestore.getInstance().collection("users_top_detail");


                DocumentReference dR_topUserCollection = cR_topUserCollection.document(myphone_extracted + "imauser");

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
                                        nameHere_ifadmin = mapHere.getValue().toString();

                                        //handle null, or not registered, or wrong data input

                                        if (nameHere_ifadmin.isEmpty() || nameHere_ifadmin == null) {

                                            nameHere_ifadmin = "";
                                        }
                                    }


                                    if (mapHere.getKey().equals("user_name_2")) {
                                        nameHere_2_ifadmin = mapHere.getValue().toString();


                                        if (nameHere_2_ifadmin.isEmpty() || nameHere_2_ifadmin == null) {

                                            nameHere_2_ifadmin = "";
                                        }
                                    }


                                    if (mapHere.getKey().equals("phone")) {
                                        phoneHere_ifadmin = mapHere.getValue().toString();


                                        if (phoneHere_ifadmin.isEmpty() || phoneHere_ifadmin == null) {

                                            phoneHere_ifadmin = "";
                                        }
                                    }
//
//                                if(mapHere.getKey().equals("admin_count")){
//                                    admin_count = mapHere.getValue().toString();
//                                }
//
                                    if (mapHere.getKey().equals("admin_name_1")) {
                                        adminName_ifadmin = mapHere.getValue().toString();


                                        if (adminName_ifadmin.isEmpty() || adminName_ifadmin == null) {

                                            adminName_ifadmin = "";
                                        }


                                    }


                                    if (mapHere.getKey().equals("admin_name_2")) {
                                        adminName_2_ifadmin = mapHere.getValue().toString();


                                        if (adminName_2_ifadmin.isEmpty() || adminName_2_ifadmin == null) {

                                            adminName_2_ifadmin = "";
                                        }
                                    }


                                    if (mapHere.getKey().equals("admin_phone_1")) {
                                        adminPhone_ifadmin = mapHere.getValue().toString();


                                        if (adminPhone_ifadmin.isEmpty() || adminPhone_ifadmin == null) {

                                            adminPhone_ifadmin = "";
                                        }

                                    }


                                    if (mapHere.getKey().equals("admin_phone_2")) {
                                        adminPhone_2_ifadmin = mapHere.getValue().toString();


                                        if (adminPhone_2_ifadmin.isEmpty() || adminPhone_2_ifadmin == null) {

                                            adminPhone_2_ifadmin = "";
                                        }
                                    }

                                }

//                                nameHere_boolean = true;
//
//                                Toast.makeText(getContext(), "Success getting admin detail", Toast.LENGTH_SHORT).show();


                            //buttonGetReport.setVisibility(View.VISIBLE);

                            if(phoneHere_ifadmin.equals(adminPhone_ifadmin)|| phoneHere_ifadmin.equals(adminPhone_2_ifadmin)){

                                runOnUiThread(new Runnable() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void run() {

                                        buttonGetReport.setVisibility(View.VISIBLE);
                                        textGetReport.setVisibility(View.VISIBLE);

                                        buttonGetReport.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if(phoneHere_ifadmin.equals(adminPhone_ifadmin)){
                                                    //it belongs to admin 1

                                                    Intent getReportIntent = new Intent(FingerPrint_LogIn_Final_Activity.this, Report_Activity.class);

                                                    getReportIntent.putExtra("phone",phoneHere_ifadmin);
                                                    getReportIntent.putExtra("name",nameHere_ifadmin);
                                                    getReportIntent.putExtra("adminname", adminName_ifadmin);
                                                    getReportIntent.putExtra("datee", dateFromPhone);

                                                    startActivity(getReportIntent);



                                                }else if(phoneHere_ifadmin.equals(adminPhone_2_ifadmin)){
                                                    //it belongs to admin 2

                                                    Intent getReportIntent2 = new Intent(FingerPrint_LogIn_Final_Activity.this, Report_Activity.class);


                                                    getReportIntent2.putExtra("phone",phoneHere_ifadmin);
                                                    getReportIntent2.putExtra("name",nameHere_ifadmin);
                                                    getReportIntent2.putExtra("adminname", adminName_2_ifadmin);
                                                    getReportIntent2.putExtra("datee", dateFromPhone);

                                                    startActivity(getReportIntent2);

                                                }



                                            }
                                        });

                                    }
                                });

                            }



                            } else {

                              //  nameHere_boolean = false;


                              //  Toast.makeText(getContext(), "Fail getting admin detail", Toast.LENGTH_SHORT).show();

                            }

                        }

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                        //Toast.makeText(getContext(), "Fail getting admin detail", Toast.LENGTH_SHORT).show();

                    }
                });



            }





            //pull status if admin

        }else {


            enableDisableViewGroup(backColor,false);


        }


        firsttime_floatButton =  findViewById(R.id.floatingActionButton3);

        firsttime_floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enableDisableViewGroup(backColor,true);

                constraintbaccck.setVisibility(View.GONE);


            }


        });



        checkAdminConstraintProcess =false;

        //27 june
        counter_to_start_timer=0;

        animation_fingerprint_ended_boolean=false;

        constrainBox = findViewById(R.id.box_success_id);

        boolean_fingerprint_first=false;

        fingerprint_count=0;

        constrainBoxError = findViewById(R.id.box_error3_id);

        textInBox = findViewById(R.id.fingerPrintFinal_textView_MessageInBox2_id);

        box_success2 = findViewById(R.id.box_success2_id);

        fingerCounter=0;

        boolean_fingerprint_animate=false;

        fingerprint_imageView = findViewById(R.id.imageView_fingerPrint_id);
        booleanResultExtracted = false;

        bottomNavigationView = findViewById(R.id.bottomNavigationView_fingerprint);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bottomNavigationView.setItemIconTintList(null);


        //17 june

        globalUserName ="";
//        globalUserPhone="";
        globalAdminName="";
        globalAdminPhone="";

        nameDisplay = findViewById(R.id.fingerPrintFinal_textView_Name_id);
        phoneDisplay = findViewById(R.id.fingerPrintFinal_textView_Phone_id);
        hourDisplay = findViewById(R.id.fingerprintFinal_textViewHourID);
        dateDisplay = findViewById(R.id.fingerPrintFinal_textView_Date_id);
        outMessageDisplay = findViewById(R.id.fingerPrintFinal_textView_Message_id);
        boxMessageDisplay = findViewById(R.id.fingerPrintFinal_textView_MessageInBox_id);

        wifiDisplay = findViewById(R.id.fingerPrintFinal_textView_wifiStatus_id);
        locationDisplay = findViewById(R.id.fingerPrintFinal_textView_locationStatus_id);
        morningDisplay = findViewById(R.id.fingerPrintFinal_textView_morningStatus_id);
        eveningDisplay = findViewById(R.id.fingerPrintFinal_textView_eveningStatus_id);
        adminDisplay = findViewById(R.id.fingerPrintFinal_textView_adminStatus_id);



        //setting up for bottom nav

        barAdmin = findViewById(R.id.constraintLayout_bar_admin_ID);
        barEvening = findViewById(R.id.constraintLayout_bar_evening_ID);
        barMorning = findViewById(R.id.constraintLayout_bar_morning_ID);
        barLocation = findViewById(R.id.constraintLayout_bar_location_id);
        barWifi = findViewById(R.id.constraintLayout_barWifi_id);


        imageViewAdminIcon = findViewById(R.id.final_admin_imageview_ID);
        imageViewEveningIcon = findViewById(R.id.final_imageView_evening_ID);
        imageViewMorningIcon = findViewById(R.id.final_imageView_morning_ID);
        imageViewLocationIcon = findViewById(R.id.final_imageView_location_id);
        imageViewWifiIcon = findViewById(R.id.final_imageView_wifi_id);

        cardViewAdminIcon =  findViewById(R.id.final_cardView_admin_ID);
        cardViewEveningIcon = findViewById(R.id.final_cardView_evening_ID);

        constraintLayoutMorningIcon = findViewById(R.id.final_morning_constraint_ID);
        constraintLayoutLocationIcon = findViewById(R.id.final_constraint_location_id);
        constraintLayoutWifiIcon = findViewById(R.id.final_constraint_wifi_id);


        amOrpmDisplay = findViewById(R.id.fingerPrintFinal_textViewAMorPM_id);

        //rolling text view

        @SuppressLint("SimpleDateFormat") final DateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");

        Log.i("checkSecond :", "time: "+timeFromPhone);


        Log.i("checkSecond :", "second: "+seconds_fromPhone);

        dateFromPhone = format.format(new Date());


        final RollingTextView timeView = findViewById(R.id.rolling_second_textView_ID);
        timeView.setAnimationDuration(300);
        timeView.setLetterSpacingExtra(10);
        handler.post(new Runnable() {
            @Override
            public void run() {
                timeFromPhone = format.format(new Date());


                seconds_fromPhone = timeFromPhone.substring(18,20);

                timeView.setText(seconds_fromPhone);
                handler.postDelayed(this, 1000L);

                Log.i("timeFromPhone", timeFromPhone);

                timeHour = timeFromPhone.substring(12,17);

                //if(timeHour.equals(timeHour))
                hourDisplay.setText(timeHour);
            }
        });

        amOrPmFromPhone = dateFromPhone.substring(12,14);

        nameDisplay.setText("username");
        phoneDisplay.setText("userphone");

        Integer intAmOrPm = Integer.valueOf(amOrPmFromPhone);

        if(intAmOrPm>=0 && intAmOrPm<=11.59){

            amOrpmDisplay.setText("AM");

        }else {

            amOrpmDisplay.setText("PM");
        }


        dateFromPhone = dateFromPhone.substring(0,6);


        dateDisplay.setText(dateFromPhone);






        //

        lastSSIDrecorded = "";
        timeFragmentBoolean =false;
        setMorningTimeStamp =false;
        setEveningTimeStamp=false;

        lastLocationRecorded="";

        countUserverified = 0;
//        textViewFinalData = findViewById(R.id.textViewFINALDATAID);
//        countFinalFlow = 0;
//        textViewFinalData2 = findViewById(R.id.textViewFinalDataID2);
//        countFinalFlow2 = 0;
//
//        textViewDataLocation = findViewById(R.id.textViewFinalDataLocationiD);


        //    userCount =0//;
        counterFlowHere = 0;
        counterFlowHere2 = 0;
        counterFlowHere3 = 0;
        Log.i("checkFinalFlow : ", " 1 oncreate() fingerprint_main_activity");

        statusBarWeSet = 0;

        onServerTime_interface = new FingerPrint_LogIn_Final_Activity();

        dayNow = null;
        dateAndTimeNow = null;
        dataPulled = false;
        user_StreetName = null;

        globalAdminPhoneHere = null;
        globalAdminNameHere = null;

        presenter = new FingerPrintFinal_Presenter(this, (Activity) FingerPrint_LogIn_Final_Activity.this);
        Log.i("checkFinalFlow : ", " 2 oncreate() fingerprint_main_activity, after presenter Constructor()");

        presenter.addObserver(this);


        //pull our data from phone. get bssid, ssid, location also.

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //request location permission
        Log.i("checkFinalFlow : ", " 3 oncreate() activity, before request location ");
        presenter.requestLocationPermission(mLocationManager);
        Log.i("checkFinalFlow : ", " 4 oncreate() activity, after request location ");

        //get the time here
        Log.i("checkFinalFlow : ", " 5 oncreate() activity, before request time ");

        presenter.getServerTimeNow(onServerTime_interface); // this will be done, in back task
        Log.i("checkFinalFlow : ", " 6 oncreate() activity, after request time ");

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Log.i("SSID_ni_main 0.0 ","wifimanage : "+wifiManager);

        wifiInfo = wifiManager.getConnectionInfo();
        Log.i("SSID_ni_main 0.1 ","wifiinfo : "+wifiInfo);

        //userSSID = wifiManager.getDhcpInfo().ipAddress

        userSSID = wifiInfo.getSSID();
        Log.i("SSID_ni_main 0 ","ssid : "+userSSID);

        userSSID = userSSID.replace("\"","");
        userBSSID = wifiInfo.getBSSID();

        Log.i("SSID_ni_main 1 ","ssid : "+userSSID);

        //pull constraint by admin, like, time constraint, location or bssid,, we do this below.

        Log.i("checkFinalFlow : ", " 1 oncreate() fingerprint_main_activity");


        timeStampthis = 0;

        nameUser = null;
        phoneUser = null;

        floatButtonGetAction = findViewById(R.id.logn_Final_floatingActionButtonID);

        floatButtonGetAction.setOnClickListener(this);

//        textView = findViewById(R.id.login_final_textViewHereID);
//        imageView = findViewById(R.id.login_final_imageViewID);
//        textView.setText("click button below to log in");


        fragment = new Login_Select_Action_Fragment();


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {

                Log.i("checkFrag Flow", "1");

                presenter.addObserver(FingerPrint_LogIn_Final_Activity.this);

                Log.i("checkFinalFlow : ", " 6 backstackFragment() fingerprint_main_activity");

                //this probably still null


                countFinalFlow2++; //this triggered two times,

               // textViewFinalData2.setText("backStackChange: " + countFinalFlow2);

                if (nameUser != null) {

                    Log.i("checkFinalFlow : ", " 7 backstackFragment() activity, nameUser :" + nameUser + " success");

                    if (nameUser != "") {
                      //  textView.setText("admin detected, fingerprint checkin now with server..");

                        Log.i("checkFinalFlow : ", " 8 backstackFragment() activity, before fingerprint");
                        presenter.checkSupportedDevice();
                        Log.i("checkFinalFlow : ", " 8 backstackFragment() activity, after fingerprint");

                    }
                } else {

                    Log.i("checkFinalFlow : ", " 9 backFragment(), waiting for fingerprint ");

                 ///   textView.setText("waiting");
                }

                Log.i("checkFinalFlow : ", " 10 backFragment(), prolem in flow ");

            }
        });


    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }


    @Override
    public void onClick(View v) {

        //september -> if wifi not detected/ dont allow clicking

       // Log.i("checkssidada_ke", "ssid : "+userSSID);

//        if(userSSID==""|| userSSID==null || userSSID.equals("")|| userSSID.equals(null) || userSSID.equals("<unknown ssid>")) {
//
//            Toast.makeText(FingerPrint_LogIn_Final_Activity.this, "please connect to admin wifi", Toast.LENGTH_SHORT).show();
//
//        }else {

            if (!presenter.getRemapLocation().isEmpty()) {
                presenter.removeLocationNow();
            }
            presenter.deleteObserver(this);
            //reset back status
            // nameUser="";
            nameUser = null;
            checkLocationProcess = false;

            //phoneAdminConstraint=null;
            // backColor.setAlpha(0.9f);

            //25 may

            booleanResultExtracted = false;


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameID, fragment)
                    .addToBackStack("")
                    .commit();
        //}

    }

    //27june, we need to set, another thread timer, to check if wifi, location existed, dont count
    //depended on update method, being updated.



    @Override
    public void update(Observable o, Object arg) {

        //18 june , what if fingerprint not working.?


//        check supplicant state first, while for api 28, need to get permission first,
//        then need to check how to trigger update without location enable


        counterUpdateCheck++;

        //counterFlowHere++;
        Log.i("checkUpdateFinal", "1");
        Log.i("counterUpdateCheck", "counterUpdateCheck : "+counterUpdateCheck);

        checkCounterFlow.setText("counter : "+ counterUpdateCheck);

        if (booleanResultExtracted) {
                //ensure result extracted

        if (o instanceof FingerPrintFinal_Presenter) {

             s = ((FingerPrintFinal_Presenter) o).getFinalStringResult();

            if (s.equals("success verified")) {

                //27 june
//                counter_to_start_timer++;
//
//                if (counter_to_start_timer == 1) {
//
//                    //create timer
//
//                    timer_async = new Timer();
//
//                }

                ////
                boolean_fingerprint_first = true;

                fingerprint_count++;

                // fingerprint_imageView.setImageDrawable(R.drawable.fingerprint_anim);

                fingerprint_imageView.setImageDrawable(getDrawable(R.drawable.fingerprint_anim));

                if (fingerprint_count == 1) { //so only called once.
                    animateFingerPrint(fingerprint_imageView);
                }
                countUserverified++;

                //boxMessageDisplay.setText("fingerprint verified");

                Log.i("wherelocationRegister :", "FLOW 1, countVerified:" + countUserverified + " , userLatitude: " + userLatitude);

                presenter.getCurrent_User_Admin_Server_Value(nameUser, phoneUser, globalAdminNameHere, globalAdminPhoneHere);

                presenter.stopListetingFingerprint();

                boolean_fingerprint_first = false;

            } else if (s.equals("try again")) {
                // userLongitude=null;

                //boxMessageDisplay.setText("fingerprint not verified");

                boolean_fingerprint_first = false;
                presenter.stopListetingFingerprint();

                Animation fadeOut = AnimationUtils.loadAnimation(FingerPrint_LogIn_Final_Activity.this, R.anim.fadeout);
                constrainBox.startAnimation(fadeOut);


                Animation fadeInError = AnimationUtils.loadAnimation(FingerPrint_LogIn_Final_Activity.this, R.anim.fadein);
                constrainBoxError.startAnimation(fadeInError);


                Log.i("checkFinalFlow : ", " 7 backFragment(), try again fingerprint ");

                Toast.makeText(FingerPrint_LogIn_Final_Activity.this, "please select admin, try finger again", Toast.LENGTH_SHORT).show();
                presenter.removeLocationNow();
                presenter.stopListetingFingerprint();
            }

            //getTime
            String time = ((FingerPrintFinal_Presenter) o).getDateAndTimeNow(); //problem if, always updating,

            dateAndTimeNow = time;

            //getFireStore
            Map<String, Object> remapAdminConstraint = ((FingerPrintFinal_Presenter) o).getReturnMap();



            if (remapAdminConstraint != null) {

                Log.i("check_here,","1 : constraint not null");



                for (Map.Entry<String, Object> kk : remapAdminConstraint.entrySet()) {

                    if (kk.getKey().equals("location")) {

                        locationConstraint = kk.getValue().toString();
                    }
                    if (kk.getKey().equals("latitude")) {
                        latitudeConstraint = kk.getValue().toString();
                    }

                    if (kk.getKey().equals("longitude")) {

                        longitudeConstraint = kk.getValue().toString();
                    }
                    if (kk.getKey().equals("morning_constraint")) {
                        morningConstraint = kk.getValue().toString();
                    }

                    if (kk.getKey().equals("evening_constraint")) {

                        eveningConstraint = kk.getValue().toString();
                    }
                    if (kk.getKey().equals("admin_street_name")) {
                        streetConstraint = kk.getValue().toString();
                    }

                    if (kk.getKey().equals("bssid")) {


                        bssidConstraint = kk.getValue().toString();
                    }
                    if (kk.getKey().equals("ssid")) {
                        ssidConstraint = kk.getValue().toString();

                        checkConstraintText.setText("constraint ssid downloaded : "+ ssidConstraint);

                    }
                    if (kk.getKey().equals("phone")) {
                        phoneAdminConstraint = kk.getValue().toString();
                    }


                }

                checkAdminConstraintProcess = true;


                morningDisplay.setText(morningConstraint);
                eveningDisplay.setText(eveningConstraint);
                wifiDisplay.setText(ssidConstraint);
                locationDisplay.setText(locationConstraint);
                adminDisplay.setText(globalAdminNameHere);

                nameDisplay.setText(nameUser);

                phoneDisplay.setText(phoneUser);

            }
            //getLocation

            Map<String, Object> remapLocation = ((FingerPrintFinal_Presenter) o).getRemapLocation();

            //this may not triggered in first loop
            Log.i("checkUpdateFinal", "5 remapLocation :" + remapLocation);
            if (remapLocation != null) {

                //Toast.makeText(FingerPrint_LogIn_Final_Activity.this,"location process HACK: latitude "+userLatitude,Toast.LENGTH_SHORT).show();


                for (Map.Entry<String, Object> kk : remapLocation.entrySet()) {

                    if (kk.getKey().equals("userLatitude")) {

                        //https://stackoverflow.com/questions/20438627/getlastknownlocation-returns-null
                        //if(mLocationManager.getLastKnownLocation())
                        userLatitude = kk.getValue().toString();

                        checklocationText.setText("latitude : "+ userLatitude);

                        if (userLatitude.equals(lastLocationRecorded)) {

                            userLatitude = "0"; //stop reading this.
                        }



                    }


                    if (kk.getKey().equals("userLongitude")) {
                        userLongitude = kk.getValue().toString();
                    }

                }

                Log.i("c", "6 remapLocation :" + remapLocation + " latite :" + userLatitude);

                Log.i("wherelocationRegister :", "FLOW 6, countVerified:" + countUserverified + " , userLatitude: " + userLatitude);
                checkLocationProcess = true;
            }

            //process for check ssid for 1st enter, with wifi connected, diallow 2nd enter, reset
            if (lastSSIDrecorded.equals(userSSID)) { //
                wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifiInfo = wifiManager.getConnectionInfo();
                userSSID = null;
                userBSSID = null;

                if (wifiInfo != null) {
                    Toast.makeText(this, "getting wifi network info", Toast.LENGTH_SHORT).show();
                    userSSID = wifiInfo.getSSID();

                    Log.i("SSID_ni_main 2 ","ssid : "+userSSID);

                    wifiDisplay.setText(userSSID);

                    userBSSID = wifiInfo.getBSSID();
                } else {
                    userSSID = "--rerun";
                    userBSSID = null;
                    Toast.makeText(this, "please check wifi network connection", Toast.LENGTH_SHORT).show();

                }
            }
            if (lastSSIDrecorded.equals("--rerun")) {

                wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifiInfo = wifiManager.getConnectionInfo();

                if (wifiInfo != null) {

                    if (counterFlowHere == 100) {
                        Toast.makeText(this, "getting wifi network info again", Toast.LENGTH_SHORT).show();
                    }
                    userSSID = wifiInfo.getSSID();
                    wifiDisplay.setText(userSSID);
                    userBSSID = wifiInfo.getBSSID();

                }

            }

            //then get time.


            //here we process



            if (checkAdminConstraintProcess == true && checkLocationProcess == true && dateAndTimeNow != null && !dateAndTimeNow.equals("") && (avd1!=null || avd!=null)) {
//            if(morningConstraint!=null &&eveningConstraint!=null && dateAndTimeNow!=null && userLongitude!=null && userLatitude!=null
//        && latitudeConstraint!=null && longitudeConstraint!=null && userBSSID!=null && userSSID!=null
//        && ssidConstraint!=null && bssidConstraint!=null) {

                Log.i("finalCheckFlowHere", "1");

                Log.i("wherelocationRegister :", "FLOW 6, countVerified:" + countUserverified + " , userLatitude: " + userLatitude);



                if(avd.isRunning()) {


                }else {
                if (phoneAdminConstraint != null && !phoneAdminConstraint.equals("")) { //means the right admin have finish downloaded, but might some case, phone data retrieve, but not others?
                    //MAYBE
                    Log.i("finalCheckFlowHere", "2, phone admin pull:" + phoneAdminConstraint);

                    //first check if within network.
                    if (userSSID.equals(ssidConstraint)) {



                        lastSSIDrecorded = userSSID;


                        if (userBSSID.equals(bssidConstraint)) {

                            Toast.makeText(this, "bssid same, ssid :" + userSSID, Toast.LENGTH_LONG).show();

                            presenter.deleteObserver(this);

                            //       if(animation_fingerprint_ended_boolean){
                            setUserTimeStamp(globalAdminNameHere, globalAdminPhoneHere, nameUser, phoneUser, dateAndTimeNow, userLatitude, userLongitude, morningConstraint, eveningConstraint);
                            if (avd != null) {
                                avd.clearAnimationCallbacks();
                            } else if (avd1 != null) {
                                avd1.clearAnimationCallbacks();
                            }

                            // }

                            //26 june
//                            setUserTimeStamp(globalAdminNameHere, globalAdminPhoneHere, nameUser, phoneUser, dateAndTimeNow, userLatitude, userLongitude, morningConstraint, eveningConstraint);


                        } else { //if bssid different might need to check other bssid available by admin.

                            // Toast.makeText(this,"bssid different, bssid "+userBSSID ,Toast.LENGTH_LONG).show();
                            // presenter.deleteObserver(this);

                            Log.i("wherelocationRegister :", "FLOW 8, countVerified:" + countUserverified + " , userLatitude: " + userLatitude);
                            loginWithLocation();

                        }

                    } else { //if outside wifi network. use location instead

                        Log.i("wherelocationRegister :", "FLOW 9, countVerified:" + countUserverified + " , userLatitude: " + userLatitude);


                        Log.i("finalCheckFlowHere", "3, ssid different, location check, latitude admin:"
                                + latitudeConstraint + " , user latitude" + userLatitude);

                        Log.i("finalCheckNetwork", "2, ssid different, ssidUser:"
                                + userSSID + " , ssidAdmin: " + ssidConstraint);

                        Log.i("finalCheckNetwork", "3, ssid different, bssidUser:"
                                + userBSSID + " , bssidConstraint: " + bssidConstraint);


                        //presenter.

                        //so process location., //if user dont provide location, ask user to provide.

                        loginWithLocation();


                    }


                }

            } //another avd test

                //       }
            }

//        } //av1 and avd test

        }

    } //this is to ensure we extracted from top user collection
    else {

            Log.i("checkFinal_18June", "result not finish extracted");
     }


    if(counterFlowHere==250){

        //means stop updating.
        Toast.makeText(this,"please select admin, try again with wifi or location", Toast.LENGTH_LONG).show();
        presenter.deleteObserver(this);
    }

    }

    private void animateFingerPrint(ImageView fingerprint_imageViewhere) {


        //    constrainBox.

        if (boolean_fingerprint_first) {

        Animation fadeOut = AnimationUtils.loadAnimation(FingerPrint_LogIn_Final_Activity.this, R.anim.fadeout);
        constrainBox.startAnimation(fadeOut);
        //constrainBox.setVisibility(View.INVISIBLE);

        //26 june
        //box_success2.setVisibility(View.VISIBLE);
//
        Animation fadeIn = AnimationUtils.loadAnimation(FingerPrint_LogIn_Final_Activity.this, R.anim.fadein);
        box_success2.startAnimation(fadeIn);


        boolean_fingerprint_animate = true;

        timer_finger_animate = new Timer();

        //  timer_handler = new Handler();

//        },0,4000);

        ImageView view = fingerprint_imageViewhere;
        Drawable drawable = view.getDrawable();

        if (drawable instanceof AnimatedVectorDrawableCompat) {

             avd1 = (AnimatedVectorDrawableCompat) drawable;

            avd1.start();




        } else if (drawable instanceof AnimatedVectorDrawable) {

           avd = (AnimatedVectorDrawable) drawable;

            avd.start();


        }

    }
    }

    private void loginWithLocation(){

   //     Log.i("wherelocationRegister :","FLOW 9, countVerified:"+countUserverified+" , userLatitude: "+userLatitude);

        if(userLatitude==null || userLongitude==null||latitudeConstraint==null || longitudeConstraint==null
                ||userLatitude.equals("")||userLongitude.equals("")||latitudeConstraint.equals("")||longitudeConstraint.equals("")||userLatitude.equals("0"))
        {

            counterFlowHere2++;

//            Log.i("finalCheckFlowHere", "4, ssid different, location NULL, latitude admin:"
//                    + latitudeConstraint+" , user latitude"+ userLatitude);
//          //
            // textViewDataLocation.setText("basic flow:"+ counterFlowHere+ " , turn on GPS:"+counterFlowHere2+" , GPS ON:"+ counterFlowHere3);
            // Toast.makeText(this,"please turn on GPS",Toast.LENGTH_LONG).show();
            //this is always excuted.
            //ask user to provide location, turn on GPS.
            //else cannot log in.
            //

            Log.i("wherelocationRegister :","FLOW 10, countVerified:"+countUserverified+" , userLatitude: "+userLatitude);

            if(counterFlowHere2==50){
                Toast.makeText(this,"GPS do not work under building, please find better spot, near window",Toast.LENGTH_LONG).show();
            }

            if(counterFlowHere2==77){
                Toast.makeText(this,"Please ensure internet is Connected",Toast.LENGTH_LONG).show();
            }


            //statusBarWeSet=false;

            if(statusBarWeSet==0) {

                Toast.makeText(this,"please turn ON GPS in Setting "+userBSSID ,Toast.LENGTH_LONG).show();

                statusBarWeSet++;

                //presenter.deleteObserver(this);

                @SuppressLint("WrongConstant") Object sbservice = getSystemService("statusbar");
                Class<?> statusbarManager = null;
                try {
                    statusbarManager = Class.forName("android.app.StatusBarManager");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 17) {
                    try {
                        showsb = statusbarManager.getMethod("expandNotificationsPanel");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        showsb = statusbarManager.getMethod("expand");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    showsb.invoke(sbservice);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
//                        Intent openIntent = new Intent(Intent)

            // presenter.deleteObserver(this);
            // onResume(); // restart process?

        }else {

            counterFlowHere3++;

//            Log.i("wherelocationRegister :","FLOW 10, countVerified:"+countUserverified+" , userLatitude: "+userLatitude);
//
//         //   textViewDataLocation.setText("basic flow:"+ counterFlowHere+ " , turn on GPS:"+counterFlowHere2+" , GPS ON:"+ counterFlowHere3);
//
//            Log.i("finalCheckFlowHere", "5, ssid different, location CHECK, latitude admin:"
//                    + latitudeConstraint+" , user latitude"+ userLatitude);
//



            presenter.removeLocationNow(); //need check here, if location got zero updated before process


            //record current location here, actually latitude

            lastLocationRecorded =  userLatitude;

            //if location provided, process, and check with admin
            Location  user= new Location("point User");
            Location admin = new Location("point Admin");
            if(userLatitude!=null) {

                Log.i("wherelocationRegister :","FLOW 11, countVerified:"+countUserverified+" , userLatitude: "+userLatitude);

                Double userLatitudeDouble = Double.valueOf(userLatitude);
                Double userLongitudeDouble = Double.valueOf(userLongitude);

                Double adminLatitude = Double.valueOf(latitudeConstraint);
                Double adminLongitude = Double.valueOf(longitudeConstraint);



                user.setLatitude(userLatitudeDouble);
                user.setLongitude(userLongitudeDouble);


                admin.setLongitude(adminLongitude);
                admin.setLatitude(adminLatitude);
            }

            Log.i("finalCheckFlowHere", "6, ssid different, location CHECK, latitude admin:"
                    + latitudeConstraint+" , user latitude"+ userLatitude);

            distanceOffset = user.distanceTo(admin);

            if(distanceOffset<=50){  //assume 50 is 50 meter.
                //here can process ask to stamp.

                Log.i("wherelocationRegister :","FLOW 12, countVerified:"+countUserverified+" , userLatitude: "+userLatitude);

                Log.i("finalCheckFlowHere", "7, ssid different, location CHECK, OFFSET RIGHT");

                Toast.makeText(this,"distance within provided, distance: "+ distanceOffset,Toast.LENGTH_LONG).show();


                //26 june


               setUserTimeStamp(globalAdminNameHere,globalAdminPhoneHere,nameUser,phoneUser,dateAndTimeNow,userLatitude,userLongitude,morningConstraint,eveningConstraint);
                if (avd != null) {
                    avd.clearAnimationCallbacks();
                } else if (avd1 != null) {
                    avd1.clearAnimationCallbacks();
                }

                userLatitude=null;

                presenter.removeLocationNow();

                // presenter.deleteObserver(this);
            }else { //means more than 50 meter.

                Log.i("wherelocationRegister :","FLOW 13, countVerified:"+countUserverified+" , userLatitude: "+userLatitude);

                Log.i("finalCheckFlowHere", "8, ssid different, location CHECK, OFFSET OUT");

                counterFlowHere3++;

                Toast.makeText(this,"distance outside provided "+ distanceOffset,Toast.LENGTH_LONG).show();

//                timer_finger_animate.scheduleAtFixedRate(new TimerTask() {
//                    @Override
//                    public void run() {
//                        fingerCounter++;
//
//
//
//                        //if(fingerCounter>=2 && animation_fingerprint_ended_boolean){
//
//                        if(fingerCounter>=2){
//                            setUserTimeStamp(globalAdminNameHere,globalAdminPhoneHere,nameUser,phoneUser,dateAndTimeNow,userLatitude,userLongitude,morningConstraint,eveningConstraint);
//                            timer_finger_animate.cancel();
//                            timer_finger_animate.purge();
//                        }
//
//                    }
//                },0,2700);


                //26 june

                setUserTimeStamp(globalAdminNameHere,globalAdminPhoneHere,nameUser,phoneUser,dateAndTimeNow,userLatitude,userLongitude,morningConstraint,eveningConstraint,true);

                if (avd != null) {
                    avd.clearAnimationCallbacks();
                } else if (avd1 != null) {
                    avd1.clearAnimationCallbacks();
                }


                //recorded location to admin, and send notification to admin.
                //after that ask to time stamp
                //can apply machine learning. //detect location manipulation.

                //can apply machine learning for timestamp process.

                // presenter.deleteObserver(this);
            }

            Log.i("wherelocationRegister :","FLOW 14, countVerified:"+countUserverified+" , userLatitude: "+userLatitude);

            presenter.deleteObserver(this);
            //after delete,
            nameUser=null;
            userLatitude=null;
            phoneAdminConstraint=null;
            locationConstraint=null;
           // presenter.setRemapLocation(null);


        }

        lastSSIDrecorded="--rerun";

        return;

    }

    private void setUserTimeStamp(String adminName,String adminPhone,String userName,String userPhone,String dateAndTimeNow2, String userLatitude2,String userLongitude2, String morningConstraint,String eveningConstraint){

        //need to check morning constraint.
        //with fragment?

        //we passed all data, then reset all back to null. to avoid manipulation.

        Log.i("wherelocationRegister :","FLOW 15, countVerified:"+countUserverified+" , userLatitude: "+userLatitude+" , > morningConstraint: "+morningConstraint+" , > eveningConstraint: "+eveningConstraint);

        animation_fingerprint_ended_boolean=false;

        documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
                .document(userName+userPhone+"doc");

        //dayNow = dateAndTimeNow2.substring(0,3);
        dayNow="Mon";
        timeCurrent = dateAndTimeNow2.substring(11, 13);      //process time current first, by server
        timeCurrent2 = dateAndTimeNow2.substring(14, 16);
        dateNow = dateAndTimeNow2.substring(8,10)+" "+dateAndTimeNow2.substring(4,7);
        timeCurrent = timeCurrent + "." + timeCurrent2; //this output current time.

        //Log.i()

        //process time stamp and constraint
        Float adminMorning = Float.valueOf(morningConstraint);
        Float adminEvening = Float.valueOf(eveningConstraint);

        Float userCurrentTimeStamp = Float.valueOf(timeCurrent);

        Float offsetMorning = userCurrentTimeStamp -adminMorning;


        Log.i("checkDiaglogFragment", "1, time:"+timeCurrent+" , dayNow:"+dayNow+" , date today:"+dateNow);

        Log.i("checkDiaglogFragment", "2, offsetMorning: "+ offsetMorning);


        globalUserPhone=userPhone;
        globalUserName=userName;
        globalAdminName=globalAdminNameHere;
        globalAdminPhone=globalAdminPhoneHere;

        if(offsetMorning>=-3f&&offsetMorning<=3f){ //meaning withing 3 hourse or morning constraint

            String morning = "morning";
            FragmentManager fragmentManager = getSupportFragmentManager();
           // TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow+","+dateNow+" : "+"punch card morning time now?",dateNow+", "+timeCurrent+" AM");
            TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow,dateNow,timeCurrent,userName,userPhone,adminName,adminPhone, morning);

            frag.show(fragmentManager,"frag_name");


            setMorningTimeStamp=true;
        }

        Float offsetEvening = userCurrentTimeStamp-adminEvening;

        Log.i("checkDiaglogFragment", "3, time:"+timeCurrent+" , dayNow:"+dayNow+" , date today:"+dateNow);

        Log.i("checkDiaglogFragment", "4, offsetEvening: "+offsetEvening);


        if(offsetEvening>=-3f && offsetEvening<=3f){//pass as evening timestamp
//
            String evening = "evening";
            FragmentManager fragmentManager = getSupportFragmentManager();
            TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow,dateNow,timeCurrent,userName,userPhone,adminName,adminPhone,evening);

            frag.show(fragmentManager,"frag_name");


            setEveningTimeStamp=true;
        }



        if(!setEveningTimeStamp && !setMorningTimeStamp){ //outside both constraint , MC or stuff like that

            String outsideConstraint = "outsideConstraint";
            Boolean zeroOutTimeStamp = true;
            FragmentManager fragmentManager = getSupportFragmentManager();
            TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow,dateNow,timeCurrent,userName,userPhone,adminName,adminPhone,outsideConstraint, zeroOutTimeStamp);

            frag.show(fragmentManager,"frag_name");


        }

        //if()




        userLatitude=null;
        userLongitude=null;

        //before return, set back to false,
        setEveningTimeStamp =false;
        setMorningTimeStamp = false;

        return;
    }


    //7 july

    private void setUserTimeStamp(String adminName,String adminPhone,String userName,String userPhone,String dateAndTimeNow2, String userLatitude2,String userLongitude2, String morningConstraint,String eveningConstraint, boolean outsideLocation){

        //need to check morning constraint.
        //with fragment?

        //we passed all data, then reset all back to null. to avoid manipulation.

        //Log.i("wherelocationRegister :","FLOW 15, countVerified:"+countUserverified+" , userLatitude: "+userLatitude);

        //9 july

        //if(outsideLocation==false){

        if(outsideLocation){

            Geocoder coder = new Geocoder(FingerPrint_LogIn_Final_Activity.this, Locale.getDefault());

            List<Address> addresses;

            try {
                addresses = coder.getFromLocation(Double.valueOf(userLatitude2),Double.valueOf(userLongitude2),1);

                streetname="";

                streetname = addresses.get(0).getAddressLine(0);

                if(streetname==null || streetname.equals("")){
                    streetname= addresses.get(0).getThoroughfare();
                }



                animation_fingerprint_ended_boolean=false;

                documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                        .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
                        .document(userName+userPhone+"doc");

                //dayNow = dateAndTimeNow2.substring(0,3);
                dayNow="Mon";
                timeCurrent = dateAndTimeNow2.substring(11, 13);      //process time current first, by server
                timeCurrent2 = dateAndTimeNow2.substring(14, 16);
                dateNow = dateAndTimeNow2.substring(8,10)+" "+dateAndTimeNow2.substring(4,7);
                timeCurrent = timeCurrent + "." + timeCurrent2; //this output current time.

                //process time stamp and constraint
                Float adminMorning = Float.valueOf(morningConstraint);
                Float adminEvening = Float.valueOf(eveningConstraint);

                Float userCurrentTimeStamp = Float.valueOf(timeCurrent);

                Float offsetMorning = userCurrentTimeStamp -adminMorning;


                Log.i("checkDiaglogFragment", "1, time:"+timeCurrent+" , dayNow:"+dayNow+" , date today:"+dateNow);

                Log.i("checkDiaglogFragment", "2, offsetMorning: "+ offsetMorning);


                globalUserPhone=userPhone;
                globalUserName=userName;
                globalAdminName=globalAdminNameHere;
                globalAdminPhone=globalAdminPhoneHere;

                if(offsetMorning>=-3f&&offsetMorning<=3f){ //meaning withing 3 hourse or morning constraint

                    String morning = "morning";
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    // TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow+","+dateNow+" : "+"punch card morning time now?",dateNow+", "+timeCurrent+" AM");
                    TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow,dateNow,timeCurrent,userName,userPhone,adminName,adminPhone, morning,streetname);

                    frag.show(fragmentManager,"frag_name");


                    setMorningTimeStamp=true;
                }

                Float offsetEvening = userCurrentTimeStamp-adminEvening;

                Log.i("checkDiaglogFragment", "3, time:"+timeCurrent+" , dayNow:"+dayNow+" , date today:"+dateNow);

                Log.i("checkDiaglogFragment", "4, offsetEvening: "+offsetEvening);


                if(offsetEvening>=-3f && offsetEvening<=3f){//pass as evening timestamp
//
                    String evening = "evening";
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow,dateNow,timeCurrent,userName,userPhone,adminName,adminPhone,evening, streetname);

                    frag.show(fragmentManager,"frag_name");


                    setEveningTimeStamp=true;
                }



                if(!setEveningTimeStamp && !setMorningTimeStamp){ //outside both constraint , MC or stuff like that

                    String outsideConstraint = "outsideConstraint";
                    Boolean zeroOutTimeStamp = true;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow,dateNow,timeCurrent,userName,userPhone,adminName,adminPhone,outsideConstraint, zeroOutTimeStamp, streetname);

                    frag.show(fragmentManager,"frag_name");


                }

                //if()




                userLatitude=null;
                userLongitude=null;

                //before return, set back to false,
                setEveningTimeStamp =false;
                setMorningTimeStamp = false;








            } catch (IOException e) {
                e.printStackTrace();
            }


        }

//        animation_fingerprint_ended_boolean=false;
//
//        documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
//                .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
//                .document(userName+userPhone+"doc");
//
//        dayNow = dateAndTimeNow2.substring(0,3);
//        timeCurrent = dateAndTimeNow2.substring(11, 13);      //process time current first, by server
//        timeCurrent2 = dateAndTimeNow2.substring(14, 16);
//        dateNow = dateAndTimeNow2.substring(8,10)+" "+dateAndTimeNow2.substring(4,7);
//        timeCurrent = timeCurrent + "." + timeCurrent2; //this output current time.
//
//        //process time stamp and constraint
//        Float adminMorning = Float.valueOf(morningConstraint);
//        Float adminEvening = Float.valueOf(eveningConstraint);
//
//        Float userCurrentTimeStamp = Float.valueOf(timeCurrent);
//
//        Float offsetMorning = userCurrentTimeStamp -adminMorning;
//
//
//        Log.i("checkDiaglogFragment", "1, time:"+timeCurrent+" , dayNow:"+dayNow+" , date today:"+dateNow);
//
//        Log.i("checkDiaglogFragment", "2, offsetMorning: "+ offsetMorning);
//
//
//        globalUserPhone=userPhone;
//        globalUserName=userName;
//        globalAdminName=globalAdminNameHere;
//        globalAdminPhone=globalAdminPhoneHere;
//
//        if(offsetMorning>=-3f&&offsetMorning<=3f){ //meaning withing 3 hourse or morning constraint
//
//            String morning = "morning";
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            // TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow+","+dateNow+" : "+"punch card morning time now?",dateNow+", "+timeCurrent+" AM");
//            TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow,dateNow,timeCurrent,userName,userPhone,adminName,adminPhone, morning);
//
//            frag.show(fragmentManager,"frag_name");
//
//
//            setMorningTimeStamp=true;
//        }
//
//        Float offsetEvening = userCurrentTimeStamp-adminEvening;
//
//        Log.i("checkDiaglogFragment", "3, time:"+timeCurrent+" , dayNow:"+dayNow+" , date today:"+dateNow);
//
//        Log.i("checkDiaglogFragment", "4, offsetEvening: "+offsetEvening);
//
//
//        if(offsetEvening>=-3f && offsetEvening<=3f){//pass as evening timestamp
////
//            String evening = "evening";
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow,dateNow,timeCurrent,userName,userPhone,adminName,adminPhone,evening);
//
//            frag.show(fragmentManager,"frag_name");
//
//
//            setEveningTimeStamp=true;
//        }
//
//
//
//        if(!setEveningTimeStamp && !setMorningTimeStamp){ //outside both constraint , MC or stuff like that
//
//            String outsideConstraint = "outsideConstraint";
//            Boolean zeroOutTimeStamp = true;
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            TimeStampCheckFragment frag = TimeStampCheckFragment.newInstance(dayNow,dateNow,timeCurrent,userName,userPhone,adminName,adminPhone,outsideConstraint, zeroOutTimeStamp);
//
//            frag.show(fragmentManager,"frag_name");
//
//
//        }
//
//        //if()
//
//
//
//
//        userLatitude=null;
//        userLongitude=null;
//
//        //before return, set back to false,
//        setEveningTimeStamp =false;
//        setMorningTimeStamp = false;

        return;
    }

    @Override
    protected void onResume() {

   //     statusBarWeSet =true;
        super.onResume();

//        if(mLocationManager.isLocationEnabled()){
//           // statusBarWeSet=true;
//        }

//        Log.i("checkFrag Flow", "1");
//        if(timeFragmentBoolean) {
//            userSSID = null;
//            userBSSID = null;
//            Log.i("checkFrag Flow", "2");
//            userLatitude=null;
//            userLongitude=null;
//            timeFragmentBoolean=false;
//        }


    }

    @Override
    protected void onStart() {
        super.onStart();



    }



    @Override
    protected void onDestroy() {

        presenter.removeLocationNow();
        presenter.deleteObserver(this);


        super.onDestroy();

    }

    //probably unnecessary
    @Override
    public void onSuccess(long timeStampHere) {

        Log.i("checkkTime : ", " 2 "+ timeStampHere);
        this.timeStampthis =timeStampHere;
        return;
    }

    @Override
    public void onFailed() {

        if(dayNow==null) {
            //getBackupTimeFromUser(); , still ask user to make sure, have internet connection

        return;
        }
    }

    // we cant afford to get data from user, since user can offline,
    //and reset next day data.

    private void getBackupTimeFromUser() {

        //getting date time from user

        Log.i("checkFinalFlow : ", " 15 getBackupTimeFromUser(), setup our day without server ");

        Date date2 = new Date();

        Log.i("checkFinalFlow : ", " 15v` getBackupTimeFromUser(), setup our day without server :" + date2);

        Toast.makeText(FingerPrint_LogIn_Final_Activity.this,"time without server is: "+ date2 +" day:"+dayNow, Toast.LENGTH_LONG).show();

        dayNow  = (date2.toString()).substring(0,3);

        return;



    }


}
