package com.example.game_store.service.impl;

import com.example.game_store.data.dto.GameAddDTO;
import com.example.game_store.data.dto.GameAllDTO;
import com.example.game_store.data.dto.GameDetailsDTO;
import com.example.game_store.data.entity.Game;
import com.example.game_store.data.entity.User;
import com.example.game_store.repository.GameRepository;
import com.example.game_store.service.GameService;
import com.example.game_store.service.UserService;
import com.example.game_store.util.ValidatorService;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final UserService userService;
    private final ModelMapper mapper;
    private final GameRepository gameRepository;
    private final ValidatorService validatorService;

    public GameServiceImpl(UserService userService, ModelMapper mapper, GameRepository gameRepository, ValidatorService validatorService) {
        this.userService = userService;
        this.mapper = mapper;
        this.gameRepository = gameRepository;
        this.validatorService = validatorService;
    }

    @Override
    public String addGame(GameAddDTO gameAddDTO) {

        Game game = new Game();
        if (userService.getLoggedIn() != null && userService.getLoggedIn().isAdmin()) {
            if (!validatorService.isValid(gameAddDTO)) {
                Set<ConstraintViolation<GameAddDTO>> set = validatorService.validate(gameAddDTO);
                return set.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(System.lineSeparator()));
            }

            if (gameRepository.findByTitle(gameAddDTO.getTitle()) == null) {

                game = mapper.map(gameAddDTO, Game.class);
                this.gameRepository.saveAndFlush(game);
                return String.format("Added %s", game.getTitle());
            }
            return "Game already exist.";
        }
        return "Logged in user is not admin.";
    }

    @Override
    public String editGame(Long id, Map<String, String> map) {

        Game game = gameRepository.findGameById(id);
        if (userService.getLoggedIn() != null && userService.getLoggedIn().isAdmin()) {

            if (game == null) {
                return "Game with the given id dose not exist.";
            }

            for (Map.Entry<String, String> value : map.entrySet()) {
                switch (value.getKey()) {
                    case "title" -> game.setTitle(value.getValue());
                    case "price" -> game.setPrice(new BigDecimal(value.getValue()));
                    case "size" -> game.setSize(Double.parseDouble(value.getValue()));
                    case "trailer" -> game.setTrailer(value.getValue());
                    case "thumbnail" -> game.setThumbnail(value.getValue());
                    case "description" -> game.setDescription(value.getValue());
                    case "releaseDate" ->
                            game.setReleaseDate(LocalDate.parse(value.getValue(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                }
            }
            return String.format("Edited %s", game.getTitle());
        }
        return "Logged in user is not admin.";
    }

    @Override
    public String deleteGame(Long id) {
        String output = "";
        if (userService.getLoggedIn() != null && userService.getLoggedIn().isAdmin()) {
            Game game = gameRepository.findGameById(id);
            if (game == null) {
                output = "No such game with given id.";
            } else {
                output = String.format("Deleted %s", game.getTitle());
                gameRepository.delete(game);
            }
        }
        return output;
    }

    @Override
    public String allGames() {
        List<String> games = gameRepository.findAll()
                .stream()
                .map(g -> mapper.map(g, GameAllDTO.class))
                .map(GameAllDTO::toString)
                .toList();

        return String.join(System.lineSeparator(), games);
    }

    @Override
    public String detailGame(String title) {
        Game game = gameRepository.findByTitle(title);
        if (game == null) {
            return "Game dose not exist.";
        }

        GameDetailsDTO gameDetailsDTO = mapper.map(game, GameDetailsDTO.class);
        return gameDetailsDTO.toString();
    }

    @Override
    public String ownedGames() {

        User user = userService.getLoggedIn();

        if (user == null){
            return "No user is logged in.";
        }
        return user.getGames().stream().map(Game::getTitle).collect(Collectors.joining(System.lineSeparator()));
    }
}

