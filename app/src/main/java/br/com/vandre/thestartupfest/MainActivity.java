package br.com.vandre.thestartupfest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apollographql.apollo.sample.GetAllStartupsQuery;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.vandre.thestartupfest.adapters.StartupsAdapter;
import br.com.vandre.thestartupfest.modelo.Segmento;
import br.com.vandre.thestartupfest.modelo.Startup;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Startup> startups;
    StartupsAdapter startupsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciar();
    }

    private void iniciar() {
        declarar();
        listarStartups();

        startupsAdapter = new StartupsAdapter(MainActivity.this, startups);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(startupsAdapter);

    }

    private void declarar() {
        recyclerView = findViewById(R.id.main_recycleview);
        startupsAdapter = new StartupsAdapter(MainActivity.this, startups);
        GetAllStartupsQuery getAllStartupsQuery = GetAllStartupsQuery.builder()
                .build();

    }


    private void listarStartups() {
        startups = new ArrayList<>();

        Startup startup = new Startup("Vandre", 1, "Descricao", "URL",
                55555.00, new Segmento("SEGMENTO", "Segmento"));
        startups.add(startup);
        startups.add(startup);
        startups.add(startup);
        startups.add(startup);
    }

}
