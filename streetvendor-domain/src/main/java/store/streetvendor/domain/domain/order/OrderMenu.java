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
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private String purchaseName;

    @Column(nullable = false)
    private int purchasePrice;

    @Column(nullable = false)
    private int totalCount;

    @Builder
    public OrderMenu(Orders orders, Long menuId, int count, String purchaseName, int purchasePrice, int totalCount) {
        this.orders = orders;
        this.menuId = menuId;
        this.count = count;
        this.purchaseName = purchaseName;
        this.purchasePrice = purchasePrice;
        this.totalCount = totalCount;
    }

    public static OrderMenu of(Orders orders, Menu menu, int totalCount) {
        return OrderMenu.builder()
            .orders(orders)
            .menuId(menu.getId())
            .count(menu.getCount())
            .purchaseName(menu.getName())
            .purchasePrice(menu.getPrice())
            .totalCount(totalCount)
            .build();
    }

}
