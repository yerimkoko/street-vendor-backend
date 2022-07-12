package store.streetvendor.domain.domain.order_history;

import lombok.*;
import store.streetvendor.domain.domain.BaseTimeEntity;
import store.streetvendor.domain.domain.order.OrderStatusCanceled;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.store.Store;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class OrderHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    @Embedded
    private StoreInfo storeInfo;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusCanceled orderCanceledStatus;

    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderHistoryMenu> menus = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public OrderHistory(Long memberId, StoreInfo storeInfo, Long orderId, OrderStatusCanceled orderCanceledStatus) {
         this.memberId = memberId;
         this.storeInfo = storeInfo;
         this.orderId = orderId;
         this.orderCanceledStatus = orderCanceledStatus;
    }

    public static OrderHistory newHistory(Store store, Long memberId, Long orderId, OrderStatusCanceled orderStatus) {
        return OrderHistory.builder()
            .storeInfo(StoreInfo.of(store))
            .memberId(memberId)
            .orderId(orderId)
            .orderCanceledStatus(orderStatus)
            .build();
    }

    public static OrderHistory cancel(Orders order, Store store) {
        return OrderHistory.builder()
            .storeInfo(StoreInfo.of(store))
            .memberId(order.getMemberId())
            .orderId(order.getId())
            .orderCanceledStatus(OrderStatusCanceled.CANCELED)
            .build();
    }

    public void addMenus(List<OrderHistoryMenu> menus) {
        for (OrderHistoryMenu menu : menus) {
            this.addMenu(menu);
        }
    }

    public void addMenu(OrderHistoryMenu menu) {
        this.menus.add(menu);
    }

}
