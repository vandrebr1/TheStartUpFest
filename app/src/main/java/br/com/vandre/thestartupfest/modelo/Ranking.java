package br.com.vandre.thestartupfest.modelo;

import android.support.annotation.NonNull;

public class Ranking implements Comparable<Ranking> {
    private Startup startup;
    private int totalVotos;
    private int qtdVotos;

    public Ranking(Startup startup) {
        this.startup = startup;
    }

    public Ranking() {
    }

    public Startup getStartup() {
        return startup;
    }

    public int getTotalVotos() {
        return totalVotos;
    }


    public void contabilizarVoto(int voto) {
        totalVotos += voto;
        qtdVotos++;
    }

    public float media() {
        return totalVotos / qtdVotos;
    }


    @Override
    public int compareTo(@NonNull Ranking ranking) {

        if (this.media() > ranking.media()) {
            return -1;
        } else if (this.media() < ranking.media()) {
            return 1;
        } else {
            return 0;
        }

    }
}
