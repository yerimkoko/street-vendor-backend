package store.streetvendor.controller.review;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.review.AddReviewRequest;
import store.streetvendor.service.review.ReviewService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @Auth
    @ApiOperation(value = "[리뷰] 등록하기")
    @PostMapping("/api/v1/review/{storeId}")
    public ApiResponse<String> addNewEvaluation(@MemberId Long memberId,
                                                @PathVariable Long storeId,
                                                @Valid @RequestBody AddReviewRequest request) {
        reviewService.addReview(request, memberId, storeId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "[리뷰] 삭제하기")
    @DeleteMapping("/api/v1/store/review")
    public ApiResponse<String> deleteReview(@MemberId Long memberId, @RequestParam Long storeId, @RequestParam Long reviewId) {
        return ApiResponse.OK;
    }

}
