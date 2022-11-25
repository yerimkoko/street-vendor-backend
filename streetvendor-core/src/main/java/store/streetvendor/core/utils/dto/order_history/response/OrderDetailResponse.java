package store.streetvendor.core.utils.dto.order_history.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import store.streetvendor.core.domain.order.OrderMenu;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.store.PaymentMethod;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.utils.ConvertUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderDetailResponse {

    private Long orderId;

    private Long storeId;

    private List<Long> menuIds;

    private String menuName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderTime;

    private PaymentMethod paymentMethod;

    private int totalPrice;

    @Builder
    public OrderDetailResponse(Long orderId, Long storeId, List<Long> menuIds, String menuName, LocalDateTime orderTime, PaymentMethod paymentMethod, int totalPrice) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.menuIds = menuIds;
        this.menuName = menuName;
        this.orderTime = orderTime;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }

    public static OrderDetailResponse of(Orders order, Store store) {
        return OrderDetailResponse.builder()
            .menuName(ConvertUtil.countMenu(order.getOrderMenus().get(0).getMenu().getName(), order.getOrderMenus().size()))
            .storeId(store.getId())
            .menuIds(order.getOrderMenus()
                .stream()
                .map(menu -> menu.getMenu().getId()).collect(Collectors.toList()))
            .orderId(order.getId())
            .orderTime(order.getCreatedAt())
            .paymentMethod(order.getPaymentMethod())
            .totalPrice(order.getOrderMenus().stream()
                .mapToInt(OrderMenu::getTotalPrice)
                .sum())
            .build();
    }
}
