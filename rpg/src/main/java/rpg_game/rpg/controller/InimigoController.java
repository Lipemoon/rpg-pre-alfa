package rpg_game.rpg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpg_game.rpg.entity.InimigoEntity;
import rpg_game.rpg.entity.JogadorEntity;
import rpg_game.rpg.model.Error;
import rpg_game.rpg.model.InimigoNaoEncontradoException;
import rpg_game.rpg.model.JogadorNaoEncontradoException;
import rpg_game.rpg.model.PontosInvalidosException;
import rpg_game.rpg.service.InimigoService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rpg-game/inimigo")
public class InimigoController {

    private InimigoService inimigoService;

    public InimigoController(InimigoService inimigoService) {
        this.inimigoService = inimigoService;
    }

    @PostMapping()
    ResponseEntity criarInimigo() {
        try {
            InimigoEntity inimigo = inimigoService.criarInimigo();
            return ResponseEntity.status(HttpStatus.CREATED).body(inimigo);
        } catch (PontosInvalidosException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/{idInimigo}")
    ResponseEntity pegarInimigoPeloId(@PathVariable("idInimigo") Long idInimigo) {
        try {
            InimigoEntity inimigo = inimigoService.acharInimigoPeloId(idInimigo);
            return ResponseEntity.status(HttpStatus.OK).body(inimigo);
        } catch (InimigoNaoEncontradoException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{idInimigo}")
    ResponseEntity deletarInimigoPeloId(@PathVariable("idInimigo") Long idInimigo) {
        try {
            InimigoEntity inimigo = inimigoService.deletarInimigo(idInimigo);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(inimigo);
        } catch (JogadorNaoEncontradoException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

}
