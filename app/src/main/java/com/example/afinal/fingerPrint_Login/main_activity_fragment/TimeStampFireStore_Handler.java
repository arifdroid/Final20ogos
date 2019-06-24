package com.example.afinal.fingerPrint_Login.main_activity_fragment;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.Upload;
import com.example.afinal.fingerPrint_Login.fingerprint_login.FingerPrint_LogIn_Final_Activity;
import com.example.afinal.fingerPrint_Login.oop.TestTimeStamp;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import static java.security.AccessController.getContext;

public class TimeStampFireStore_Handler  extends Observable {

    private final CollectionReference collectionReferenceTest17june;
    private CollectionReference collectionReferenceTest;
    private List<Entry> entriesV2;

    // try return list of entry
    private List<Entry> entriesV3;

    private LineDataSet dataSet;

    private LineData data;

    private LineChart chart;

    private Context mContext;

    private ArrayList<TestTimeStamp> testTimeStampsList;

    //document size

    int sizeDoc;

    //final return

    private ArrayList<ArrayList<Entry>> listof_entryList;

    private String userName, userPhone,adminName,adminPhone;
    private String namehere;
    private String phoneHere;
    private Uri urlImage;
    private TestTimeStamp object;
    private int i;
    private int j;
    private ArrayList<TestTimeStamp> testTimeStampsList3;


    public TimeStampFireStore_Handler(Context context,LineDataSet dataSet, LineData data, LineChart chart) {
        this.mContext = context;
        this.chart = chart;
        this.data = data;
        this.dataSet=dataSet;

        adminName= FingerPrint_LogIn_Final_Activity.globalAdminName;
        adminPhone = FingerPrint_LogIn_Final_Activity.globalAdminPhone;
        userName = FingerPrint_LogIn_Final_Activity.globalUserName;
        userPhone = FingerPrint_LogIn_Final_Activity.globalUserPhone;

        entriesV2 = new ArrayList<>();
        entriesV3 = new ArrayList<>();

        testTimeStampsList3 = new ArrayList<>();

        testTimeStampsList = new ArrayList<>();

        collectionReferenceTest = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                .document("ariff+60190_doc")
                .collection("all_employee_thisAdmin_collection");

        //17 june

//        date, name admin, name user
        collectionReferenceTest17june = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                .document(adminName+adminPhone+"doc")
                .collection("all_employee_thisAdmin_collection");



        Log.i("20_june", "[Handler 1]");



    }


    //decide to fetch which data.
    public void startFecthData(){

        //18 june, we start to load images.

        Log.i("20_june", "[Handler 1] > [ fecthdata() 1 ]");

        collectionReferenceTest17june.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                //17 june we need to add date, into our document

                if (task.isSuccessful()) {

                    Log.i("20_june", "[Handler 1] > [ fecthdata() 2 ] > task success");

                    i = 0;
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                    sizeDoc = task.getResult().size();


                        Log.i("20_june", "[Handler 1] > [ fecthdata() 3 ] > task success :, size doc :"+sizeDoc);


                        Map<String, Object> map;
                        map = documentSnapshot.getData();

                        i++;
                         object = new TestTimeStamp(i); //setting object, with index i, as reference.
                        //what we want to do, we set each object, as references that we want to extract.

                        // then setup return array list of array list of entries,

                        for (Map.Entry<String, Object> kk : map.entrySet()) {

                            Log.i("checkChartFlowFinal ", "handler, 3");

                            //17 june >>>>>>>>>>>>>>

                            if(kk.getKey().equals("image_url")){

                                object.setImage_url(kk.getValue().toString());

                            }

                            if (kk.getKey().equals("mon_date")) {

                                String mondate = kk.getValue().toString();

                                object.setMon_date(mondate);

                            }

                            if (kk.getKey().equals("tue_date")) {

                                String tuedate = kk.getValue().toString();

                                object.setTue_date(tuedate);

                            }

                            if (kk.getKey().equals("wed_date")) {

                                String weddate = kk.getValue().toString();

                                object.setWed_date(weddate);

                            }

                            if (kk.getKey().equals("thu_date")) {

                                String thudate = kk.getValue().toString();

                                object.setThu_date(thudate);

                            }

                            if (kk.getKey().equals("fri_date")) {

                                String fridate = kk.getValue().toString();

                                object.setFri_date(fridate);

                            }


                            // >>>>>>>>>>>>>>>>

                            if (kk.getKey().equals("name")) {
                                //first access value might be ts morning, hence, when found name, this field not available anymore
                                //this is the problem, since we have to retrieve evrything now.
                                //lets try first

                                 namehere = kk.getValue().toString();

                                Log.i("checkTimeStamp ", "flow: special 99 : " + namehere);


                                    object.setName(namehere);
                            } // end name hash

                            //18 june

                            if(kk.getKey().equals("phone")){

                                 phoneHere = kk.getValue().toString();



                                //object.setUrlCreation();
                            }




                            if (kk.getKey().equals("ts_mon_morning")) { //this is never set? why?

                                //try to get

                                //remap into single array of object.
                                //Float mon_morning = (Float) kk.getValue();

                                String mon_morning =  kk.getValue().toString();

                                object.setMon_morning(mon_morning);

                                Log.i("checkTimeStamp ", "flow: 6" + mon_morning);


                            }

                            if (kk.getKey().equals("ts_tue_morning")) {

                                //try to get

                                //remap into single array of object.
                                //Float tue_morning = (Float) kk.getValue();
                                String tue_morning =  kk.getValue().toString();

                                object.setTue_morning(tue_morning);

                                Log.i("checkTimeStamp ", "flow: 7" + tue_morning);

                            }

                            if (kk.getKey().equals("ts_wed_morning")) {

                                //try to get

                                //remap into single array of object.
                                //Float wed_morning = (Float) kk.getValue();

                                String wed_morning =kk.getValue().toString();

                                object.setWed_morning(wed_morning);

                            }

                            if (kk.getKey().equals("ts_thu_morning")) {

                                //try to get

                                //remap into single array of object.
                                //Float thu_morning = (Float) kk.getValue();
                                String thu_morning = kk.getValue().toString();


                                object.setThu_morning(thu_morning);
                            }

                            if (kk.getKey().equals("ts_fri_morning")) {

                                String fri_morning = kk.getValue().toString();
                                object.setFri_morning(fri_morning);

                            }

                            //>> here fetch evening data.

                            if(kk.getKey().equals("ts_mon_evening")){

                                String mon_evening = kk.getValue().toString();
                                //object
                                object.setMon_evening(mon_evening);
                            }

                            if(kk.getKey().equals("ts_tue_evening")){

                                String tue_evening = kk.getValue().toString();
                                //object
                                object.setTue_evening(tue_evening);
                            }
                            if(kk.getKey().equals("ts_wed_evening")){

                                String wed_evening = kk.getValue().toString();
                                //object
                                object.setWed_evening(wed_evening);
                            }
                            if(kk.getKey().equals("ts_thu_evening")){

                                String thu_evening = kk.getValue().toString();
                                //object
                                object.setThu_evening(thu_evening);
                            }
                            if(kk.getKey().equals("ts_fri_evening")){

                                String fri_evening = kk.getValue().toString();
                                //object
                                object.setFri_evening(fri_evening);
                            }

                            if(kk.getKey().equals("phone")){

                                object.setPhone(kk.getValue().toString());
                            }

                        } //end hash-map loop


                        Log.i("20_june", "[Handler 1] > [ fecthdata() 4 ] > FINISH LOOP document ");


                        Log.i("20_june", "[Loop Handler] , i :"+i+" name:"+object.getName()+" , phone:"+object.getPhone());


                        //21 june , //wait for all object to be setup first, then we process to get the value of the image url


                        testTimeStampsList.add(object);



//                        //  testTimeStampsList.add(object);
//
//                        StorageReference storage = FirebaseStorage.getInstance().getReference().child("uploads").child("picture"+object.getName()+object.getPhone());
//
//
//                        //as expected, not finish loading in time.
//
//                        //urlImage = storage.getDownloadUrl().getResult();
//
//
//                        // >>>>>>>>>> 20 june
////
//                        storage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                          //      try get uri here.
//
//                                if(task.isSuccessful()){
//
//                                        urlImage = task.getResult();
//
//                                        object.setUrlCreation(urlImage);
//
//                                        testTimeStampsList.add(object); //after finish getting all data, then we add to array
//
//                                    if(testTimeStampsList.size()==3) {
//                                        Log.i("20_june", "[Handler] > INSIDE DOWNLOAD" + " ,, name 1) " + testTimeStampsList.get(0).getName()
//
//                                                + " ,, name 2) " + testTimeStampsList.get(1).getName() + " ,, name 3) " + testTimeStampsList.get(2).getName());
//
//                                    }
//
//                                    if(sizeDoc==i) { //meaning, finish loop all document, then we can update returned result, return true with result.
//
//                                        Log.i("checkChartFlowFinal ", "handler, 5");
//
//                                        // setReturnData();
//
//                                        // setReturnEntry();
//
//                                        // problem this never true
//
//                                        if (sizeDoc == testTimeStampsList.size()) {
//
//                                        setReturnListOfEntry(testTimeStampsList);
//
//                                    }
//
//                                    }
//
//
//
//                                }
//                                else {
//
//
//
//                                }
//                            }
//                        }).addOnCanceledListener(new OnCanceledListener() {
//                            @Override
//                            public void onCanceled() {
//
//                            }
//                        });
//
//
//                        // >>>>>>>>>> 20 june UPP
//


                        Log.i("checkTimeStamp ", "flow: 10");




                    } //document loop



                    //21 june, get the


                    if (sizeDoc == testTimeStampsList.size()) {

                        //this means, we finish get all data, but no image just yet, now we get the image url


                       // getThemPicture(testTimeStampsList);


                        setReturnListOfEntry(testTimeStampsList);

                    }


//

//
//                    }


                    //20 june, change place inside downloading picture loop

//                    if(sizeDoc==i){ //meaning, finish loop all document, then we can update returned result, return true with result.
//
//                        Log.i("20_june", "[Handler check size ], sizedocument: "+sizeDoc+ " , i:"+i + " > SAME");
//
//
//                        Log.i("checkChartFlowFinal ", "handler, 5");
//
//                        // setReturnData();
//
//                       // setReturnEntry();
//
//                        setReturnListOfEntry(testTimeStampsList);
//
//
//                    }
//
//                    else {
//
//                        Log.i("20_june", "[Handler check size ], sizedocument: "+sizeDoc+ " , i:"+i + " > NOT SAME");
//                    }

                } //end task loop
                else {

                    Log.i("20_june", "[Handler] task not successful ");

                }//task not successful

            }


        });

        return;

     // return false;

} // end method

    // 21 june.

    private void getThemPicture(final ArrayList<TestTimeStamp> testTimeStampsList2) {

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("uploads");

        Log.i("21_june","[ OUTSIDE LOOP ] > list size: "+ testTimeStampsList2.size()+" name 1:"+testTimeStampsList2.get(0).getName()+ ", name 2:" + testTimeStampsList2.get(1).getName());

            for(j =0; j<testTimeStampsList2.size();j++){

                storageReference= storageReference.child("picture"+testTimeStampsList2.get(j).getName()+testTimeStampsList2.get(j).getPhone());

                testTimeStampsList2.get(j).setStorageReference(storageReference);

//                Log.i("21_june","[ INSIDE LOOP ] > list size: "+ testTimeStampsList2.size()+" name 1:"+testTimeStampsList2.get(0).getName()+ ", name 2:" + testTimeStampsList2.get(1).getName());
//
//                if(j==1){
//
//                    Log.i("21_june","[ INSIDE J ] > list size: "+ testTimeStampsList2.size()+" name j 1:"+testTimeStampsList2.get(0).getName()+ ", name j 2:" + testTimeStampsList2.get(j).getName());
//                }
//

                //storageReference.getDownloadUrl().

//                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//
//                        if(task.isSuccessful()){
//
//                            Log.i("21_june","[ INSIDE TASK SUCCESS ] > list size: "+ testTimeStampsList2.size()+" name j 1:"+testTimeStampsList2.get(0).getName());
//
//
//                            //testTimeStampsList2.get(j).setUrlCreation(task.getResult());
//
//                            testTimeStampsList2.get(j).getName();
//
//
//                        }
//
//                        if(task.isComplete()){
//
//
//                            if(j==testTimeStampsList.size()){
//
//                                setChanged();
//                                notifyObservers();
//                            }
//
//                        }
//
//
//                    }
//                });
            }


            //while loop

//            while(testTimeStampsList3.size()!=testTimeStampsList2.size()){
//
//
//
//
//
//
//
//            }


            if(testTimeStampsList2.size()==testTimeStampsList.size()){

                this.testTimeStampsList=testTimeStampsList2;

                setChanged();

                notifyObservers();

            }



    }

    private void setReturnListOfEntry(ArrayList<TestTimeStamp> testTimeStampsList) {

        //then we want to populate entry.
        //int k=0;


        //sizeDoc == document amount found in collection, equal to number or employee or user registered to the admin.
        // testtimestamplist.

        if(testTimeStampsList.size()==sizeDoc){


            Log.i("20_june", "[Handler] > set to return. sizeDoc: "+sizeDoc +" , list: "+testTimeStampsList.size()+" >> SAME");

            Log.i("20_june", "[Handler] > SAME" +" ,, name 1) "+testTimeStampsList.get(0).getName()

            +" ,, name 2) "+testTimeStampsList.get(1).getName()+" ,, name 3) "+testTimeStampsList.get(2).getName());


            //testTimeStampsList

       //     this.testTimeStampsList = testTimeStampsList;
            setChanged();
            notifyObservers();

            //return;

        }
        else {



            Log.i("20_june", "[Handler] > set to return. sizeDoc: "+sizeDoc +" , list: "+testTimeStampsList.size()+" >> NOT SAME");

            Log.i("20_june", "[Handler] 99 >  NOT SAME : "+ testTimeStampsList.get(i));


            //Log.i("20_june", "[Handler] > NOT SAME sizeDoc & timestamplist , timestamplist : "+ testTimeStampsList.get(0).getName() );
        }


    }

    public ArrayList<TestTimeStamp> reTURNFINAL(){

        Log.i("20_june", "[Handler] > RETURN RESULT , name:"+ testTimeStampsList.get(0).getName()+ " ,, name 2"+ testTimeStampsList.get(1).getName());


        return testTimeStampsList;
    }



} //end class
