package com.example.pedro.tobuyit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.pedro.tobuyit.Login;

import java.util.ArrayList;


public class Inicio extends AppCompatActivity {
    private static int TIME_OUT = 2500; //Tempo até sair do SplashScreen

    ArrayList<Utilizador> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);



        final View myLayout = findViewById(R.id.showTitle);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Inicio.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 1300);
    }


}
