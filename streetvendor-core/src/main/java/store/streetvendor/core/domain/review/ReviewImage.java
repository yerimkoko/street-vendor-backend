package store.streetvendor.core.domain.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class ReviewImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewImageStatus status;

    @Builder(access = AccessLevel.PACKAGE)
    public ReviewImage(@NotNull Review review, @NotNull Long memberId, @NotNull String imageUrl, @NotNull ReviewImageStatus status) {
        this.review = review;
        this.memberId = memberId;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public static ReviewImage newInstance(Review review, @NotNull String imageUrl) {
        return ReviewImage.builder()
            .review(review)
            .memberId(review.getMember().getId())
            .imageUrl(imageUrl)
            .status(ReviewImageStatus.ACTIVE)
            .build();
    }
}
