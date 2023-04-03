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

    @Column(nullable = false)
    private Long reviewId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewImageStatus status;

    @Builder(access = AccessLevel.PACKAGE)
    public ReviewImage(@NotNull Long reviewId, @NotNull Long memberId, @NotNull String imageUrl, @NotNull ReviewImageStatus status) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public static ReviewImage of(@NotNull Long reviewId, @NotNull Long memberId, @NotNull String imageUrl) {
        return ReviewImage.builder()
            .reviewId(reviewId)
            .memberId(memberId)
            .imageUrl(imageUrl)
            .status(ReviewImageStatus.ACTIVE)
            .build();

    }
}
