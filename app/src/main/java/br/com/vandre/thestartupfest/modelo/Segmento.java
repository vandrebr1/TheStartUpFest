package br.com.vandre.thestartupfest.modelo;

public class Segmento {

    private String code;
    private String name;

    public Segmento(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name == null ? "" : name;
    }
}
