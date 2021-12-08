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

    @Column(nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public Orders(Long storeId, Long memberId, OrderStatus orderStatus) {
        this.storeId = storeId;
        this.memberId = memberId;
        this.orderStatus = orderStatus;
    }

    public static Orders newOrder(Long storeId, Long memberId) {
        return Orders.builder()
            .storeId(storeId)
            .memberId(memberId)
            .orderStatus(OrderStatus.REQUEST)
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
        this.orderStatus = OrderStatus.READY;
    }

    public void changeStatusToComplete() {
        this.orderStatus = OrderStatus.COMPLETE;
    }

}
