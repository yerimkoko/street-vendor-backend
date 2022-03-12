package store.streetvendor.domain.domain.order_history;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class OrderHistoryMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_history_id", nullable = false)
    private OrderHistory orderHistory;

    @Builder(access = AccessLevel.PRIVATE)
    public OrderHistoryMenu(OrderHistory orderHistory, String menuName, int count, int price) {
        this.orderHistory = orderHistory;
        this.menuName = menuName;
        this.count = count;
        this.price = price;
    }

    public static OrderHistoryMenu of(String menuName, int count, int price) {
        return OrderHistoryMenu.builder()
            .menuName(menuName)
            .count(count)
            .price(price)
            .build();
    }

}
