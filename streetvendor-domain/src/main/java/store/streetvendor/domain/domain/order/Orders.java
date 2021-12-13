package store.streetvendor.domain.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatusCanceled orderStatusCanceled;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public Orders(Long storeId, Long memberId, OrderStatus orderStatus, OrderStatusCanceled orderStatusCanceled) {
        this.storeId = storeId;
        this.memberId = memberId;
        this.orderStatus = orderStatus;
        this.orderStatusCanceled = orderStatusCanceled;
    }

    public static Orders newOrder(Long storeId, Long memberId) {
        return Orders.builder()
            .storeId(storeId)
            .memberId(memberId)
            .orderStatus(OrderStatus.REQUEST)
            .orderStatusCanceled(OrderStatusCanceled.ACTIVE)
            .build();
    }

    public void addMenus(List<OrderMenu> menus) {
        for (OrderMenu menu : menus) {
            this.addMenu(menu);
        }
    }

    public void addMenu(OrderMenu menu) {
        this.orderMenus.add(menu);
    }

    public void changeStatusToReady() {
        if (this.orderStatus.canChangeToReady()) {
            this.orderStatus = OrderStatus.READY;
        }
    }

    public void changeStatusToComplete() {
        if (this.orderStatus == OrderStatus.READY) {
            this.orderStatus = OrderStatus.COMPLETE;
        }
    }

    public void cancelOrderByBoss() {
        validateCompleteOrder();
        cancelOrder();
    }

    public void cancelOrderByUser() {
        validateRequestOrder();
        validateCompleteOrder();
        cancelOrder();
    }

    private void cancelOrder() {
        this.orderStatusCanceled = OrderStatusCanceled.CANCELED;
    }

    private void validateCompleteOrder() {
        if (this.orderStatus.cantCancelOrder()) {
            throw new IllegalArgumentException(String.format("주문이 (%s)일 때에는 변경할 수 없습니다.", orderStatus));
        }
    }
    private void validateRequestOrder() {
        if (this.orderStatus.cantUserCancelOrder()) {
            throw new IllegalArgumentException(String.format("주문이 (%s)일 때에는 변경할 수 없습니다.", orderStatus));
        }
    }

}
