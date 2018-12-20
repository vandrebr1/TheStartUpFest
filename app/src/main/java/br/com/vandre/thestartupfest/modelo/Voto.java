package br.com.vandre.thestartupfest.modelo;

public class Voto {
    private String usuarioID;
    private String startupNome;
    private int votoProposta;
    private int votoApresentacao;
    private int votoDesenvolvimento;

    public Voto(String usuarioID, String startupNome, int votoProposta, int votoApresentacao, int votoDesenvolvimento) {
        this.usuarioID = usuarioID;
        this.votoProposta = votoProposta;
        this.votoApresentacao = votoApresentacao;
        this.votoDesenvolvimento = votoDesenvolvimento;
        this.startupNome = startupNome;
    }

    public Voto() {
    }

    public String getUsuarioID() {
        return usuarioID == null ? "" : usuarioID;
    }

    public int getVotoProposta() {
        return votoProposta;
    }

    public int getVotoApresentacao() {
        return votoApresentacao;
    }

    public int getVotoDesenvolvimento() {
        return votoDesenvolvimento;
    }

    public String getStartupNome() {
        return startupNome;
    }

    @Override
    public String toString() {
        return "Voto{" +
                "usuarioID='" + usuarioID + '\'' +
                ", startupNome='" + startupNome + '\'' +
                ", votoProposta=" + votoProposta +
                ", votoApresentacao=" + votoApresentacao +
                ", votoDesenvolvimento=" + votoDesenvolvimento +
                '}';
    }
}
