package rpg_game.rpg.model;

public class Ataque {
    private Long idJogador;
    private Long idInimigo;
    private Long idTurno;
    private Long ataque;

    public Long getIdJogador() {
        return idJogador;
    }

    public Long getIdInimigo() {
        return idInimigo;
    }

    public Long getIdTurno() {
        return idTurno;
    }

    public Long getAtaque() {
        return ataque;
    }

    public void setIdJogador(Long idJogador) {
        this.idJogador = idJogador;
    }

    public void setIdInimigo(Long idInimigo) {
        this.idInimigo = idInimigo;
    }

    public void setIdTurno(Long idTurno) {
        this.idTurno = idTurno;
    }

    public void setAtaque(Long ataque) {
        this.ataque = ataque;
    }
}
