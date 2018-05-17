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
import android.widget.TextView;

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

import static android.graphics.Color.rgb;

public class Login extends AppCompatActivity {

    ArrayList<Utilizador> users = new ArrayList<>();
    EditText txfMailLogin;
    EditText txfPasswordLogin;
    EditText txfUsernameRegistar;
    EditText txfPasswordRegistar;
    TextView txvAvisoLoginUsername;
    TextView txvAvisoLoginPassword;

    int num; //vai permitir introduzir o login e a password em paginas diferentes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            users = lerUtilizadoresGuardados();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //----------------------------- FUNCOES PARA BOTOES ----------------------------------//
    public void irParaRegistar(View view){
        setContentView(R.layout.layout_registar);
    }
    public void voltarALogin(View view) throws ClassNotFoundException {
        setContentView(R.layout.activity_login);
    }

    //--------------------------- FUNCOES EM INICIAR SESSAO ----------------------------//
    public void avancarParaPassword(View view){
        txfMailLogin = (EditText) findViewById(R.id.campo_mail_login);
        txvAvisoLoginUsername = (TextView) findViewById(R.id.aviso_login_username);

        if(txfMailLogin.getText().length() == 0){
            txvAvisoLoginUsername.setText("Introduza o nome utilizador");
            txvAvisoLoginUsername.setTextColor(rgb(255, 0, 0));
        }
        else {
            //------------ VERIFICA SE O USERNAME INTRODUZIDO EXISTE NA BASE DE DADOS
            if (users.size() > 0) {
                for (int i = 0; i < users.size(); i++) {
                    if (txfMailLogin.getText().toString().equals(users.get(i).getUsername())) {
                        setContentView(R.layout.layout_login_password);     //----------- MUDA PARA O LAYOUT DA PASSWORD
                        num = i;        //----- FAZ COM QUE NAO SEJA NECESSÁRIO VOLTAR A CORRER TODOS OS UTILIZADORES
                    } else {
                        txvAvisoLoginUsername.setText("O username introduzido não existe");
                        txvAvisoLoginUsername.setTextColor(rgb(255, 0, 0));
                    }
                }
            } else {
                txfMailLogin.setVisibility(view.INVISIBLE);
                txvAvisoLoginUsername.setText("Não existem utilizadores");
                txvAvisoLoginUsername.setTextColor(rgb(255, 0, 0));
            }
        }
    }

    public void iniciarSessao(View view){
        txfPasswordLogin = (EditText) findViewById(R.id.campo_password_login);
        txvAvisoLoginPassword = (TextView) findViewById(R.id.aviso_login_password);

        if (users.get(num).getPassword().equals(txfPasswordLogin.getText().toString())) {

            //--------- mudar para a activity de entrada -----------//
            Intent intent = new Intent(this, MainActivity.class);

            //Bundle b = new Bundle();
            //intent.putExtras(b);

            startActivity(intent);
            finish();
        } else {
            txvAvisoLoginPassword.setText("A palavra-passe introduzida está incorreta");
            txvAvisoLoginPassword.setTextColor(rgb(255, 0, 0));
        }
    }


    //------------------------------ FUNCOES EM REGISTAR ---------------------------/
    public void registar(View view) throws IOException {
        txfUsernameRegistar = findViewById(R.id.campo_username_registar);
        txfPasswordRegistar = findViewById(R.id.campo_pass_registar);

        Utilizador user = new Utilizador();
        user.setUsername(txfUsernameRegistar.getText().toString());
        user.setPassword(txfPasswordRegistar.getText().toString());
        users.add(user);

        gravarUtilizador(view, users);
    }




    //--------------------------------------------- FICHEIROS ------------------------------------------------
    //------------ GRAVAR DADOS EM FICHEIRO
    private void gravarUtilizador(View view, ArrayList<Utilizador> users) throws IOException {

        Context context = this.getApplicationContext();
        try{
            FileOutputStream fos = context.openFileOutput("users.txt", MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(users);
            os.close();
            fos.close();

            System.out.println("\n\nUTILIZADOR GUARDADO!");
        }catch (IOException e){
            Log.e("Exception", "Erro ao gravar para ficheiro: " + e.toString());
        }
    }

    //---------- LER DADOS DE FICHEIRO
    private ArrayList<Utilizador> lerUtilizadoresGuardados() throws ClassNotFoundException {

        ArrayList<Utilizador> users = new ArrayList<>();
        try {
            FileInputStream fis = getApplicationContext().openFileInput("users.txt");
            ObjectInputStream is = new ObjectInputStream(fis);
            users = (ArrayList<Utilizador>) is.readObject();
            is.close();
            fis.close();

            System.out.println("\nUTILIZADOR RECEBIDO!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}

