package br.com.vandre.thestartupfest.modelo;

public class Startup {

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

    public String getName() {
        return name == null ? "" : name;
    }

    public String getImageUrl() {
        return imageUrl == null ? "" : imageUrl;
    }

    public Segmento getSegmento() {
        return segmento  == null ? new Segmento("", "") : segmento;
    }


}
