package store.streetvendor.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.SetUpBoss;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.menu.MenuRepository;
import store.streetvendor.core.domain.store.storeimage.StoreImage;
import store.streetvendor.core.domain.store.storeimage.StoreImageRepository;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.utils.dto.store.request.BusinessHourRequest;
import store.streetvendor.core.utils.dto.store.request.MenuRequest;
import store.streetvendor.core.utils.dto.store.request.StoreImageRequest;
import store.streetvendor.core.utils.dto.store.request.StoreUpdateRequest;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.streetvendor.core.domain.store.StoreCategory.OTHER_DESSERT;

@SpringBootTest
public class BossStoreServiceTest extends SetUpBoss {

    @Autowired
    private BossStoreService bossStoreService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StoreImageRepository storeImageRepository;

    @Autowired
    private BusinessHoursRepository businessHoursRepository;

    @AfterEach
    void cleanUp() {
        businessHoursRepository.deleteAll();
        menuRepository.deleteAll();
        paymentRepository.deleteAll();
        storeImageRepository.deleteAll();
        storeRepository.deleteAll();


    }

    @Test
    void 가게의_메뉴를_수정한다() {
        // given
        Store store = storeFixture(boss.getId());
        Menu menu = Menu.of(store, "붕어빵", 2, 2000, "pictureUrl");
        store.addMenus(List.of(menu));
        storeRepository.save(store);

        MenuRequest newMenu = MenuRequest.testInstance("새로운 붕어빵", 3, 3000, "imageUrl");
        StoreUpdateRequest updateRequest = StoreUpdateRequest.testBuilder()
            .menus(List.of(newMenu))
            .storeImages(Collections.emptyList())
            .paymentMethods(Collections.emptyList())
            .businessHours(Collections.emptyList())
            .name(store.getName())
            .build();

        // when
        bossStoreService.updateMyStore(store.getBossId(), store.getId(), updateRequest);

        // then
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(1);

        assertMenu(menuList.get(0), store.getId(), newMenu.getName(), newMenu.getMenuCount(), newMenu.getPrice(), newMenu.getPictureUrl());

    }

    @Test
    void 가게의_결제정보를_수정한다() {
        // given
        Store store = storeFixture(boss.getId());
        List<PaymentMethod> paymentMethod = (List.of(PaymentMethod.ACCOUNT_TRANSFER));
        store.addPayments(paymentMethod);
        storeRepository.save(store);

        StoreUpdateRequest updateRequest = StoreUpdateRequest.testBuilder()
            .paymentMethods(List.of(PaymentMethod.CASH))
            .name(store.getName())
            .businessHours(Collections.emptyList())
            .storeImages(Collections.emptyList())
            .menus(Collections.emptyList()).build();

        // when
        bossStoreService.updateMyStore(store.getBossId(), store.getId(), updateRequest);

        // then
        List<Payment> paymentMethods = paymentRepository.findAll();
        assertThat(paymentMethods).hasSize(1);
        assertPayment(paymentMethods.get(0), store.getId(), PaymentMethod.CASH);
    }

    @Test
    void 가게의_사진을_변경한다() {
        // given
        Store store = storeFixture(boss.getId());

        StoreImage storeImage = StoreImage.builder()
            .store(store)
            .pictureUrl("picture")
            .isThumbNail(true)
            .build();

        StoreImageRequest newRequest = StoreImageRequest.testInstance(false, "notThumbNail");
        store.addStoreImages(List.of(storeImage));

        storeRepository.save(store);

        StoreUpdateRequest storeUpdateRequest = StoreUpdateRequest.testBuilder()
            .storeImages(List.of(newRequest))
            .name(store.getName())
            .paymentMethods(Collections.emptyList())
            .businessHours(Collections.emptyList())
            .menus(Collections.emptyList())
            .build();

        // when
        bossStoreService.updateMyStore(store.getBossId(), store.getId(), storeUpdateRequest);

        // then
        List<StoreImage> storeImages = storeImageRepository.findAll();
        assertThat(storeImages).hasSize(1);
        assertStoreImage(storeImages.get(0), newRequest.getIsThumbNail(), newRequest.getImageUrl());


    }

    private void assertStoreImage(StoreImage storeImage, Boolean isThumbNail, String imageUrl) {
        assertThat(storeImage.getIsThumbNail()).isEqualTo(isThumbNail);
        assertThat(storeImage.getPictureUrl()).isEqualTo(imageUrl);
    }

    private void assertPayment(Payment payment, Long storeId, PaymentMethod paymentMethod) {
        assertThat(payment.getStore().getId()).isEqualTo(storeId);
        assertThat(payment.getPaymentMethod()).isEqualTo(paymentMethod);
    }



    private void assertMenu(Menu menu, Long storeId, String menuName, int count, int menuPrice, String menuPictureUrl) {
        assertThat(menu.getStore().getId()).isEqualTo(storeId);
        assertThat(menu.getName()).isEqualTo(menuName);
        assertThat(menu.getMenuCount()).isEqualTo(count);
        assertThat(menu.getPrice()).isEqualTo(menuPrice);
        assertThat(menu.getPictureUrl()).isEqualTo(menuPictureUrl);
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

    @Test
    void 가게를_삭제한다() {
        // given
        Store store = storeFixture(boss.getId());
        storeRepository.save(store);

        // when
        bossStoreService.deleteMyStore(boss.getId(), store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getStatus()).isEqualTo(StoreStatus.DELETED);
    }

    @Test
    void 가게를_수정하려고_했을_때_가게_id가_없는경우() {
        // given
        Store store = storeFixture(boss.getId());
        storeRepository.save(store);

        // newStore
        StoreUpdateRequest request = StoreUpdateRequest.testBuilder()
            .name(store.getName())
            .paymentMethods(Collections.emptyList())
            .menus(Collections.emptyList())
            .build();

        // when & then
        assertThatThrownBy(() -> bossStoreService.updateMyStore(boss.getId(), store.getId() + 1, request))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 가게_영업시간을_수정한다() {
        // given
        Store store = storeFixture(boss.getId());

        BusinessHours businessHour = BusinessHours.builder()
            .store(store)
            .days(Days.SUN)
            .startTime(LocalTime.of(8, 0))
            .endTime(LocalTime.of(10, 0))
            .build();

        store.addBusinessDays(List.of(businessHour));
        storeRepository.save(store);

        BusinessHourRequest newBusinessHour = BusinessHourRequest.builder()
            .days(Days.MON)
            .startTime(LocalTime.of(10, 0))
            .endTime(LocalTime.of(12, 0))
            .build();

        StoreUpdateRequest updateRequest = StoreUpdateRequest.testBuilder()
            .businessHours(List.of(newBusinessHour))
            .paymentMethods(Collections.emptyList())
            .name(store.getName())
            .storeImages(Collections.emptyList())
            .menus(Collections.emptyList()).build();

        // when
        bossStoreService.updateMyStore(store.getBossId(), store.getId(), updateRequest);

        // then
        List<BusinessHours> businessHours = businessHoursRepository.findAll();
        assertThat(businessHours).hasSize(1);
        assertBusinessHours(businessHours.get(0), newBusinessHour.getStartTime(), newBusinessHour.getEndTime(), newBusinessHour.getDays());

    }

    private void assertBusinessHours(BusinessHours businessHours, LocalTime startTime, LocalTime endTime, Days day) {
        assertThat(businessHours.getOpeningTime().getStartTime()).isEqualTo(startTime);
        assertThat(businessHours.getOpeningTime().getEndTime()).isEqualTo(endTime);
        assertThat(businessHours.getDays()).isEqualTo(day);
    }




}
