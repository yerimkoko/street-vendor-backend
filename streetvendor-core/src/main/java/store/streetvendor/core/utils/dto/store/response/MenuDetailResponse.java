package store.streetvendor.core.utils.dto.store.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.menu.MenuSalesStatus;

@NoArgsConstructor
@Getter
public class MenuDetailResponse {
    private Long menuId;
    private String menuName;
    private int menuCount;
    private int menuPrice;
    private String pictureUrl;
    private MenuSalesStatus menuSalesStatus;

    @Builder
    public MenuDetailResponse(Long menuId, String menuName, int menuCount, int menuPrice, String pictureUrl, MenuSalesStatus menuSalesStatus) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuCount = menuCount;
        this.menuPrice = menuPrice;
        this.pictureUrl = pictureUrl;
        this.menuSalesStatus = menuSalesStatus;
    }

    public static MenuDetailResponse of(Menu menu) {
        return MenuDetailResponse.builder()
            .menuId(menu.getId())
            .menuName(menu.getName())
            .menuCount(menu.getMenuCount())
            .menuPrice(menu.getPrice())
            .pictureUrl(menu.getPictureUrl())
            .menuSalesStatus(menu.getSalesStatus())
            .build();
    }

}
