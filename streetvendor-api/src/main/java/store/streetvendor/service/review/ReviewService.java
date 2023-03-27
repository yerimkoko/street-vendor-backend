package store.streetvendor.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.review.ReviewRepository;
import store.streetvendor.core.domain.store.StoreRepository;
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

    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void addReview(AddReviewRequest request, Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, request.getOrderId());
        reviewRepository.save(request.toEntity(member, order));
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviews(Long storeId, Long cursor, int size) {
        return reviewRepository.findByStoreId(storeId, cursor, size).stream()
            .map(ReviewResponse::of)
            .collect(Collectors.toList());
    }
}
