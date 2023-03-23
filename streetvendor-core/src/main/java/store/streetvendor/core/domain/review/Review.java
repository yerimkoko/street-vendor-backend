package store.streetvendor.core.domain.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.store.Rate;
import store.streetvendor.core.domain.store.Store;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rate rate;

    @Builder
    public Review(Store store, Long memberId, String comment, Rate rate) {
        this.store = store;
        this.memberId = memberId;
        this.comment = comment;
        this.rate = rate;
    }

    public static Review newInstance(Store store, Long memberId, Rate rate, String comment) {
        return Review.builder()
            .comment(comment)
            .memberId(memberId)
            .store(store)
            .rate(rate)
            .build();
    }

}
