package com.example.pedro.tobuyit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Login extends AppCompatActivity {

    EditText campoTextoMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        campoTextoMail = (EditText) findViewById(R.id.campo_texto_mail);
    }



    public void iniciarSessao(View view){
        campoTextoMail.getText();

        confirmarInicioSessao(view);
    }

    public void confirmarInicioSessao(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    //----------- ALTERAR LAYOUT PARA REGISTAR
    public void irParaRegistar(View view){
        setContentView(R.layout.layout_registar);
    }
    public void voltarALogin(View view){
        setContentView(R.layout.activity_login);
    }




    //--------------------------------------------- FICHEIROS ------------------------------------------------
    //------------ GRAVAR DADOS EM FICHEIRO
    public void gravarEmFicheiro(String dados, Context context){
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("users.txt",  Context.MODE_PRIVATE));
            outputStreamWriter.write("Ola");
            outputStreamWriter.close();
        }catch (IOException e){
            Log.e("Exception", "Erro ao gravar para ficheiro: " + e.toString());
        }
    }


    //---------- LER DADOS DE FICHEIRO
    public void lerDeFicheiro(Context context){

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "Ficheiro nao encontrado: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Nao foi possivel ler ficheiro: " + e.toString());
        }

    }
}

