package store.streetvendor.core.utils.dto.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order_history.OrderHistoryMenu;

@Getter
@NoArgsConstructor
public class OrderHistoryMenuResponse {

    private Long menuId;

    private String menuName;

    private int count;

    private String imageUrl;

    private int price;

    @Builder
    public OrderHistoryMenuResponse(Long menuId, String menuName, int count, String imageUrl, int price) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.count = count;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static OrderHistoryMenuResponse of(OrderHistoryMenu menu) {
        return OrderHistoryMenuResponse.builder()
            .menuId(menu.getId())
            .menuName(menu.getMenuName())
            .count(menu.getCount())
            .imageUrl(menu.getPictureUrl())
            .price(menu.getPrice())
            .build();
    }
}
