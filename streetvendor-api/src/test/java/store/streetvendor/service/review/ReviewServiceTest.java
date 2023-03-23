package store.streetvendor.service.review;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.review.Review;
import store.streetvendor.core.domain.review.ReviewRepository;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.utils.dto.review.AddReviewRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @AfterEach
    void cleanUp() {
        reviewRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @Test
    void 리뷰를_등록한다() {
        // given
        String comment = "리뷰 입니다.";
        int rate = 1;
        Long memberId = 1L;
        AddReviewRequest request = AddReviewRequest.builder()
            .comment(comment)
            .rate(rate)
            .build();

        // when
        reviewService.addReview(request, memberId, store().getId());

        // then
        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(1);
        assertThat(reviews.get(0).getRate().getValue()).isEqualTo(rate);
        assertThat(reviews.get(0).getComment()).isEqualTo(comment);

    }

    private Store store() {
        Long bossId = 1L;
        String name = "토끼의 새로운 붕어빵";
        String storeDescription = "팥 붕어빵 맛집";
        String locationDescription = "군포역 2번 출구 앞";
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;
        Location location = new Location(34.2222, 128.222);
        Store store = Store.newInstance(bossId, name, location, storeDescription, locationDescription, category);
        storeRepository.save(store);

        return store;
    }
}
