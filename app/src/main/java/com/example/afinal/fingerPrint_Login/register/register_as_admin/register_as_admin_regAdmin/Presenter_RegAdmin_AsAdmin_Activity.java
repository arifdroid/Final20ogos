package com.example.afinal.fingerPrint_Login.register.register_as_admin.register_as_admin_regAdmin;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import androidx.annotation.NonNull;

public class Presenter_RegAdmin_AsAdmin_Activity extends Observable {

    //22 may


    //16 june, change database structure, remove dependency from offline data, like sharedprefsces.




    private String nameUser_admin;
    private String phoneUser_admin;

    private Context mContext;
    private int allowCreateAdmin;
    private PhoneAuthCredential credential;
    private String sharedPrefs_label;
    private int count_admin_RegAsAdmin;


    //13 june

    private String admin_one;
    private String admin_two;

    public Presenter_RegAdmin_AsAdmin_Activity(Context context){
        this.mContext= context;
        allowCreateAdmin=0;
        admin_one="";
        admin_two="";
        count_admin_RegAsAdmin =1; //by default it is 1, first recorded into shared prefs.



        return;
    }

    //16june

    public void checkCredentialWithUpdates_NewStructure(PhoneAuthCredential phoneAuthCredential, final String name, final String phone){
        this.nameUser_admin = name;
        this.phoneUser_admin = phone;

        Log.i("check_custom_auth","7 credential");

        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //here we check if can create admin.

                if(task.isSuccessful()){

                    Log.i("check_custom_auth","8 credential");

                    final CollectionReference collectionReference_newStructure = FirebaseFirestore.getInstance()
                            .collection("users_top_detail");


                    if(name!=null && phone!=null && !name.equals("") && !phone.equals("")){


                        Query query_new = collectionReference_newStructure.whereEqualTo("phone",phone);

                        query_new.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                                //task.getResult().

                                if(task.isSuccessful()) {

                                    if(!task.getResult().isEmpty()){   //meaning there is document.

                                    //size must always be one. else, fail.


                                    int sizeDoc = task.getResult().getDocuments().size();

                                    if (sizeDoc == 1) { //exist aleady

                                        //here need to pull and read data to know, if one or two admin registered to this user.

                                        Map<String, Object> remap = (Map<String, Object>) task.getResult().getDocuments();


                                        //check balik

                                        for (Map.Entry<String, Object> entry : remap.entrySet()) {

                                            if (entry.getKey().equals("admin_phone_1")) {

                                                //meaning, admin one already registered.
                                                admin_one = entry.getValue().toString();

                                            }

//                                            if (entry.getKey().equals("admin_phone_2")) {
//
//                                                //meaning admin_2 already registered to another admin
//                                                admin_two = entry.getValue().toString();
//
//                                            }
//
//                                          if(entry.getKey().equals("admin_two")){
//
//                                              admin_two = entry.getValue().toString();
//                                          }
                                        }
//
//
//                                      if(!admin_one.equals("")){
//
//                                          //means already one admin registered, correct for admin two position
//
//                                          allowCreateAdmin =2; //means admin 2 should be created.
//
//
//                                      }else {
//
//                                          allowCreateAdmin=1; //means admin1 should be created.
//
//                                      }

                                        //we should assume should create admin 2

                                        //need to check if user already admin. then cannot create
                                        if (admin_one.equals(phone)) { //mean user is already an admin.

                                            //do not allow createion
                                            allowCreateAdmin = 4;
                                            setChanged();
                                            notifyObservers();

                                        }else if(!admin_two.isEmpty()||!admin_two.equals("")){ //this should mean, no more room to register admin


                                            //do not allow createion
                                            allowCreateAdmin = 4;
                                            setChanged();
                                            notifyObservers();

                                        } else {

                                            //though still need checking, since

                                            allowCreateAdmin = 4; // do not allow if exist
                                            setChanged();
                                            notifyObservers();
                                        }

                                        //after finish getting data from map
                                        //then decide create or update


                                    } else if (sizeDoc == 0) { //can create new one here.

                                        allowCreateAdmin = 1;
                                        setChanged();
                                        notifyObservers();


                                    } else {

                                        //false structure

                                        allowCreateAdmin = 3;
                                        setChanged();
                                        notifyObservers();


                                    }


                                } //document should be exist
                                else { //not exist, mean first time registering as user right?

                                    //16 june
                                        allowCreateAdmin = 1;       //not sure which one would run
                                        setChanged();
                                        notifyObservers();
                                }

                                }else {

                                    allowCreateAdmin=3;
                                    setChanged();
                                    notifyObservers();
                                }


                            }
                        }).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {


                                allowCreateAdmin=3;
                                setChanged();
                                notifyObservers();

                            }
                        });

                    }


                }else {

                    allowCreateAdmin=3;
                    setChanged();
                    notifyObservers();


                }

            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

                allowCreateAdmin=3;
                setChanged();
                notifyObservers();

            }
        });

    }


    public void checkCredentialWithUpdates2(PhoneAuthCredential phoneAuthCredential, String name, String phone) {

        this.nameUser_admin=name;
        this.phoneUser_admin=phone;

        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    //check user withing admin document.

                    final CollectionReference collectionReference = FirebaseFirestore.getInstance()
                            .collection("all_admins_collections");


                    if(phoneUser_admin!=null) {
                        Query query1 = collectionReference.whereEqualTo("phone", phoneUser_admin);

                        query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if(task.isSuccessful()){

                                        if(task.getResult().size()==0){

                                            //meaning no admin being registered yet with this number. thus
                                            //here we can add document, but we need to add tag, so that we know which admin
                                            //to pull this

                                            Query queryForLable = collectionReference.whereArrayContains("employee_this_admin",phoneUser_admin);

                                            queryForLable.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                    if(task.isSuccessful()){


                                                        //we could just check from sharedprefs,

                                                        if(task.getResult().size()==1){ //means already a user for another admin,
                                                            //just confirmation.

                                                            //in sharedprefs, count_admin should count to 2.


                                                            count_admin_RegAsAdmin = 2; //mean there already admin registered, so this will be 2

                                                            //labeled as TWO
                                                            Map<String,Object> kk = new HashMap<>();

                                                           // sharedPrefs_label = "com.example.finalV8_punchCard." + phoneUser_admin;
                                                            kk.put("name",nameUser_admin);
                                                            kk.put("phone",phoneUser_admin);
                                                           // kk.put("sharedPrefs_label",sharedPrefs_label);

                                                            //sharedPrefs_label = "TWO";

                                                            DocumentReference reference = FirebaseFirestore.getInstance()
                                                                    .collection("all_admins_collections")
                                                                    .document(nameUser_admin+phoneUser_admin+"doc");




                                                            reference.set(kk).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if(task.isSuccessful()) {

                                                                        allowCreateAdmin=1;
                                                                        setChanged();
                                                                        notifyObservers();



                                                                    }else {

                                                                        allowCreateAdmin=2;
                                                                        setChanged();
                                                                        notifyObservers();
                                                                        //
                                                                        //Toast.makeText(mContext,"please try again",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }).addOnCanceledListener(new OnCanceledListener() {
                                                                @Override
                                                                public void onCanceled() {

                                                                    allowCreateAdmin=2;


                                                                }
                                                            });


                                                        }
                                                        if(task.getResult().size()==0){ // first time admin, user, not user for another admin

                                                            //labeled as ONE

                                                            Map<String,Object> kk = new HashMap<>();

                                                          //  sharedPrefs_label = "com.example.finalV8_punchCard." + phoneUser_admin;

                                                            kk.put("name",nameUser_admin);
                                                            kk.put("phone",phoneUser_admin);
                                                           // kk.put("sharedPrefs_label",sharedPrefs_label); // or dont write it?


                                                            //change label to share preferences

                                                            DocumentReference reference = FirebaseFirestore.getInstance()
                                                                    .collection("all_admins_collections")
                                                                    .document(nameUser_admin+phoneUser_admin+"doc");

                                                            reference.set(kk).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if(task.isSuccessful()) {

                                                                        allowCreateAdmin=1;
                                                                        setChanged();
                                                                        notifyObservers();



                                                                    }else {

                                                                        allowCreateAdmin=2;
                                                                        setChanged();
                                                                        notifyObservers();
                                                                        //
                                                                        //Toast.makeText(mContext,"please try again",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }).addOnCanceledListener(new OnCanceledListener() {
                                                                @Override
                                                                public void onCanceled() {

                                                                    allowCreateAdmin=2;


                                                                }
                                                            });


                                                        }

                                                        else { // other error., somehow more than 1



                                                        }

                                                    }
                                                    else { //task fail, need to check again


                                                    }
                                                }
                                            }).addOnCanceledListener(new OnCanceledListener() {
                                                @Override
                                                public void onCanceled() {



                                                }
                                            });
//
//
//
//                                            Map<String,Object> kk = new HashMap<>();
//
//                                            kk.put("name",nameUser_admin);
//                                            kk.put("phone",phoneUser_admin);
//
//
//                                            DocumentReference reference = FirebaseFirestore.getInstance()
//                                                    .collection("all_admins_collections")
//                                                    .document(nameUser_admin+phoneUser_admin+"collection");
//
//
//
//
//                                            reference.set(kk).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//
//                                                    if(task.isSuccessful()) {
//
//                                                        allowCreateAdmin=1;
//                                                        setChanged();
//                                                        notifyObservers();
//
//
//
//                                                    }else {
//
//                                                         allowCreateAdmin=2;
//                                                        setChanged();
//                                                        notifyObservers();
//                                                        //
//                                                        //Toast.makeText(mContext,"please try again",Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            }).addOnCanceledListener(new OnCanceledListener() {
//                                                @Override
//                                                public void onCanceled() {
//
//                                                    allowCreateAdmin=2;
//
//
//                                                }
//                                            });

                                        } else {
                                            //toast not forward
                                            allowCreateAdmin=3;
                                            setChanged();
                                            notifyObservers();
                                            //Toast.makeText(mContext,"please try again",Toast.LENGTH_SHORT).show();

                                            //already exist.
                                        }


                                }else {

                                    allowCreateAdmin=2;
                                    setChanged();
                                    notifyObservers();
                                    //
                                    //toast not forward
                                }

                            }
                        }).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {

                                allowCreateAdmin=2;
                                setChanged();
                                notifyObservers();
                                //
                            }
                        });




                    }


                }else { //task unsuccessful

                    allowCreateAdmin=2;
                    setChanged();
                    notifyObservers();
                    //
                }
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

            }
        });

        return;
    }

    public int getIfDocumentCreated(){

        Log.i("check_custom_auth","9 credential, return allowCreateAdmin : "+ allowCreateAdmin);
        return allowCreateAdmin;
    }

    //count admin record

    public int getCount_admin_RegAsAdmin(){
        return count_admin_RegAsAdmin;
    }

    public void getCredentialWithUpdates(String codeUserAdminEnter, String codeFromFirebase) {

        Log.i("check_custom_auth","3 credential");
        credential = PhoneAuthProvider.getCredential(codeUserAdminEnter,codeFromFirebase);

        if(credential!=null){
            setChanged();
            notifyObservers();
        }

    }

    public PhoneAuthCredential getCredential() {
        Log.i("check_custom_auth","4 credential");
        return credential;
    }
}
