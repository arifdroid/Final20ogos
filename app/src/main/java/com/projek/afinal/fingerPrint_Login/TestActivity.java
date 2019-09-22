package com.projek.afinal.fingerPrint_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.projek.afinal.R;

public class TestActivity extends AppCompatActivity {

    private String userName, userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        Intent intent = getIntent();

        userName = intent.getStringExtra("adminName_asAdmin_2");
        userPhone = intent.getStringExtra("adminPhone_asAdmin_2 ");


        Log.i("checkTestHere", "username:"+userName+ " , userphone:"+userPhone);


    }
}
