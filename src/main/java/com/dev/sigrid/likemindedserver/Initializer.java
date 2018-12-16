package com.dev.sigrid.likemindedserver;

import com.dev.sigrid.likemindedserver.domain.*;
import com.dev.sigrid.likemindedserver.repository.CategoryRepository;
import com.dev.sigrid.likemindedserver.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Transactional
public class Initializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;

    public Initializer(CategoryRepository categoryRepository, RoleRepository roleRepository) {
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... strings) {
        //create roles
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");

        if (!userRole.isPresent()) {
            Role role = Role.builder()
                    .name("ROLE_USER")
                    .description("Regular user")
                    .build();
            roleRepository.save(role);
        }

        if (!adminRole.isPresent()) {
            Role role = Role.builder()
                    .name("ROLE_ADMIN")
                    .description("Admin user")
                    .build();
            roleRepository.save(role);
        }

        //create categories
        Category boardgameCategory = categoryRepository.findByName("BOARDGAMES");
        Category dicegameCategory = categoryRepository.findByName("DICEGAMES");
        Category roleplayingCategory = categoryRepository.findByName("ROLEPLAYING");
        Category miniatureCategory = categoryRepository.findByName("MINIATURES");
        Category tileGameCategory = categoryRepository.findByName("TILEGAMES");
        Category classicalGameCategory = categoryRepository.findByName("CLASSICAL");
        Category cardGameCategory = categoryRepository.findByName("CARDGAMES");

        if (boardgameCategory == null) {
            Category boardGames = Category.builder()
                    .name("BOARDGAMES")
                    .description("Boardgames")
                    .iconFilePath("")
                    .build();
            categoryRepository.save(boardGames);
        }
        if (dicegameCategory == null) {
            Category dicegames = Category.builder()
                    .name("DICEGAMES")
                    .description("Games that are centered around rolling dice.")
                    .iconFilePath("")
                    .build();
            categoryRepository.save(dicegames);
        }
        if (roleplayingCategory == null) {
            Category roleplaying = Category.builder()
                    .name("ROLEPLAYING")
                    .description("Roleplaying games like D&D")
                    .iconFilePath("")
                    .build();
            categoryRepository.save(roleplaying);
        }
        if (miniatureCategory == null) {
            Category miniatures = Category.builder()
                    .name("MINIATURES")
                    .description("Games involving miniatures like Warhammer")
                    .iconFilePath("")
                    .build();
            categoryRepository.save(miniatures);
        }
        if (tileGameCategory == null) {
            Category tilegames = Category.builder()
                    .name("TILEGAMES")
                    .description("Games that revolves around tiles like Domino.")
                    .iconFilePath("")
                    .build();
            categoryRepository.save(tilegames);
        }
        if (classicalGameCategory == null) {
            Category classical = Category.builder()
                    .name("CLASSICAL")
                    .description("Classical games.")
                    .iconFilePath("")
                    .build();
            categoryRepository.save(classical);
        }
        if (cardGameCategory == null) {
            Category cardgame = Category.builder()
                    .name("CARDGAMES")
                    .description("Games that involve cards only.")
                    .iconFilePath("")
                    .build();
            categoryRepository.save(cardgame);
        }

        roleRepository.findAll().forEach(System.out::println);
        List<Category> categories = categoryRepository.findAll();
        System.out.println(categories);
    }
}
