package com.example.pedro.tobuyit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    ArrayList<Utilizador> users = new ArrayList<>();
    EditText txfMailLogin;
    EditText txfUsernameRegistar;
    EditText txfPasswordRegistar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //----------------------------- FUNCOES PARA BOTOES ----------------------------------//
    public void irParaRegistar(View view){
        setContentView(R.layout.layout_registar);
    }
    public void voltarALogin(View view) throws ClassNotFoundException {
        setContentView(R.layout.activity_login);
    }

    //--------------------------- FUNCOES EM INICIAR SESSAO ----------------------------//
    public void iniciarSessao(View view){
        txfMailLogin = (EditText) findViewById(R.id.campo_mail_login);

        for(int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
            if (txfMailLogin.getText().toString() == users.get(i).getUsername()){
                System.out.println("USERNAME VALIDO \n\n\n\n");
            }
            else{
                System.out.println("USERNAME INVALIDO! \n\n\n\n\n");
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("nome", txfMailLogin.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }


    //------------------------------ FUNCOES EM REGISTAR ---------------------------/
    public void registar(View view) throws IOException {
        txfUsernameRegistar = findViewById(R.id.campo_username_registar);
        txfPasswordRegistar = findViewById(R.id.campo_pass_registar);

        Utilizador user = new Utilizador();
        user.setUsername(txfUsernameRegistar.getText().toString());
        user.setPassword(txfPasswordRegistar.getText().toString());
        users.add(user);
    }




    //--------------------------------------------- FICHEIROS ------------------------------------------------
    //------------ GRAVAR DADOS EM FICHEIRO
    private void gravarUtilizador(View view, Utilizador user) throws IOException {

        Context context = this.getApplicationContext();
        try{
            FileOutputStream fos = context.openFileOutput("users.txt", MODE_APPEND);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(users);
            System.out.println("\n\n\n--------------------- GRAVOU" + users.get(0).getUsername() + " \n \n \n \n \n \n " +
                    "------------------------------- "+ users.get(0).getPassword());
            os.close();
            fos.close();
        }catch (IOException e){
            Log.e("Exception", "Erro ao gravar para ficheiro: " + e.toString());
        }
    }
    //---------- LER DADOS DE FICHEIRO
    private ArrayList lerUtilizadoresGuardados(View view) throws ClassNotFoundException {

        try {
            FileInputStream fis = getApplicationContext().openFileInput("users.txt");
            ObjectInputStream is = new ObjectInputStream(fis);
            ArrayList<Utilizador> users = (ArrayList) is.readObject();
            is.close();
            fis.close();

            System.out.println("\n\n\n--------------------- LEUUUUU" + users.get(0).getUsername() + " \n \n \n \n \n \n " +
                    "------------------------------- "+ users.get(0).getPassword());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}

