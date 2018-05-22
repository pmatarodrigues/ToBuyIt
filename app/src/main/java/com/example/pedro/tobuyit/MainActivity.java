package com.example.pedro.tobuyit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int num;
    ArrayList<Utilizador> users = new ArrayList<>();
    public ArrayList<Produto> produtos = new ArrayList<>();

    private TextView usernameNav;
    private ListView listViewProdutos;

    private ProdutoAdapter adapter;
    private Dialog popup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TO.BUY.IT");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Entrada entrada = new Entrada();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout, entrada, entrada.getTag()).commit();

        popup = new Dialog(this);


        num = Integer.parseInt(getIntent().getStringExtra("USER_ATIVO"));
        try {
            users = lerUtilizadoresGuardados();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        addProdutos();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aminhaconta) {

        } else if (id == R.id.nav_inicio) {
            Entrada fragment = new Entrada();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

        } else if (id == R.id.nav_produtos) {
            FragmentProdutos fragment = new FragmentProdutos();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

        } else if (id == R.id.nav_promocoes) {
            FragmentPromocoes fragment = new FragmentPromocoes();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

        } else if (id == R.id.nav_carrinho) {
            FragmentCarrinho fragment = new FragmentCarrinho();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

        } else if (id == R.id.nav_lista_compras) {
            FragmentListaCompras fragment = new FragmentListaCompras();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

        } else if (id == R.id.nav_ondeestamos) {
            FragmentOndeEstamos fragment = new FragmentOndeEstamos();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

        } else if (id == R.id.nav_contactos) {
            FragmentContactos fragment = new FragmentContactos();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

        } else if (id == R.id.nav_ajuda) {
            FragmentAjuda fragment = new FragmentAjuda();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

        } else if (id == R.id.nav_logout) {
            users.get(num).setAtivo(false);
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("ANTERIOR", true);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // --------- LER UTILIZADORES GUARDADOS ------------------//
    public ArrayList<Utilizador> lerUtilizadoresGuardados() throws ClassNotFoundException {
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


    public void abrirPopupProduto(View view){
        TextView fecharPopup;
        TextView titulo;
        TextView aviso;
        TextView tituloBotao;
        TextView curiosidade;
        ImageView imagem;

        popup.setContentView(R.layout.popup_produto);
        fecharPopup = (TextView) popup.findViewById(R.id.fechar_popup);
        titulo = (TextView) popup.findViewById(R.id.popup_titulo);
        aviso = (TextView) popup.findViewById(R.id.popup_aviso);
        curiosidade = (TextView) popup.findViewById(R.id.popup_curiosidade);
        imagem = (ImageView) popup.findViewById(R.id.popup_imagem);
        tituloBotao = (TextView) view.findViewById(R.id.lista_produto_nome);

        System.out.println(produtos.size());

        try{

            for(int i = 0; i < produtos.size(); i++) {
                if(produtos.get(i).getNome().equals(tituloBotao.getText())){
                    titulo.setText(produtos.get(i).getNome());
                    aviso.setText(produtos.get(i).getAviso());
                    curiosidade.setText(produtos.get(i).getCuriosidade());
                    imagem.setImageResource(produtos.get(i).getImagem());
                }
            }
        } catch(Exception e){
            System.out.println("ERRO AQUI");
        }

        fecharPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();
    }


    // ----- fazer produtos aparecerem na lista de produtos
    public void addProdutosLista(View view){
        addProdutos();
        // ---------- DIRECIONAR ADAPTER PARA O RECYCLER VIEW -----------//
        ProdutoAdapter adapter = new ProdutoAdapter(produtos);
        RecyclerView myView = (RecyclerView) view.findViewById(R.id.recycler_view_produtos);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        // ------------- UTILIZAR O LAYOUT MANAGER ------------- //
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);
    }

    // ------- ADICIONAR PRODUTOS À LISTA DE PRODUTOS
    public void addProdutos(){
        produtos.add(new Produto(produtos.size()-1,"Maçã Golden", (float) 3.30, R.drawable.imagem_produtos_maca, false,"Este produto contém gluten", "Uma maçã por dia não sabe o bem que lhe fazia!"));
        produtos.add(new Produto(produtos.size()-1,"Bebida Energética Monster", (float) 3.30, R.drawable.imagem_produtos_bebidaenergetica_monster, false,"Não recomendado para diabéticos", "Este produto é energético"));
        produtos.add(new Produto(produtos.size()-1,"Coca-Cola Lata", (float) 3.30, R.drawable.imagem_produtos_cocacola, false,"A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size()-1,"Compal", (float) 3.30, R.drawable.imagem_produtos_compal, false,"A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size()-1,"Leite Achocolatado Agros", (float) 3.30, R.drawable.imagem_produtos_leiteachocolatado_agros, false,"A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size()-1,"Quejo Limianos", (float) 3.30, R.drawable.imagem_produtos_queijo_limianos, false,"A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size()-1,"Pizza Congelada", (float) 3.30, R.drawable.imagem_produtos_pizza, false,"A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size()-1,"Pudim Baunilha", (float) 3.30, R.drawable.imagem_produtos_pudim_baunilha, false,"A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size()-1,"Queijo Cabra", (float) 3.30, R.drawable.imagem_produtos_queijo_cabra, false,"A maça esta podre", "A Maça da pra comer?"));
    }

}
