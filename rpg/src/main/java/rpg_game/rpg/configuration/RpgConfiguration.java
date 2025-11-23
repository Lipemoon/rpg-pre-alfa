package rpg_game.rpg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rpg_game.rpg.controller.BatalhaController;
import rpg_game.rpg.controller.InimigoController;
import rpg_game.rpg.controller.JogadorController;
import rpg_game.rpg.repository.InimigoRepository;
import rpg_game.rpg.repository.JogadorRepository;
import rpg_game.rpg.service.BatalhaService;
import rpg_game.rpg.service.InimigoService;
import rpg_game.rpg.service.JogadorService;

import java.util.Random;

@Configuration
public class RpgConfiguration {
    @Bean
    JogadorController jogadorController(JogadorService jogadorService) {
        return new JogadorController(jogadorService);
    }

    @Bean
    BatalhaController batalhaController(BatalhaService batalhaService) {
        return new BatalhaController(batalhaService);
    }

    @Bean
    InimigoController inimigoController(InimigoService inimigoService) {
        return new InimigoController(inimigoService);
    }

    @Bean
    Random random() {
        return new Random();
    }

    @Bean
    BatalhaService batalhaService(JogadorRepository jogadorRepository, Random random, InimigoRepository inimigoRepository) {
        return new BatalhaService(jogadorRepository, inimigoRepository, random);
    }

    @Bean
    InimigoService inimigoService(InimigoRepository inimigoRepository) {
        return new InimigoService(inimigoRepository, random());
    }

    @Bean
    JogadorService jogadorService(JogadorRepository jogadorRepository) {
        return new JogadorService(jogadorRepository);
    }
}


