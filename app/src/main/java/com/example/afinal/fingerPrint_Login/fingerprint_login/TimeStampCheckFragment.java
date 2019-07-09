package com.example.afinal.fingerPrint_Login.fingerprint_login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.main_activity_fragment.Main_BottomNav_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

//https://guides.codepath.com/android/using-dialogfragment

public class TimeStampCheckFragment extends DialogFragment {

    private static DocumentReference documentReference;
    private TextView textViewAsk,textViewTime;
    private Button buttonCheckin, buttonCheckOut;
    private String timestampnow;
    private String morOrEveNow;
    private boolean zeroOut;
    private String day;
    private String date2;
    private String amOrPM;
    private Map<String, Object> kk = new HashMap<>(); //careful dont want to ducplicate
    private String streetOutside;


    public TimeStampCheckFragment() {
    }


    public static TimeStampCheckFragment newInstance(String day, String date,String timeStamp,String userName, String userPhone,String adminName,String adminPhone,String monOrEve, Boolean zeroOut){
        TimeStampCheckFragment timeStampCheckFragment = new TimeStampCheckFragment();
        Bundle args = new Bundle();
        args.putString("day",day);
        args.putString("date2",date);
        args.putString("timestamp",timeStamp);
        args.putString("monOrEve",monOrEve);
        args.putBoolean("zeroOut",zeroOut);

        documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
                .document(userName+userPhone+"doc");


        timeStampCheckFragment.setArguments(args);

        return timeStampCheckFragment;
    }


    //7 july

    public static TimeStampCheckFragment newInstance(String day, String date,String timeStamp,String userName, String userPhone,String adminName,String adminPhone,String morOrEve, String streetNameOutside){
        TimeStampCheckFragment timeStampCheckFragment = new TimeStampCheckFragment();
        Bundle args = new Bundle();
        args.putString("day",day);
        args.putString("date2",date);
        args.putString("timestamp",timeStamp);
        args.putString("morOrEve",morOrEve);
        args.putString("streetname", streetNameOutside);

        documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
                .document(userName+userPhone+"doc");


        timeStampCheckFragment.setArguments(args);

        return timeStampCheckFragment;
    }


    //7 july, outside timestamp, and outside location
    public static TimeStampCheckFragment newInstance(String day, String date,String timeStamp,String userName, String userPhone,String adminName,String adminPhone,String morOrEve, Boolean zeroOut, String streetnameOutside){
        TimeStampCheckFragment timeStampCheckFragment = new TimeStampCheckFragment();
        Bundle args = new Bundle();
        args.putString("day",day);
        args.putString("date2",date);
        args.putString("timestamp",timeStamp);
        args.putString("morOrEve",morOrEve);
        args.putBoolean("zeroOut",zeroOut);
        args.putString("streetname",streetnameOutside);

        documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
                .document(userName+userPhone+"doc");


        timeStampCheckFragment.setArguments(args);

        return timeStampCheckFragment;
    }




    public static TimeStampCheckFragment newInstance(String day, String date,String timeStamp,String userName, String userPhone,String adminName,String adminPhone,String morOrEve){
        TimeStampCheckFragment timeStampCheckFragment = new TimeStampCheckFragment();
        Bundle args = new Bundle();
        args.putString("day",day);
        args.putString("date2",date);
        args.putString("timestamp",timeStamp);
        args.putString("morOrEve",morOrEve);

        documentReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                .document(adminName+adminPhone+"doc").collection("all_employee_thisAdmin_collection")
                .document(userName+userPhone+"doc");


        timeStampCheckFragment.setArguments(args);

        return timeStampCheckFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.cardview_morning_evening_timestamp, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


         amOrPM = "";

        textViewAsk = view.findViewById(R.id.textView2);
        buttonCheckin = view.findViewById(R.id.cardview_PunchIntiD);
        buttonCheckOut = view.findViewById(R.id.cardview_PunchOutiD);
        textViewTime = view.findViewById(R.id.textViewCardView_TimeFragmentiD);

        day = getArguments().getString("day", "");
        // getDialog().setTitle(question);

        //7 july

        streetOutside = getArguments().getString("streetname", "");

        if (streetOutside != null || !streetOutside.equals("")) {

            textViewAsk.setText("is this your location ? " + streetOutside);

        } else {


        streetOutside="location within perimeter";

//a
         date2 = getArguments().getString("date2", "");

        timestampnow = getArguments().getString("timestamp", "");

        morOrEveNow = getArguments().getString("morOrEve", "");

        if (morOrEveNow.equals("morning")) {
            amOrPM = "AM";
        } else {
            amOrPM = "PM";
        }

        zeroOut = getArguments().getBoolean("zeroOut", false);

        textViewAsk.setText(day + "," + date2 + ": Do you want to punch card this " + morOrEveNow + ",");
        textViewTime.setText("at " + timestampnow + " " + amOrPM + " ?");


        textViewAsk.requestFocus(); //what is this?
        textViewTime.requestFocus();

    }

        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        buttonCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we need data like, admin name , admin phone, user name, user phone, undoubtly time stamp. and day.
//                Map<String, Object> kk = new HashMap<>();

                if (streetOutside != null || !streetOutside.equals("") && (date2.equals("") || date2 == null)) {


                    kk.put("outsideLocation", streetOutside);

                    date2 = getArguments().getString("date2", "");

                    timestampnow = getArguments().getString("timestamp", "");

                    morOrEveNow = getArguments().getString("morOrEve", "");

                    if (morOrEveNow.equals("morning")) {
                        amOrPM = "AM";
                    } else {
                        amOrPM = "PM";
                    }

                    zeroOut = getArguments().getBoolean("zeroOut", false);

                    textViewAsk.setText(day + "," + date2 + ": Do you want to punch card this " + morOrEveNow + ",");
                    textViewTime.setText("at " + timestampnow + " " + amOrPM + " ?");


                    textViewAsk.requestFocus(); //what is this?
                    textViewTime.requestFocus();
                } else {


           //     }

                Log.i("onclickFinal", "1");

               // Map<String, Object> kk = new HashMap<>();

                if (morOrEveNow.equals("morning")) {



                    Log.i("onclickFinal", "2");

                    if (day != null) {

                        Log.i("onclickFinal", "3");
                        if (zeroOut) {
                            timestampnow = "0";
                        }

                        if (day.equals("Mon")) {

                            kk.put("ts_mon_morning", timestampnow);
                            kk.put("mon_date",date2);
                            kk.put("loc_mon_morning", streetOutside);

                        } else if (day.equals("Tue")) {

                            kk.put("ts_tue_morning", timestampnow);
                            kk.put("tue_date",date2);
                            kk.put("loc_tue_morning", streetOutside);


                        } else if (day.equals("Wed")) {

                            kk.put("ts_wed_morning", timestampnow);
                            kk.put("wed_date",date2);
                            kk.put("loc_wed_morning", streetOutside);

                        } else if (day.equals("Thu")) {

                            kk.put("ts_thu_morning", timestampnow);
                            kk.put("thu_date",date2);
                            kk.put("loc_thu_morning", streetOutside);


                        } else if (day.equals("Fri")) {

                            kk.put("ts_fri_morning", timestampnow);
                            kk.put("fri_date",date2);
                            kk.put("loc_fri_morning", streetOutside);


                        }

                    }

                } else if (morOrEveNow.equals("evening")) { //evening time stamp.

                    if (zeroOut) {

                        timestampnow = "0";
                    }

                    if (day != null) {

                        if (day.equals("Mon")) {

                            kk.put("ts_mon_evening", timestampnow);
                            kk.put("mon_date",date2);
                            kk.put("loc_mon_evening", streetOutside);

                        } else if (day.equals("Tue")) {

                            kk.put("ts_tue_evening", timestampnow);
                            kk.put("tue_date",date2);
                            kk.put("loc_tue_evening", streetOutside);

                        } else if (day.equals("Wed")) {

                            kk.put("ts_wed_evening", timestampnow);
                            kk.put("wed_date",date2);
                            kk.put("loc_wed_evening", streetOutside);

                        } else if (day.equals("Thu")) {

                            kk.put("ts_thu_evening", timestampnow);
                            kk.put("thu_date",date2);
                            kk.put("loc_thu_evening", streetOutside);

                        } else if (day.equals("Fri")) {

                            kk.put("ts_fri_evening", timestampnow);
                            kk.put("fri_date",date2);
                            kk.put("loc_fri_evening", streetOutside);

                        }
                    }


                }

                if (kk != null) {

                    documentReference.set(kk, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {


                                Toast.makeText(getActivity(), "punch card recorded succesfully", Toast.LENGTH_SHORT).show();
                                //intent to next activity,

                                Intent intent = new Intent(getActivity(), Main_BottomNav_Activity.class);

                                //17 june

                                intent.putExtra("today", day);
                                intent.putExtra("date2", date2);
                                //intent.putExtra()
                                //intent.getStringExtra("username",)

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);


                            } else { //task not succesfull, ask user to try time stamp again.

                                Toast.makeText(getActivity(), "punch card failed attempt, please try again", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getActivity(), FingerPrint_LogIn_Final_Activity.class); //can we call same activity,
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                //intent call back original activity, we need to clear all data.

                            }
                        }
                    });
                }
            }

        }
        });

        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // just intent to view data. //do we create new task or instance of activity?

                Toast.makeText(getActivity(),"No data recorded", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), Main_BottomNav_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        FingerPrint_LogIn_Final_Activity.timeFragmentBoolean=true;

    }


}
