package br.com.vandre.thestartupfest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloCallback;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.fetcher.ApolloResponseFetchers;
import com.apollographql.apollo.sample.GetAllStartupsQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.vandre.thestartupfest.adapters.StartupsAdapter;
import br.com.vandre.thestartupfest.app.Constantes;
import br.com.vandre.thestartupfest.app.TheStartUpFestAplicacao;
import br.com.vandre.thestartupfest.atividades.StartupActivity;
import br.com.vandre.thestartupfest.listeners.RecyclerItemClickListener;
import br.com.vandre.thestartupfest.modelo.Segmento;
import br.com.vandre.thestartupfest.modelo.Startup;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StartupsAdapter startupsAdapter;
    private ApolloClient apolloClient;
    private Handler uiHandler = new Handler(Looper.getMainLooper());
    private List<Startup> startups;
    ApolloCall<GetAllStartupsQuery.Data> startupCall;
    TheStartUpFestAplicacao app;

    private TextView tvFalha;
    private Button btnTentarNovamente;
    private TextView tvEscolhaSuaStartUP;
    private ProgressBar pbConectando;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciar();
    }

    private void iniciar() {
        declarar();
        ocultarParaCarregar();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(startupsAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (startups != null) {
                            Startup startup = startups.get(position);
                            abrirStartup(startup);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do
                    }
                })
        );

        btnTentarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarStartups();
            }
        });

        buscarStartups();

    }

    private void declarar() {

        tvFalha = findViewById(R.id.main_tvFalha);
        btnTentarNovamente = findViewById(R.id.main_btnTentarNovamente);
        tvEscolhaSuaStartUP = findViewById(R.id.main_tvEscolhaSuaStartUP);
        pbConectando = findViewById(R.id.main_pbConectando);


        startupsAdapter = new StartupsAdapter(MainActivity.this);
        recyclerView = findViewById(R.id.main_recycleview);
        app = (TheStartUpFestAplicacao) getApplication();
    }

    private void buscarStartups() {
        final GetAllStartupsQuery query = GetAllStartupsQuery.builder()
                .build();
        startupCall = app.apolloClient()
                .query(query)
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST);
        startupCall.enqueue(dataCallback);
    }

    private ApolloCall.Callback<GetAllStartupsQuery.Data> dataCallback
            = new ApolloCallback<>(new ApolloCall.Callback<GetAllStartupsQuery.Data>() {
        @Override
        public void onResponse(@NotNull Response<GetAllStartupsQuery.Data> response) {
            Log.e("TAG", response.toString());
            startups = setStartups(response);
            startupsAdapter.setStartups(startups);
            mostrarAposCarregado();
        }

        @Override
        public void onFailure(@NotNull ApolloException e) {
            Log.e("TAG", e.getMessage(), e);
            mostrarErro();
        }
    }, uiHandler);


    List<Startup> setStartups(Response<GetAllStartupsQuery.Data> response) {
        List<Startup> startups = new ArrayList<>();

        final GetAllStartupsQuery.Data responseData = response.data();

        if (responseData == null) {
            return Collections.emptyList();
        }
        final List<GetAllStartupsQuery.AllStartup> listastartups = responseData.allStartups();

        if (listastartups == null) {
            return Collections.emptyList();
        }

        for (GetAllStartupsQuery.AllStartup entry : listastartups) {
            if (entry.Segment() != null) {
                Startup startup = new Startup(entry.name(),

                        entry.teamCount(),
                        entry.description(),
                        entry.imageUrl(),
                        entry.annualReceipt(),
                        new Segmento(entry.Segment().code(), entry.Segment().name()));
                startups.add(startup);
            }
        }
        return startups;
    }

    private void ocultarParaCarregar() {
        tvFalha.setVisibility(GONE);
        btnTentarNovamente.setVisibility(GONE);
        tvEscolhaSuaStartUP.setVisibility(GONE);
        recyclerView.setVisibility(GONE);

        pbConectando.setVisibility(VISIBLE);
    }

    private void mostrarAposCarregado() {
        tvFalha.setVisibility(GONE);
        tvEscolhaSuaStartUP.setVisibility(GONE);
        pbConectando.setVisibility(View.GONE);

        tvEscolhaSuaStartUP.setVisibility(VISIBLE);
        recyclerView.setVisibility(VISIBLE);

    }

    private void mostrarErro() {
        btnTentarNovamente.setVisibility(VISIBLE);
        tvFalha.setVisibility(VISIBLE);

        tvEscolhaSuaStartUP.setVisibility(GONE);
        recyclerView.setVisibility(GONE);
        pbConectando.setVisibility(View.GONE);
    }

    private void abrirStartup(Startup startup) {
        Intent intent = new Intent(MainActivity.this, StartupActivity.class);
        intent.putExtra(Constantes.STARTUP, startup);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_maisvotados:
                //startActivity(new Intent(MainActivity.this, LocalLoginActivity.class));
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}




