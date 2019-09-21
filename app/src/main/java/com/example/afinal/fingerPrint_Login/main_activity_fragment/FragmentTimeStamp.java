package com.example.afinal.fingerPrint_Login.main_activity_fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.customclass.OurLayoutManager;
import com.example.afinal.fingerPrint_Login.fingerprint_login.FingerPrint_LogIn_Final_Activity;
import com.example.afinal.fingerPrint_Login.oop.EntryMorning;
import com.example.afinal.fingerPrint_Login.oop.MC_Null_TestTimeStamp;
import com.example.afinal.fingerPrint_Login.oop.TestTimeStamp;
import com.github.lzyzsd.randomcolor.RandomColor;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentTimeStamp extends Fragment implements Observer, View.OnClickListener {

    // https://javadoc.jitpack.io/com/github/PhilJay/MPAndroidChart/v3.0.3/javadoc/com/github/mikephil/charting/data/BaseEntry.html#setIcon-android.graphics.drawable.Drawable-

    private LineChart chart;
    private int loopCount;
    private int seconds;
    private boolean startRun;
    private Handler mTimerHandler;
    private Timer mTimer1;
    private TimerTask mTt1;
    private int iii;
    private String date;
    private Handler mTimerHandler_today;
    private Timer timer_today;

    //25june

    private TextView textView_today, textView_week,textView_clear_chart;
    private boolean boolean_halt_back;
    private boolean before_show_boolean;

    //interface pass result.

    interface PassText{
        void updateTextView(String text);
    }

    public PassText passText;

    public void setPassText(PassText passText){
        this.passText=passText;
    }

    public FragmentTimeStamp() {
    }

    //setup collection reference

    private CollectionReference collectionReferenceTest;

    //setup list return from firestore;

    private ArrayList<TestTimeStamp> testTimeStampsList;

    //drawing graph

    private ArrayList<Entry> entries = new ArrayList<>();

    private ArrayList<Entry> entriesV2 = new ArrayList<>();

    ArrayList<Entry> entries3 = new ArrayList<>();


    LineDataSet dataSet;

    LineDataSet dataSet2;

    LineData data;

    /// list of dataset, reset color.

    ArrayList<LineDataSet> dataSetArrayList;

    //wrap firestore for observable

    private TimeStampFireStore_Handler timeStampFireStore_handler;


    //// LAST

    private ArrayList<TestTimeStamp> finalListRemap;

    private ArrayList<ArrayList<Entry>> listof_entryList_Morning;

    //revise
    private ArrayList<EntryMorning> entryMorningArrayList;
    private ArrayList<EntryEvening> entryEveningArrayList;

    private ArrayList<ArrayList<Entry>> listof_entryList_Evening;


    private ArrayList<LineDataSet> dataSetArrayList_Final;

    XAxis xAxis;

    //adding option manipulate viewport

    private FloatingActionButton fButton, fButtonReset , fButtonToday, fButtonWeeek;

    //set list, to refer who is MC or null, or not recorded.

    //private ArrayList<MC_Null_Test>

    private ArrayList<MC_Null_TestTimeStamp> arrayListMC_Null;

    //// check  int pointer for late person, from datasetFinal point of view.

    class FinalPointer{

        int pointer_1;
        int pointer_2;

        public FinalPointer(){

        }

        public FinalPointer(int pointer_1,int pointer_2){
            this.pointer_1 = pointer_1;
            this.pointer_2 = pointer_2;
        }

        public int getPointer_1() {
            return pointer_1;
        }

        public void setPointer_1(int pointer_1) {
            this.pointer_1 = pointer_1;
        }

        public int getPointer_2() {
            return pointer_2;
        }

        public void setPointer_2(int pointer_2) {
            this.pointer_2 = pointer_2;
        }
    }

    ArrayList<FinalPointer> finalPointerArrayList;

    private CountDownTimer countDownTimer;

    private Timer timer;

    //adding recycler view showing whos late.

    private RecyclerView recyclerView;

    private RecyclerView_Frag_Adapter recyclerView_frag_adapter;

    private OurLayoutManager layoutManager;

    private ArrayList<ReturnToRecycler> returnToRecyclerArrayList;

    Timer timer2;

    private TextView textView;

    // we need the time pass today. so we request here.


    //17 june

    private String today;

    //we need to pull and store date as welll.


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.bottom_nav_timestamp_fragment, container, false);
        //textView = rootView.findViewById(R.id.bottom_nav_fragment_timeStamp_textView);

        Log.i("mainbottomnav_our","2 : fragment");
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        Toast.makeText(getContext(),"please use button to go back", Toast.LENGTH_SHORT).show();

                        return true;
                    }
                }
                return false;
            }
        });

        textView_today = rootView.findViewById(R.id.textView_who_late_week_id);
        textView_week = rootView.findViewById(R.id.textView_who_late_today_id);
        textView_clear_chart = rootView.findViewById(R.id.textView_clear_chart_id);


        boolean_halt_back = false;

        ///

        today = Main_BottomNav_Activity.dayToday;
        date = Main_BottomNav_Activity.dateToday;


        before_show_boolean =false;

       // need to use timestamp, then process it later. in retrieving part.


        returnToRecyclerArrayList = new ArrayList<>();

        //21 june

        returnToRecyclerArrayList.add(new ReturnToRecycler("name","date","0800","on time","url" ));




        //recycler view setup 10.20AM
        startRun = false;
        recyclerView = rootView.findViewById(R.id.bottom_nav_timeStamp_recycler_id);

        textView = rootView.findViewById(R.id.bottom_nav_timeStamp_textView2);

        seconds=0;


        layoutManager = new OurLayoutManager(getContext(), OurLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);


        //pass data, and pointer.
        recyclerView_frag_adapter = new RecyclerView_Frag_Adapter(getContext(),returnToRecyclerArrayList); // pass pointer, and data.


        ///

        fButton = rootView.findViewById(R.id.bottomNav_floatButtonBackiD);

        fButtonReset = rootView.findViewById(R.id.bottomNav_floatButtonShowLateResetiD);


        fButtonToday = rootView.findViewById(R.id.bottomNav_floatButtonShowLateToday2iD);


        fButtonWeeek = rootView.findViewById(R.id.bottomNav_floatButtonShowLateWeeklyiD);




        dataSetArrayList = new ArrayList<>();
        testTimeStampsList = new ArrayList<>();

        arrayListMC_Null = new ArrayList<>();

        //revise
        entryMorningArrayList = new ArrayList<>();
        entryEveningArrayList = new ArrayList<>();

        dataSetArrayList_Final = new ArrayList<>();
        finalListRemap = new ArrayList<>();

        listof_entryList_Morning = new ArrayList<>();

        listof_entryList_Evening = new ArrayList<>();

        finalPointerArrayList = new ArrayList<>();

        loopCount=0;

        Log.i("checkfragment ", "flow: 1");

        chart = rootView.findViewById(R.id.chartiD);



        collectionReferenceTest = FirebaseFirestore.getInstance().collection("all_admin_doc_collections")
                .document("ariff+60190_doc")
                .collection("all_employee_thisAdmin_collection");

        Log.i("checkChart Flow: ", "0");


//
        xAxis = chart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //setting x axis values

        //final String[] months = new String[]{"Mon", "Tue","Wed","Thu","Fri"};

        final String[] months = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"};

        xAxis.setGranularity(1f);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return months[(int) value];
            }
        });


        //xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        //  xAxis.setValueFormatter(formatter);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //***
        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
       // yAxisLeft.setGranularity(1f);
        //yAxisLeft.setInverted(true);
//
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setGranularity(1f);
        //yAxisLeft.setAxisMaximum(21f);
//
//        final String[] hours = new String[]{"","","","","","",   "",   "2100", "2000","1900","1800","1700","1600", "1500","1400","1300","1200", "1100", "1000","0900","0800","0700"};
//                                    //       0, 1, 2, 3, 4, 5,    6,       7,     8      9,    10,     11,    12,    13,    14,    15,     16,     17,    18,    19,   20,  21
//        yAxisLeft.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return hours[(int) value];
//            }
//        });


        final String[] hours2 = new String[]{"2300", "2200", "2100", "2000", "1900", "1800", "1700", "1600", "1500", "1400", "1300", "1200", "1100", "1000", "0900", "0800", "0700", "0600", "0500", "0400", "0300", "0200", "0100", "0000"};
                                     //           0,   1,      2,     3,      4,     5,    6,     7,      8      9,     10,     11,   12,     13,     14,    15,    16,    17,     18,    19,    20,    21,      22,  23
        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return hours2[(int) value];
            }
        });


        Log.i("checkChart Flow: ", "1");

        // Setting Data
        entries.add(new Entry(0, 7));
        entries.add(new Entry(1, 8));
        entries.add(new Entry(2, 12));
        entries.add(new Entry(3, 5));
        entries.add(new Entry(4, 6));


        //by default, transparent.
       // chart.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.transparent2));



        //chart.color

        //dataSet2 = new LineDataSet();
        dataSet = new LineDataSet(entries, "check out V1");

        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        chart.setBorderColor(ContextCompat.getColor(getContext(), R.color.material_white));




        dataSet.getEntryForIndex(1).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_outline_black_24dp));
        //dataSet.getEntryForIndex(1).

        //dataSetArrayList.add(dataSet);

        data = new LineData(dataSet);

        chart.setData(data);  //set adapter
//        chart.getLegend().setWordWrapEnabled(true);

        //chart.getLegend();

        chart.getLegend().setEnabled(true);

        chart.animateX(1500);

        Log.i("checkChart Flow: ", "2");


        //   chart.invalidate();

        // part >> we wrap firestore process in a class,

        Log.i("checkChartFlowFinal ", "observer, 1");

        timeStampFireStore_handler = new TimeStampFireStore_Handler(getContext(), dataSet, data, chart);

        timeStampFireStore_handler.addObserver(this);

        Log.i("20_june", "[FragTimeStamp 1]");

        timeStampFireStore_handler.startFecthData(); //will always be false, unless notified update, and update ui once return true

        Log.i("20_june", "[FragTimeStamp 2]");


        fButton.setOnClickListener(this);
        fButtonReset.setOnClickListener(this);
        fButtonToday.setOnClickListener(this);
        fButtonWeeek.setOnClickListener(this);

        return rootView;

    }


    @Override
    public void update(Observable o, Object arg) {

        Log.i("checkChartFlowFinal ", "observer, 4");

        if (o instanceof TimeStampFireStore_Handler) {

//        TimeStampFireStore_Handler handler = (TimeStampFireStore_Handler) o;

            Log.i("checkChartFlowFinal ", "observer, 5");

            //this is updated dataset
            //dataSet = ((TimeStampFireStore_Handler) o).returnDataSet();

            finalListRemap = ((TimeStampFireStore_Handler) o).reTURNFINAL();


            ////////////>>>>>>>>>>>>>>>> 4 april 10AM

            HashMap<String, Float> remapAfter = new HashMap<>(); //this is final will be reuse.

            int sizeTest = finalListRemap.size();

            if (finalListRemap.size() >= 1) {


            Log.i("20_june", "[FragTimeStamp UPDATE 1]");

            for (TestTimeStamp testTimeStamp : finalListRemap) {

                //by default need to set pro_ ** to false?

                Log.i("20_june", "[FragTimeStamp UPDATE 2] , name: " + testTimeStamp.getName() + ", phone:" + testTimeStamp.getPhone());

                testTimeStamp.setProb_mon_morning(false);
                testTimeStamp.setProb_tue_morning(false);
                testTimeStamp.setProb_wed_morning(false);
                testTimeStamp.setProb_thu_morning(false);
                testTimeStamp.setProb_fri_morning(false);


                testTimeStamp.setProb_mon_evening(false);
                testTimeStamp.setProb_tue_evening(false);
                testTimeStamp.setProb_wed_evening(false);
                testTimeStamp.setProb_thu_evening(false);
                testTimeStamp.setProb_fri_evening(false);


                ArrayList<Entry> entryArrayList = new ArrayList<>();

                //we need to handle this separately though, for cases like no entry recorded, like MC. >> add remark in testTimeStamp.

                if (testTimeStamp.getMon_morning().equals("") || testTimeStamp.getMon_morning().isEmpty() || testTimeStamp.getMon_morning() == null || testTimeStamp.getMon_morning().equals("0")) {

                    //arrayListMC_Null.add(new MC_Null_TestTimeStamp(testTimeStamp.getName(),testTimeStamp)); //we add original value,, need to add pointer? so no need to track loop which day, or entry got problem?
                    // no need for this. just add boolean to original time stamp.
                    testTimeStamp.setProb_mon_morning(true);  // add tag for fast? tag or counter?

                    testTimeStamp.setMon_morning("8");
                }

                if (testTimeStamp.getTue_morning().equals("") || testTimeStamp.getTue_morning().isEmpty() || testTimeStamp.getTue_morning() == null || testTimeStamp.getTue_morning().equals("0")) {

                    testTimeStamp.setProb_tue_morning(true);
                    testTimeStamp.setTue_morning("8");
                }

                if (testTimeStamp.getWed_morning().equals("") || testTimeStamp.getWed_morning().isEmpty() || testTimeStamp.getWed_morning() == null || testTimeStamp.getWed_morning().equals("0")) {

                    testTimeStamp.setProb_wed_morning(true);
                    testTimeStamp.setWed_morning("8");
                }

                if (testTimeStamp.getThu_morning().equals("") || testTimeStamp.getThu_morning().isEmpty() || testTimeStamp.getThu_morning() == null || testTimeStamp.getThu_morning().equals("0")) {
                    testTimeStamp.setProb_thu_morning(true);
                    testTimeStamp.setThu_morning("8");
                }

                if (testTimeStamp.getFri_morning().equals("") || testTimeStamp.getFri_morning().isEmpty() || testTimeStamp.getFri_morning() == null || testTimeStamp.getFri_morning().equals("0")) {
                    testTimeStamp.setProb_fri_morning(true);
                    testTimeStamp.setFri_morning("8");
                }

                // evening,

                if (testTimeStamp.getMon_evening().equals("") || testTimeStamp.getMon_evening().isEmpty() || testTimeStamp.getMon_evening() == null || testTimeStamp.getMon_evening().equals("0")) {
                    testTimeStamp.setProb_mon_evening(true);
                    testTimeStamp.setMon_evening("17");
                }
                if (testTimeStamp.getTue_evening().equals("") || testTimeStamp.getTue_evening().isEmpty() || testTimeStamp.getTue_evening() == null || testTimeStamp.getTue_evening().equals("0")) {
                    testTimeStamp.setProb_tue_evening(true);
                    testTimeStamp.setTue_evening("17");
                }

                if (testTimeStamp.getWed_evening().equals("") || testTimeStamp.getWed_evening().isEmpty() || testTimeStamp.getWed_evening() == null || testTimeStamp.getWed_evening().equals("0")) {
                    testTimeStamp.setProb_wed_evening(true);
                    testTimeStamp.setWed_evening("17");
                }
                if (testTimeStamp.getThu_evening().equals("") || testTimeStamp.getThu_evening().isEmpty() || testTimeStamp.getThu_evening() == null || testTimeStamp.getThu_evening().equals("0")) {

                    testTimeStamp.setProb_thu_evening(true);
                    testTimeStamp.setThu_evening("17");
                }
                if (testTimeStamp.getFri_evening().equals("") || testTimeStamp.getFri_evening().isEmpty() || testTimeStamp.getFri_evening() == null || testTimeStamp.getFri_evening().equals("0")) {
                    testTimeStamp.setProb_fri_evening(true);
                    testTimeStamp.setFri_evening("17");
                }


                //we could remap timeStampList, single out all morning entry into single morning list.,, remap into hasmap

                boolean check = testTimeStamp.isProb_fri_evening();

                HashMap<String, Float> remap = new HashMap<>();

                Float mon_morning_remap = Float.valueOf(testTimeStamp.getMon_morning());
                remap.put("mon_morning_map", mon_morning_remap);

                Float tue_morning_remap = Float.valueOf(testTimeStamp.getTue_morning());
                remap.put("tue_morning_map", tue_morning_remap);

                Float wed_morning_remap = Float.valueOf(testTimeStamp.getWed_morning());
                remap.put("wed_morning_map", wed_morning_remap);

                Float thu_morning_remap = Float.valueOf(testTimeStamp.getThu_morning());
                remap.put("thu_morning_map", thu_morning_remap);

                Float fri_morning_remap = Float.valueOf(testTimeStamp.getFri_morning());
                remap.put("fri_morning_map", fri_morning_remap);

                // .. 1pm , we could add evening , and set it up direct as well.

                Float mon_evening_remap = Float.valueOf(testTimeStamp.getMon_evening());    // value return will be in 17pm not 5pm.
                remap.put("mon_evening_map", mon_evening_remap);

                Float tue_evening_remap = Float.valueOf(testTimeStamp.getTue_evening());
                remap.put("tue_evening_map", tue_evening_remap);

                Float wed_evening_remap = Float.valueOf(testTimeStamp.getWed_evening());
                remap.put("wed_evening_map", wed_evening_remap);

                Float thu_evening_remap = Float.valueOf(testTimeStamp.getThu_evening());
                remap.put("thu_evening_map", thu_evening_remap);

                Float fri_evening_remap = Float.valueOf(testTimeStamp.getFri_evening());
                remap.put("fri_evening_map", fri_evening_remap);


                // we could remap

                for (Map.Entry<String, Float> entry : remap.entrySet()) {

                    if (entry.getKey().equals("mon_morning_map")) {

                        Float afterAdjusted = 0f;

                        if (entry.getValue() >= 6f && entry.getValue() < 7f) { // which entry got extracted,
                            Float beforeAdjusted = 6f + (1f - ((entry.getValue() - 6f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 12f - 2f); // here we adjusted right?

                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 7f && entry.getValue() < 8f) {
                            Float beforeAdjusted = 7f + (1f - ((entry.getValue() - 7f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 10f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 8f && entry.getValue() < 9f) {
                            Float beforeAdjusted = 8f + (1f - ((entry.getValue() - 8f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 8f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 9f && entry.getValue() < 10f) {
                            Float beforeAdjusted = 9f + (1f - ((entry.getValue() - 9f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 6f - 2f);
                            afterAdjusted = entry.getValue();
                        }


                        if (entry.getValue() >= 10f && entry.getValue() < 11f) {
                            Float beforeAdjusted = 10f + (1f - ((entry.getValue() - 10f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 4f - 2f);
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 11f && entry.getValue() < 12f) {
                            Float beforeAdjusted = 11f + (1f - ((entry.getValue() - 11f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 2f - 2f);

                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 12f && entry.getValue() < 13f) {

                            Float beforeAdjusted = 12f + (1f - ((entry.getValue() - 12f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 0f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        remapAfter.put("mon_morning", afterAdjusted);

                    } //finish monday

                    if (entry.getKey().equals("tue_morning_map")) {

                        Float afterAdjusted = 0f;

                        if (entry.getValue() >= 6f && entry.getValue() < 7f) { // which entry got extracted,
                            Float beforeAdjusted = 6f + (1f - ((entry.getValue() - 6f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 12f - 2f); // here we adjusted right?

                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 7f && entry.getValue() < 8f) {
                            Float beforeAdjusted = 7f + (1f - ((entry.getValue() - 7f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 10f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 8f && entry.getValue() < 9f) {
                            Float beforeAdjusted = 8f + (1f - ((entry.getValue() - 8f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 8f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 9f && entry.getValue() < 10f) {
                            Float beforeAdjusted = 9f + (1f - ((entry.getValue() - 9f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 6f - 2f);
                            afterAdjusted = entry.getValue();
                        }


                        if (entry.getValue() >= 10f && entry.getValue() < 11f) {
                            Float beforeAdjusted = 10f + (1f - ((entry.getValue() - 10f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 4f - 2f);
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 11f && entry.getValue() < 12f) {
                            Float beforeAdjusted = 11f + (1f - ((entry.getValue() - 11f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 2f - 2f);

                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 12f && entry.getValue() < 13f) {

                            Float beforeAdjusted = 12f + (1f - ((entry.getValue() - 12f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 0f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        remapAfter.put("tue_morning", afterAdjusted);

                    } // tue morning

                    if (entry.getKey().equals("wed_morning_map")) {

                        Float afterAdjusted = 0f;

                        if (entry.getValue() >= 6f && entry.getValue() < 7f) { // which entry got extracted,
                            Float beforeAdjusted = 6f + (1f - ((entry.getValue() - 6f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 12f - 2f); // here we adjusted right?

                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 7f && entry.getValue() < 8f) {
                            Float beforeAdjusted = 7f + (1f - ((entry.getValue() - 7f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 10f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 8f && entry.getValue() < 9f) {
                            Float beforeAdjusted = 8f + (1f - ((entry.getValue() - 8f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 8f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 9f && entry.getValue() < 10f) {
                            Float beforeAdjusted = 9f + (1f - ((entry.getValue() - 9f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 6f - 2f);
                            afterAdjusted = entry.getValue();
                        }


                        if (entry.getValue() >= 10f && entry.getValue() < 11f) {
                            Float beforeAdjusted = 10f + (1f - ((entry.getValue() - 10f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 4f - 2f);
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 11f && entry.getValue() < 12f) {
                            Float beforeAdjusted = 11f + (1f - ((entry.getValue() - 11f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 2f - 2f);

                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 12f && entry.getValue() < 13f) {

                            Float beforeAdjusted = 12f + (1f - ((entry.getValue() - 12f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 0f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        remapAfter.put("wed_morning", afterAdjusted);

                    } // wed morning

                    if (entry.getKey().equals("thu_morning_map")) {

                        Float afterAdjusted = 0f;

                        if (entry.getValue() >= 6f && entry.getValue() < 7f) { // which entry got extracted,
                            Float beforeAdjusted = 6f + (1f - ((entry.getValue() - 6f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 12f - 2f); // here we adjusted right?

                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 7f && entry.getValue() < 8f) {
                            Float beforeAdjusted = 7f + (1f - ((entry.getValue() - 7f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 10f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 8f && entry.getValue() < 9f) {
                            Float beforeAdjusted = 8f + (1f - ((entry.getValue() - 8f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 8f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 9f && entry.getValue() < 10f) {
                            Float beforeAdjusted = 9f + (1f - ((entry.getValue() - 9f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 6f - 2f);
                            afterAdjusted = entry.getValue();
                        }


                        if (entry.getValue() >= 10f && entry.getValue() < 11f) {
                            Float beforeAdjusted = 10f + (1f - ((entry.getValue() - 10f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 4f - 2f);
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 11f && entry.getValue() < 12f) {
                            Float beforeAdjusted = 11f + (1f - ((entry.getValue() - 11f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 2f - 2f);

                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 12f && entry.getValue() < 13f) {

                            Float beforeAdjusted = 12f + (1f - ((entry.getValue() - 12f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 0f - 2f);
                            afterAdjusted = entry.getValue();
                        }
                        remapAfter.put("thu_morning", afterAdjusted);

                    } // thursday morning

                    if (entry.getKey().equals("fri_morning_map")) {

                        Float afterAdjusted = 0f;

                        if (entry.getValue() >= 6f && entry.getValue() < 7f) { // which entry got extracted,
                            Float beforeAdjusted = 6f + (1f - ((entry.getValue() - 6f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 12f - 2f); // here we adjusted right?

                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 7f && entry.getValue() < 8f) {
                            Float beforeAdjusted = 7f + (1f - ((entry.getValue() - 7f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 10f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 8f && entry.getValue() < 9f) {
                            Float beforeAdjusted = 8f + (1f - ((entry.getValue() - 8f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 8f - 2f);
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 9f && entry.getValue() < 10f) {
                            Float beforeAdjusted = 9f + (1f - ((entry.getValue() - 9f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 6f - 2f);
                            afterAdjusted = entry.getValue();
                        }


                        if (entry.getValue() >= 10f && entry.getValue() < 11f) {
                            Float beforeAdjusted = 10f + (1f - ((entry.getValue() - 10f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 4f - 2f);
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 11f && entry.getValue() < 12f) {
                            Float beforeAdjusted = 11f + (1f - ((entry.getValue() - 11f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 2f - 2f);

                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 12f && entry.getValue() < 13f) {

                            Float beforeAdjusted = 12f + (1f - ((entry.getValue() - 12f) / 0.6f)); // ... >  6.30 .. +
                            entry.setValue(beforeAdjusted + 0f - 2f);
                            afterAdjusted = entry.getValue();
                        }
                        remapAfter.put("fri_morning", afterAdjusted);

                    }


                    //////////// evening partt


                    //actually, number return in +12h format, so . 5pm = 17pm.


                    if (entry.getKey().equals("mon_evening_map")) {

                        Float afterAdjusted = 0f;

                        if (entry.getValue() >= 16f && entry.getValue() < 17f) {
                            Float beforeAdjusted = 16f + (1f - ((entry.getValue() - 16f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 10f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 17f && entry.getValue() < 18f) {
                            Float beforeAdjusted = 17f + (1f - ((entry.getValue() - 17f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 12f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 18f && entry.getValue() < 19f) {
                            Float beforeAdjusted = 18f + (1f - ((entry.getValue() - 18f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 14f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 19f && entry.getValue() < 20f) {
                            Float beforeAdjusted = 19f + (1f - ((entry.getValue() - 19f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 16f); //this is the offset,
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 20f && entry.getValue() < 21f) {
                            Float beforeAdjusted = 20f + (1f - ((entry.getValue() - 20f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 18f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 21f && entry.getValue() < 22f) {
                            Float beforeAdjusted = 21f + (1f - ((entry.getValue() - 21f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 20f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        remapAfter.put("mon_evening", afterAdjusted);

                    }

                    if (entry.getKey().equals("tue_evening_map")) {

                        Float afterAdjusted = 0f;
                        if (entry.getValue() >= 16f && entry.getValue() < 17f) {
                            Float beforeAdjusted = 16f + (1f - ((entry.getValue() - 16f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 10f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 17f && entry.getValue() < 18f) {
                            Float beforeAdjusted = 17f + (1f - ((entry.getValue() - 17f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 12f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 18f && entry.getValue() < 19f) {
                            Float beforeAdjusted = 18f + (1f - ((entry.getValue() - 18f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 14f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 19f && entry.getValue() < 20f) {
                            Float beforeAdjusted = 19f + (1f - ((entry.getValue() - 19f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 16f); //this is the offset,
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 20f && entry.getValue() < 21f) {
                            Float beforeAdjusted = 20f + (1f - ((entry.getValue() - 20f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 18f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 21f && entry.getValue() < 22f) {
                            Float beforeAdjusted = 21f + (1f - ((entry.getValue() - 21f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 20f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }
                        remapAfter.put("tue_evening", afterAdjusted);

                    }

                    if (entry.getKey().equals("wed_evening_map")) {

                        Float afterAdjusted = 0f;


                        if (entry.getValue() >= 16f && entry.getValue() < 17f) {
                            Float beforeAdjusted = 16f + (1f - ((entry.getValue() - 16f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 10f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 17f && entry.getValue() < 18f) {
                            Float beforeAdjusted = 17f + (1f - ((entry.getValue() - 17f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 12f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 18f && entry.getValue() < 19f) {
                            Float beforeAdjusted = 18f + (1f - ((entry.getValue() - 18f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 14f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 19f && entry.getValue() < 20f) {
                            Float beforeAdjusted = 19f + (1f - ((entry.getValue() - 19f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 16f); //this is the offset,
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 20f && entry.getValue() < 21f) {
                            Float beforeAdjusted = 20f + (1f - ((entry.getValue() - 20f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 18f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 21f && entry.getValue() < 22f) {
                            Float beforeAdjusted = 21f + (1f - ((entry.getValue() - 21f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 20f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        remapAfter.put("wed_evening", afterAdjusted);

                    }

                    if (entry.getKey().equals("thu_evening_map")) {

                        Float afterAdjusted = 0f;


                        if (entry.getValue() >= 16f && entry.getValue() < 17f) {
                            Float beforeAdjusted = 16f + (1f - ((entry.getValue() - 16f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 10f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 17f && entry.getValue() < 18f) {
                            Float beforeAdjusted = 17f + (1f - ((entry.getValue() - 17f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 12f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 18f && entry.getValue() < 19f) {
                            Float beforeAdjusted = 18f + (1f - ((entry.getValue() - 18f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 14f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 19f && entry.getValue() < 20f) {
                            Float beforeAdjusted = 19f + (1f - ((entry.getValue() - 19f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 16f); //this is the offset,
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 20f && entry.getValue() < 21f) {
                            Float beforeAdjusted = 20f + (1f - ((entry.getValue() - 20f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 18f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 21f && entry.getValue() < 22f) {
                            Float beforeAdjusted = 21f + (1f - ((entry.getValue() - 21f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 20f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        remapAfter.put("thu_evening", afterAdjusted);

                    }

                    if (entry.getKey().equals("fri_evening_map")) {

                        Float afterAdjusted = 0f;


                        if (entry.getValue() >= 16f && entry.getValue() < 17f) {
                            Float beforeAdjusted = 16f + (1f - ((entry.getValue() - 16f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 10f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 17f && entry.getValue() < 18f) {
                            Float beforeAdjusted = 17f + (1f - ((entry.getValue() - 17f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 12f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 18f && entry.getValue() < 19f) {
                            Float beforeAdjusted = 18f + (1f - ((entry.getValue() - 18f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 14f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 19f && entry.getValue() < 20f) {
                            Float beforeAdjusted = 19f + (1f - ((entry.getValue() - 19f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 16f); //this is the offset,
                            afterAdjusted = entry.getValue();

                        }

                        if (entry.getValue() >= 20f && entry.getValue() < 21f) {
                            Float beforeAdjusted = 20f + (1f - ((entry.getValue() - 20f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 18f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        if (entry.getValue() >= 21f && entry.getValue() < 22f) {
                            Float beforeAdjusted = 21f + (1f - ((entry.getValue() - 21f) / 0.6f)); // ... > this is getting  the minutes valued, scaled.
                            entry.setValue(beforeAdjusted - 20f); //this is the offset,
                            afterAdjusted = entry.getValue();
                        }

                        remapAfter.put("fri_evening", afterAdjusted);

                    }


                } //fnisih hashmap remap loop

                //checking remapAfter size,

                int sizeRemap = remapAfter.size();

                int sizemap = remap.size();

                entryArrayList.add(new Entry(0, remapAfter.get("mon_morning")));
                entryArrayList.add(new Entry(1, remapAfter.get("tue_morning")));
                entryArrayList.add(new Entry(2, remapAfter.get("wed_morning")));
                entryArrayList.add(new Entry(3, remapAfter.get("thu_morning")));
                entryArrayList.add(new Entry(4, remapAfter.get("fri_morning")));

                for (int i = 0; i < entryArrayList.size(); i++) {

                    Float checkConstraintTime = entryArrayList.get(i).getY();

                    if (checkConstraintTime <= 15f) {
                        entryArrayList.get(i).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                    }


                }

                entryMorningArrayList.add(new EntryMorning(testTimeStamp.getName(), entryArrayList)); // hence this list, will always correspond to how big our document/employee is
                //settle morning


                // .>> 10.30AM settle 8 april here,

                ArrayList<Entry> entryArrayList2 = new ArrayList<>();

                entryArrayList2.add(new Entry(0, remapAfter.get("mon_evening"))); //
                entryArrayList2.add(new Entry(1, remapAfter.get("tue_evening")));
                entryArrayList2.add(new Entry(2, remapAfter.get("wed_evening")));
                entryArrayList2.add(new Entry(3, remapAfter.get("thu_evening")));
                entryArrayList2.add(new Entry(4, remapAfter.get("fri_evening")));


                for (int i = 0; i < entryArrayList2.size(); i++) {

                    Float checkConstraintTime = entryArrayList2.get(i).getY();

                    if (checkConstraintTime > 6f) {
                        entryArrayList2.get(i).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                    }

                }

                entryEveningArrayList.add(new EntryEvening(testTimeStamp.getName(), entryArrayList2));
//

                Log.i("20_june", "[FragTimeStamp UPDATE 3] , name: " + testTimeStamp.getName() + ", phone:" + testTimeStamp.getPhone());


            }


            //evening
            ////////////>>>>>>>>>>>>>>>>

            // >> >>>>>>>>

            chart.clearValues();


            for (int listDataSet = 0; listDataSet < entryMorningArrayList.size(); listDataSet++) {


                dataSetArrayList_Final.add(new LineDataSet(entryMorningArrayList.get(listDataSet).getEntryArrayList(), entryMorningArrayList.get(listDataSet).getName()));


            }

            //

            for (int listDataSet2 = 0; listDataSet2 < entryEveningArrayList.size(); listDataSet2++) {

                //adding to the same list of data set
                dataSetArrayList_Final.add(new LineDataSet(entryEveningArrayList.get(listDataSet2).getEntryArrayList(), entryEveningArrayList.get(listDataSet2).getName()));


            }

            dataSetArrayList_Final.get(0).setValueTextSize(0f);

            //set up icon for null value, and mc , check morning first


            for (TestTimeStamp testTimeStamp : finalListRemap) {


                //for(EntryMorning entryMorning: entryMorningArrayList){
                // we not sure entry represent which. , so better use index since, 0 always mean monday, and so on
                for (int k = 0; k < entryMorningArrayList.size(); k++) {

                    if (testTimeStamp.getName() == entryMorningArrayList.get(k).getName()) {

                        if (testTimeStamp.isProb_mon_morning()) {
                            //entryMorning.getEntry().setIcon(); // something wrong ,, we not sure which entry , is

                            entryMorningArrayList.get(k).getEntryArrayList().get(0).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }
                        if (testTimeStamp.isProb_tue_morning()) {
                            entryMorningArrayList.get(k).getEntryArrayList().get(1).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }
                        if (testTimeStamp.isProb_wed_morning()) {
                            entryMorningArrayList.get(k).getEntryArrayList().get(2).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }
                        if (testTimeStamp.isProb_thu_morning()) {
                            entryMorningArrayList.get(k).getEntryArrayList().get(3).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }
                        if (testTimeStamp.isProb_fri_morning()) {
                            entryMorningArrayList.get(k).getEntryArrayList().get(4).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }

                    }

                }

            }

            for (TestTimeStamp testTimeStamp : finalListRemap) {


                //now set evening MC or null

                for (int k = 0; k < entryEveningArrayList.size(); k++) {

                    if (testTimeStamp.getName() == entryEveningArrayList.get(k).getName()) {

                        if (testTimeStamp.isProb_mon_evening()) {
                            //entryMorning.getEntry().setIcon(); // something wrong ,, we not sure which entry , is

                            entryEveningArrayList.get(k).getEntryArrayList().get(0).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }
                        if (testTimeStamp.isProb_tue_evening()) {
                            entryEveningArrayList.get(k).getEntryArrayList().get(1).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }
                        if (testTimeStamp.isProb_wed_evening()) {
                            entryEveningArrayList.get(k).getEntryArrayList().get(2).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }
                        if (testTimeStamp.isProb_thu_evening()) {
                            entryEveningArrayList.get(k).getEntryArrayList().get(3).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }
                        if (testTimeStamp.isProb_fri_evening()) {
                            entryEveningArrayList.get(k).getEntryArrayList().get(4).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_error_small_16dp));

                        }

                    }

                }

            }


            //then add to the data.

            //add all
            for (int dataCount = 0; dataCount < (entryEveningArrayList.size() * 2); dataCount++) {

                data.addDataSet(dataSetArrayList_Final.get(dataCount));
            }

            dataSet.notifyDataSetChanged();

            data.notifyDataChanged();
            chart.notifyDataSetChanged();


            //chart.notifyDataSetChanged(); // THIS ONE FUKIN LINE

            Legend legend = chart.getLegend();

            List<LegendEntry> entrieList = new ArrayList<>();

            RandomColor randomColor = new RandomColor();

            int[] colors = randomColor.randomColor(entryMorningArrayList.size()); //only need color correspond to number of users


            ////// >> color setup below

            //go through colors, if detect red variant generate again., can improve by editing [ randomColor() library ]

            for (int i = 0; i < colors.length; i++) {

                Log.i("checkColor now: ", " flow : 2" + "  >> i:" + i);

                //while((colors[i]<-90000 && colors[i]>-180000 )|| colors[i]>-5000 || (colors[i]>-67000 && colors[i]<-40000)||(colors[i]>-1838656 && colors[i]<-1238656) ) {  // non of the while loop gets triggered

                while ((colors[i] > -67000 && colors[i] < -40000)) {  // non of the while loop gets triggered

                    Log.i("checkColor now: ", " flow : 3" + "  >> i:" + i);

                    colors[i] = randomColor.randomColor(); //making sure color not too bright.

                    Log.i("checkColor now: ", " flow : 3" + " random color generated: " + colors[i] + "  >> i:" + i);


                    if (i < (colors.length - 1)) {

                        Log.i("checkColor now: ", " flow : 5");

                        int check = -(colors[i]) + colors[i + 1];

                        Log.i("checkColor diff: ", "check difference 1 : " + colors[i] + "  >> i:" + i);
                        Log.i("checkColor diff: ", "check difference 2 : " + colors[i + 1] + "  >> i:" + i);
                        Log.i("checkColor diff: ", "check difference color : " + check + "  >> i:" + i);

                        if (colors[i] < -900035) { //for other variant

                            int check2 = -(colors[i]) + colors[i + 1];

                            while (check2 <= 100000 && check2 >= -100000) { // numbers could be
                                colors[i] = randomColor.randomColor();


                                check2 = -colors[i] + colors[i + 1]; //everytime we create we check this

                                Log.i("checkColor now: ", " flow : 6" + "  >> i:" + i);
                            }

                        }


                        while (check <= 10000 && check >= -10000) {
                            colors[i] = randomColor.randomColor();
                            check = -colors[i] + colors[i + 1]; //everytime we create we check this

                            Log.i("checkColor now: ", " flow : 6" + "  >> i:" + i);
                        }

                    }
                    //   } //still check if color[i] within red

                } // still check if color[i] too bright

            }


            //finish making sure no red, recored

            //adding setup

            //chart.setBorderColor(Color.w);



            for (int i = 0; i < dataSetArrayList_Final.size(); i++) {

                dataSetArrayList_Final.get(i).setAxisDependency(YAxis.AxisDependency.LEFT);
            }

            for (int j = 0; j < entryMorningArrayList.size(); j++) {

                LegendEntry legendEntry_Morning = new LegendEntry();

                legendEntry_Morning.formColor = colors[j];
                dataSetArrayList_Final.get(j).setColor(colors[j]);

               // dataSetArrayList_Final.get(j).setValueTextColor(ContextCompat.getColor(getContext(), R.color.material_white_blue));

                dataSetArrayList_Final.get(j).notifyDataSetChanged();
                legendEntry_Morning.label = entryMorningArrayList.get(j).getName();
                entrieList.add(legendEntry_Morning);
            }

            int offset = dataSetArrayList_Final.size() - entryMorningArrayList.size();

            for (int j = 0; j < entryEveningArrayList.size(); j++) {

                LegendEntry legendEntry_Evening = new LegendEntry();

                // legendEntry_Evening.formColor = colors[j];
                dataSetArrayList_Final.get(j + offset).setColor(colors[j]);

             //   dataSetArrayList_Final.get(j).setValueTextColor(ContextCompat.getColor(getContext(), R.color.material_white_blue));

                dataSetArrayList_Final.get(j + offset).notifyDataSetChanged();
                //legendEntry_Morning.label = entryMorningArrayList.get(j).getName();
                // legendEntry_Evening.label = "NONE";

                entrieList.add(legendEntry_Evening);

                //label
            }


            //for some data which exceeds pre-defined constraint time stamp, will be coloured red.

            // dataSetArrayList_Final.get(4).setLabel();

            legend.setCustom(entrieList);

            legend.setTextColor(ContextCompat.getColor(getContext(), R.color.material_white_blue));



            //data.color

            //24junecolor
            chart.setGridBackgroundColor(ContextCompat.getColor(getContext(), R.color.material_white));



            data.notifyDataChanged();
            chart.notifyDataSetChanged();

            chart.invalidate();

            return;

        }

        }

    }


    private void setupWhosLate_todayReal(){

        //for(LineDataSet dataSet: dataSetArrayList_Final){
        int todayHere=0;

        if(today.equals("Mon")){
            todayHere=0;
        }else if(today.equals("Tue")){
            todayHere=1;
        }else if(today.equals("Wed")){
            todayHere=2;
        }else if(today.equals("Thu")){
            todayHere=3;
        }else if(today.equals("Fri")){
            todayHere=4;
        }

        // check morning first., mean halve the data
        for(int j= 0; j<dataSetArrayList_Final.size()/2;j++){

            //for(today.equals())

            for(int i=0; i<5; i++) {

                float timestampthis = dataSetArrayList_Final.get(j).getEntryForIndex(i).getY();


                if(todayHere==i){

                if (timestampthis <= 15.01f) {

                    finalPointerArrayList.add(new FinalPointer(j, i));
                    if (returnToRecyclerArrayList.get(0).getName().equals("name")) {
                        returnToRecyclerArrayList.remove(0);
                    }

                    //float timenow = dataSetArrayList_Final.get(j).getEntryForIndex(i).getY();

                    String namehere = dataSetArrayList_Final.get(j).getLabel();

                    for (TestTimeStamp testTimeStamp : finalListRemap) {

                        if (testTimeStamp.getName().equals(namehere)) {

                            if (i == 0) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getMon_date(), testTimeStamp.getMon_morning(), "LATE", testTimeStamp.getImage_url()));
                            }
                            if (i == 1) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getTue_date(), testTimeStamp.getTue_morning(), "LATE", testTimeStamp.getImage_url()));
                            }
                            if (i == 2) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getWed_date(), testTimeStamp.getWed_morning(), "LATE", testTimeStamp.getImage_url()));
                            }
                            if (i == 3) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getThu_date(), testTimeStamp.getThu_morning(), "LATE", testTimeStamp.getImage_url()));
                            }
                            if (i == 4) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getFri_date(), testTimeStamp.getFri_morning(), "LATE", testTimeStamp.getImage_url()));
                            }


                        }
                    }

                    //returnToRecyclerArrayList.add(new ReturnToRecycler(dataSetArrayList_Final.get(j).getLabel(),"9 April",dataSetArrayList_Final.get(j).getEntryForIndex(i).getY(),))

                }

            }//if today

            } //loop cancel, change one day only.

        }

        //then evening logic.

        //returnToRecyclerArrayList ...

        for(int j= dataSetArrayList_Final.size()/2; j<dataSetArrayList_Final.size();j++){

            for(int i=0; i<5; i++) {

                float timestampthis = dataSetArrayList_Final.get(j).getEntryForIndex(i).getY();

                if(todayHere==i){

                if (timestampthis >= 5.99f) { // relative to final data, not chart, no offset.

                    finalPointerArrayList.add(new FinalPointer(j, i)); // add another complete set pointer.

                    // finalPointerArrayList.add(new FinalPointer(j,i));
                    if (returnToRecyclerArrayList.get(0).getName().equals("name")) {
                        returnToRecyclerArrayList.remove(0);
                    }

                    //float timenow = dataSetArrayList_Final.get(j).getEntryForIndex(i).getY();

                    String namehere = dataSetArrayList_Final.get(j).getLabel();

                    for (TestTimeStamp testTimeStamp : finalListRemap) {

                        if (testTimeStamp.getName().equals(namehere)) {

                            if (i == 0) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getMon_date(), testTimeStamp.getMon_evening(), "LATE", testTimeStamp.getImage_url()));
                            }
                            if (i == 1) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getTue_date(), testTimeStamp.getTue_evening(), "LATE", testTimeStamp.getImage_url()));
                            }
                            if (i == 2) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getWed_date(), testTimeStamp.getWed_evening(), "LATE", testTimeStamp.getImage_url()));
                            }
                            if (i == 3) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getThu_date(), testTimeStamp.getThu_evening(), "LATE", testTimeStamp.getImage_url()));
                            }
                            if (i == 4) {

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere, testTimeStamp.getFri_date(), testTimeStamp.getFri_evening(), "LATE", testTimeStamp.getImage_url()));
                            }


                        }
                    }


                }


            }// evening today
            }

        }

        recyclerView_frag_adapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerView_frag_adapter);


    }

    private void setupWhosLate_Week(){

        //for(LineDataSet dataSet: dataSetArrayList_Final){


        // check morning first., mean halve the data
        for(int j= 0; j<dataSetArrayList_Final.size()/2;j++){

            for(int i=0; i<5; i++){

                float timestampthis = dataSetArrayList_Final.get(j).getEntryForIndex(i).getY();

                if(timestampthis<=15.01f){

                    finalPointerArrayList.add(new FinalPointer(j,i));
                    if(returnToRecyclerArrayList.get(0).getName().equals("name")){
                        returnToRecyclerArrayList.remove(0);
                    }

                    //float timenow = dataSetArrayList_Final.get(j).getEntryForIndex(i).getY();

                    String namehere = dataSetArrayList_Final.get(j).getLabel();

                    for(TestTimeStamp testTimeStamp: finalListRemap){

                        if(testTimeStamp.getName().equals(namehere)){

                            if(i==0){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getMon_date(),testTimeStamp.getMon_morning(),"LATE", testTimeStamp.getImage_url()));
                            }if(i==1){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getTue_date(),testTimeStamp.getTue_morning(),"LATE", testTimeStamp.getImage_url()));
                            }
                            if(i==2){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getWed_date(),testTimeStamp.getWed_morning(),"LATE", testTimeStamp.getImage_url()));
                            }
                            if(i==3){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getThu_date(),testTimeStamp.getThu_morning(),"LATE", testTimeStamp.getImage_url()));
                            }
                            if(i==4){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getFri_date(),testTimeStamp.getFri_morning(),"LATE", testTimeStamp.getImage_url()));
                            }



                        }
                    }

                    //returnToRecyclerArrayList.add(new ReturnToRecycler(dataSetArrayList_Final.get(j).getLabel(),"9 April",dataSetArrayList_Final.get(j).getEntryForIndex(i).getY(),))

                }

            }

        }

        //then evening logic.

        for(int j= dataSetArrayList_Final.size()/2; j<dataSetArrayList_Final.size();j++){

            for(int i=0; i<5; i++){

                float timestampthis = dataSetArrayList_Final.get(j).getEntryForIndex(i).getY();

                if(timestampthis>=5.99f){ // relative to final data, not chart, no offset.

                    finalPointerArrayList.add(new FinalPointer(j,i)); // add another complete set pointer.

                    // finalPointerArrayList.add(new FinalPointer(j,i));
                    if(returnToRecyclerArrayList.get(0).getName().equals("name")){
                        returnToRecyclerArrayList.remove(0);
                    }

                    //float timenow = dataSetArrayList_Final.get(j).getEntryForIndex(i).getY();

                    String namehere = dataSetArrayList_Final.get(j).getLabel();

                    for(TestTimeStamp testTimeStamp: finalListRemap){

                        if(testTimeStamp.getName().equals(namehere)){

                            if(i==0){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getMon_date(),testTimeStamp.getMon_evening(),"LATE", testTimeStamp.getImage_url()));
                            }if(i==1){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getTue_date(),testTimeStamp.getTue_evening(),"LATE", testTimeStamp.getImage_url()));
                            }
                            if(i==2){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getWed_date(),testTimeStamp.getWed_evening(),"LATE", testTimeStamp.getImage_url()));
                            }
                            if(i==3){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getThu_date(),testTimeStamp.getThu_evening(),"LATE", testTimeStamp.getImage_url()));
                            }
                            if(i==4){

                                returnToRecyclerArrayList.add(new ReturnToRecycler(namehere,testTimeStamp.getFri_date(),testTimeStamp.getFri_evening(),"LATE", testTimeStamp.getImage_url()));
                            }



                        }
                    }


                }

            }

        }

        recyclerView_frag_adapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerView_frag_adapter);


    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.bottomNav_floatButtonShowLateResetiD:

                chart.fitScreen();

                //banish button

                //banishButton();

                //

                break;

            case R.id.bottomNav_floatButtonShowLateWeeklyiD:

                //boolean halt to back until finish animation


                boolean_halt_back = true;

                loopCount=0;

                //logic for who we want to view. , so we need an array record, which entry is late. , correspond to who is late.

                setupWhosLate_Week(); //setup data first.



                 mTimerHandler = new Handler();

                mTimerHandler_today = new Handler();



                timer = new Timer();

                timer_today = new Timer();



                chart.setVisibleXRange(0f, 2f);
                chart.setVisibleYRange(0f, 2f, dataSetArrayList_Final.get(1).getAxisDependency());



                timer.scheduleAtFixedRate(new TimerTask() {

                    @Override
                    public void run() {


                        if(loopCount<finalPointerArrayList.size()) {

                            loopWhosLate();
                            // this will not work, because, ,,
                            // or maybe we can detect, if changing


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(loopCount+"");
                                }
                            });


                        }else {

                            resizechart_andStopLoop();



//                            if(before_show_boolean) {
//                                showButton();
//                            }
                        }

                        //if(timer.)

                        if(before_show_boolean){

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showButton();
                                }
                            });

                        }


                    }

                },0,4000);
                //problem with this method, will increase exponentially everytime executed.
                //for small number, no problem, when number gets bigger, we will run into problem.
                Log.i("checkLate ", " name :" + dataSetArrayList_Final.get(4).getLabel() + " , x: " + dataSetArrayList_Final.get(4).getEntryForIndex(2).getX() + " , y: " + dataSetArrayList_Final.get(4).getEntryForIndex(2).getY());


                //banish button

                banishButton();

                //

                break;


            case R.id.bottomNav_floatButtonShowLateToday2iD:

                //animate all these buttons out of frame.

                boolean_halt_back = true;

                Log.i("flowShowbutton", "99 button pushed");

                //  ObjectAnimator animator = ObjectAnimator.ofFloat(fButtonToday)


                ////

                loopCount=0;

                setupWhosLate_todayReal(); //setup data first.

                mTimerHandler_today = new Handler();



                timer_today = new Timer();

                chart.setVisibleXRange(0f, 2f);
                chart.setVisibleYRange(0f, 2f, dataSetArrayList_Final.get(0).getAxisDependency());



                timer_today.scheduleAtFixedRate(new TimerTask() {

                    @Override
                    public void run() {


                        Log.i("flowShowbutton", "99._1 timer_task");


                        if(loopCount<finalPointerArrayList.size()) {

                            Log.i("flowShowbutton", "99._2 timer_task");

                            loopWhosLate();
                            // this will not work, because, ,,
                            // or maybe we can detect, if changing


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(loopCount+"");

                                    Log.i("flowShowbutton", "99._4 timer_task");
                                }
                            });

                            Log.i("flowShowbutton", "99._3 timer_task");

                        }else {

                            Log.i("flowShowbutton", "1");

                            resizechart_andStopLoop();

                            Log.i("flowShowbutton", "2");



                        }


                        if(before_show_boolean){

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showButton();
                                }
                            });

                        }
                        Log.i("flowShowbutton", "3");
                    }

                },0,4000);

                Log.i("flowShowbutton", "4");
                //banish button

                banishButton();

                if(before_show_boolean){
                    showButton();
                }

                //

                break;


            case R.id.bottomNav_floatButtonBackiD:

                //boolean_halt_back = true;

                if(!boolean_halt_back) {

                    Intent intent = new Intent(getActivity(), FingerPrint_LogIn_Final_Activity.class);
                    //finish();

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);





                }else {

                    Toast.makeText(getContext(),"please wait until animation finish", Toast.LENGTH_SHORT).show();

                }
                break;

        }


    }

    //showButton()

    public void callParent(){

        getActivity().onBackPressed();
    }


    private void showButton(){


        //// button today

//        try {
//            wait(1500);
//
//
//            ObjectAnimator animator = ObjectAnimator.ofFloat(fButtonToday,"translationY",70f);
//
//            fButtonToday.animate()
//                    .alpha(1f)
//                    .setDuration(200)
//                    .setListener(null);
//
//            animator.setDuration(200);
//            animator.start();
//
//            //// button week
//
//            ObjectAnimator animator2 = ObjectAnimator.ofFloat(fButtonWeeek,"translationY",70f);
//
//            animator2.setDuration(200);
//            animator2.start();
//
//            Animation fadeIn = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
//            fButtonWeeek.startAnimation(fadeIn);
//
//            //// button rse
//
//            ObjectAnimator animator3 = ObjectAnimator.ofFloat(fButtonReset,"translationY",70f);
//
//            animator3.setDuration(200);
//            animator3.start();
//
//            Animation fadeOut2 = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
//            fButtonReset.startAnimation(fadeOut2);
//
//            //text view today
//
//            ObjectAnimator animator_text_today = ObjectAnimator.ofFloat(textView_today,"translationY",70f);
//
//            animator_text_today.setDuration(200);
//            animator_text_today.start();
//
//            Animation fadeOut_text_today = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
//            textView_today.startAnimation(fadeOut_text_today);
//
//
//            //text view today
//
//            ObjectAnimator animator_text_week = ObjectAnimator.ofFloat(textView_week,"translationY",70f);
//
//            animator_text_week.setDuration(200);
//            animator_text_week.start();
//
//            Animation fadeOut_text_week = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
//            textView_week.startAnimation(fadeOut_text_week);
//
//            //text view resrt
//
//            ObjectAnimator animator_text_reset = ObjectAnimator.ofFloat(textView_clear_chart,"translationY",70f);
//
//            animator_text_reset.setDuration(200);
//            animator_text_reset.start();
//
//            Animation fadeOut_text_reset = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
//            textView_clear_chart.startAnimation(fadeOut_text_reset);
//
//            before_show_boolean =false;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//
        ObjectAnimator animator = ObjectAnimator.ofFloat(fButtonToday,"translationY",0f);

        Animation fadeIn2 = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        fButtonToday.startAnimation(fadeIn2);

        animator.setDuration(200);
        animator.start();

        //// button week

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(fButtonWeeek,"translationY",0f);

        animator2.setDuration(200);
        animator2.start();

        Animation fadeIn = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        fButtonWeeek.startAnimation(fadeIn);

        //// button rse

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(fButtonReset,"translationY",0f);

        animator3.setDuration(200);
        animator3.start();

        Animation fadeOut2 = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        fButtonReset.startAnimation(fadeOut2);

        //text view today

        ObjectAnimator animator_text_today = ObjectAnimator.ofFloat(textView_today,"translationY",0f);

        animator_text_today.setDuration(200);
        animator_text_today.start();

        Animation fadeOut_text_today = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        textView_today.startAnimation(fadeOut_text_today);


        //text view today

        ObjectAnimator animator_text_week = ObjectAnimator.ofFloat(textView_week,"translationY",0f);

        animator_text_week.setDuration(200);
        animator_text_week.start();

        Animation fadeOut_text_week = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        textView_week.startAnimation(fadeOut_text_week);

        //text view resrt

        ObjectAnimator animator_text_reset = ObjectAnimator.ofFloat(textView_clear_chart,"translationY",0f);

        animator_text_reset.setDuration(200);
        animator_text_reset.start();

        Animation fadeOut_text_reset = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        textView_clear_chart.startAnimation(fadeOut_text_reset);

        before_show_boolean =false;



        recyclerView.setAlpha(0.3f);

    }


    //banish button
    private void banishButton() {

        recyclerView.setAlpha(1f);

        //// button today


        ObjectAnimator animator = ObjectAnimator.ofFloat(fButtonToday,"translationY",70f);

//        fButtonToday.animate()
//                .alpha(0f)
//                .setDuration(200)
//                .setListener(null);
//
        animator.setDuration(200);
        animator.start();

        Animation fadeOutToday = AnimationUtils.loadAnimation(getContext(),R.anim.fadeout);
        fButtonToday.startAnimation(fadeOutToday);


        //// button week

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(fButtonWeeek,"translationY",70f);

        animator2.setDuration(200);
        animator2.start();

        Animation fadeOut = AnimationUtils.loadAnimation(getContext(),R.anim.fadeout);
        fButtonWeeek.startAnimation(fadeOut);

        //// button rse

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(fButtonReset,"translationY",70f);

        animator3.setDuration(200);
        animator3.start();

        Animation fadeOut2 = AnimationUtils.loadAnimation(getContext(),R.anim.fadeout);
        fButtonReset.startAnimation(fadeOut2);

       //text view today

        ObjectAnimator animator_text_today = ObjectAnimator.ofFloat(textView_today,"translationY",70f);

        animator_text_today.setDuration(200);
        animator_text_today.start();

        Animation fadeOut_text_today = AnimationUtils.loadAnimation(getContext(),R.anim.fadeout);
        textView_today.startAnimation(fadeOut_text_today);


        //text view today

        ObjectAnimator animator_text_week = ObjectAnimator.ofFloat(textView_week,"translationY",70f);

        animator_text_week.setDuration(200);
        animator_text_week.start();

        Animation fadeOut_text_week = AnimationUtils.loadAnimation(getContext(),R.anim.fadeout);
        textView_week.startAnimation(fadeOut_text_week);

        //text view resrt

        ObjectAnimator animator_text_reset = ObjectAnimator.ofFloat(textView_clear_chart,"translationY",70f);

        animator_text_reset.setDuration(200);
        animator_text_reset.start();

        Animation fadeOut_text_reset = AnimationUtils.loadAnimation(getContext(),R.anim.fadeout);
        textView_clear_chart.startAnimation(fadeOut_text_reset);




    }


    private void resizechart_andStopLoop() {

        //showButton();

        Log.i("flowShowbutton", "5");


        boolean_halt_back = false;

        chart.centerViewToAnimated(4f, 18f, dataSetArrayList_Final.get(4).getAxisDependency(), 900);
        chart.zoomOut();
        chart.setVisibleXRange(0f, 4f);
        chart.setVisibleYRange(0f, dataSetArrayList_Final.get(1).getYMax(), dataSetArrayList_Final.get(1).getAxisDependency());

        before_show_boolean=true;

        // chart.fitScreen();6
        Log.i("flowShowbutton", "6");

        if(timer_today!=null) {
            timer_today.cancel();
            timer_today.purge();
        }

        if(timer!=null) {
            timer.cancel();
            timer.purge();
        }

        Log.i("flowShowbutton", "7");


    }

    private void loopWhosLate(){

        Log.i("flowShowbutton", "99._5 loopwhoslate");
        recyclerView.smoothScrollToPosition(loopCount);
        chart.centerViewToAnimated(dataSetArrayList_Final.get(finalPointerArrayList.get(loopCount).getPointer_1()).getEntryForIndex(finalPointerArrayList.get(loopCount).getPointer_2()).getX(), dataSetArrayList_Final.get(finalPointerArrayList.get(loopCount).getPointer_1()).getEntryForIndex(finalPointerArrayList.get(loopCount).getPointer_2()).getY(), dataSetArrayList_Final.get(4).getAxisDependency(), 1500);
        //chart.centerViewToAnimated(dataSetArrayList_Final.get(finalPointerArrayList.get(loopCount).getPointer_1()));
        loopCount++;
    }

    //runOnUiThread()
}