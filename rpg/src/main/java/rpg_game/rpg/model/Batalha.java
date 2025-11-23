package rpg_game.rpg.model;

public class Batalha {
    Boolean batalhaFinalizada;
    String mensagem;

    public Boolean getBatalhaFinalizada() {
        return batalhaFinalizada;
    }

    public void setBatalhaFinalizada(Boolean batalhaFinalizada) {
        this.batalhaFinalizada = batalhaFinalizada;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
