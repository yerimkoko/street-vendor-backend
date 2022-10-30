package store.streetvendor.core.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.store.PaymentMethod;
import store.streetvendor.core.domain.store.Store;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public Orders(Store store, Long memberId, PaymentMethod paymentMethod, OrderStatus orderStatus, OrderStatusCanceled orderStatusCanceled) {
        this.store = store;
        this.memberId = memberId;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
    }

    public static Orders newOrder(Store store, Long memberId, PaymentMethod paymentMethod) {
        return Orders.builder()
            .store(store)
            .memberId(memberId)
            .paymentMethod(paymentMethod)
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

    public void validateUserCan() {
        validateRequestOrder();
        validateCompleteOrder();
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
