package store.streetvendor.service.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Menu;
import store.streetvendor.domain.domain.store.Store;

@Getter
@NoArgsConstructor
public class OrderMenusRequest {

    private long menuId;

    private int totalCount;

    @Builder
    public OrderMenusRequest(long menuId, int totalCount) {
        this.menuId = menuId;
        this.totalCount = totalCount;
    }

    public Menu toEntity(Store store, Menu menu) {
        return Menu.of(store, menu.getName(), menu.getCount(),
            menu.getPrice(), menu.getPictureUrl());
    }


}
