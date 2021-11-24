package store.streetvendor.service.store.dto.request;

import lombok.*;
import store.streetvendor.domain.domain.store.Menu;
import store.streetvendor.domain.domain.store.Store;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRequest {

    private String name;

    private String price;

    private String pictureUrl;

    public Menu toEntity(Store store) {
        return Menu.of(store, name, price, pictureUrl);
    }

    public static MenuRequest testInstance(String name, String price, String pictureUrl) {
        return new MenuRequest(name, price, pictureUrl);
    }

}
