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
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private Long memberId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Order(Long storeId, Long memberId, List<OrderMenu> orderMenus) {
        this.storeId = storeId;
        this.memberId = memberId;
    }

    public static Order newOrder(Long storeId, Long memberId) {
        return Order.builder()
            .storeId(storeId)
            .memberId(memberId)
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

}
