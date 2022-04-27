package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.store.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class StoreDetailResponse {
    private Long storeId;

    private String storeName;

    private String bossNumber;

    private String description;

    private StoreCategory category;

    private List<MenuDetailResponse> menuList;

    private List<StoreOpeningTimeResponse> openingTime;

    @Builder
    public StoreDetailResponse(Long storeId, String storeName, String bossNumber, String description, StoreCategory category, List<MenuDetailResponse> menuList, List<StoreOpeningTimeResponse> openingTime) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.bossNumber = bossNumber;
        this.description = description;
        this.category = category;
        this.menuList = menuList;
        this.openingTime = openingTime;
    }

    public static StoreDetailResponse of(Store store, Member member) {
        List<MenuDetailResponse> menuDetailResponse = store.getMenus().stream()
            .map(MenuDetailResponse::of).collect(Collectors.toList());

        return StoreDetailResponse.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .bossNumber(member.getPhoneNumber())
            .category(store.getCategory())
            .description(store.getDescription())
            .menuList(menuDetailResponse)
            .openingTime(store.getBusinessDays().stream().map(StoreOpeningTimeResponse::of).collect(Collectors.toList()))
            .build();
    }
}
