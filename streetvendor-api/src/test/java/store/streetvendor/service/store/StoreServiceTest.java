package store.streetvendor.service.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.store.*;
import store.streetvendor.service.store.dto.request.AddNewStoreRequest;
import store.streetvendor.service.store.dto.request.MenuRequest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MenuRepository menuRepository;

    @AfterEach
    void cleanUp() {
        paymentRepository.deleteAllInBatch();
        menuRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
    }

    @Test
    void 새로운_가게를_등록히면_가게와_메뉴와_결제_방법이_저장된다() {
        // given
        Long memberId = 100000L;
        String name = "토끼의 붕어빵 가게";
        String pictureUrl = "https://rabbit.com";
        String location = "신정네거리역 1번출구";
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime endTime = LocalTime.of(21, 0);

        String menuName = "팥 붕어빵";
        String menuPrice = "5개에 2천원";
        String menuPictureUrl = "https://menu.com";
        List<MenuRequest> menuRequests = List.of(MenuRequest.testInstance(menuName, menuPrice, menuPictureUrl));
        List<PaymentMethod> paymentMethods = List.of(PaymentMethod.CASH, PaymentMethod.ACCOUNT_TRANSFER);

        AddNewStoreRequest request = AddNewStoreRequest.testBuilder()
            .name(name)
            .pictureUrl(pictureUrl)
            .location(location)
            .startTime(startTime)
            .endTime(endTime)
            .menus(menuRequests)
            .paymentMethods(paymentMethods)
            .build();

        // when
        storeService.addNewStore(request, memberId);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertStore(stores.get(0), name, pictureUrl, location, memberId, startTime, endTime);

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(1);
        assertMenu(menus.get(0), stores.get(0).getId(), menuName, menuPrice, menuPictureUrl);

        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(2);
        assertPayment(payments.get(0), stores.get(0).getId(), PaymentMethod.CASH);
        assertPayment(payments.get(1), stores.get(0).getId(), PaymentMethod.ACCOUNT_TRANSFER);
    }

    private void assertPayment(Payment payment, Long storeId, PaymentMethod paymentMethod) {
        assertThat(payment.getStore().getId()).isEqualTo(storeId);
        assertThat(payment.getPaymentMethod()).isEqualTo(paymentMethod);
    }

    private void assertMenu(Menu menu, Long storeId, String menuName, String menuPrice, String menuPictureUrl) {
        assertThat(menu.getStore().getId()).isEqualTo(storeId);
        assertThat(menu.getName()).isEqualTo(menuName);
        assertThat(menu.getPrice()).isEqualTo(menuPrice);
        assertThat(menu.getPictureUrl()).isEqualTo(menuPictureUrl);
    }

    private void assertStore(Store store, String name, String pictureUrl, String location, Long memberId, LocalTime startTime, LocalTime endTime) {
        assertThat(store.getName()).isEqualTo(name);
        assertThat(store.getPictureUrl()).isEqualTo(pictureUrl);
        assertThat(store.getLocation()).isEqualTo(location);
        assertThat(store.getMemberId()).isEqualTo(memberId);
        assertThat(store.getOpeningTime()).isEqualTo(OpeningTime.of(startTime, endTime));
    }

}
