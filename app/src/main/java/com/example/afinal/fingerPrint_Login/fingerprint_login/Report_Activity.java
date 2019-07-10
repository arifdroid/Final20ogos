package com.example.afinal.fingerPrint_Login.fingerprint_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.oop.TestTimeStamp;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Report_Activity extends AppCompatActivity {

    private String nameHere;
    private String phoneHere;
    private String adminname;

    private CollectionReference collectionReference;


    private RecyclerView recyclerView;
    private TextView textViewNamePhone, textViewDate;

    private LinearLayoutManager layoutManager;
    private ArrayList<TestTimeStamp> lateList;

    private RecyclerAdapterReport recyclerAdapter;

    //to add to lateList
    private TestTimeStamp object_to_add;
    private String dateFromPhone;
    private CollectionReference collectionReference2;
    private DocumentReference documentReference_forAdmin;
    private String morning_constraint, evening_constraint;
    private boolean boolean_finish_constraint;
    private int sizeObject;
    private HashMap<String, Object> objectMap;

    private ArrayList<SingleTimeStamp> lissFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_);

        // https://stackoverflow.com/questions/29730402/how-to-convert-android-view-to-pdf

        boolean_finish_constraint = false;

        recyclerView = findViewById(R.id.recyclerID_report);

        textViewNamePhone = findViewById(R.id.textView_name_phone_report);

        textViewDate = findViewById(R.id.textView_date_report);

        layoutManager = new LinearLayoutManager(Report_Activity.this);

        initRecycler();


        Intent intent = getIntent();

        if(intent!=null){


            @SuppressLint("SimpleDateFormat") final DateFormat format2 = new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");

            dateFromPhone = format2.format(new Date());

            dateFromPhone = dateFromPhone.substring(0,6);



            nameHere = intent.getStringExtra("name");
            phoneHere = intent.getStringExtra("phone");
            adminname = intent.getStringExtra("adminname");
        //    String date = intent.getStringExtra("datee");

            textViewNamePhone.setText("Admin: "+ adminname+ " Phone: "+phoneHere);

            textViewDate.setText(dateFromPhone + " 2019");

            Log.i("checkReport ","date : "+ dateFromPhone);



            collectionReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections").document(adminname+phoneHere+"doc")
                                .collection("all_employee_thisAdmin_collection");

            documentReference_forAdmin = FirebaseFirestore.getInstance().collection("all_admin_doc_collections").document(adminname+phoneHere+"doc");

            documentReference_forAdmin.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful()){

                        if(task.getResult().exists()){

                            Map<String,Object> remap = task.getResult().getData();

                            for(Map.Entry<String,Object> kk : remap.entrySet()){

                                if(kk.getKey().equals("morning_constraint")){

                                    morning_constraint = kk.getValue().toString();

                                }
                                if(kk.getKey().equals("evening_constraint")){

                                   evening_constraint = kk.getValue().toString();

                                }
                            }

                            //here finish loop, got value. set boolean

                            if(!evening_constraint.isEmpty() && !morning_constraint.isEmpty()){

                                collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if(task.isSuccessful()){

                                            if(!task.getResult().isEmpty()){


                                                for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){


                                                    Map<String,Object> remap = documentSnapshot.getData();

                                                    object_to_add = new TestTimeStamp();
                                                    objectMap = new HashMap<>();

                                                    for(Map.Entry<String,Object> kk : remap.entrySet()){

                                                        if(kk.getKey().equals("name")){

                                                            object_to_add.setName(kk.getValue().toString());
                                                            objectMap.put("name",kk.getValue().toString() );

                                                        }

                                                        if (kk.getKey().equals("ts_mon_morning")) { //this is never set? why?

                                                            String mon_morning =  kk.getValue().toString();

                                                            object_to_add.setMon_morning(mon_morning);
                                                            objectMap.put("ts_mon_morning",kk.getValue().toString());
                                                            object_to_add.setProb_mon_morning(true);

                                                            Log.i("checkTimeStamp ", "flow: 6" + mon_morning);


                                                        }

                                                        if (kk.getKey().equals("ts_tue_morning")) {

                                                            //try to get

                                                            //remap into single array of object.
                                                            //Float tue_morning = (Float) kk.getValue();
                                                            String tue_morning =  kk.getValue().toString();

                                                            object_to_add.setTue_morning(tue_morning);
                                                            object_to_add.setProb_tue_morning(true);


                                                            Log.i("checkTimeStamp ", "flow: 7" + tue_morning);

                                                        }

                                                        if (kk.getKey().equals("ts_wed_morning")) {

                                                            //try to get

                                                            //remap into single array of object.
                                                            //Float wed_morning = (Float) kk.getValue();

                                                            String wed_morning =kk.getValue().toString();

                                                            object_to_add.setWed_morning(wed_morning);
                                                            object_to_add.setProb_wed_morning(true);


                                                        }

                                                        if (kk.getKey().equals("ts_thu_morning")) {

                                                            //try to get

                                                            //remap into single array of object.
                                                            //Float thu_morning = (Float) kk.getValue();
                                                            String thu_morning = kk.getValue().toString();


                                                            object_to_add.setThu_morning(thu_morning);
                                                            object_to_add.setProb_thu_morning(true);

                                                        }

                                                        if (kk.getKey().equals("ts_fri_morning")) {

                                                            String fri_morning = kk.getValue().toString();
                                                            object_to_add.setFri_morning(fri_morning);
                                                            object_to_add.setProb_fri_morning(true);


                                                        }

                                                        //>> here fetch evening data.

                                                        if(kk.getKey().equals("ts_mon_evening")){

                                                            String mon_evening = kk.getValue().toString();
                                                            //object
                                                            object_to_add.setMon_evening(mon_evening);
                                                            object_to_add.setProb_mon_evening(true);

                                                        }

                                                        if(kk.getKey().equals("ts_tue_evening")){

                                                            String tue_evening = kk.getValue().toString();
                                                            //object
                                                            object_to_add.setTue_evening(tue_evening);
                                                            object_to_add.setProb_tue_evening(true);
                                                        }
                                                        if(kk.getKey().equals("ts_wed_evening")){

                                                            String wed_evening = kk.getValue().toString();
                                                            //object
                                                            object_to_add.setWed_evening(wed_evening);
                                                            object_to_add.setProb_wed_evening(true);
                                                        }
                                                        if(kk.getKey().equals("ts_thu_evening")){

                                                            String thu_evening = kk.getValue().toString();
                                                            //object
                                                            object_to_add.setThu_evening(thu_evening);
                                                            object_to_add.setProb_thu_evening(true);
                                                        }
                                                        if(kk.getKey().equals("ts_fri_evening")){

                                                            String fri_evening = kk.getValue().toString();
                                                            //object
                                                            object_to_add.setFri_evening(fri_evening);
                                                            object_to_add.setProb_fri_evening(true);
                                                        }

                                                        if(kk.getKey().equals("mon_date")){

                                                            object_to_add.setMon_date(kk.getValue().toString());

                                                        }


                                                        if(kk.getKey().equals("tue_date")){

                                                            object_to_add.setTue_date(kk.getValue().toString());

                                                        }


                                                        if(kk.getKey().equals("wed_date")){

                                                            object_to_add.setWed_date(kk.getValue().toString());

                                                        }

                                                        if(kk.getKey().equals("thu_date")){

                                                            object_to_add.setThu_date(kk.getValue().toString());

                                                        }

                                                        if(kk.getKey().equals("fri_date")){

                                                            object_to_add.setFri_date(kk.getValue().toString());

                                                        }


                                                        //location

                                                        if(kk.getKey().equals("loc_mon_morning")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationMon(kk.getValue().toString());
                                                        }


                                                        if(kk.getKey().equals("loc_tue_morning")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationTue(kk.getValue().toString());
                                                        }


                                                        if(kk.getKey().equals("loc_wed_morning")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationWed(kk.getValue().toString());
                                                        }

                                                        if(kk.getKey().equals("loc_thu_morning")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationThu(kk.getValue().toString());
                                                        }

                                                        if(kk.getKey().equals("loc_fri_morning")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationFri(kk.getValue().toString());
                                                        }

                                                        if(kk.getKey().equals("loc_mon_evening")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationMon_evening(kk.getValue().toString());
                                                        }


                                                        if(kk.getKey().equals("loc_tue_evening")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationTue_evening(kk.getValue().toString());
                                                        }


                                                        if(kk.getKey().equals("loc_wed_evening")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationWed_evening(kk.getValue().toString());
                                                        }

                                                        if(kk.getKey().equals("loc_thu_evening")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationThu_evening(kk.getValue().toString());
                                                        }

                                                        if(kk.getKey().equals("loc_fri_evening")){
                                                            //this could be empty. or zero
                                                            object_to_add.setLocationFri_evening(kk.getValue().toString());
                                                        }



                                                    }

                                                    sizeObject = object_to_add.size(); //didnt know we could do this.

                                                    if(lateList.get(0).getName()==null) {

                                                        lateList.remove(0);
                                                    }

                                                    lateList.add(object_to_add);
                                                    //                  recyclerAdapter.notifyDataSetChanged();


                                                }

                                                //here we process the data.

                                                int i;
                                                int j;

                                                //lateList.get(0)

                                                //we could create .size function in testtimestamp.
                                                //or we could direct, since it is always mon,tue...

                                                for(i=0;i<=lateList.size();i++){

                                                    SingleTimeStamp singleTimeStamp = new SingleTimeStamp();
                                                    SingleTimeStamp singleTimeStampMonday = new SingleTimeStamp();
                                                    SingleTimeStamp singleTimeStampMonday_evening = new SingleTimeStamp();



                                                    SingleTimeStamp singleTimeStampTuesday = new SingleTimeStamp();

                                                    singleTimeStampMonday.setName(lateList.get(i).getName());

                                                    for(Map.Entry<String,Object> jj : objectMap.entrySet()){




                                                        if(jj.getKey().equals("ts_mon_morning")){
                                                           singleTimeStampMonday.setClock(jj.getValue().toString());

                                                           singleTimeStampMonday.setAmOrPm("AM");

                                                        }

                                                        if(jj.getKey().equals("loc_mon_morning")){

                                                            singleTimeStampMonday.setStreet(jj.getValue().toString());

                                                        }

                                                        if(jj.getKey().equals("mon_date")){
                                                            singleTimeStampMonday.setDate(jj.getValue().toString());
                                                            singleTimeStampMonday_evening.setDate(jj.getValue().toString());
                                                        }



                                                        if(jj.getKey().equals("ts_mon_evening")){
                                                            singleTimeStampMonday_evening.setClock(jj.getValue().toString());

                                                            singleTimeStampMonday_evening.setAmOrPm("PM");

                                                        }

                                                        if(jj.getKey().equals("loc_mon_evening")){

                                                            singleTimeStampMonday_evening.setStreet(jj.getValue().toString());

                                                        }




                                                        if(jj.getKey().equals("ts_tue_morning")){
                                                            singleTimeStampTuesday.setClock(jj.getValue().toString());

                                                        }



                                                    }

                                                    lissFinal = new ArrayList<>();

                                                    lissFinal.add(singleTimeStampMonday);


//                                                    for(j=0;j<=sizeObject;j++){
//
//                                                        //if(lateList.get(i).getMon_morning())
//
//                                                     for(Map.Entry<String,Object> jj : sizeObject)
//
//
//
//                                                    }

                                                }







                                                Log.i("checkReport ","size : "+ lateList.size());
                                                Log.i("checkReport ","size : "+ lateList.get(0).getName());

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        recyclerAdapter.notifyDataSetChanged();

//                                recyclerView.setAdapter(recyclerAdapter);

                                                        Log.i("checkReport ","after dataset change : "+ lateList.get(0).getName());
//



                                                    }
                                                });


                                            }else {

                                                //mean document empty somehow.

                                            }


                                        }else {



                                        }
                                    }
                                }).addOnCanceledListener(new OnCanceledListener() {
                                    @Override
                                    public void onCanceled() {



                                    }
                                });


                            }

                        }
                        else {

                        }

                    }


                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {

                }
            });





        }
    }

    private void initRecycler() {

        recyclerView.setLayoutManager(layoutManager);

        lateList = new ArrayList<>();

        lateList.add(new TestTimeStamp());

        recyclerAdapter = new RecyclerAdapterReport(Report_Activity.this,lateList);

        recyclerView.setAdapter(recyclerAdapter);


    }


    private String getDateToday(){

        //need to process date, but, will it finish in time, need to check.






        return "";
    }
}
