package store.streetvendor.controller.review;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.domain.review.ReviewImage;
import store.streetvendor.core.exception.InvalidException;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.review.request.AddReviewImageRequest;
import store.streetvendor.core.utils.dto.review.response.ReviewImageResponse;
import store.streetvendor.service.review.ReviewImageService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewImageController {

    private final ReviewImageService reviewImageService;

    @ApiOperation(value = "[리뷰] 리뷰 이미지를 등록합니다")
    @Auth
    @PostMapping("/api/v1/review/images")
    public ApiResponse<List<ReviewImageResponse>> addReview(@RequestPart List<MultipartFile> images,
                                                            @RequestBody AddReviewImageRequest request,
                                                            @MemberId Long memberId) {
        if (images.isEmpty()) {
            throw new InvalidException(String.format("유저 [%s]가 업로드한 리뷰[%s]의 파일이 비어있습니다.", memberId, request.getReviewId()));
        }
        List<ReviewImage> reviewImages = reviewImageService.uploadReviewImage(request, images, memberId);
        List<ReviewImageResponse> response = reviewImageService.addReviewImages(reviewImages);
        return ApiResponse.success(response);

    }


}
