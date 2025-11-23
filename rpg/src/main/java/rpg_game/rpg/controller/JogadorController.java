package rpg_game.rpg.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpg_game.rpg.entity.JogadorEntity;
import rpg_game.rpg.model.Error;
import rpg_game.rpg.model.JogadorNaoEncontradoException;
import rpg_game.rpg.model.PontosInvalidosException;
import rpg_game.rpg.service.JogadorService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rpg-game/jogador")
public class JogadorController {
    private JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @PostMapping()
    ResponseEntity criarJogador(@RequestBody JogadorEntity input) {
        try {
            JogadorEntity jogador = jogadorService.criarJogador(input);
            return ResponseEntity.status(HttpStatus.CREATED).body(jogador);
        } catch (PontosInvalidosException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/{idJogador}")
    ResponseEntity pegarJogadorPeloId(@PathVariable("idJogador") Long idJogador) {
        try {
            JogadorEntity jogador = jogadorService.acharJogadorPeloId(idJogador);
            return ResponseEntity.status(HttpStatus.OK).body(jogador);
        } catch (JogadorNaoEncontradoException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{idJogador}")
    ResponseEntity deletarJogadorPeloId(@PathVariable("idJogador") Long idJogador) {
        try {
            JogadorEntity jogador = jogadorService.deletarJogador(idJogador);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(jogador);
        } catch (JogadorNaoEncontradoException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

}