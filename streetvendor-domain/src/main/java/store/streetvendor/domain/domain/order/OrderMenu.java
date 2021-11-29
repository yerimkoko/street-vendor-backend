package store.streetvendor.domain.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;
import store.streetvendor.domain.domain.store.Menu;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private String purchaseName;

    @Column(nullable = false)
    private int purchasePrice;

    @Builder
    private OrderMenu(Order order, Long menuId, int count, String purchaseName, int purchasePrice) {
        this.order = order;
        this.menuId = menuId;
        this.count = count;
        this.purchaseName = purchaseName;
        this.purchasePrice = purchasePrice;
    }

    public static OrderMenu of(Order order, Menu menu, int count) {
        return OrderMenu.builder()
            .order(order)
            .menuId(menu.getId())
            .count(count)
            .purchaseName(menu.getName())
            .purchasePrice(menu.getPrice())
            .build();
    }

}
