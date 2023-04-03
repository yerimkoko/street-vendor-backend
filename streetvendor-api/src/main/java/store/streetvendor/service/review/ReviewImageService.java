package store.streetvendor.service.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.aws.AwsS3Service;
import store.streetvendor.core.aws.ImageFileType;
import store.streetvendor.core.aws.request.FileUploadRequest;
import store.streetvendor.core.aws.request.ImageFileUploadRequest;
import store.streetvendor.core.aws.response.ImageUrlResponse;
import store.streetvendor.core.domain.review.Review;
import store.streetvendor.core.domain.review.ReviewImage;
import store.streetvendor.core.domain.review.ReviewImageRepository;
import store.streetvendor.core.domain.review.ReviewRepository;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.utils.dto.review.request.AddReviewImageRequest;
import store.streetvendor.core.utils.dto.review.response.ReviewImageResponse;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewImageService {

    private final ReviewRepository reviewRepository;

    private final ReviewImageRepository reviewImageRepository;

    private final AwsS3Service s3Service;

    public List<ReviewImage> uploadReviewImage(AddReviewImageRequest request, List<MultipartFile> imageFiles, @NotNull Long memberId) {
        Review review = reviewRepository.findByReviewIdAndMemberId(memberId, request.getReviewId());
        if (review == null) {
            throw new NotFoundException(String.format("[%s]는 존재하지 않는 리뷰 입니다.", request.getReviewId()));
        }

        List<FileUploadRequest> fileUploadRequests = imageFiles.stream()
            .map(imageFile -> ImageFileUploadRequest.of(imageFile, ImageFileType.REVIEW_IMAGE))
            .collect(Collectors.toList());

        return s3Service.uploadImageFiles(fileUploadRequests).stream()
                .map(response -> request.toEntity(memberId, response.getImageUrl()))
            .collect(Collectors.toList());
    }

    @Transactional
    public List<ReviewImageResponse> addReviewImages(List<ReviewImage> reviewImages) {
        return reviewImageRepository.saveAll(reviewImages).stream()
            .map(ReviewImageResponse::of)
            .collect(Collectors.toList());
    }


}
