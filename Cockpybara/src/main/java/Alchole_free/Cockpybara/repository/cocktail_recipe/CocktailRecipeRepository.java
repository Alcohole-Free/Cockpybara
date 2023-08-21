package Alchole_free.Cockpybara.repository.cocktail_recipe;

import Alchole_free.Cockpybara.domain.cocktail_recipe.CocktailRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CocktailRecipeRepository extends JpaRepository<CocktailRecipe, Long>, CocktailRepositoryCustom{

    Page<CocktailRecipe> findCocktailRecipeByNameContaining(String name, Pageable pageable);
    List<CocktailRecipe> findCocktailRecipeByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<CocktailRecipe> findCocktailRecipeByOrderByCreatedAtDesc();
}
