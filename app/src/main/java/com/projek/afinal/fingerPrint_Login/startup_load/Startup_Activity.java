package com.projek.afinal.fingerPrint_Login.startup_load;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.projek.afinal.R;
import com.projek.afinal.fingerPrint_Login.fingerprint_login.FingerPrint_LogIn_Final_Activity;
import com.hanks.htextview.base.HTextView;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class Startup_Activity extends AppCompatActivity implements Observer {

    private Timer timer;
    private int countHere;
    private Presenter_Startup presenter;

    private HTextView hTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_);

        hTextView = findViewById(R.id.scaletextID);


        ConstraintLayout constraintLayout = findViewById(R.id.layoutStartupID);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        countHere=0;

        presenter = new Presenter_Startup();

        animationDrawable.setEnterFadeDuration(800);
        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();





        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                countHere++;

                if(countHere==1){

                           hTextView.animateText("\"Hello..\"");

                }
                if(countHere==3){

                           // hTextView.setTextSize(30);
                          hTextView.animateText("\"Welcome..\"");

                }

                if(countHere==5){
                    timer.cancel();

                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(Startup_Activity.this,R.anim.fadein,R.anim.fadeout);

                    Intent intent = new Intent(Startup_Activity.this, FingerPrint_LogIn_Final_Activity.class);
                    startActivity(intent,activityOptions.toBundle());
                    finish();


                }


            }
        },0,1200);


    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
