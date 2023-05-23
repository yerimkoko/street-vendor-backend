package store.streetvendor.controller.review;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.auth.Auth;
import store.streetvendor.core.auth.MemberId;
import store.streetvendor.core.aws.response.ImageUrlResponse;
import store.streetvendor.core.exception.InvalidException;
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

    @Value("${cloud.aws.s3.baseUrl}")
    private String baseUrl;


    @Auth
    @ApiOperation(value = "[리뷰] 등록하기")
    @PostMapping("/api/v1/review")
    public ApiResponse<String> addNewEvaluation(@MemberId Long memberId,
                                                @Valid @RequestBody AddReviewRequest request) {

        reviewService.addReview(request, memberId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "[리뷰] 이미지 등록하기")
    @PostMapping("/api/v1/review/images")
    public ApiResponse<List<ImageUrlResponse>> addReviewImage(@RequestPart List<MultipartFile> reviewImages) {

        if (reviewImages.isEmpty()) {
            throw new InvalidException("리뷰 이미지를 추가해주세요.");
        }

        return ApiResponse.success(reviewService.addReviewImages(reviewImages));
    }


    @ApiOperation(value = "[리뷰] 가게에 있는 리뷰 가져오기")
    @GetMapping("/api/v1/reviews/{storeId}")
    public ApiResponse<List<ReviewResponse>> getAllReviews(@PathVariable Long storeId,
                                                           @RequestParam(required = false) Long cursor,
                                                           @RequestParam(required = false, defaultValue = "5") int size) {

        return ApiResponse.success(reviewService.getReviews(storeId, cursor, size, baseUrl));

    }

}
