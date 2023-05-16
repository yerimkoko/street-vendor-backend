package store.streetvendor.core.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.store.PaymentMethod;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.exception.ConflictException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusCanceled orderStatusCanceled;

    @Column(nullable = false)
    private LocalDateTime pickUpTime;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public Orders(Store store, Long memberId, PaymentMethod paymentMethod, OrderStatus orderStatus, OrderStatusCanceled orderStatusCanceled, LocalDateTime pickUpTime) {
        this.store = store;
        this.memberId = memberId;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.pickUpTime = pickUpTime;
        this.orderStatusCanceled = orderStatusCanceled;
    }

    public static Orders newOrder(Store store, Long memberId, PaymentMethod paymentMethod, LocalDateTime pickUpTime) {
        validatePickUpTime(pickUpTime);
        return Orders.builder()
            .store(store)
            .memberId(memberId)
            .paymentMethod(paymentMethod)
            .orderStatus(OrderStatus.REQUEST)
            .orderStatusCanceled(OrderStatusCanceled.ACTIVE)
            .pickUpTime(pickUpTime)
            .build();
    }

    public static Orders preparingOrder(Store store, Long memberId, PaymentMethod paymentMethod, @NotNull LocalDateTime pickUpTime) {
        validatePickUpTime(pickUpTime);
        return Orders.builder()
            .store(store)
            .memberId(memberId)
            .paymentMethod(paymentMethod)
            .orderStatus(OrderStatus.PREPARING)
            .pickUpTime(pickUpTime)
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
            throw new ConflictException(String.format("주문이 (%s)일 때에는 변경할 수 없습니다.", orderStatus));
        }
    }
    private void validateRequestOrder() {
        if (this.orderStatus.cantUserCancelOrder()) {
            throw new ConflictException(String.format("주문이 (%s)일 때에는 변경할 수 없습니다.", orderStatus));
        }
    }

    private static void validatePickUpTime(LocalDateTime pickUpTime) {
        if (pickUpTime.isBefore(LocalDateTime.now())) {
            throw new ConflictException("픽업시간은 현재 시간보다 이전으로 설정할 수 없습니다.");
        }
    }

}
