package com.example.afinal.fingerPrint_Login.register.register_as_admin_setupProfile;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;



import com.example.afinal.fingerPrint_Login.PassResult;
import com.example.afinal.fingerPrint_Login.register.PassResultMap;
import com.example.afinal.fingerPrint_Login.register.TimePickerFragment;
import com.example.afinal.fingerPrint_Login.register.WifiReceiver;
import com.example.afinal.fingerPrint_Login.register.register_as_admin_add_userList.Add_User_Activity;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.afinal.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;



public class RegAdmin_asAdmin_Profile_Activity extends AppCompatActivity implements Observer, TimePickerDialog.OnTimeSetListener {

    //

    //setup listener
    private PassResult passResult;
    private boolean eveningBooleanSet;
    private boolean morningBooleanSet;
    private boolean imagetest;
    private Uri urihere;
    private String count_admin;
    private String image_url;

    public void setPassResult(PassResult passResult){
        this.passResult = passResult;
    }

    //this is for returning from fragment, problem is, what method gets called after fragment.

    ///

    private CircleImageView circleImageView;

    private TextView textViewName, textViewPhone;

    private RecyclerView recyclerView;

    private RecyclerView_Admin_Profile_Adapter recyclerView_Admin_Profile_Adapter;

    private LocationManager mLocationManager;

    private LocationListener mLocationListener;

    private ArrayList<AdminDetail> adminDetailsList;

    private ArrayList<AdminDetail> returnAdminDetailList;

    WifiManager wifiManager;

    WifiInfo wifiInfo;

    String streetName;

    private FloatingActionButton floatingActionButton;

//    private final int REQUEST_LOCATION_PERMISSION = 1;

    //static final for image

    private static final int READ_REQUEST_CODE = 42;

    //setup firebase storage reference to test

    private StorageReference storageReference;

    private FirebaseFirestore firebaseFirestore;

    private Presenter_RegAdmin_asAdmin_Profile_Activity presenter;
    private boolean imageSetupTrue;
    private Map<String,String> locationMapFinal;
    private String user_name_asAdmin;
    private String user_phone_asAdmin;
    public static String wifiSSIDHere;
    public static String wifiBSSIDHere;
    private boolean gotWifi;
    private Timer timer;
    private int count;
    private WifiReceiver wifiReceiver;
    private Double latitudeHere;
    private Double longitudeHere;
    private String morning_constraint;
    private String evening_constraint;
    public static String hour;
    public static String minute;

    private TextView textTryAgain;

    private TextView locationtesttextview;

    //28 may button test

   // private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_admin_as_admin__profile_);

        morningBooleanSet =false;
        eveningBooleanSet =false;

        locationtesttextview= findViewById(R.id.textView_location_setprofile);

        textTryAgain = findViewById(R.id.textView_tryagain_setup_profile_id);


        imagetest = false;

        wifiReceiver = new WifiReceiver();

        Intent intent = getIntent();
        gotWifi=false;
        wifiSSIDHere="";
        wifiBSSIDHere="";

        user_name_asAdmin = intent.getStringExtra("adminName_asAdmin");
        user_phone_asAdmin = intent.getStringExtra("adminPhone_asAdmin");

        count_admin = intent.getStringExtra("admin_count"); //who
//
        image_url = intent.getStringExtra("image_url");


        Log.i("downloadimagehere,", "name :"+user_name_asAdmin+ " , phone :"+user_phone_asAdmin);

        //storageReference = (intent.getStringExtra("image_ref_asAdmin"));


        //23 may check

        storageReference = FirebaseStorage.getInstance().getReference().child("uploads").child("picture"+ user_name_asAdmin+user_phone_asAdmin);
//

        //storageReference.getDownloadUrl().getResult()

        //

        adminDetailsList = new ArrayList<>();

        count++;

        locationMapFinal=new HashMap<>();
//
//        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        wifiInfo = wifiManager.getConnectionInfo();

        imageSetupTrue=false;

        presenter = new Presenter_RegAdmin_asAdmin_Profile_Activity(this);


        returnAdminDetailList = new ArrayList<>();

        floatingActionButton = findViewById(R.id.admin_Profile_fButtoniD);
        circleImageView = findViewById(R.id.admin_Profile_circleImageViewID);
        textViewName = findViewById(R.id.admin_Profile_textViewNameiD);
        textViewPhone = findViewById(R.id.admin_Profile_textViewPhoneiD);


        if(user_name_asAdmin!=null && user_phone_asAdmin!=null){

            textViewName.setText(user_name_asAdmin);
            textViewPhone.setText(user_phone_asAdmin);
        }

        presenter.addObserver(this);

        //firebase reference


        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //process data getWifi
        String wifiName = checkWifiStep1();
        String wifiBssid = checkBSSIDStep();

        //get Location

        //requestLocationPermission();

        presenter.requestLocationPermission(mLocationManager);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textTryAgain.setVisibility(View.INVISIBLE);

                int count = 0;

                //also check for location. address.

                if (streetName!=null && !streetName.equals("")) {


                    if (morningBooleanSet && eveningBooleanSet) {

 //                   if (imageSetupTrue) {

                        for (AdminDetail adminDetail : returnAdminDetailList) {

                            Log.i("checkkLocation", "13 checkBox = " + adminDetail.isCheckBox());

                            boolean checkBoxHere = adminDetail.isCheckBox();

                            Log.i("checkkLocation", "14 checkBox = " + adminDetail.isCheckBox());

                            if (checkBoxHere) {
                                count++;
                            }

                        }
                        //problem is count always 1, inevitable, keep


                        //if (count == 1 && RecyclerView_Admin_Profile_Adapter.sentCheck == false) {
                        if (count == 0) {

                        } else {

                            if ((count) == adminDetailsList.size()) {

                                Toast.makeText(RegAdmin_asAdmin_Profile_Activity.this, "all " + count + " boxes checked", Toast.LENGTH_SHORT).show();



                                //13 june, 2 things change, sharedprefs, change, and create documet

                                //"users_top_detail"

                                Map<String, Object> mapUserTopDetail = new HashMap<>();
                                Map<String, Object> mapForAdminOnly = new HashMap<>();


                                mapUserTopDetail.put("phone", user_phone_asAdmin);
                                mapUserTopDetail.put("admin_count", count_admin);
                                mapUserTopDetail.put("admin_phone_"+count_admin,user_phone_asAdmin);
                                mapUserTopDetail.put("admin_name_"+count_admin,user_name_asAdmin);
                                mapUserTopDetail.put("user_name_"+count_admin, user_name_asAdmin);

                                //this can be either admin_phone_1 or admin_phone_2




                                CollectionReference cR_AllUser = FirebaseFirestore.getInstance().collection("users_top_detail");

                                DocumentReference dR_User_Top = cR_AllUser.document(user_phone_asAdmin+"imauser");

                                //set this

                                if(count_admin.equals("2")) {

                                    //dR_User_Top.set(mapUserTopDetail, SetOptions.merge());

                                    //merge existing one.

                                }else {

                                     //create new one, else
                                    dR_User_Top.set(mapUserTopDetail);
                                }

                                CollectionReference cR_admin_only= FirebaseFirestore.getInstance()
                                        .collection("all_admins_collections");

                                DocumentReference dR_admin_only = cR_admin_only.document(user_name_asAdmin+user_phone_asAdmin+"doc");

                                mapForAdminOnly.put("phone",user_phone_asAdmin);
                                mapForAdminOnly.put("name",user_name_asAdmin);


                                dR_admin_only.set(mapForAdminOnly);



                                //then for admin, set another collection



                                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                                        .document(user_name_asAdmin + user_phone_asAdmin + "doc");

                                //here add all to

                                Map<String, Object> mapUserAsAdmin = new HashMap<>();
                                mapUserAsAdmin.put("name", user_name_asAdmin);
                                mapUserAsAdmin.put("phone", user_phone_asAdmin);
                                mapUserAsAdmin.put("ssid", wifiSSIDHere);
                                mapUserAsAdmin.put("bssid", wifiBSSIDHere);

                                String latitudeString = String.valueOf(latitudeHere);

                                String longitudeString = String.valueOf(longitudeHere);

                                mapUserAsAdmin.put("latitude", latitudeString);

                                mapUserAsAdmin.put("longitude", longitudeString);

                                mapUserAsAdmin.put("morning_constraint", morning_constraint);

                                mapUserAsAdmin.put("evening_constraint", evening_constraint);

                                mapUserAsAdmin.put("admin_street_name", streetName);

                                documentReference.set(mapUserAsAdmin);


                                DocumentReference documentReferenceAdmin_asUser = documentReference.collection("all_employee_thisAdmin_collection")
                                            .document(user_name_asAdmin+user_phone_asAdmin+"doc");

                                final DocumentReference documentReferenceAdmin_asUser_2 = documentReference.collection("all_employee_thisAdmin_collection")
                                        .document("dummy"+user_phone_asAdmin+"doc");


                                final Map<String, Object> mapAdminAsUser = new HashMap<>();
                                mapAdminAsUser.put("name", user_name_asAdmin);
                                mapAdminAsUser.put("phone", user_phone_asAdmin);
                                //mapAdminAsUser.put("rating", wifiSSIDHere);
                                mapAdminAsUser.put("ts_mon_morning", "");
                                mapAdminAsUser.put("ts_tue_morning", "");
                                mapAdminAsUser.put("ts_wed_morning", "");
                                mapAdminAsUser.put("ts_thu_morning", "");
                                mapAdminAsUser.put("ts_fri_morning", "");
                                mapAdminAsUser.put("ts_mon_evening", "");
                                mapAdminAsUser.put("ts_tue_evening", "");
                                mapAdminAsUser.put("ts_wed_evening", "");
                                mapAdminAsUser.put("ts_thu_evening", "");
                                mapAdminAsUser.put("ts_fri_evening", "");

                                //location

                                mapAdminAsUser.put("loc_mon_morning", "");
                                mapAdminAsUser.put("loc_tue_morning", "");
                                mapAdminAsUser.put("loc_wed_morning", "");
                                mapAdminAsUser.put("loc_thu_morning", "");
                                mapAdminAsUser.put("loc_fri_morning", "");
                                mapAdminAsUser.put("loc_mon_evening", "");
                                mapAdminAsUser.put("loc_tue_evening", "");
                                mapAdminAsUser.put("loc_wed_evening", "");
                                mapAdminAsUser.put("loc_thu_evening", "");
                                mapAdminAsUser.put("loc_fri_evening", "");

                                //mapAdminAsUser.put("ts_mon_evening", "");

                                mapAdminAsUser.put("status","admin");

                                mapAdminAsUser.put("mon_date","");
                                mapAdminAsUser.put("tue_date","");
                                mapAdminAsUser.put("wed_date","");
                                mapAdminAsUser.put("thu_date","");
                                mapAdminAsUser.put("fri_date","");

                                mapAdminAsUser.put("image_url",image_url);

                                /////////////////


                                final Map<String, Object> mapAdminAsUser_2 = new HashMap<>();
                                mapAdminAsUser_2.put("name", "dummy");
                                mapAdminAsUser_2.put("phone", user_phone_asAdmin);
                                //mapAdminAsUser.put("rating", wifiSSIDHere);
                                mapAdminAsUser_2.put("ts_mon_morning", "");
                                mapAdminAsUser_2.put("ts_tue_morning", "");
                                mapAdminAsUser_2.put("ts_wed_morning", "");
                                mapAdminAsUser_2.put("ts_thu_morning", "");
                                mapAdminAsUser_2.put("ts_fri_morning", "");
                                mapAdminAsUser_2.put("ts_mon_evening", "");
                                mapAdminAsUser_2.put("ts_tue_evening", "");
                                mapAdminAsUser_2.put("ts_wed_evening", "");
                                mapAdminAsUser_2.put("ts_thu_evening", "");
                                mapAdminAsUser_2.put("ts_fri_evening", "");

                                //location

                                mapAdminAsUser_2.put("loc_mon_morning", "");
                                mapAdminAsUser_2.put("loc_tue_morning", "");
                                mapAdminAsUser_2.put("loc_wed_morning", "");
                                mapAdminAsUser_2.put("loc_thu_morning", "");
                                mapAdminAsUser_2.put("loc_fri_morning", "");
                                mapAdminAsUser_2.put("loc_mon_evening", "");
                                mapAdminAsUser_2.put("loc_tue_evening", "");
                                mapAdminAsUser_2.put("loc_wed_evening", "");
                                mapAdminAsUser_2.put("loc_thu_evening", "");
                                mapAdminAsUser_2.put("loc_fri_evening", "");

                                //mapAdminAsUser.put("ts_mon_evening", "");

                                mapAdminAsUser_2.put("status","admin");

                                mapAdminAsUser_2.put("mon_date","");
                                mapAdminAsUser_2.put("tue_date","");
                                mapAdminAsUser_2.put("wed_date","");
                                mapAdminAsUser_2.put("thu_date","");
                                mapAdminAsUser_2.put("fri_date","");

                                mapAdminAsUser_2.put("image_url",image_url);


                                documentReferenceAdmin_asUser.set(mapAdminAsUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){


                                            documentReferenceAdmin_asUser_2.set((mapAdminAsUser_2)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){


                                                        Intent intent = new Intent(RegAdmin_asAdmin_Profile_Activity.this, Add_User_Activity.class);
                                                        intent.putExtra("adminName_asAdmin",user_name_asAdmin);
                                                        intent.putExtra("adminPhone_asAdmin",user_phone_asAdmin);
                                                        startActivity(intent);



                                                    }


                                                }
                                            }).addOnCanceledListener(new OnCanceledListener() {
                                                @Override
                                                public void onCanceled() {

                                                }



                                            });



                                        } else {

                                          Toast.makeText(RegAdmin_asAdmin_Profile_Activity.this,"please wait 5 seconds and try again.", Toast.LENGTH_SHORT).show();

                                          textTryAgain.setVisibility(View.VISIBLE);

                                        }
                                    }
                                }).addOnCanceledListener(new OnCanceledListener() {
                                    @Override
                                    public void onCanceled() {

                                        Toast.makeText(RegAdmin_asAdmin_Profile_Activity.this,"please wait 5 seconds and try again.", Toast.LENGTH_SHORT).show();

                                        textTryAgain.setVisibility(View.VISIBLE);

                                    }
                                });

                            } else {

                                Toast.makeText(RegAdmin_asAdmin_Profile_Activity.this, "only " + count + " boxes checked , size list " + adminDetailsList.size(), Toast.LENGTH_SHORT).show();

                            }

                        }

//                    } else { //please setup image
//
//                        Toast.makeText(RegAdmin_asAdmin_Profile_Activity.this, "please set image", Toast.LENGTH_SHORT).show();
//                    }
                } else { //set morning and evening time

                    Toast.makeText(RegAdmin_asAdmin_Profile_Activity.this, "please set both morning check in and evening check out", Toast.LENGTH_SHORT).show();
                }

            } else{

                Toast.makeText(RegAdmin_asAdmin_Profile_Activity.this, "please turn on GPS", Toast.LENGTH_SHORT).show();
            }
            }
        });


        //populate data
        if (wifiName != null) {
            populateData(wifiName, wifiBssid, streetName);
        }
        //recyclerView
        //initRecycler();



        Intent intentWifi = new Intent();

        intentWifi.setAction("com.example.afinal.fingerPrint_Login.register.WifiReceiver");
        sendBroadcast(intentWifi);

        // https://www.journaldev.com/10356/android-broadcastreceiver-example-tutorial

        // https://stackoverflow.com/questions/5888502/how-to-detect-when-wifi-connection-has-been-established-in-android


        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if(task.isSuccessful()){

                    //image_url=task.getResult().getPath();

                    urihere = task.getResult();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Picasso.with(RegAdmin_asAdmin_Profile_Activity.this).load(urihere).into(circleImageView);
                        }
                    });

                }

            }
        });


    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deleteObserver(this);
    }




//
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void populateData(String wifiName, String wifiBssid, String streetName) {

        //adminDetailsList.add(new AdminDetail())

        //circleImageView.setImageResource(R.drawable.ic_location_on_black_24dp);

        Log.i("checkkLocation", "6");

        adminDetailsList.add(new AdminDetail("please connect to your wifi network", "drawable/wifi_list_icon"));
        adminDetailsList.add(new AdminDetail("please connect to your wifi network", "drawable/router_list"));
        adminDetailsList.add(new AdminDetail("click here to set morning time","drawable/ic_am_list_icon_2nd"));

        adminDetailsList.add(new AdminDetail("click here to set evening time","drawable/ic_pm_list_icon"));
        initRecycler();


//        DialogFragment timepickerFragment = new TimePickerFragment();
//
//        timepickerFragment.show(this.getSupportFragmentManager(),);
    }




    private void initRecycler() {

        recyclerView = findViewById(R.id.admin_Profile_recyclerViewiD);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_Admin_Profile_Adapter = new RecyclerView_Admin_Profile_Adapter(RegAdmin_asAdmin_Profile_Activity.this, adminDetailsList);

        Log.i("checkkLocation", "8");

        recyclerView.setAdapter(recyclerView_Admin_Profile_Adapter);
        recyclerView_Admin_Profile_Adapter.setPassResult_checkBox_interface(new PassResult_CheckBox_Interface() {
            @Override
            public void passingArray(ArrayList<AdminDetail> adminDetails) {
                //returned list.

                returnAdminDetailList = adminDetails;

            }
        });

        //setting up wifi if not initially setup.
        presenter.getWifiNow();

    }

    private String checkBSSIDStep() {


        String bssidName ="";
        return bssidName;
    }

    private String checkWifiStep1() {


        String name = "";



        return name;
    }

    @Override
    public void update(Observable observable, Object o) {

        Log.i("checkFlowData ", "1");



        if(observable instanceof Presenter_RegAdmin_asAdmin_Profile_Activity){
            Log.i("checkFlowData ", "2");
            //keep listening for location, wifi provided, and

            Map<String,String> locationHere = ((Presenter_RegAdmin_asAdmin_Profile_Activity) observable).getRemapReturnLocation();

            if(locationHere!=null){
                Log.i("checkFlowData ", "3 , location not null");
                Geocoder geocoder = new Geocoder(RegAdmin_asAdmin_Profile_Activity.this, Locale.getDefault());

                latitudeHere =null;
                longitudeHere =null;

                for(Map.Entry<String,String> kk: locationHere.entrySet()){

                    if(kk.getKey().equals("latitudeHere")){

                      //  locationMapFinal.put("latitudeFinal" ,kk.getValue());
                        latitudeHere = Double.valueOf(kk.getValue());

                        locationtesttextview.setText("lat : "+latitudeHere);
                    }
                    if(kk.getKey().equals("longitudeHere")){

                      //  locationMapFinal.put("longitudeFinal", kk.getValue());
                        longitudeHere = Double.valueOf(kk.getValue());
                    }


                    if(latitudeHere!=null && longitudeHere!=null ) {
                        try {

                            streetName = geocoder.getFromLocation(latitudeHere, longitudeHere, 1).get(0).getAddressLine(0);

                            if(streetName==null || streetName.equals("")){
                                streetName = geocoder.getFromLocation(latitudeHere, longitudeHere, 1).get(0).getThoroughfare();
                            }


                            if(streetName!=null || streetName.equals("")) {

                                if(adminDetailsList.size()<=4) {
                                    adminDetailsList.add(new AdminDetail(streetName, "drawable/location_list"));
                                    recyclerView_Admin_Profile_Adapter.notifyDataSetChanged();
                                    // recyclerView.setAdapter(recyclerView_Admin_Profile_Adapter);
                                    recyclerView_Admin_Profile_Adapter.setPassResult_checkBox_interface(new PassResult_CheckBox_Interface() {
                                        @Override
                                        public void passingArray(ArrayList<AdminDetail> adminDetails) {
                                            //returned list.

                                            returnAdminDetailList = adminDetails;
                                        }
                                    });
                                }else {
                                    presenter.stopListening(mLocationManager);

                                }
                            }

                            Log.i("checkFlowData ", "4 , streetName: "+streetName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//

//                        }
                        presenter.stopListening(mLocationManager);
                    }
                }


            }

            Boolean wifiHere = ((Presenter_RegAdmin_asAdmin_Profile_Activity) observable).getReturnMapWifi();

            if(wifiHere){

                Log.i("checkFlowData ", "4 , wifi status: "+wifiHere);


                Map<String,String> wifiresult = ((Presenter_RegAdmin_asAdmin_Profile_Activity) observable).getwifiResult();


                for(Map.Entry<String,String> kk : wifiresult.entrySet()){

                    Log.i("checkFlowData ", "5 , wifi status: "+kk.getValue());

                    if(kk.getKey().equals("SSID")){

                        String wifi = kk.getValue();

                        while (wifi.contains("\"")){

                            wifi= wifi.replace("\"","");
                        }

                        wifiSSIDHere = wifi;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //this being written despite the fact data is empty.

//                                if(wifiSSIDHere!=null|| !wifiSSIDHere.equals("")) {
//                                    adminDetailsList.get(0).setTextShow(wifiSSIDHere);
//
//                                    recyclerView_Admin_Profile_Adapter.notifyDataSetChanged();
//                                }

                                if(wifiSSIDHere!=null) {
                                    adminDetailsList.get(0).setTextShow(wifiSSIDHere);

                                    recyclerView_Admin_Profile_Adapter.notifyDataSetChanged();
                                }


                                if(wifiSSIDHere.endsWith("ssid>")) {
                                    adminDetailsList.get(0).setTextShow("please connect to admin wifi");

                                    recyclerView_Admin_Profile_Adapter.notifyDataSetChanged();
                                }


                               // adminDetailsList.get(0).setTextShow(wifiSSIDHere);

                            }
                        });
                    }
                    if(kk.getKey().equals("BSSID")){

                        wifiBSSIDHere = kk.getValue();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


//
//                                if(wifiBSSIDHere!=null|| !wifiBSSIDHere.equals("")) {
//                                    adminDetailsList.get(1).setTextShow(wifiBSSIDHere);
//                                }

                                if(wifiBSSIDHere!=null) {
                                    adminDetailsList.get(1).setTextShow(wifiBSSIDHere);
                                    recyclerView_Admin_Profile_Adapter.notifyDataSetChanged();
                                }

//                                adminDetailsList.get(1).setTextShow(wifiBSSIDHere);
//                                recyclerView_Admin_Profile_Adapter.notifyDataSetChanged();
                            }
                        });
                    }

                }


            }else{ //wifi is not here



            }




        }

    }

    private PassResultMap passResultMap;

    public void setPassResultMap(PassResultMap passResultMap){
        this.passResultMap = passResultMap;
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        this.hour =String.valueOf(i);
        this.minute =String.valueOf(i1);

        //need to access current label

        int labelHere = TimePickerFragment.getLabel();

        if(minute.length()<=1){
            minute="0"+minute;
        }


        if(labelHere==2){

            if(Integer.valueOf(hour)<12){

                Log.i("checkTime", "2");

                adminDetailsList.get(2).setTextShow("AM checkin: "+hour+":"+minute);
                morning_constraint=hour+"."+minute;
                recyclerView_Admin_Profile_Adapter.notifyDataSetChanged();

                morningBooleanSet=true;

            }
            else {

                Toast.makeText(this,"please check AM in time picker, try again", Toast.LENGTH_LONG).show();
            }

        }if(labelHere==3){

            Log.i("checkTime", "3");

            adminDetailsList.get(3).setTextShow("PM checkout: "+hour+":"+minute);
            evening_constraint=hour+"."+minute;
            recyclerView_Admin_Profile_Adapter.notifyDataSetChanged();
            eveningBooleanSet=true;

        }



    }
}
