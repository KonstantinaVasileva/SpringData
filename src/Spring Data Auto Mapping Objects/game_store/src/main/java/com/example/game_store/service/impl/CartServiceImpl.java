package com.example.game_store.service.impl;

import com.example.game_store.data.dto.CartDTO;
import com.example.game_store.data.entity.Game;
import com.example.game_store.data.entity.Order;
import com.example.game_store.data.entity.User;
import com.example.game_store.repository.GameRepository;
import com.example.game_store.repository.OrderRepository;
import com.example.game_store.repository.UserRepository;
import com.example.game_store.service.CartService;
import com.example.game_store.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    private Set<Game> cart;
    private final UserService userService;
    private final GameRepository gameRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(UserService userService, GameRepository gameRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
        this.cart = new HashSet<>();
        this.userRepository = userRepository;
    }


    @Override
    public String addItem(CartDTO item) {
        User user = userService.getLoggedIn();
        if (user == null) {
            return "No user is logged in.";
        }
        Game game = gameRepository.findByTitle(item.getTitle());
        if (game == null) {
            return "There is not such a game.";
        }

        this.cart.add(game);
        return String.format("%s added to cart", game.getTitle());
    }

    @Override
    public String removeItem(CartDTO item) {
        User user = userService.getLoggedIn();
        if (user == null) {
            return "No user is logged in.";
        }

        Game game = gameRepository.findByTitle(item.getTitle());
        boolean remove = this.cart.remove(game);
        if (remove) {
            return String.format("%s removed from cart", game.getTitle());
        }
        return "This game is not in cart.";
    }

    @Override
    public String buyItem() {
        User user = userService.getLoggedIn();
        if (user == null) {
            return "No user is logged in.";
        }

        user.getGames().addAll(cart);
        userRepository.saveAndFlush(user);

        String output = String.format("Successfully bought games: %s",
                String.join("%n", cart.stream().map(Game::getTitle).toList()));

        cart = new HashSet<>();

        Order order = new Order(user, cart);
        orderRepository.saveAndFlush(order);
        return output;
    }
}
