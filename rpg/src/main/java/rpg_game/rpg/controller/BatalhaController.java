package rpg_game.rpg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpg_game.rpg.model.*;
import rpg_game.rpg.model.Error;
import rpg_game.rpg.service.BatalhaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rpg-game/batalha")
public class BatalhaController {

    private BatalhaService batalhaService;

    public BatalhaController(BatalhaService batalhaService) {
        this.batalhaService = batalhaService;
    }

    @PostMapping("/escolherAtaque")
    ResponseEntity escolherAtaque(@RequestBody Ataque ataque) {
        try {
            Batalha batalha = batalhaService.realizarAtaque(ataque);
            return ResponseEntity.status(HttpStatus.OK).body(batalha);
        } catch (JogadorNaoEncontradoException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (InimigoNaoEncontradoException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (MagiaIndisponivelException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (AtaqueInvalidoException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (TurnoInvalidoException exception) {
            Error error = new Error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

}
