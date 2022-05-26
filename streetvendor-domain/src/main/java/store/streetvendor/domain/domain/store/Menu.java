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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int menuCount;

    @Column(nullable = false)
    private int price;

    private String pictureUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuSalesStatus salesStatus;

    @Builder(access = AccessLevel.PRIVATE)
    private Menu(Store store, String name, int menuCount, int price, String pictureUrl, MenuSalesStatus salesStatus) {
        this.store = store;
        this.name = name;
        this.menuCount = menuCount;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.salesStatus = salesStatus;
    }

    public static Menu of(Store store, String name, int count, int price, String pictureUrl) {
        return Menu.builder()
            .store(store)
            .name(name)
            .menuCount(count)
            .price(price)
            .pictureUrl(pictureUrl)
            .salesStatus(MenuSalesStatus.ON_SALE)
            .build();
    }

    public Menu changeMenuStatus(MenuSalesStatus salesStatus) {
        validateSalesStatus(salesStatus);
        this.salesStatus = salesStatus;
        return this;
    }

    private void validateSalesStatus(MenuSalesStatus salesStatus) {
        if (this.getSalesStatus() == salesStatus) {
            throw new IllegalArgumentException(String.format("이미 <%s> 상태입니다. 다시 확인해주세요.", salesStatus));
        }
    }

}
