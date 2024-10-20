package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    public ConsoleRunner(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
//        String size = scanner.nextLine();

//        String name = scanner.nextLine();
//        long id = Long.parseLong(scanner.nextLine());
        //        String letter = scanner.nextLine();
        //        BigDecimal price = new BigDecimal(scanner.nextLine());
        //        List<String> names = Arrays.stream(scanner.nextLine().split("\\s+")).toList();

//        int number = Integer.parseInt(scanner.nextLine());

        //        shampooService.getShampooBySize(Size.valueOf(size)).forEach(System.out::println);
//        shampooService.getShampooBySizeOrId(Size.valueOf(size), id)
//                .forEach(System.out::println);
//
//        shampooService.getShampooByPrice(price).forEach(System.out::println);
//
//        ingredientService.getIngredientStartWith(letter).forEach(System.out::println);

//        ingredientService.getIngredientFromList(names).forEach(System.out::println);

//        System.out.println(shampooService.countShampooByPrice(price));

//        shampooService.getShampooByIngredient(names).forEach(System.out::println);

//        shampooService.getShampooByIngredientCount(number).forEach(System.out::println);

//        ingredientService.deleteByName(name);

        //ingredientService.updatePrice();

        ingredientService.updatePriceWithName();
    }
}
