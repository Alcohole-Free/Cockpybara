package Alchole_free.Cockpybara.controller.review;

import Alchole_free.Cockpybara.controller.cocktailrecipe.recipe_detail.review.ReviewDTO;
import Alchole_free.Cockpybara.controller.review.add_review.AddReviewRequest;
import Alchole_free.Cockpybara.controller.review.commented_recipes.CommentedRecipesResponse;
import Alchole_free.Cockpybara.domain.cocktail_recipe.taste.Taste;
import Alchole_free.Cockpybara.service.review.ReviewService;
import Alchole_free.Cockpybara.util.pagination.CustomPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/recipe/detail/{recipeId}/{memberId}")
    public ResponseEntity<String> addReview(@PathVariable Long recipeId,
                                            @PathVariable Long memberId,
                                            @RequestBody @Valid AddReviewRequest addReviewRequest) {
        Integer stars = addReviewRequest.getStars();
        String review = addReviewRequest.getReview();
        List<Taste> tastes = addReviewRequest.getTastes();

        reviewService.addReview(recipeId, memberId, stars, review, tastes);
        return ResponseEntity.ok("successfully add new review");
    }

    @DeleteMapping("/recipe/detail/{recipeId}/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long recipeId, @PathVariable Long reviewId) {
        reviewService.deleteReview(recipeId, reviewId);

        return ResponseEntity.accepted().body("successfully delete review");
    }

    @GetMapping("/user/{userId}/my-page/commented-recipes")
    public ResponseEntity<CustomPageResponse<CommentedRecipesResponse>> getCommentedRecipes(@PathVariable Long userId, int page){
        CustomPageResponse<CommentedRecipesResponse> commentedRecipes = reviewService.findCommentedRecipesByMember(userId, page);
        return ResponseEntity.ok(commentedRecipes);
    }

    @GetMapping("/recipe/detail/{recipeId}/reviews")
    public ResponseEntity<CustomPageResponse<ReviewDTO>> getReviews(@PathVariable Long recipeId, int page){
        CustomPageResponse<ReviewDTO> reviews = reviewService.getReviews(recipeId, page);
        return ResponseEntity.ok(reviews);
    }
}
