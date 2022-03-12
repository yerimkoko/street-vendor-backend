package store.streetvendor.service.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Menu;
import store.streetvendor.domain.domain.store.MenuSalesStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {

    private Long menuId;

    private String name;

    private int count;

    private int price;

    private String pictureUrl;

    private MenuSalesStatus salesStatus;

    public static MenuResponse of(Menu menu) {
        return new MenuResponse(menu.getId(), menu.getName(), menu.getMenuCount(), menu.getPrice(),
            menu.getPictureUrl(), menu.getSalesStatus());
    }

}
