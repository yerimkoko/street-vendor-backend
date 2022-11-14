package store.streetvendor.core.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.store.menu.Menu;

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

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String pictureUrl;

    @Column
    private long numberOfOrders;

    @Builder
    public OrderMenu(Orders orders, Menu menu, int count, String pictureUrl) {
        this.orders = orders;
        this.menu = menu;
        this.count = count;
        this.totalPrice = count * menu.getPrice();
        this.pictureUrl = pictureUrl;
        this.numberOfOrders = 0;
    }

    public static OrderMenu of(Orders orders, Menu menu, int count) {
        return OrderMenu.builder()
            .orders(orders)
            .menu(menu)
            .count(count)
            .pictureUrl(menu.getPictureUrl())
            .build();
    }

    public void addOrderCount() {
        this.numberOfOrders += 1;
    }


}
