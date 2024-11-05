package com.example.game_store;

import com.example.game_store.data.dto.CartDTO;
import com.example.game_store.data.dto.GameAddDTO;
import com.example.game_store.data.dto.UserLogInDTO;
import com.example.game_store.data.dto.UserRegisterDTO;
import com.example.game_store.service.CartService;
import com.example.game_store.service.GameService;
import com.example.game_store.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    UserService userService;
    GameService gameService;
    CartService cartService;

    public CommandLineRunnerImpl(GameService gameService, UserService userService, CartService cartService) {
        this.gameService = gameService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    public void run(String... args) throws Exception {
        //•	The first registered user becomes also an administrator

        Scanner scanner = new Scanner(System.in);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String[] token = scanner.nextLine().split("\\|");

        String output = "";
        while (!token[0].equals("END")) {
            output = switch (token[0]) {
                case "RegisterUser" -> userService.userRegister(new UserRegisterDTO
                        (token[1], token[2], token[3], token[4]));
                case "LoginUser" -> userService.userLogin(new UserLogInDTO(token[1], token[2]));
                case "Logout" -> userService.userLogout();
                case "AddGame" -> gameService.addGame
                        (new GameAddDTO(token[1], new BigDecimal(token[2]),
                                Double.parseDouble(token[3]), token[4], token[5], token[6], LocalDate.parse(token[7], formatter)));
                case "EditGame" -> {
                    Map<String, String> map = Arrays.stream(token).skip(2)
                            .map(t -> t.split("="))
                            .collect(Collectors.toMap(t -> t[0], t -> t[1]));
                    yield gameService.editGame(Long.valueOf(token[1]), map);
                }
                case "DeleteGame" -> gameService.deleteGame(Long.parseLong(token[1]));
                case "AllGames" -> gameService.allGames();
                case "DetailGame" -> gameService.detailGame(token[1]);
                case "OwnedGames" -> gameService.ownedGames();
                case "AddItem" -> cartService.addItem(new CartDTO(token[1]));
                case "RemoveItem" -> cartService.removeItem(new CartDTO(token[1]));
                case "BuyItem" -> cartService.buyItem();
                default -> output;
            };

            System.out.println(output);
            token = scanner.nextLine().split("\\|");
        }
    }
}
