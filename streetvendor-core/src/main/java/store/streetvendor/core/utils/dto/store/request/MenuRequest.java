package store.streetvendor.core.utils.dto.store.request;

import lombok.*;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.Store;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRequest {

    private String name;

    private int menuCount;

    private int price;

    private String pictureUrl;

    public Menu toEntity(Store store) {
        return Menu.of(store, name, menuCount, price, pictureUrl);
    }

    public static MenuRequest testInstance(String name, int menuCount, int price, String pictureUrl) {
        return new MenuRequest(name, menuCount, price, pictureUrl);
    }

}
