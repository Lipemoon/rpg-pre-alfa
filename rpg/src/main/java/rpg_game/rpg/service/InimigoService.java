package rpg_game.rpg.service;

import org.springframework.stereotype.Service;
import rpg_game.rpg.entity.InimigoEntity;
import rpg_game.rpg.model.InimigoNaoEncontradoException;
import rpg_game.rpg.model.PontosInvalidosException;
import rpg_game.rpg.repository.InimigoRepository;

import java.util.List;
import java.util.Random;

@Service

public class InimigoService {

    private InimigoRepository inimigoRepository;
    private Random random;
    public InimigoService(InimigoRepository inimigoRepository, Random random) {
        this.inimigoRepository = inimigoRepository;
        this.random = random;
    }

    public InimigoEntity criarInimigo() {
        InimigoEntity inimigo = new InimigoEntity();
        Long maxVidaAleatoria = 50L;
        Long vidaExtra = random.nextLong(maxVidaAleatoria);
        inimigo.setForca(25L);
        inimigo.setVida(100 + vidaExtra);
        inimigo.setVidaMaxima(inimigo.getVida());
        inimigoRepository.save(inimigo);
        return inimigo;
    }

    public InimigoEntity acharInimigoPeloId(Long idInimigo) {
        InimigoEntity inimigo = inimigoRepository.findById(idInimigo).orElseThrow(
                () -> new InimigoNaoEncontradoException("Inimigo não encontrado pelo id " + idInimigo)
        );
        return inimigo;
    }

    public InimigoEntity deletarInimigo(Long idInimigo) {
        InimigoEntity inimigo = inimigoRepository.findById(idInimigo).orElseThrow(
                () -> new InimigoNaoEncontradoException("Inimigo não encontrado pelo id " + idInimigo)
        );
        inimigoRepository.delete(inimigo);
        return inimigo;
    }
}
