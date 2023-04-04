package store.streetvendor.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.aws.AwsS3Service;
import store.streetvendor.core.aws.ImageFileType;
import store.streetvendor.core.aws.request.FileUploadRequest;
import store.streetvendor.core.aws.request.ImageFileUploadRequest;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryRepository;
import store.streetvendor.core.domain.review.Review;
import store.streetvendor.core.domain.review.ReviewImage;
import store.streetvendor.core.domain.review.ReviewImageRepository;
import store.streetvendor.core.domain.review.ReviewRepository;
import store.streetvendor.core.utils.dto.review.request.AddReviewRequest;
import store.streetvendor.core.utils.dto.review.response.ReviewResponse;
import store.streetvendor.core.utils.service.MemberServiceUtils;
import store.streetvendor.core.utils.service.OrderServiceUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    
    private final MemberRepository memberRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    private final AwsS3Service s3Service;

    public void addReview(AddReviewRequest request, List<MultipartFile> images, Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);

        OrderHistory orderHistory = OrderServiceUtils.findOrderHistoryByOrderId(orderHistoryRepository, request.getOrderId());

        Review review = request.toEntity(member, orderHistory);

        List<FileUploadRequest> fileUploadRequests = images.stream()
            .map(imageFile -> ImageFileUploadRequest.of(imageFile, ImageFileType.REVIEW_IMAGE))
            .collect(Collectors.toList());

        List<ReviewImage> reviewImages = s3Service.uploadImageFiles(fileUploadRequests).stream()
            .map(imageUrlResponse -> ReviewImage.of(review, imageUrlResponse.getImageUrl()))
            .collect(Collectors.toList());

        review.addReviewImages(reviewImages);

        reviewRepository.save(review);


    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviews(Long storeId, Long cursor, int size) {
        return reviewRepository.findByStoreId(storeId, cursor, size).stream()
            .map(ReviewResponse::of)
            .collect(Collectors.toList());
    }
}
