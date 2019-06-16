package com.example.afinal.fingerPrint_Login.register.register_with_activity;

import android.os.Handler;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;


//22 May, nothing updated, just checking flow.

public class RegAdmin_Model extends Observable implements RegAdminModel_Interface{

    private boolean returnSimulation;

    private String nameAdminCheck;
    private String phoneAdminCheck;

    private Map<String,Object> dataPulled;

    public RegAdmin_Model(){

        dataPulled= new HashMap<>();

    }

    @Override
    public boolean getFromSimulation(String name, String phone) {
        returnSimulation=false;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                setNewSussesfulFecthData(true);

            }
        },4000);


        return returnSimulation;
    }

    //9 june

    @Override
    public HashMap getIfLogInExist(String name, String phone, String adminname, String adminphone) {

        //return returnLogInInfo;


        DocumentReference documentReference= FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                                                    .document(adminname+adminphone+"doc").collection("all_employee_thisAdmin_collection")
                                                    .document(name+phone+"doc"  );


        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    //we need to pull pin number to check, record of admin 1, or admin 2 if exist,
                    //and save to preferences.

                    Map<String, Object> dataHere = new HashMap<>();

                    dataHere = Objects.requireNonNull(task.getResult()).getData();

                    //Map.Entry<String, Object>  remap = (Map.Entry<String, Object>) task.getResult().getData().entrySet(i8iu8uiuuuuuuu7777777777777777777777777777777y);

                    if (dataHere != null) {
                        for(Map.Entry<String,Object> remap : dataHere.entrySet()){


                            if(remap.getKey().equals("pinCode")){

                            }

                            if(remap.getKey().equals("")){ //this is record for sharedpreferences.

                            }



                        }
                    }


                }


            }
        });




        //question will return empty or null, will it check if .empty.

        return (HashMap) dataPulled;
    }

    @Override
    public boolean returnSimulation() {

        return returnSimulation;

    }


    //this is admin name and phone.
    @Override
    public boolean getFromFirebase(String name1, final String phone) {

        final String name = name1;

        final CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("all_admins_collections");

        Query query_admin = collectionReference.whereEqualTo("phone",phone);

        query_admin.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){

                    QuerySnapshot querySnapshot =task.getResult();

                  //  int size = querySnapshot.size();

                    if(Objects.requireNonNull(querySnapshot).size()==1){ //this means admin is registered, so user can register to this admin.

                        for(QueryDocumentSnapshot documentSnapshot:querySnapshot){

                            Map<String,Object> map;

                            map= documentSnapshot.getData();

                            for(Map.Entry<String,Object> remap: map.entrySet()){  //here we try to pull admin name, and phone to check

                                if(remap.getKey().equals("name")){

                                    nameAdminCheck= remap.getValue().toString();
                                }if(remap.getKey().equals("phone")){
                                    phoneAdminCheck = remap.getValue().toString();
                                }

                            }

                        }

                        if(nameAdminCheck.equals(name)&&phoneAdminCheck.equals(phone)){

//                    returnSimulation =true;
//                    setChanged();
//                    notifyObservers();

                            setNewSussesfulFecthData(true);


                        }else {


                            setNewSussesfulFecthData(false);

                        }






                    }else {


                        //here means either name or phone number for admin is wrong.

                        setNewSussesfulFecthData(false);


                    }





                } //try again, connection problem?




            }


        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

                // noticeString="failed";
                setNewSussesfulFecthData(false);



            }
        });




        return returnSimulation;
    }

    private void setNewSussesfulFecthData(boolean returned) {



        if(returned){

            returnSimulation = true;
            setChanged();
            notifyObservers();

        }else {

            returnSimulation = false;
            setChanged();
            notifyObservers();

            //return;
        }

    }


}
