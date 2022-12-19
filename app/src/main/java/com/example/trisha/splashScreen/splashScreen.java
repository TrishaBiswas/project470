package com.example.trisha.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.trisha.DashBoard.DashBoard;
import com.example.trisha.R;
import com.example.trisha.logRegister.Login;
import com.google.firebase.auth.FirebaseAuth;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // check user AlreadyLogged In
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), DashBoard.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            }
        }, secondsDelayed * 2500);
    }
}