package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Menu;
@NoArgsConstructor
@Getter
public class MenuDetailResponse {
    private String menuName;
    private int menuCount;
    private int menuPrice;
    private String pictureUrl;

    @Builder
    public MenuDetailResponse(String menuName, int menuCount, int menuPrice, String pictureUrl) {
        this.menuName = menuName;
        this.menuCount = menuCount;
        this.menuPrice = menuPrice;
        this.pictureUrl = pictureUrl;
    }

    public static MenuDetailResponse of(Menu menu) {
        return MenuDetailResponse.builder()
            .menuName(menu.getName())
            .menuPrice(menu.getPrice())
            .pictureUrl(menu.getPictureUrl())
            .build();
    }

}
