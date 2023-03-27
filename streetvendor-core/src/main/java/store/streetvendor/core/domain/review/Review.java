package store.streetvendor.core.domain.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.store.Rate;

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
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rate rate;

    @Builder
    public Review(Orders order, Member member, String comment, Rate rate) {
        this.order = order;
        this.member = member;
        this.comment = comment;
        this.rate = rate;
    }

    public static Review newInstance(Orders order, Member member, Rate rate, String comment) {
        return Review.builder()
            .comment(comment)
            .member(member)
            .order(order)
            .rate(rate)
            .build();
    }

}
