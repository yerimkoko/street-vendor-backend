package store.streetvendor.core.domain.store;

import lombok.*;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.store.storemenu.StoreMenu;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.menu.MenuSalesStatus;
import store.streetvendor.core.domain.store.storeimage.StoreImage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends BaseTimeEntity {

    private static final int MAX_STORE_IMAGE_COUNT = 3;

    private static final int THUMB_NAIL_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bossId;

    @Column(nullable = false)
    private String name;

    @Embedded
    @Column
    private Location location;

    private String storeDescription;

    private String locationDescription;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Enumerated(EnumType.STRING)
    private StoreSalesStatus salesStatus;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Payment> paymentMethods = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BusinessHours> businessDays = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<StoreImage> storeImages = new ArrayList<>();


    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<StoreMenu> storeMenus = new ArrayList<>();


    @Builder
    private Store(Long bossId, String name, Location location, StoreSalesStatus salesStatus, String storeDescription, String locationDescription, StoreStatus status, StoreCategory category) {
        this.bossId = bossId;
        this.name = name;
        this.location = location;
        this.salesStatus = salesStatus;
        this.storeDescription = storeDescription;
        this.locationDescription = locationDescription;
        this.status = status;
        this.category = category;
    }

    public static Store newInstance(Long bossId, String name, Location location, String storeDescription, String locationDescription, StoreCategory category) {
        return Store.builder()
            .bossId(bossId)
            .name(name)
            .location(location)
            .salesStatus(StoreSalesStatus.CLOSED)
            .storeDescription(storeDescription)
            .locationDescription(locationDescription)
            .status(StoreStatus.ACTIVE)
            .category(category)
            .build();
    }

    public static Store newSalesStore(Long bossId, String name, Location location, String storeDescription, String locationDescription, StoreCategory category) {
        return Store.builder()
            .bossId(bossId)
            .name(name)
            .location(location)
            .salesStatus(StoreSalesStatus.OPEN)
            .storeDescription(storeDescription)
            .locationDescription(locationDescription)
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

    public StoreImage findMainImage() {
        return this.storeImages.stream()
            .filter(StoreImage::getIsThumbNail)
            .findFirst()
            .orElse(null);
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

    public void addStoreImages(List<StoreImage> storeImages) {
        // validateStoreImage(storeImages);
        for(StoreImage image : storeImages) {
            this.addStoreImage(image);
        }
    }

    private void validateStoreImage(List<StoreImage> storeImages) {

        if (storeImages.size() > MAX_STORE_IMAGE_COUNT) {
            throw new IllegalArgumentException(String.format("사진은 <%s>개 까지 등록 가능합니다.", MAX_STORE_IMAGE_COUNT));
        }

        long thumbNail = storeImages.stream()
            .filter(image -> image.getIsThumbNail().equals(true))
            .count();

        if (thumbNail != THUMB_NAIL_COUNT) {
            throw new IllegalArgumentException(String.format("대표사진을 <%s>개로 지정해주세요.", THUMB_NAIL_COUNT));
        }

    }


    public void addStoreImage(StoreImage storeImage) {
        this.storeImages.add(storeImage);
    }


    public void updateStoreInfo(String name, String description, Location location, StoreCategory category) {
        this.name = name;
        this.storeDescription = description;
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

    public void updateStoreImages(List<StoreImage> storeImages) {
        this.storeImages.clear();
        this.addStoreImages(storeImages);
    }

    public void delete() {
        this.status = StoreStatus.DELETED;
    }


    public void changeMenuSalesStatus(Long menuId, MenuSalesStatus salesStatus) {
        this.menus.stream()
            .filter(menu -> menu.isMenuId(menuId))
            .forEach(menu -> menu.changeMenuStatus(salesStatus));
    }

    public void changeSalesStatus(StoreSalesStatus salesStatus) {
        this.salesStatus = salesStatus;
    }

}
