package rpg_game.rpg.service;

import org.springframework.stereotype.Service;
import rpg_game.rpg.entity.InimigoEntity;
import rpg_game.rpg.entity.JogadorEntity;
import rpg_game.rpg.model.*;
import rpg_game.rpg.repository.InimigoRepository;
import rpg_game.rpg.repository.JogadorRepository;

import java.util.Random;

@Service
public class BatalhaService {
    private JogadorRepository jogadorRepository;
    private InimigoRepository inimigoRepository;
    private Random random;
    public BatalhaService(JogadorRepository jogadorRepository, InimigoRepository inimigoRepository, Random random) {
        this.jogadorRepository = jogadorRepository;
        this.inimigoRepository = inimigoRepository;
        this.random = random;
    }

    public Batalha realizarAtaque(Ataque ataque) {
        Batalha batalha = new Batalha();
        JogadorEntity jogador = jogadorRepository.findById(ataque.getIdJogador()).orElseThrow(
                () -> new JogadorNaoEncontradoException("Jogador não encontrado pelo id " + ataque.getIdJogador())
        );
        InimigoEntity inimigo = inimigoRepository.findById(ataque.getIdInimigo()).orElseThrow(
                () -> new InimigoNaoEncontradoException("Inimigo não encontrado pelo id " + ataque.getIdInimigo())
        );
        if (ataque.getIdTurno().equals(jogador.getId())) {
            if (ataque.getAtaque() == 1) {
                Long danoExtra = random.nextLong(20);
                Long dano = jogador.getForca() + danoExtra;
                inimigo.setVida(inimigo.getVida() - dano);
                if (inimigo.getVida() <= 0) {
                    batalha.setMensagem("Você atacou e tirou " + dano + " de vida dele, " +
                            "VOCÊ VENCEU A LUTA!!! " +
                            "Batalha Finalizada Obrigado por Jogar a Beta do RPG");
                    batalha.setBatalhaFinalizada(true);
                    inimigo.setVida(0L);
                    jogadorRepository.save(jogador);
                    inimigoRepository.save(inimigo);
                    return batalha;
                }
                batalha.setMensagem("Você atacou e tirou " + dano + " de vida do inimigo");
            } else if (ataque.getAtaque() == 2) {
                Long danoExtra = random.nextLong(40);
                Long danoMagia = (jogador.getMagia() / 2) + danoExtra;
                inimigo.setVida(inimigo.getVida() - danoMagia);
                if (inimigo.getVida() <= 0) {
                    batalha.setMensagem("Você atacou usando magia e tirou " + danoMagia + " de vida dele, " +
                            "VOCÊ VENCEU A LUTA!!! " +
                            "Batalha Finalizada Obrigado por Jogar a Alfa do RPG");
                    batalha.setBatalhaFinalizada(true);
                    inimigo.setVida(0L);
                    jogadorRepository.save(jogador);
                    inimigoRepository.save(inimigo);
                    return batalha;
                }
                batalha.setMensagem("Você atacou usando magia e tirou " + danoMagia + " de vida do inimigo");
            } else {
                throw new AtaqueInvalidoException("Esse ataque é inválido!");
            }
        } else if (ataque.getIdTurno().equals(inimigo.getId())) {
            Long danoExtra = random.nextLong(inimigo.getForca());
            if (ataque.getAtaque() == 1) {
                Long dano = inimigo.getForca() + danoExtra;
                jogador.setVida(jogador.getVida() - dano);
                if (jogador.getVida() <= 0) {
                    batalha.setMensagem("O inimigo atacou você e tirou "
                            + dano + " de sua vida, VOCÊ PERDEU A LUTA :/ " +
                            " Batalha Finalizada Obrigado por Jogar a Beta do RPG");
                    batalha.setBatalhaFinalizada(true);
                    jogador.setVida(0L);
                    jogadorRepository.save(jogador);
                    inimigoRepository.save(inimigo);
                    return batalha;
                }
                batalha.setMensagem("O inimigo atacou você e tirou " + dano + " de sua vida");
            } else {
                throw new AtaqueInvalidoException("Esse ataque é inválido!");
            }
        } else {
            throw new TurnoInvalidoException("Id do turno é inválido!");
        }
        batalha.setBatalhaFinalizada(false);
        jogadorRepository.save(jogador);
        inimigoRepository.save(inimigo);
        return batalha;
    }
}
