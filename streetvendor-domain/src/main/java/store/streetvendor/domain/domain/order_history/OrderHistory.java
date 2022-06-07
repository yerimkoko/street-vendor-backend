package store.streetvendor.domain.domain.order_history;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;
import store.streetvendor.domain.domain.store.Store;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class OrderHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    @Embedded
    private StoreInfo storeInfo;

    @Column(nullable = false)
    private Long orderId;

    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderHistoryMenu> menus = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public OrderHistory(Long memberId, StoreInfo storeInfo, Long orderId) {
         this.memberId = memberId;
         this.storeInfo = storeInfo;
         this.orderId = orderId;
    }

    public static OrderHistory newHistory(Store store, Long memberId, Long orderId) {
        return OrderHistory.builder()
            .storeInfo(StoreInfo.of(store))
            .memberId(memberId)
            .orderId(orderId)
            .build();
    }

    public void addMenus(List<OrderHistoryMenu> menus) {
        for (OrderHistoryMenu menu : menus) {
            this.addMenu(menu);
        }
    }

    public void addMenu(OrderHistoryMenu menu) {
        this.menus.add(menu);
    }

}
