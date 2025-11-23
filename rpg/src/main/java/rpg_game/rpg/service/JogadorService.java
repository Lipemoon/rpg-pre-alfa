package rpg_game.rpg.service;

import org.springframework.stereotype.Service;
import rpg_game.rpg.entity.JogadorEntity;
import rpg_game.rpg.model.JogadorNaoEncontradoException;
import rpg_game.rpg.model.PontosInvalidosException;
import rpg_game.rpg.repository.JogadorRepository;

@Service
public class JogadorService {
     private JogadorRepository jogadorRepository;

    public JogadorService(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    public JogadorEntity criarJogador(JogadorEntity input) {
        Long somaPontos = input.getForca() + input.getVida() + input.getMagia();
        if (somaPontos > 45) {
            throw new PontosInvalidosException("Você ultrapassou os pontos limites disponíveis!");
        }
        JogadorEntity jogador = new JogadorEntity();
        jogador.setForca(10 + input.getForca());
        jogador.setMagia(10 + input.getMagia());
        jogador.setVida(100 + (input.getVida() * 2));
        jogador.setVidaMaxima(100 + (input.getVida() * 2));
        jogadorRepository.save(jogador);
        return jogador;
}
    public JogadorEntity acharJogadorPeloId(Long idJogador) {
        JogadorEntity jogador = jogadorRepository.findById(idJogador).orElseThrow(
                () -> new JogadorNaoEncontradoException("Jogador não encontrado pelo id " + idJogador)
        );
        return jogador;
    }

    public JogadorEntity deletarJogador(Long idJogador) {
        JogadorEntity jogador = jogadorRepository.findById(idJogador).orElseThrow(
                () -> new JogadorNaoEncontradoException("Jogador não encontrado pelo id " + idJogador)
        );
        jogadorRepository.delete(jogador);
        return jogador;
    }
}


