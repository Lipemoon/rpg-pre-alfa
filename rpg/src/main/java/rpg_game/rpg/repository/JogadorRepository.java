package rpg_game.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rpg_game.rpg.entity.JogadorEntity;

public interface JogadorRepository extends JpaRepository<JogadorEntity, Long> {

}