package store.streetvendor.service.order_history.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order_history.OrderHistoryMenu;


@Getter
@NoArgsConstructor
public class OrderHistoryMenuResponse {

    private String menuName;

    private Long storeId;

    private int menuCount;

    private int price;

    private int count;

    private int totalPrice;

    private String pictureUrl;

    @Builder(access = AccessLevel.PRIVATE)
    public OrderHistoryMenuResponse(Long storeId, String menuName, int menuCount, int price, int count, int totalPrice, String pictureUrl) {
        this.storeId = storeId;
        this.menuName = menuName;
        this.menuCount = menuCount;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
        this.pictureUrl = pictureUrl;
    }

    public static OrderHistoryMenuResponse toEntity(OrderHistoryMenu menus, Long storeId) {
        return OrderHistoryMenuResponse.builder()
            .storeId(storeId)
            .menuName(menus.getMenuName())
            .menuCount(menus.getCount())
            .price(menus.getPrice())
            .count(menus.getCount())
            .totalPrice(menus.getPrice() * menus.getCount())
            .pictureUrl(menus.getPictureUrl())
            .build();
    }
}
