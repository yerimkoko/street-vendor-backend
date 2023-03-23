package store.streetvendor.service.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.menu.MenuRepository;
import store.streetvendor.core.domain.store.storeimage.StoreImageRepository;
import store.streetvendor.core.redis.storecount.StoreCountRepository;
import store.streetvendor.core.utils.dto.store.request.*;
import store.streetvendor.service.SetupBoss;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static store.streetvendor.core.domain.store.StoreCategory.OTHER_DESSERT;

@SpringBootTest
class StoreServiceTest extends SetupBoss {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private BusinessHoursRepository businessHoursRepository;

    @Autowired
    private StoreImageRepository storeImageRepository;


    @Autowired
    private StoreCountRepository storeCountRepository;


    @AfterEach
    void cleanUp() {
        businessHoursRepository.deleteAll();
        paymentRepository.deleteAll();
        menuRepository.deleteAll();
        storeImageRepository.deleteAll();
        storeRepository.deleteAll();
        memberRepository.deleteAll();
    }

    private void assertMenu(Menu menu, Long storeId, String menuName, int count, int menuPrice, String menuPictureUrl) {
        assertThat(menu.getStore().getId()).isEqualTo(storeId);
        assertThat(menu.getName()).isEqualTo(menuName);
        assertThat(menu.getMenuCount()).isEqualTo(count);
        assertThat(menu.getPrice()).isEqualTo(menuPrice);
        assertThat(menu.getPictureUrl()).isEqualTo(menuPictureUrl);
    }

    @Test
    void 가게를_상세조회한다() {
        // given
        Store store = storeFixture(boss.getId());
        storeRepository.save(store);

        // when
        storeService.getStoreDetail(store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertStore(stores.get(0), store.getName(), store.getLocation(), store.getStoreDescription(), store.getBossId(), store.getCategory());


        // TODO: redis 적용시키기 (dev)
        // Long value = storeCountRepository.getValueByKey(new StoreCountKey(store.getId()));
        // assertThat(value).isEqualTo(1);

    }


    @Test
    void 카테고리별로_운영중인_가게를_보여준다() {
        // given
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;
        StoreSalesStatus open = StoreSalesStatus.OPEN;
        StoreStatus status = StoreStatus.ACTIVE;
        Double latitude = 37.78639644286605;
        Double longitude = 126.40572677813635;
        Double distance = 999.00;

        createSalesStore(boss);
        StoreCategoryRequest request = new StoreCategoryRequest(open, status, latitude, longitude, distance);

        // when
        storeService.getStoresByCategoryAndLocationAndStoreStatus(request, category);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getCategory()).isEqualTo(category);
        assertThat(stores.get(0).getSalesStatus()).isEqualTo(open);
    }


    private Store createStore(Boss boss) {
        // store
        Long memberId = this.boss.getId();
        String name = "토끼의 붕어빵 가게";
        Location location = new Location(37.78639644286605, 126.40572677813635);
        String storeDescription = "슈크림 맛집 입니다!";
        String locationDescription = "당정역 1번 출구 앞";
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;

        return storeRepository.save(Store.newInstance(memberId, name, location, storeDescription, locationDescription, category));
    }

    private Store createSalesStore(Boss boss) {
        Long memberId = this.boss.getId();
        String name = "토끼의 붕어빵 가게";
        Location location = new Location(34.232323, 128.242424);
        String storeDescription = "슈크림 맛집 입니다!";
        String locationDescription = "당정역 1번 출구 앞";
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;

        return storeRepository.save(Store.newSalesStore(memberId, name, location, storeDescription, locationDescription, category));
    }

    private Store storeFixture(Long memberId) {
        // newStore
        String name = "토끼의 새로운 붕어빵";
        String storeDescription = "팥 붕어빵 맛집";
        String locationDescription = "군포역 2번 출구 앞";
        StoreCategory category = OTHER_DESSERT;
        Location location = new Location(34.2222, 128.222);
        return Store.newInstance(memberId, name, location, storeDescription, locationDescription, category);
    }

    private Menu createMenu(Store store) {
        return Menu.of(store, "붕어빵", 2, 2000, "pictureUrl");
    }


    private void assertStore(Store store, String name, Location location, String description, Long memberId, StoreCategory category) {
        assertThat(store.getName()).isEqualTo(name);
        assertThat(store.getLocation()).isEqualTo(location);
        assertThat(store.getStoreDescription()).isEqualTo(description);
        assertThat(store.getBossId()).isEqualTo(memberId);
        assertThat(store.getCategory()).isEqualTo(category);
    }

}
