package store.streetvendor.core.service.utils.dto.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.domain.store.StoreStatus;

@Getter
@NoArgsConstructor
public class MemberStarsResponse {

    private Long starId;

    private String storeName;

    private String thumbNail;

    private StoreCategory storeCategory;

    private String description;

    private StoreStatus status;

    private long reviewCount;

    private double grade;

    @Builder
    public MemberStarsResponse(Long starId, String storeName, String thumbNail, StoreCategory storeCategory, String description, StoreStatus status, long reviewCount, double grade) {
        this.starId = starId;
        this.storeName = storeName;
        this.thumbNail = thumbNail;
        this.storeCategory = storeCategory;
        this.description = description;
        this.status = status;
        this.reviewCount = reviewCount;
        this.grade = grade;
    }

//    public static MemberStarsResponse of(Store store, Long memberId) {
//        return MemberStarsResponse.builder()
//            .starId(store.findStar(memberId).getId())
//            .storeName(store.getName())
//            .thumbNail(store.findMainImage().getPictureUrl())
//            .storeCategory(store.getCategory())
//            .description(store.getLocationDescription())
//            .status(store.getStatus())
//            .reviewCount(store.getReviews().stream()
//                .map(r -> r.getGrade()
//                    .getValue()).count() / store.getReviews().size())
//            .grade(store.getReviews().size())
//            .build();
//
//    }
}
