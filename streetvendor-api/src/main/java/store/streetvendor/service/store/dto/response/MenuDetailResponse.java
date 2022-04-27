package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Menu;
import store.streetvendor.domain.domain.store.Store;

@NoArgsConstructor
@Getter
public class MenuDetailResponse {
    private String menuName;

    private Long storeId;
    private int menuCount;
    private int menuPrice;
    private String pictureUrl;

    @Builder
    public MenuDetailResponse(String menuName, Long storeId, int menuCount, int menuPrice, String pictureUrl) {
        this.menuName = menuName;
        this.storeId = storeId;
        this.menuCount = menuCount;
        this.menuPrice = menuPrice;
        this.pictureUrl = pictureUrl;
    }

    public static MenuDetailResponse of(Menu menu, Store store) {
        return MenuDetailResponse.builder()
            .menuName(menu.getName())
            .storeId(store.getId())
            .menuPrice(menu.getPrice())
            .pictureUrl(menu.getPictureUrl())
            .build();
    }

}
