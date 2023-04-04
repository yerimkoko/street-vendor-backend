package store.streetvendor.controller.review;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
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


    @Auth
    @ApiOperation(value = "[리뷰] 등록하기")
    @PostMapping("/api/v1/review")
    public ApiResponse<String> addNewEvaluation(@MemberId Long memberId,
                                                @Valid @RequestPart AddReviewRequest request,
                                                @RequestPart List<MultipartFile> images) {
        if (images.isEmpty()) {
            throw new InvalidException(String.format("유저 [%s]가 업로드한 리뷰의 파일이 비어있습니다.", memberId));
        }
        reviewService.addReview(request, images, memberId);
        return ApiResponse.OK;
    }

    @ApiOperation(value = "[리뷰] 가게에 있는 리뷰 가져오기")
    @GetMapping("/api/v1/reviews/{storeId}")
    public ApiResponse<List<ReviewResponse>> getAllReviews(@PathVariable Long storeId,
                                                           @RequestParam(required = false) Long cursor,
                                                           @RequestParam(required = false, defaultValue = "5") int size) {

        return ApiResponse.success(reviewService.getReviews(storeId, cursor, size));

    }

}
