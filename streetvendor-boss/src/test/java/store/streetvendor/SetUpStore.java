package store.streetvendor;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.storeimage.StoreImage;

import java.time.LocalTime;
import java.util.List;


@SpringBootTest
public abstract class SetUpStore extends SetupBoss {

    @Autowired
    public StoreRepository storeRepository;

    protected String storeDescription = "뽀미가 판매하는 붕어빵이에요.";

    protected String locationDescription = "뽀미네 집 근처입니다. 분홍색 집을 찾아주세요.";

    protected String pictureUrl = "image.jpeg";

    protected String menuName = "찹쌀 붕어빵";

    protected int count = 3;

    protected int price = 2000;

    protected Location location = new Location(30.78639644286605, 126.40572677813635);

    protected Days day = Days.MON;

    protected LocalTime start = LocalTime.of(13, 0);

    protected LocalTime end = LocalTime.of(19, 0);

    protected StoreCategory storeCategory = StoreCategory.BUNG_EO_PPANG;

    protected PaymentMethod paymentMethod = PaymentMethod.ACCOUNT_TRANSFER;

    protected Store store;

    protected Menu menu;

    protected StoreImage storeImage;

    protected BusinessHours businessHours;


    @BeforeEach
    void storeFixture() {

        store = Store.newSalesStore(boss.getId(), boss.getName(), location, storeDescription, locationDescription, storeCategory);

        menu = Menu.of(store, menuName, count, price, pictureUrl);

        storeImage = StoreImage.of(store, true, pictureUrl);

        businessHours = BusinessHours.of(store, day, start, end);

        store.addBusinessDays(List.of(businessHours));

        store.addMenus(List.of(menu));

        store.addStoreImages(List.of(storeImage));

        store.addPayments(List.of(paymentMethod));

        storeRepository.save(store);

    }

    protected void cleanup() {
        super.cleanup();
        storeRepository.deleteAll();

    }


}
