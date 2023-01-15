package store.streetvendor.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.SetupBoss;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.menu.MenuRepository;
import store.streetvendor.core.utils.dto.store.request.AddNewStoreRequest;
import store.streetvendor.core.utils.dto.store.request.BusinessHourRequest;
import store.streetvendor.core.utils.dto.store.request.MenuRequest;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BossServiceTest extends SetupBoss {

    @Autowired
    private BossService bossService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private BusinessHoursRepository businessHoursRepository;

    @AfterEach
    void clean_up() {
        paymentRepository.deleteAll();
        menuRepository.deleteAll();
        businessHoursRepository.deleteAll();
        storeRepository.deleteAll();
        bossRepository.deleteAll();
    }


    @Test
    void 새로운_가게를_등록히면_영업시간_정보가_입력된다() {
        // given
        BusinessHourRequest businessHourRequest = BusinessHourRequest
            .builder()
            .startTime(LocalTime.of(8, 0))
            .endTime(LocalTime.of(8, 5))
            .days(Days.SUN)
            .build();

        AddNewStoreRequest request = AddNewStoreRequest.testBuilder()
            .name("가게 이름")
            .businessHours(List.of(businessHourRequest))
            .location(new Location(34.0, 128.0))
            .storeDescription("가게 설명")
            .locationDescription("위치 설명")
            .category(StoreCategory.BUNG_EO_PPANG)
            .paymentMethods(Collections.emptyList())
            .menus(Collections.emptyList())
            .storeImages(Collections.emptyList())
            .build();

        // when
        bossService.addNewStore(request, boss.getId());


        // then
        List<BusinessHours> businessHours = businessHoursRepository.findAll();
        assertThat(businessHours).hasSize(1);
        assertThat(businessHours.get(0).getDays()).isEqualTo(businessHourRequest.getDays());
        assertThat(businessHours.get(0).getOpeningTime()).isEqualTo(OpeningTime.of(businessHourRequest.getStartTime(), businessHourRequest.getEndTime()));
    }

        @Test
    void 새로운_가게를_등록히면_가게의_정보가_등록된다() {
        // given
        AddNewStoreRequest request = AddNewStoreRequest.testBuilder()
            .name("가게 이름")
            .location(new Location(34.0, 128.0))
            .storeDescription("가게 설명")
            .locationDescription("위치 설명")
            .category(StoreCategory.BUNG_EO_PPANG)
            .storeImages(Collections.emptyList())
            .businessHours(Collections.emptyList())
            .menus(Collections.emptyList())
            .paymentMethods(Collections.emptyList())
            .build();

        // when
        bossService.addNewStore(request, boss.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        //assertStore(stores.get(0), request.getName(), request.getLocation(), request.getStoreDescription(), boss.getId(), request.getCategory());
    }

    @Test
    void 새로운_가게를_등록히면_메뉴_정보가_등록된다() {
        // given
        MenuRequest menuRequest = MenuRequest.testInstance("붕어빵", 2, 2000, "pictureUrl");

        AddNewStoreRequest request = AddNewStoreRequest.testBuilder()
            .name("가게 이름")
            .location(new Location(34.0, 128.0))
            .storeDescription("가게 설명")
            .locationDescription("위치 설명")
            .category(StoreCategory.BUNG_EO_PPANG)
            .menus(List.of(menuRequest))
            .storeImages(Collections.emptyList())
            .businessHours(Collections.emptyList())
            .paymentMethods(Collections.emptyList())
            .build();

        // when
        bossService.addNewStore(request, boss.getId());

        // then
        List<Store> stores = storeRepository.findAll();

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(1);
        assertMenu(menus.get(0), stores.get(0).getId(), menuRequest.getName(), menuRequest.getMenuCount(), menuRequest.getPrice(), menuRequest.getPictureUrl());
    }

    @Test
    void 새로운_가게를_등록히면_결제_정보가_입력된다() {
        // given
        AddNewStoreRequest request = AddNewStoreRequest.testBuilder()
            .name("가게 이름")
            .location(new Location(34.0, 128.0))
            .storeDescription("가게 설명")
            .locationDescription("위치 설명")
            .category(StoreCategory.BUNG_EO_PPANG)
            .paymentMethods(List.of(PaymentMethod.CASH, PaymentMethod.ACCOUNT_TRANSFER))
            .menus(Collections.emptyList())
            .storeImages(Collections.emptyList())
            .businessHours(Collections.emptyList())
            .build();

        // when
        bossService.addNewStore(request, boss.getId());

        // then
        List<Store> stores = storeRepository.findAll();

        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(2);
        assertPayment(payments.get(0), stores.get(0).getId(), PaymentMethod.CASH);
        assertPayment(payments.get(1), stores.get(0).getId(), PaymentMethod.ACCOUNT_TRANSFER);
    }

    private Menu createMenu(Store store) {
        return Menu.of(store, "붕어빵", 2, 2000, "pictureUrl");
    }


    private void assertPayment(Payment payment, Long storeId, PaymentMethod paymentMethod) {
        assertThat(payment.getStore().getId()).isEqualTo(storeId);
        assertThat(payment.getPaymentMethod()).isEqualTo(paymentMethod);
    }


    private void assertStore(Store store, String name, Location location, String description, Long memberId, StoreCategory category) {
        assertThat(store.getName()).isEqualTo(name);
        assertThat(store.getLocation()).isEqualTo(location);
        assertThat(store.getStoreDescription()).isEqualTo(description);
        assertThat(store.getBossId()).isEqualTo(memberId);
        assertThat(store.getCategory()).isEqualTo(category);
    }

    private void assertMenu(Menu menu, Long storeId, String menuName, int count, int menuPrice, String menuPictureUrl) {
        assertThat(menu.getStore().getId()).isEqualTo(storeId);
        assertThat(menu.getName()).isEqualTo(menuName);
        assertThat(menu.getMenuCount()).isEqualTo(count);
        assertThat(menu.getPrice()).isEqualTo(menuPrice);
        assertThat(menu.getPictureUrl()).isEqualTo(menuPictureUrl);
    }

}
