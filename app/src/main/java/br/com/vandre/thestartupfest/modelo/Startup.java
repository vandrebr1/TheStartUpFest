package br.com.vandre.thestartupfest.modelo;

import java.io.Serializable;

public class Startup implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int teamCount;
    private String description;
    private String imageUrl;
    private double annualReceipt;
    private Segmento segmento;

    public Startup(String name, int teamCount, String description, String imageUrl, double annualReceipt, Segmento segmento) {
        this.name = name;
        this.teamCount = teamCount;
        this.description = description;
        this.imageUrl = imageUrl;
        this.annualReceipt = annualReceipt;
        this.segmento = segmento;
    }

    public Startup() {
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getImageUrl() {
        return imageUrl == null ? "" : imageUrl;
    }

    public Segmento getSegmento() {
        return segmento == null ? new Segmento("", "") : segmento;
    }

    public String getDescription() {
        return description;
    }
}
