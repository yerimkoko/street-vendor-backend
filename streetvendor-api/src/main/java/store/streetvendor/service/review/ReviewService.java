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
import store.streetvendor.core.domain.review.ReviewRepository;
import store.streetvendor.core.domain.review.reviewcount.ReviewCountRepository;
import store.streetvendor.core.exception.ConflictException;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.utils.dto.review.request.AddReviewRequest;
import store.streetvendor.core.utils.dto.review.response.ReviewImageResponse;
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

    private final ReviewCountRepository reviewCountRepository;


    @Transactional
    public Long addReview(AddReviewRequest request, Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        MemberServiceUtils.validateMember(member, memberId);
        Review findReview = reviewRepository.findByOrderIdAndMemberId(memberId, request.getOrderId());
        if (findReview != null) {
            throw new ConflictException(String.format("[%s]에 대한 리뷰는 이미 작성하셨습니다.", request.getOrderId()));
        }

        List<OrderHistory> orderHistories = orderHistoryRepository.findOrderHistoryByOrderIdAndMemberId(request.getOrderId(), memberId);
        OrderServiceUtils.validateOrderHistoryIsEmpty(orderHistories, request.getOrderId());

        Review review = request.toEntity(member, orderHistories.get(0));

        reviewCountRepository.incrByCount(orderHistories.get(0).getStoreInfo().getStoreId());

        return reviewRepository.save(review).getId();

    }

    @Transactional
    public List<ReviewImageResponse> addReviewImages(List<MultipartFile> reviewImages, Long reviewId, Long memberId, String baseUrl) {

        Review review = reviewRepository.findByReviewIdAndMemberId(memberId, reviewId);
        if (review == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 review 는 존재하지 않습니다.", reviewId));
        }

        List<FileUploadRequest> fileUploadRequests = reviewImages.stream()
            .map(imageFile -> ImageFileUploadRequest.of(imageFile, ImageFileType.REVIEW_IMAGE))
            .collect(Collectors.toList());

        List<ReviewImage> reviewImageList = s3Service.uploadImageFiles(fileUploadRequests).stream()
            .map(imageUrlResponse -> ReviewImage.newInstance(review, imageUrlResponse.getImageUrl()))
            .collect(Collectors.toList());

        review.addReviewImages(reviewImageList);

        return reviewImageList.stream()
            .map(image -> ReviewImageResponse.of(image, baseUrl))
            .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviews(Long storeId, Long cursor, int size, String baseUrl) {
        return reviewRepository.findByStoreId(storeId, cursor, size).stream()
            .map(review -> ReviewResponse.of(review, baseUrl))
            .collect(Collectors.toList());
    }
}
