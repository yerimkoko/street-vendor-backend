package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.store.*;

import java.util.List;

@NoArgsConstructor
@Getter
public class StoreDetailResponse {
    private Long storeId;

    private String storeName;

    private String bossNumber;

    private String description;

    private StoreCategory category;

    private List<Menu> menuList;

    private List<BusinessHours> businessHours;

    @Builder
    public StoreDetailResponse(Long storeId, String storeName, String bossNumber, String description, StoreCategory category, List<Menu> menuList, List<BusinessHours> businessHours) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.bossNumber = bossNumber;
        this.description = description;
        this.category = category;
        this.menuList = menuList;
        this.businessHours = businessHours;
    }

    public static StoreDetailResponse of(Store store, Member member) {
        return StoreDetailResponse.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .bossNumber(member.getPhoneNumber())
            .category(store.getCategory())
            .description(store.getDescription())
            .menuList(store.getMenus())
            .businessHours(store.getBusinessDays())
            .build();
    }
}
