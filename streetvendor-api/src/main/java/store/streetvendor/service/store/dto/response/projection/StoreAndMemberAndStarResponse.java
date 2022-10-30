package store.streetvendor.service.store.dto.response.projection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.domain.store.StoreStatus;
import store.streetvendor.core.domain.store.star.Star;


@Getter
@NoArgsConstructor
public class StoreAndMemberAndStarResponse {

    private Long starId;

    private String storeName;

    private String thumbNail;

    private StoreCategory storeCategory;

    private String description;

    private StoreStatus status;

    private long reviewCount;

    private double averageGrade;

    @Builder
    public StoreAndMemberAndStarResponse(Long starId, String storeName, String thumbNail, StoreCategory storeCategory, String description, StoreStatus status, long reviewCount, double averageGrade) {
        this.starId = starId;
        this.storeName = storeName;
        this.thumbNail = thumbNail;
        this.storeCategory = storeCategory;
        this.description = description;
        this.status = status;
        this.reviewCount = reviewCount;
        this.averageGrade = averageGrade;
    }

    public static StoreAndMemberAndStarResponse of(Star star) {
        return StoreAndMemberAndStarResponse.builder()
            .starId(star.getId())
            .storeName(star.getStore().getName())
            .thumbNail(getThumbNail(star))
            .storeCategory(star.getStore().getCategory())
            .description(star.getStore().getLocationDescription())
            .status(star.getStore().getStatus())
            .reviewCount(star.getStore().getReviews().size())
            .averageGrade((double) star.getStore().getReviews().stream()
                .map(review -> review.getGrade().getValue()).count() / star.getStore().getReviews().size())
            .build();
    }

    private static String getThumbNail(Star star) {
        if (star.getStore().getStoreImages().isEmpty()) {
            return null;
        }
        return star.getStore().findMainImage().getPictureUrl();
    }

}
