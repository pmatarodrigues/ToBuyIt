package com.example.pedro.tobuyit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.LinearLayout;
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

    Carrinho carrinho;


    private TextView usernameNav;
    private ListView listViewProdutos;
    int indexProdutoARemover;

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

        for(int i = 0; i < users.size(); i++){
            System.out.println(users.get(i).getUsername());
        }


        if (users.get(num).getComprasRecentes() == null) {
            users.get(num).setComprasRecentes(comprasRecentes);
        }
        if(users.get(num).getListasDeCompras() == null) {
            users.get(num).setListasDeCompras(listaCompras);
        }

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

    public void abrirProdutoDaSemana(View view){
        FragmentProdutos fragment = new FragmentProdutos();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();
    }

    public void abrirPromocaoDaSemana(View view){
        FragmentPromocoes fragment = new FragmentPromocoes();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();
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
        produtos.add(new Produto(produtos.size() + 1, "Maçã Golden", (float) 1.89, R.drawable.imagem_produtos_maca, false, "Este produto contém gluten", "Uma maçã por dia não sabe o bem que lhe fazia!"));
        produtos.add(new Produto(produtos.size() + 1, "Bebida Energética Monster", (float) 1.49, R.drawable.imagem_produtos_bebidaenergetica_monster, false, "Não recomendado para diabéticos", "Este produto é energético"));
        produtos.add(new Produto(produtos.size() + 1, "Coca-Cola Lata", (float) 0.99, R.drawable.imagem_produtos_cocacola, true, "Contém açúcar", "3.1% dos produtos consumidos em todo o mundo são da Coca-Cola"));
        produtos.add(new Produto(produtos.size() + 1, "Compal", (float) 1.49, R.drawable.imagem_produtos_compal, false, "Contém açúcar", "É da Compal"));
        produtos.add(new Produto(produtos.size() + 1, "Leite Achocolatado Agros", (float) 1.54, R.drawable.imagem_produtos_leiteachocolatado_agros, false, "Contém 158 calorias", "É da Agros"));
        produtos.add(new Produto(produtos.size() + 1, "Quejo Limianos", (float) 1.99, R.drawable.imagem_produtos_queijo_limianos, false, "Não recomendado para intolerantes à lactose", "É da Limianos"));
        produtos.add(new Produto(produtos.size() + 1, "Pizza Congelada", (float) 3.99, R.drawable.imagem_produtos_pizza, true, "Pode conter caroços", "Inventada em Itália"));
        produtos.add(new Produto(produtos.size() + 1, "Pudim Baunilha", (float) 0.99, R.drawable.imagem_produtos_pudim_baunilha, false, "Contém baunilha", "Não é pudim caseiro"));
        produtos.add(new Produto(produtos.size() + 1, "Queijo Cabra", (float) 1.99, R.drawable.imagem_produtos_queijo_cabra, true, "Não recomendado para intolerantes à lactose", "É de cabra"));
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


    public void concluirListaCompras(View view) throws IOException {
        gravarUtilizador(users);

        FragmentListaCompras fragment = new FragmentListaCompras();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();
    }

    // ------------------- FUNÇÕES DO CARRINHO ------------------------- //
    public void adicionarProdutoAoCarrinho(View view) throws IOException {
        TextView txvQuantidade = popup.findViewById(R.id.popup_produto_quantidadeproduto);
        produtoAAdicionarAoCarrinho.setQtdNoCarrinho(Integer.parseInt(txvQuantidade.getText().toString()));
        produtosNoCarrinho.add(produtoAAdicionarAoCarrinho);
        //users.get(num).getCarrinho().addProduto(findProduto(2));
        //System.out.println(users.get(num).getUsername());
        popup.dismiss();
        Toast.makeText(this, produtoAAdicionarAoCarrinho.getQtdNoCarrinho() + " '" + produtoAAdicionarAoCarrinho.getNome() + "' adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
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

    public void removerProdutoDoCarrinho(View view) {
        produtosNoCarrinho.remove(indexProdutoARemover);

        Toast.makeText(this, "Produto removido do carrinho", Toast.LENGTH_SHORT).show();
        popup.dismiss();

        FragmentCarrinho fragment = new FragmentCarrinho();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();
    }

    public void abrirPopupProdutoDoCarrinho(View view){
        TextView fecharPopup;
        TextView titulo;
        TextView tituloBotao;
        ImageView imagem;

        // ------------- associar às views
        popup.setContentView(R.layout.popup_produto_removerdocarrinho);
        fecharPopup = (TextView) popup.findViewById(R.id.fechar_popup);
        titulo = (TextView) popup.findViewById(R.id.popup_titulo);
        imagem = (ImageView) popup.findViewById(R.id.popup_imagem);
        tituloBotao = (TextView) view.findViewById(R.id.lista_produto_nome);

        try {
            // -------- verificar qual o produto que foi clicado
            for (int i = 0; i < produtosNoCarrinho.size(); i++) {
                if (produtosNoCarrinho.get(i).getNome().equals(tituloBotao.getText().toString())) {
                    titulo.setText(produtosNoCarrinho.get(i).getNome());
                    imagem.setImageResource(produtosNoCarrinho.get(i).getImagem());
                    indexProdutoARemover = i;
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

    public void adicionarTextoListaCompras(View view) throws IOException, ClassNotFoundException {
        //Button botaoAdicionarALista = (Button) findViewById(R.id.botao_adicionar_compra);
        EditText textoCompra = (EditText) findViewById(R.id.adicionar_compra_nome);

        ListaCompras compra = new ListaCompras(listaCompras.size() + 1, textoCompra.getText().toString(), false);
        users.get(num).getListasDeCompras().add(compra);
        textoCompra.setText("");
        FragmentListaCompras fragment = new FragmentListaCompras();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

        for (int i = 0; i < users.get(num).getListasDeCompras().size(); i++) {
            System.out.println("COMPRAS: " + users.get(num).getListasDeCompras().get(i).getTexto());
        }

        try {
            gravarUtilizador(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----- abrir popup de introdução dos dados para a conclusao do carrinho
    public void concluirCarrinho(View view) {
        TextView fecharPopup;
        TextView popupCarrinhoPreco;

        float precoTotalCarrinho = 0;

        for (int i = 0; i < produtosNoCarrinho.size(); i++) {
            // -------- recebe o preço dos produtos e adiciona ao total
            precoTotalCarrinho += produtosNoCarrinho.get(i).getPreco() * produtosNoCarrinho.get(i).getQtdNoCarrinho();
        }

        carrinho = new Carrinho(produtosNoCarrinho);
        carrinho.setPrecoTotal(precoTotalCarrinho);

        if(produtosNoCarrinho.size() == 0){
            Toast.makeText(this, "Não tem produtos no carrinho", Toast.LENGTH_SHORT).show();
        }
        else {
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
    }

    // ------ avançar para a escolha do tipo de compra
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

    // ------ confirmar compra e encomenda
    public void compraConfirmada(View view) throws IOException {
        float precoTotalCarrinho = 0;

        for (int i = 0; i < produtosNoCarrinho.size(); i++) {
            // -------- recebe o preço dos produtos e adiciona ao total
            precoTotalCarrinho += produtosNoCarrinho.get(i).getPreco() * produtosNoCarrinho.get(i).getQtdNoCarrinho();
        }

        Compra compra = new Compra(users.get(num), carrinho.getPrecoTotal(), Calendar.getInstance().getTime());

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
            compra.setProdutosComprados(produtosNoCarrinho);
            users.get(num).getComprasRecentes().add(compra);
            System.out.println(users.get(num).getComprasRecentes().get(0).getProdutosComprados().get(0).getNome());

            gravarUtilizador(users);
            produtosNoCarrinho.clear();
            popup.dismiss();

            FragmentCarrinho fragment = new FragmentCarrinho();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();

// ----- caso pretenda receber os produtos em casa
        } else if (opcaoCompra.getSelectedItem().toString().equals("Receber em Casa")) {
            Toast.makeText(this, users.get(num).getUsername() + ", o seu pedido será entregue na morada " +
                    fieldIntroduzaMorada.getText().toString(), Toast.LENGTH_SHORT).show();
            compra.setTipo("Domicilio");
            compra.setProdutosComprados(produtosNoCarrinho);
            users.get(num).getComprasRecentes().add(compra);

            for(int i = 0; i < users.size(); i++){
                System.out.println(users.get(i).getUsername());
            }
            gravarUtilizador(users);
            produtosNoCarrinho.clear();
            popup.dismiss();

            FragmentCarrinho fragment = new FragmentCarrinho();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();
        }
    }


    // --------------------- FUNÇÕES DE A MINHA CONTA ----------------------//
    public void abrirPopupCompra(View view) throws ClassNotFoundException {
        TextView fecharPopup;
        TextView tipo;
        TextView produto;
        TextView data;
        TextView preco;
        TextView dataCompra;

        // ------------- associar às views
        popup.setContentView(R.layout.popup_compra);
        fecharPopup = (TextView) popup.findViewById(R.id.fechar_popup);
        tipo = (TextView) popup.findViewById(R.id.popup_tipo);
        produto = (TextView) popup.findViewById(R.id.popup_compra_produto);
        data = (TextView) popup.findViewById(R.id.popup_compra_data);
        preco = (TextView) popup.findViewById(R.id.popup_compra_preco);
        dataCompra = (TextView) view.findViewById(R.id.lista_comprasrecentes_data);

        users = lerUtilizadoresGuardados();

        // -------- verificar qual o produto que foi clicado
        for(int i = 0; i < users.get(num).getComprasRecentes().size(); i++){

            // --------- verifica a data da compra clicada na lista de compras do user
            if(users.get(num).getComprasRecentes().get(i).getData().toString().equals(dataCompra.getText().toString())){
                produto.setText(users.get(num).getComprasRecentes().get(i).getProdutosComprados().get(0).getNome());
                produto.append("\n");
                tipo.setText(users.get(num).getComprasRecentes().get(i).getTipo());
                for(int p = 1; p < users.get(num).getComprasRecentes().get(i).getProdutosComprados().size(); p++) {
                    produto.append(users.get(num).getComprasRecentes().get(i).getProdutosComprados().get(p).getNome());
                    produto.append("\n");
                }
                data.setText(users.get(num).getComprasRecentes().get(i).getData().toString());
                preco.setText(String.valueOf(users.get(num).getComprasRecentes().get(i).getPreco()) + " €");
            }
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

    public void alterarPassword(View view) throws IOException {
        EditText passwordAntiga = findViewById(R.id.aminhaconta_alterarpassword_antiga);
        EditText passwordNova = findViewById(R.id.aminhaconta_alterarpassword_nova);
        EditText confirmarNova = findViewById(R.id.aminhaconta_alterarpassword_confirmarnova);
        Button confirmarAlteracao = findViewById(R.id.aminhaconta_alterarpassword_confirmar);

        if(!passwordAntiga.getText().toString().equals(users.get(num).getPassword())){
            Toast.makeText(this, "A palavra-passe antiga não esta correta!", Toast.LENGTH_SHORT).show();
        } else if(!passwordNova.getText().toString().equals(confirmarNova.getText().toString())){
            Toast.makeText(this, "As Palavras-passe novas não são iguais!", Toast.LENGTH_SHORT).show();
        }
        else{
            users.get(num).setPassword(passwordNova.getText().toString());
            gravarUtilizador(users);
            passwordAntiga.setText("");
            passwordNova.setText("");
            confirmarNova.setText("");
            Toast.makeText(this, "A Palavra-passe foi alterada com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ocultarAlterarPassword(View view){
        LinearLayout comprasRecentesTexto = findViewById(R.id.alterarpassword_espaco_aminhaconta);
        if(comprasRecentesTexto.getVisibility() == View.GONE) {
            comprasRecentesTexto.setVisibility(View.VISIBLE);
        } else{
            comprasRecentesTexto.setVisibility(View.GONE);
        }
    }


    // ---------------------- FICHEIROS ----------------------//
    public void gravarUtilizador(ArrayList<Utilizador> users) throws IOException {

        try {
            FileOutputStream fos = this.openFileOutput("users.txt", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(users);
            os.close();
            fos.close();

            System.out.println("\n\nUTILIZADOR GUARDADO!");
        } catch (IOException e) {
            Log.e("Exception", "Erro ao gravar para ficheiro: " + e.toString());
        }
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

}
