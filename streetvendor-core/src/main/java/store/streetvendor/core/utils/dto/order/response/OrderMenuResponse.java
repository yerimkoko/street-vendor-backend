package store.streetvendor.core.utils.dto.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderMenu;

@Getter
@NoArgsConstructor
public class OrderMenuResponse {
    private Long menuId;

    private String menuName;

    private int count;

    private String imageUrl;

    private int price;

    @Builder
    public OrderMenuResponse(Long menuId, String menuName, int count, int price, String imageUrl) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.count = count;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static OrderMenuResponse of(OrderMenu orderMenu) {
        return OrderMenuResponse.builder()
            .menuId(orderMenu.getMenu().getId())
            .menuName(orderMenu.getMenu().getName())
            .imageUrl(orderMenu.getPictureUrl())
            .count(orderMenu.getMenu().getMenuCount() * orderMenu.getCount())
            .price(orderMenu.getMenu().getPrice() * orderMenu.getCount())
            .build();
    }

}
