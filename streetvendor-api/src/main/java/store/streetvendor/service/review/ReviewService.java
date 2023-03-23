package store.streetvendor.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.review.ReviewRepository;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreRepository;
import store.streetvendor.core.utils.dto.review.AddReviewRequest;
import store.streetvendor.core.utils.service.StoreServiceUtils;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final StoreRepository storeRepository;

    @Transactional
    public void addReview(AddReviewRequest request, Long memberId, Long storeId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        reviewRepository.save(request.toEntity(memberId, store));
    }
}
