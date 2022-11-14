package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.utils.ConvertUtil;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StoreInfoWhenOrderResponse {

    private Long storeId;

    private String storeName;

    private StoreCategory storeCategory;

    private String storeDescription;

    private String locationDescription;

    private double evaluation;

    private List<MenuResponse> menus;

    @Builder
    public StoreInfoWhenOrderResponse(Long storeId, String storeName, StoreCategory storeCategory, String storeDescription, String locationDescription, double evaluation, List<MenuResponse> menus) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeCategory = storeCategory;
        this.storeDescription = storeDescription;
        this.locationDescription = locationDescription;
        this.evaluation = evaluation;
        this.menus = menus;
    }

    public static StoreInfoWhenOrderResponse of(Store store) {
        return StoreInfoWhenOrderResponse.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .storeCategory(store.getCategory())
            .storeDescription(store.getStoreDescription())
            .locationDescription(store.getLocationDescription())
            .evaluation(ConvertUtil.getAverageEvaluation(store))
            .menus(getMenuResponse(store))
            .build();
    }

    private static List<MenuResponse> getMenuResponse(Store store) {
        return store.getMenus().stream()
            .map(MenuResponse::of)
            .collect(Collectors.toList());
    }
}
