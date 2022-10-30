package store.streetvendor.core.service.utils.dto.order_history.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order_history.OrderHistoryMenu;

@Getter
@NoArgsConstructor
public class OrderHistoryMenuResponse {

    private String menuName;

    private int menuCount;

    private int price;

    private int count;

    private int totalPrice;

    private String pictureUrl;

    @Builder(access = AccessLevel.PRIVATE)
    public OrderHistoryMenuResponse(String menuName, int menuCount, int price, int count,
                                    int totalPrice, String pictureUrl) {
        this.menuName = menuName;
        this.menuCount = menuCount;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
        this.pictureUrl = pictureUrl;
    }

    public static OrderHistoryMenuResponse of(OrderHistoryMenu menu) {
        return OrderHistoryMenuResponse.builder()
            .menuName(menu.getMenuName())
            .menuCount(menu.getCount())
            .price(menu.getPrice())
            .count(menu.getCount())
            .totalPrice(menu.getPrice() * menu.getCount())
            .pictureUrl(menu.getPictureUrl())
            .build();
    }
}
