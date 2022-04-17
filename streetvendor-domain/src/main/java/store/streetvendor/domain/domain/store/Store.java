package store.streetvendor.domain.domain.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String name;

    private String pictureUrl;

    @Embedded
    private Location location;

    private String description;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Payment> paymentMethods = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BusinessHours> businessDays = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Store(Long memberId, String name, String pictureUrl, Location location, String description, StoreStatus status, StoreCategory category) {
        this.memberId = memberId;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.status = status;
        this.category = category;
    }

    public static Store newInstance(Long memberId, String name, String pictureUrl, Location location, String description, StoreCategory category) {
        return Store.builder()
            .memberId(memberId)
            .name(name)
            .pictureUrl(pictureUrl)
            .location(location)
            .description(description)
            .status(StoreStatus.ACTIVE)
            .category(category)
            .build();
    }

    public Menu findMenu(Long menuId) {
        return this.menus.stream()
            .filter(menu -> menu.getId().equals(menuId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("(%s)의 메뉴는 존재하지 않습니다.", menuId)));
    }

    public void addMenus(List<Menu> menus) {
        for (Menu menu : menus) {
            this.addMenu(menu);
        }
    }

    private void addMenu(Menu menu) {
        this.menus.add(menu);
    }

    public void addPayments(List<PaymentMethod> paymentMethods) {
        this.paymentMethods.addAll(paymentMethods.stream()
            .map(paymentMethod -> Payment.of(this, paymentMethod))
            .collect(Collectors.toList())
        );
    }

    public void addBusinessDays(List<BusinessHours> businessHours) {
        this.businessDays.addAll(businessHours);
    }

    public void updateStoreInfo(String name, String description, String pictureUrl, Location location, StoreCategory category) {
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.category = category;
    }

    public void updateMenus(List<Menu> newMenus) {
        this.menus.clear();
        this.addMenus(newMenus);
    }

    public void updateBusinessDaysInfo(List<BusinessHours> businessHours) {
        this.businessDays.clear();
        this.addBusinessDays(businessHours);

    }

    public void updatePayments(List<PaymentMethod> paymentMethods) {
        this.paymentMethods.clear();
        this.addPayments(paymentMethods);
    }

    public void delete() {
        this.status = StoreStatus.DELETED;
    }

}
