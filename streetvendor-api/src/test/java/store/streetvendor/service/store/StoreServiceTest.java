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
import store.streetvendor.core.exception.ConflictException;
import store.streetvendor.core.redis.storecount.StoreCountKey;
import store.streetvendor.core.redis.storecount.StoreCountRepository;
import store.streetvendor.service.SetupBoss;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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

    @Autowired
    private MemberLikeStoreRepository memberLikeStoreRepository;


    @AfterEach
    void cleanUp() {
        memberLikeStoreRepository.deleteAll();
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
        String baseUrl = "baseUrl";
        storeRepository.save(store);

        // when
        storeService.getStoreDetail(store.getId(), baseUrl);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertStore(stores.get(0), store.getName(), store.getLocation(), store.getStoreDescription(), store.getBossId(), store.getCategory());

        Long value = storeCountRepository.getValueByKey(new StoreCountKey(store.getId()));
        assertThat(value).isEqualTo(1);

    }

    @Test
    void 가게를_좋아요_한다() {
        // given
        Long memberId = 999L;
        Store store = createSalesStore(boss);

        // when
        storeService.addMemberLikeStore(memberId, store.getId());

        // then
        List<MemberLikeStore> stores = memberLikeStoreRepository.findAll();

        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getStore().getId()).isEqualTo(store.getId());
        assertThat(stores.get(0).getMemberId()).isEqualTo(memberId);
    }

    @Test
    void 유저가_이미_좋아요를_했을때() {
        // given
        Long memberId = 999L;
        Store store = createSalesStore(boss);
        memberLikeStoreRepository.save(MemberLikeStore.newInstance(memberId, store));

        // when & then
        assertThatThrownBy(() -> storeService.addMemberLikeStore(memberId, store.getId()))
            .isInstanceOf(ConflictException.class);
    }

    @Test
    void 가게_좋아요를_취소한다() {
        // given
        Long memberId = 999L;
        Store store = createSalesStore(boss);
        MemberLikeStore memberLikeStore = memberLikeStoreRepository.save(MemberLikeStore.newInstance(memberId, store));

        // when
        storeService.deleteLikeStore(memberId, memberLikeStore.getStore().getId());

        // then
        List<MemberLikeStore> memberLikeStores = memberLikeStoreRepository.findAll();
        assertThat(memberLikeStores).hasSize(1);
        assertThat(memberLikeStores.get(0).getStatus()).isEqualTo(MemberLikeStoreStatus.INACTIVE);
    }


    @Test
    void 카테고리별로_운영중인_가게를_보여준다() {
        // given
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;
        double latitude = 37.78639644286605;
        double longitude = 126.40572677813635;
        createSalesStore(boss);

        // when
        storeService.getStoresByCategoryAndLocationAndStoreStatus(category, any(), longitude, latitude, 3, 5);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getCategory()).isEqualTo(category);
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
        Location location = new Location(34.2222, 128.222);
        return Store.newInstance(memberId, name, location, storeDescription, locationDescription, OTHER_DESSERT);
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
