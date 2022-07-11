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
@Embeddable
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

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public Orders(Long storeId, Long memberId, OrderStatus orderStatus, OrderStatusCanceled orderStatusCanceled) {
        this.storeId = storeId;
        this.memberId = memberId;
        this.orderStatus = orderStatus;
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

    public void changeStatusToPreparing() {
        if (this.orderStatus.canChangeToReady()) {
            this.orderStatus = OrderStatus.PREPARING;
        }
    }

    public void changeStatusToReadyToPickUp() {
        if (this.orderStatus == OrderStatus.PREPARING) {
            this.orderStatus = OrderStatus.READY_TO_PICK_UP;
        }
    }

    // TODO: remove
    public void cancelOrderByBoss() {
        cancelOrder();
    }

    // TODO: remove
    public void cancelOrderByUser() {
        validateRequestOrder();
        validateCompleteOrder();
        cancelOrder();
    }

    // TODO: remove
    private void cancelOrder() {
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
