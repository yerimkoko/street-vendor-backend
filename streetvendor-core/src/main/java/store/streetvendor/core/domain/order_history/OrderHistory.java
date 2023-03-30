package store.streetvendor.core.domain.order_history;

import lombok.*;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.store.PaymentMethod;
import store.streetvendor.core.domain.store.Store;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusCanceled orderCanceledStatus;

    @Column(nullable = false)
    private LocalDateTime orderCreateTime;

    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderHistoryMenu> menus = new ArrayList<>();

    @Builder
    public OrderHistory(Long id, Long memberId, StoreInfo storeInfo, Long orderId, PaymentMethod paymentMethod, OrderStatusCanceled orderCanceledStatus, LocalDateTime orderCreateTime) {
        this.id = id;
        this.memberId = memberId;
        this.storeInfo = storeInfo;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.orderCanceledStatus = orderCanceledStatus;
        this.orderCreateTime = orderCreateTime;
    }

    @Builder(access = AccessLevel.PRIVATE)
    public static OrderHistory newHistory(Store store, Orders order, Long memberId, OrderStatusCanceled orderStatus) {
        return OrderHistory.builder()
            .storeInfo(StoreInfo.of(store))
            .memberId(memberId)
            .paymentMethod(order.getPaymentMethod())
            .orderId(order.getId())
            .orderCreateTime(order.getCreatedAt())
            .orderCanceledStatus(orderStatus)
            .build();
    }

    public static OrderHistory cancel(Orders order, Store store) {
        return OrderHistory.builder()
            .storeInfo(StoreInfo.of(store))
            .memberId(order.getMemberId())
            .orderId(order.getId())
            .paymentMethod(order.getPaymentMethod())
            .orderCreateTime(order.getCreatedAt())
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
