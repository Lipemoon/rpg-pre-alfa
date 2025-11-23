package rpg_game.rpg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jogadores")
public class JogadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    Long forca;

    @Column
    Long vida;

    @Column
    Long magia;

    @Column
    Long vidaMaxima;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getForca() {
        return forca;
    }

    public void setForca(Long forca) {
        this.forca = forca;
    }

    public Long getVida() {
        return vida;
    }

    public void setVida(Long vida) {
        this.vida = vida;
    }

    public Long getMagia() {
        return magia;
    }

    public void setMagia(Long magia) {
        this.magia = magia;
    }

    public Long getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(Long vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }
}
