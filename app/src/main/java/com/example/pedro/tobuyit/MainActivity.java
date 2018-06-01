package com.example.pedro.tobuyit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int num;
    ArrayList<Utilizador> users = new ArrayList<>();
    public ArrayList<Produto> produtos = new ArrayList<>();
    ArrayList<Produto> produtosNoCarrinho = new ArrayList<>();
    ArrayList<ListaCompras> listaCompras = new ArrayList<>();
    ArrayList<Compra> comprasRecentes = new ArrayList<>();


    private TextView usernameNav;
    private ListView listViewProdutos;

    private ProdutoAdapter adapter;
    private Dialog popup;
    private Produto produtoAAdicionarAoCarrinho;


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
        // ---------- RECEBE O UTILIZADOR QUE INICIOU SESSÃO
        num = Integer.parseInt(getIntent().getStringExtra("USER_ATIVO"));
        try {
            users = lerUtilizadoresGuardados();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        users.get(num).setComprasRecentes(comprasRecentes);
        users.get(num).setListasDeCompras(listaCompras);

        View hview = navigationView.inflateHeaderView(R.layout.nav_header_main);

        usernameNav = (TextView) hview.findViewById(R.id.username_nav);
        usernameNav.setText(users.get(num).getUsername());

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
            FragmentAMinhaConta fragment = new FragmentAMinhaConta();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();
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

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public ArrayList<Produto> getProdutosNoCarrinho() {
        return produtosNoCarrinho;
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

    // ----------------------------------------------- FUNÇÕES DA LISTA DE PRODUTOS ---------------------------------------------- //
    // ----- fazer produtos aparecerem na lista de produtos
    public void addProdutosLista(View view) {
        //addProdutos();
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
    public void addProdutos() {
        produtos.add(new Produto(produtos.size() + 1, "Maçã Golden", (float) 3.30, R.drawable.imagem_produtos_maca, false, "Este produto contém gluten", "Uma maçã por dia não sabe o bem que lhe fazia!"));
        produtos.add(new Produto(produtos.size() + 1, "Bebida Energética Monster", (float) 3.30, R.drawable.imagem_produtos_bebidaenergetica_monster, false, "Não recomendado para diabéticos", "Este produto é energético"));
        produtos.add(new Produto(produtos.size() + 1, "Coca-Cola Lata", (float) 3.30, R.drawable.imagem_produtos_cocacola, true, "A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size() + 1, "Compal", (float) 3.30, R.drawable.imagem_produtos_compal, false, "A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size() + 1, "Leite Achocolatado Agros", (float) 3.30, R.drawable.imagem_produtos_leiteachocolatado_agros, false, "A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size() + 1, "Quejo Limianos", (float) 3.30, R.drawable.imagem_produtos_queijo_limianos, false, "A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size() + 1, "Pizza Congelada", (float) 3.30, R.drawable.imagem_produtos_pizza, true, "A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size() + 1, "Pudim Baunilha", (float) 3.30, R.drawable.imagem_produtos_pudim_baunilha, false, "A maça esta podre", "A Maça da pra comer?"));
        produtos.add(new Produto(produtos.size() + 1, "Queijo Cabra", (float) 3.30, R.drawable.imagem_produtos_queijo_cabra, false, "A maça esta podre", "A Maça da pra comer?"));
    }

    // ------------------- ABRIR POPUP DE PRODUTO ---------------------- //
    public void abrirPopupProduto(View view) {
        TextView fecharPopup;
        TextView titulo;
        TextView aviso;
        TextView tituloBotao;
        TextView curiosidade;
        ImageView imagem;
        // ------------- associar às views
        popup.setContentView(R.layout.popup_produto);
        fecharPopup = (TextView) popup.findViewById(R.id.fechar_popup);
        titulo = (TextView) popup.findViewById(R.id.popup_titulo);
        aviso = (TextView) popup.findViewById(R.id.popup_aviso);
        curiosidade = (TextView) popup.findViewById(R.id.popup_curiosidade);
        imagem = (ImageView) popup.findViewById(R.id.popup_imagem);
        tituloBotao = (TextView) view.findViewById(R.id.lista_produto_nome);

        try {
            // -------- verificar qual o produto que foi clicado
            for (int i = 0; i < produtos.size(); i++) {
                if (produtos.get(i).getNome().equals(tituloBotao.getText().toString())) {
                    produtoAAdicionarAoCarrinho = produtos.get(i);
                    titulo.setText(produtos.get(i).getNome());
                    aviso.setText(produtos.get(i).getAviso());
                    curiosidade.setText(produtos.get(i).getCuriosidade());
                    imagem.setImageResource(produtos.get(i).getImagem());
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO A VERIFICAR PRODUTO CLICADO");
        }
        // ---------------- BOTAO DE FECHAR POPUP
        fecharPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();
    }


    // ------------------- FUNÇÕES DO CARRINHO ------------------------- //
    public void adicionarProdutoAoCarrinho(View view) throws IOException {
        produtosNoCarrinho.add(produtoAAdicionarAoCarrinho);
        //users.get(num).getCarrinho().addProduto(findProduto(2));
        //System.out.println(users.get(num).getUsername());
        Toast.makeText(this, "'" + produtoAAdicionarAoCarrinho.getNome() + "' adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
    }

    public void carregarCarrinho(View view) throws ClassNotFoundException {

        TextView textoCarrinhoVazio = view.findViewById(R.id.carrinho_vazio_texto);


        if (produtosNoCarrinho.size() == 0) {
            textoCarrinhoVazio.setVisibility(View.VISIBLE);
        } else {
            textoCarrinhoVazio.setVisibility(View.INVISIBLE);

            CarrinhoAdapter adapter = new CarrinhoAdapter(produtosNoCarrinho);
            RecyclerView myView = (RecyclerView) view.findViewById(R.id.recycler_view_carrinho);
            myView.setHasFixedSize(true);
            myView.setAdapter(adapter);
            // ------------- UTILIZAR O LAYOUT MANAGER ------------- //
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            myView.setLayoutManager(llm);
        }
    }

    public void adicionarProdutoAoCarrinhoDaLista(View view, String nomeProduto) throws IOException {


        try {
            // -------- verificar qual o produto que foi clicado
            for (int i = 0; i < produtos.size(); i++) {
                if (produtos.get(i).getNome().equals(nomeProduto)) {
                    produtoAAdicionarAoCarrinho = produtos.get(i);
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO A VERIFICAR PRODUTO CLICADO");
        }
        adicionarProdutoAoCarrinho(view);
    }

    public void removerProdutoDoCarrinho(View view) {

    }

    public void adicionarTextoListaCompras(View view) throws IOException, ClassNotFoundException {
        //Button botaoAdicionarALista = (Button) findViewById(R.id.botao_adicionar_compra);
        EditText textoCompra = (EditText) findViewById(R.id.adicionar_compra_nome);

        ListaCompras compra = new ListaCompras(listaCompras.size() + 1, textoCompra.getText().toString(), false);
        users.get(num).getListasDeCompras().add(compra);

        for (int i = 0; i < users.get(num).getListasDeCompras().size(); i++) {
            System.out.println("COMPRAS: " + users.get(num).getListasDeCompras().get(i).getTexto());
        }

        try {
            gravarUtilizador(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void gravarUtilizador(ArrayList<Utilizador> users) throws IOException {

        try {
            FileOutputStream fos = this.openFileOutput("users.txt", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(users);
            os.close();
            fos.close();

            System.out.println("\n\nUTILIZADOR GUARDADO!");
            System.out.println(users.get(num).getListasDeCompras().size());
        } catch (IOException e) {
            Log.e("Exception", "Erro ao gravar para ficheiro: " + e.toString());
        }
    }

    public void concluirCarrinho(View view) {
        TextView fecharPopup;
        TextView popupCarrinhoPreco;

        float precoTotalCarrinho = 0;

        for (int i = 0; i < produtosNoCarrinho.size(); i++) {
            // -------- recebe o preço dos produtos e adiciona ao total
            precoTotalCarrinho += produtosNoCarrinho.get(i).getPreco();
        }

        popup.setContentView(R.layout.popup_concluir_carrinho);
        popupCarrinhoPreco = (TextView) popup.findViewById(R.id.popup_carrinho_preco);
        System.out.println(precoTotalCarrinho);
        fecharPopup = (TextView) popup.findViewById(R.id.fechar_popup);
        popupCarrinhoPreco.setText(precoTotalCarrinho + "€");

        fecharPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();
    }

    public void concluirCompra(View view) {

        Spinner opcaoCompra = (Spinner) popup.findViewById(R.id.popup_carrinho_opcaocompra);
        TextView textoSelecioneSupermercado = (TextView) popup.findViewById(R.id.popup_texto_introduzasupermercado);
        Spinner opcaoSupermercado = (Spinner) popup.findViewById(R.id.popup_opcaosupermercado);
        TextView textoIntroduzaMorada = (TextView) popup.findViewById(R.id.popup_texto_introduzamorada);
        EditText fieldIntroduzaMorada = (EditText) popup.findViewById(R.id.popup_field_introduzamorada);
        Button buttonConfirmar = (Button) popup.findViewById(R.id.popup_botao_adicionar_ao_carrinho_tiposel);
        Button buttonConcluir = (Button) popup.findViewById(R.id.popup_botao_adicionar_ao_carrinho);

        if (opcaoCompra.getSelectedItem().toString().equals("Levantar no Supermercado")) {
            opcaoCompra.setVisibility(View.GONE);
            opcaoSupermercado.setVisibility(View.VISIBLE);
            textoSelecioneSupermercado.setVisibility(View.VISIBLE);
            buttonConcluir.setVisibility(View.GONE);
            buttonConfirmar.setVisibility(View.VISIBLE);
        } else if (opcaoCompra.getSelectedItem().toString().equals("Receber em Casa")) {
            opcaoCompra.setVisibility(View.GONE);
            textoIntroduzaMorada.setVisibility(View.VISIBLE);
            fieldIntroduzaMorada.setVisibility(View.VISIBLE);
            buttonConcluir.setVisibility(View.GONE);
            buttonConfirmar.setVisibility(View.VISIBLE);
        }
    }

    public void compraConfirmada(View view) throws IOException {
        float precoTotalCarrinho = 0;

        for (int i = 0; i < produtosNoCarrinho.size(); i++) {
            // -------- recebe o preço dos produtos e adiciona ao total
            precoTotalCarrinho += produtosNoCarrinho.get(i).getPreco();
        }

        Compra compra = new Compra(users.get(num), precoTotalCarrinho, Calendar.getInstance().getTime());

        Spinner opcaoCompra = (Spinner) popup.findViewById(R.id.popup_carrinho_opcaocompra);
        TextView textoSelecioneSupermercado = (TextView) popup.findViewById(R.id.popup_texto_introduzasupermercado);
        Spinner opcaoSupermercado = (Spinner) popup.findViewById(R.id.popup_opcaosupermercado);
        TextView textoIntroduzaMorada = (TextView) popup.findViewById(R.id.popup_texto_introduzamorada);
        EditText fieldIntroduzaMorada = (EditText) popup.findViewById(R.id.popup_field_introduzamorada);
        Button buttonConfirmar = (Button) popup.findViewById(R.id.popup_botao_adicionar_ao_carrinho_tiposel);
        Button buttonConcluir = (Button) popup.findViewById(R.id.popup_botao_adicionar_ao_carrinho);

// --- caso pretenda levantar os produtos no supermercado
        if (opcaoCompra.getSelectedItem().toString().equals("Levantar no Supermercado")) {
            Toast.makeText(this, users.get(num).getUsername() + ", pode levantar o seu pedido no supermercado ToBuyIt selecionado." , Toast.LENGTH_SHORT).show();
            compra.setTipo("Supermercado");
            users.get(num).getComprasRecentes().add(compra);
// ----- caso pretenda receber os produtos em casa
        } else if (opcaoCompra.getSelectedItem().toString().equals("Receber em Casa")) {
            Toast.makeText(this, users.get(num).getUsername() + ", o seu pedido será entregue na morada " +
                    fieldIntroduzaMorada.getText().toString(), Toast.LENGTH_SHORT).show();
            compra.setTipo("Domicilio");
            users.get(num).getComprasRecentes().add(compra);
        }
    }
}
