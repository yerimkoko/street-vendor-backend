package store.streetvendor.controller.review;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.review.request.AddReviewRequest;
import store.streetvendor.core.utils.dto.review.response.ReviewResponse;
import store.streetvendor.service.review.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @Auth
    @ApiOperation(value = "[리뷰] 등록하기")
    @PostMapping("/api/v1/review")
    public ApiResponse<String> addNewEvaluation(@MemberId Long memberId,
                                                @Valid @RequestBody AddReviewRequest request) {
        reviewService.addReview(request, memberId);
        return ApiResponse.OK;
    }

    @ApiOperation(value = "[리뷰] 모든 리뷰 가져오기")
    @GetMapping("/api/v1/reviews/{storeId}")
    public ApiResponse<List<ReviewResponse>> getAllReviews(@PathVariable Long storeId,
                                                           @RequestParam(required = false) Long cursor,
                                                           @RequestParam(required = false, defaultValue = "5") int size) {

        return ApiResponse.success(reviewService.getReviews(storeId, cursor, size));

    }

}
