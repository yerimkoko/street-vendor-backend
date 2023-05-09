package store.streetvendor.controller.review;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.review.request.AddReviewRequest;
import store.streetvendor.core.utils.dto.review.response.ReviewImageResponse;
import store.streetvendor.core.utils.dto.review.response.ReviewResponse;
import store.streetvendor.service.review.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Value("${cloud.aws.s3.baseUrl}")
    private String baseUrl;


    @Auth
    @ApiOperation(value = "[리뷰] 등록하기")
    @PostMapping("/api/v1/review")
    public ApiResponse<Long> addNewEvaluation(@MemberId Long memberId,
                                              @Valid @RequestBody AddReviewRequest request) {

        return ApiResponse.success(reviewService.addReview(request, memberId));
    }

    @Auth
    @ApiOperation(value = "[리뷰] 이미지 등록하기")
    @PostMapping("/api/v1/review/images/{reviewId}")
    public ApiResponse<List<ReviewImageResponse>> addReviewImage(@MemberId Long memberId,
                                                                 @PathVariable Long reviewId,
                                                                 @RequestPart List<MultipartFile> reviewImages) {

        return ApiResponse.success(reviewService.addReviewImages(reviewImages, reviewId, memberId, baseUrl));
    }


    @ApiOperation(value = "[리뷰] 가게에 있는 리뷰 가져오기")
    @GetMapping("/api/v1/reviews/{storeId}")
    public ApiResponse<List<ReviewResponse>> getAllReviews(@PathVariable Long storeId,
                                                           @RequestParam(required = false) Long cursor,
                                                           @RequestParam(required = false, defaultValue = "5") int size) {

        return ApiResponse.success(reviewService.getReviews(storeId, cursor, size, baseUrl));

    }

}
