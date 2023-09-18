package Alchole_free.Cockpybara.repository.cocktail_recipe;

import Alchole_free.Cockpybara.domain.cocktail_recipe.AlcoholicType;
import Alchole_free.Cockpybara.domain.cocktail_recipe.Category;
import Alchole_free.Cockpybara.domain.cocktail_recipe.CocktailRecipe;
import Alchole_free.Cockpybara.domain.cocktail_recipe.Glass;
import Alchole_free.Cockpybara.domain.cocktail_recipe.taste.Taste;
import Alchole_free.Cockpybara.domain.ingredient.IngredientCategory;
import Alchole_free.Cockpybara.repository.cocktail_recipe.condition.CocktailRecipeSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static Alchole_free.Cockpybara.domain.cocktail_recipe.QCocktailRecipe.cocktailRecipe;
import static Alchole_free.Cockpybara.domain.cocktail_recipe.taste.QRecipeTaste.recipeTaste;
import static Alchole_free.Cockpybara.domain.ingredient.QRecipeIngredient.recipeIngredient;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class CocktailRecipeRepositoryImpl implements CocktailRecipeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CocktailRecipe> search(CocktailRecipeSearchCondition searchCondition, Pageable pageable) {
        List<CocktailRecipe> content = queryFactory
                .selectFrom(cocktailRecipe)
                .join(cocktailRecipe.tastes, recipeTaste)
                .leftJoin(cocktailRecipe.ingredients, recipeIngredient)
                .leftJoin(recipeIngredient.ingredient)
                .where(
                        nameLike(searchCondition.getName()),
                        alcoholicTypeIn(searchCondition.getAlcoholicTypes()),
                        categoryIn(searchCondition.getCategories()),
                        glassIn(searchCondition.getGlasses()),
                        recipeTasteIn(searchCondition.getTastes()),
                        ingredientCategoryIn(searchCondition.getIngredientCategories())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = getCount(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    private JPAQuery<Long> getCount(CocktailRecipeSearchCondition searchCondition){
        return queryFactory
                .select(cocktailRecipe.count())
                .from(cocktailRecipe)
                .join(cocktailRecipe.tastes, recipeTaste)
                .leftJoin(cocktailRecipe.ingredients, recipeIngredient)
                .leftJoin(recipeIngredient.ingredient)
                .where(
                        nameLike(searchCondition.getName()),
                        alcoholicTypeIn(searchCondition.getAlcoholicTypes()),
                        categoryIn(searchCondition.getCategories()),
                        glassIn(searchCondition.getGlasses()),
                        recipeTasteIn(searchCondition.getTastes()),
                        ingredientCategoryIn(searchCondition.getIngredientCategories())
                );
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? cocktailRecipe.name.contains(name) : null;
    }

    private BooleanExpression alcoholicTypeIn(List<AlcoholicType> alcoholicTypes) {
        return alcoholicTypes==null?null:cocktailRecipe.alcoholicType.in(alcoholicTypes);
    }

    private BooleanExpression categoryIn(List<Category> categories) {
        return categories==null?null:cocktailRecipe.category.in(categories);
    }

    private BooleanExpression glassIn(List<Glass> glasses) {
        return glasses==null?null:cocktailRecipe.glass.in(glasses);
    }

    private BooleanExpression recipeTasteIn(List<Taste> tastes) {
        return tastes==null?null:recipeTaste.taste.in(tastes);
    }

    private BooleanExpression ingredientCategoryIn(List<IngredientCategory> ingredientCategories) {
        return ingredientCategories==null?null:recipeIngredient.ingredient.ingredientCategory.in(ingredientCategories);
    }


}
