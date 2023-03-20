package store.streetvendor.controller.store.review;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.core.utils.dto.store.request.AddStoreReviewRequest;
import store.streetvendor.core.utils.dto.store.request.UpdateStoreReviewRequest;
import store.streetvendor.core.utils.dto.store.response.StoreReviewResponse;
import store.streetvendor.service.store.StoreService;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final StoreService storeService;

    @Deprecated
    @Auth
    @ApiOperation(value = "[리뷰] 수정하기")
    @PutMapping("/api/v1/store/review/{storeId}")
    public ApiResponse<String> updateReview(@MemberId Long memberId, @PathVariable Long storeId, @RequestBody UpdateStoreReviewRequest request) {
        storeService.updateReview(memberId, storeId, request);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "[리뷰] 등록하기")
    @PostMapping("/api/v1/store/review/{storeId}")
    public ApiResponse<String> addNewEvaluation(@MemberId Long memberId, @PathVariable Long storeId, @RequestBody AddStoreReviewRequest request) {
        storeService.addEvaluation(memberId, storeId, request);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "[리뷰] 삭제하기")
    @DeleteMapping("/api/v1/store/review")
    public ApiResponse<String> deleteReview(@MemberId Long memberId, @RequestParam Long storeId, @RequestParam Long reviewId) {
        storeService.deleteReview(memberId, storeId, reviewId);
        return ApiResponse.OK;
    }

    @ApiOperation(value = "[리뷰] 가게 리뷰 조회하기")
    @GetMapping("/api/v1/store/review/{storeId}")
    public ApiResponse<StoreReviewResponse> getStoreReviews(@PathVariable Long storeId) {
        return ApiResponse.success(storeService.getStoreReviews(storeId));
    }
}
