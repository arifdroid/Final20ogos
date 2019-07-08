package com.example.afinal.fingerPrint_Login.fingerprint_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.afinal.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Report_Activity extends AppCompatActivity {

    private String nameHere;
    private String phoneHere;
    private String adminname;

    private CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_);

        // https://stackoverflow.com/questions/29730402/how-to-convert-android-view-to-pdf

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
}
