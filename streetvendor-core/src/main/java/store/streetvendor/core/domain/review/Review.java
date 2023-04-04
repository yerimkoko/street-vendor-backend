package store.streetvendor.core.domain.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.store.Rate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_history_id", nullable = false)
    private OrderHistory order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rate rate;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ReviewImage> reviewImages = new ArrayList<>();

    public void addReviewImages(List<ReviewImage> reviewImages) {
        this.reviewImages.addAll(reviewImages);
    }

    public void addReviewImage(ReviewImage image) {
        this.reviewImages.add(image);
    }

    @Builder
    public Review(OrderHistory order, Member member, String comment, Rate rate) {
        this.order = order;
        this.member = member;
        this.comment = comment;
        this.rate = rate;
    }

    public static Review newInstance(OrderHistory order, Member member, Rate rate, String comment) {
        return Review.builder()
            .comment(comment)
            .member(member)
            .order(order)
            .rate(rate)
            .build();
    }
}
