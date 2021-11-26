package store.streetvendor.domain.domain.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int price;

    private String pictureUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuSalesStatus salesStatus;

    @Builder(access = AccessLevel.PRIVATE)
    private Menu(Store store, String name, int amount, int price, String pictureUrl, MenuSalesStatus salesStatus) {
        this.store = store;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.salesStatus = salesStatus;
    }

    public static Menu of(Store store, String name, int amount, int price, String pictureUrl) {
        return Menu.builder()
            .store(store)
            .name(name)
            .amount(amount)
            .price(price)
            .pictureUrl(pictureUrl)
            .salesStatus(MenuSalesStatus.ON_SALE)
            .build();
    }

}
