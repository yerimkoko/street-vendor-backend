package store.streetvendor.domain.domain.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalTime;
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

    private String location;

    private String description;

    @Embedded
    private OpeningTime openingTime;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Payment> paymentMethods = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Store(Long memberId, String name, String pictureUrl, String location, String description, OpeningTime openingTime, StoreStatus status) {
        this.memberId = memberId;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.openingTime = openingTime;
        this.status = status;
    }

    public static Store newInstance(Long memberId, String name, String pictureUrl, String location, String description, LocalTime startTime, LocalTime endTime) {
        return Store.builder()
            .memberId(memberId)
            .name(name)
            .pictureUrl(pictureUrl)
            .location(location)
            .description(description)
            .openingTime(OpeningTime.of(startTime, endTime))
            .status(StoreStatus.ACTIVE)
            .build();
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

    public void update(String name, String description, String pictureUrl, String location,
                       LocalTime startTime, LocalTime endTime, List<Menu> menus, List<PaymentMethod> methods) {
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.openingTime = OpeningTime.of(startTime, endTime);
        this.updateMenus(menus);
        this.updatePayments(methods);
    }

    public void updateMenus(List<Menu> newMenus) {
        this.menus.clear();
        this.addMenus(newMenus);
    }

    public void updatePayments(List<PaymentMethod> methods) {
        this.paymentMethods.clear();
        this.addPayments(methods);

    }

    public void delete() {
        this.status = StoreStatus.DELETED;
    }


}
