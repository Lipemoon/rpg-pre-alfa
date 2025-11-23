package rpg_game.rpg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inimigos")
public class InimigoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    Long forca;

    @Column
    Long vida;

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

    public Long getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(Long vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }
}
