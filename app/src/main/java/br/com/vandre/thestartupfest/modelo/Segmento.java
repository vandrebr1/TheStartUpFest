package br.com.vandre.thestartupfest.modelo;

import java.io.Serializable;

public class Segmento implements Serializable {

    private static final long serialVersionUID = 1L;

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
