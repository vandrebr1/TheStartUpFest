package br.com.vandre.thestartupfest.atividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.vandre.thestartupfest.R;
import br.com.vandre.thestartupfest.adapters.RankingAdapter;
import br.com.vandre.thestartupfest.modelo.Ranking;
import br.com.vandre.thestartupfest.modelo.Startup;
import br.com.vandre.thestartupfest.modelo.Voto;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RankingActivity extends AppCompatActivity {

    private TextView tvFalha;
    private Button btnTentarNovamente;
    private TextView tvEscolhaSuaStartUP;
    private ProgressBar pbConectando;
    private RecyclerView rvProposta;
    private RecyclerView rvApresentacao;
    private RecyclerView rvDesenvolvimento;
    private LinearLayout llResultados;
    private RankingAdapter rankingAdapterProposta;
    private RankingAdapter rankingAdapterApresentacao;
    private RankingAdapter rankingAdapterDesenvolvimento;

    private List<Ranking> rankingPropostas;
    private List<Ranking> rankingApresentacoes;
    private List<Ranking> rankingDesenvolvimentos;

    private List<Voto> votos;
    private boolean blnTerminouStartup = false;
    private boolean blnTerminouVoto = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ranking);
        iniciar();
    }

    private void iniciar() {
        declarar();
        ocultarParaCarregar();

        setRecicleViewProposta();
        setRecicleViewApresentacao();
        setRecicleViewDesenvolvimento();

    }

    private void declarar() {

        tvFalha = findViewById(R.id.ranking_tvFalha);
        btnTentarNovamente = findViewById(R.id.ranking_btnTentarNovamente);
        tvEscolhaSuaStartUP = findViewById(R.id.ranking_tvEscolhaSuaStartUP);
        pbConectando = findViewById(R.id.ranking_pbConectando);
        llResultados = findViewById(R.id.ranking_llResultados);

        rvProposta = findViewById(R.id.ranking_rvProposta);
        rvApresentacao = findViewById(R.id.ranking_rvApresentacao);
        rvDesenvolvimento = findViewById(R.id.ranking_rvDesenvolvimento);

        rankingPropostas = new ArrayList<>();
        rankingApresentacoes = new ArrayList<>();
        rankingDesenvolvimentos = new ArrayList<>();

        votos = new ArrayList<>(0);

        rankingAdapterProposta = new RankingAdapter(RankingActivity.this);
        rankingAdapterApresentacao = new RankingAdapter(RankingActivity.this);
        rankingAdapterDesenvolvimento = new RankingAdapter(RankingActivity.this);


    }

    protected void onStart() {
        super.onStart();
        DatabaseReference databaseStartups;
        DatabaseReference databaseVotos;
        databaseStartups = FirebaseDatabase.getInstance().getReference("startup");
        databaseVotos = FirebaseDatabase.getInstance().getReference("votos");

        databaseStartups.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Startup startup = postSnapshot.getValue(Startup.class);

                    rankingPropostas.add(new Ranking(startup));
                    rankingApresentacoes.add(new Ranking(startup));
                    rankingDesenvolvimentos.add(new Ranking(startup));

                }

                blnTerminouStartup = true;
                calcularRanking();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                blnTerminouStartup = false;
                mostrarErro();
            }
        });

        databaseVotos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot postSnapshot2 : postSnapshot.getChildren()) {
                        Voto voto = postSnapshot2.getValue(Voto.class);
                        votos.add(voto);
                    }
                }
                blnTerminouVoto = true;
                calcularRanking();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                blnTerminouVoto = false;
                mostrarErro();

            }
        });
    }

    private void calcularRanking() {
        if (blnTerminouStartup && blnTerminouVoto) {

            for (Voto voto : votos) {
                for (Ranking ranking : rankingPropostas) {
                    if (ranking.getStartup().getName().equals(voto.getStartupNome())) {
                        ranking.contabilizarVoto(voto.getVotoProposta());
                        break;
                    }
                }
            }

            Collections.sort(rankingPropostas);

            for (Voto voto : votos) {
                for (Ranking ranking : rankingApresentacoes) {
                    if (ranking.getStartup().getName().equals(voto.getStartupNome())) {
                        ranking.contabilizarVoto(voto.getVotoApresentacao());
                        break;
                    }
                }
            }
            Collections.sort(rankingApresentacoes);


            for (Voto voto : votos) {
                for (Ranking ranking : rankingDesenvolvimentos) {
                    if (ranking.getStartup().getName().equals(voto.getStartupNome())) {
                        ranking.contabilizarVoto(voto.getVotoDesenvolvimento());
                        break;
                    }
                }
            }

            Collections.sort(rankingDesenvolvimentos);

            rankingAdapterProposta.setRankings(rankingPropostas);
            rankingAdapterApresentacao.setRankings(rankingApresentacoes);
            rankingAdapterDesenvolvimento.setRankings(rankingDesenvolvimentos);

            mostrarAposCarregado();
        }
    }


    private void ocultarParaCarregar() {
        tvFalha.setVisibility(GONE);
        btnTentarNovamente.setVisibility(GONE);
        tvEscolhaSuaStartUP.setVisibility(GONE);
        llResultados.setVisibility(GONE);

        pbConectando.setVisibility(VISIBLE);
    }

    private void mostrarAposCarregado() {
        tvFalha.setVisibility(GONE);
        tvEscolhaSuaStartUP.setVisibility(GONE);
        pbConectando.setVisibility(View.GONE);

        tvEscolhaSuaStartUP.setVisibility(VISIBLE);
        llResultados.setVisibility(VISIBLE);

    }

    private void mostrarErro() {
        btnTentarNovamente.setVisibility(VISIBLE);
        tvFalha.setVisibility(VISIBLE);

        tvEscolhaSuaStartUP.setVisibility(GONE);
        llResultados.setVisibility(GONE);
        pbConectando.setVisibility(View.GONE);
    }

    private void setRecicleViewProposta() {
        setRecicleview(rvProposta, rankingAdapterProposta);
    }

    private void setRecicleViewApresentacao() {
        setRecicleview(rvApresentacao, rankingAdapterApresentacao);
    }

    private void setRecicleViewDesenvolvimento() {
        setRecicleview(rvDesenvolvimento, rankingAdapterDesenvolvimento);
    }

    private void setRecicleview(RecyclerView rv, RankingAdapter ra) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(ra);

    }
}
