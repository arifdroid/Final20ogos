package com.example.afinal.fingerPrint_Login.fingerprint_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_);

        // https://stackoverflow.com/questions/29730402/how-to-convert-android-view-to-pdf

        recyclerView = findViewById(R.id.recyclerID_report);

        textViewNamePhone = findViewById(R.id.textView_name_phone_report);

        textViewDate = findViewById(R.id.textView_date_report);

        layoutManager = new LinearLayoutManager(Report_Activity.this);

        initRecycler();


        Intent intent = getIntent();

        if(intent!=null){


            nameHere = intent.getStringExtra("name");
            phoneHere = intent.getStringExtra("phone");
            adminname = intent.getStringExtra("adminname");


            collectionReference = FirebaseFirestore.getInstance().collection("all_admin_doc_collections").document(adminname+phoneHere+"doc")
                                .collection("all_employee_thisAdmin_collection");




            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(task.isSuccessful()){

                    if(!task.getResult().isEmpty()){


                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){


                            Map<String,Object> remap = documentSnapshot.getData();

                            object_to_add = new TestTimeStamp();

                            for(Map.Entry<String,Object> kk : remap.entrySet()){

                                if(kk.getKey().equals("name")){

                                    object_to_add.setName(kk.getValue().toString());

                                }

                                if (kk.getKey().equals("ts_mon_morning")) { //this is never set? why?

                                    String mon_morning =  kk.getValue().toString();

                                    object_to_add.setMon_morning(mon_morning);

                                    Log.i("checkTimeStamp ", "flow: 6" + mon_morning);


                                }

                                if (kk.getKey().equals("ts_tue_morning")) {

                                    //try to get

                                    //remap into single array of object.
                                    //Float tue_morning = (Float) kk.getValue();
                                    String tue_morning =  kk.getValue().toString();

                                    object_to_add.setTue_morning(tue_morning);

                                    Log.i("checkTimeStamp ", "flow: 7" + tue_morning);

                                }

                                if (kk.getKey().equals("ts_wed_morning")) {

                                    //try to get

                                    //remap into single array of object.
                                    //Float wed_morning = (Float) kk.getValue();

                                    String wed_morning =kk.getValue().toString();

                                    object_to_add.setWed_morning(wed_morning);

                                }

                                if (kk.getKey().equals("ts_thu_morning")) {

                                    //try to get

                                    //remap into single array of object.
                                    //Float thu_morning = (Float) kk.getValue();
                                    String thu_morning = kk.getValue().toString();


                                    object_to_add.setThu_morning(thu_morning);
                                }

                                if (kk.getKey().equals("ts_fri_morning")) {

                                    String fri_morning = kk.getValue().toString();
                                    object_to_add.setFri_morning(fri_morning);

                                }

                                //>> here fetch evening data.

                                if(kk.getKey().equals("ts_mon_evening")){

                                    String mon_evening = kk.getValue().toString();
                                    //object
                                    object_to_add.setMon_evening(mon_evening);
                                }

                                if(kk.getKey().equals("ts_tue_evening")){

                                    String tue_evening = kk.getValue().toString();
                                    //object
                                    object_to_add.setTue_evening(tue_evening);
                                }
                                if(kk.getKey().equals("ts_wed_evening")){

                                    String wed_evening = kk.getValue().toString();
                                    //object
                                    object_to_add.setWed_evening(wed_evening);
                                }
                                if(kk.getKey().equals("ts_thu_evening")){

                                    String thu_evening = kk.getValue().toString();
                                    //object
                                    object_to_add.setThu_evening(thu_evening);
                                }
                                if(kk.getKey().equals("ts_fri_evening")){

                                    String fri_evening = kk.getValue().toString();
                                    //object
                                    object_to_add.setFri_evening(fri_evening);
                                }




                            }

                        }

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

    private void initRecycler() {

        recyclerView.setLayoutManager(layoutManager);

        lateList = new ArrayList<>();

        recyclerAdapter = new RecyclerAdapterReport(Report_Activity.this,lateList);

        recyclerView.setAdapter(recyclerAdapter);


    }
}
