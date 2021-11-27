package store.streetvendor.service.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.controller.dto.store.StoreUpdateRequest;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.store.*;
import store.streetvendor.service.store.dto.request.AddNewStoreRequest;
import store.streetvendor.service.store.dto.request.MenuRequest;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void cleanUp() {
        paymentRepository.deleteAllInBatch();
        menuRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    void 새로운_가게를_등록히면_가게와_메뉴와_결제_방법이_저장된다() {
        // given
        Long memberId = 100000L;
        String name = "토끼의 붕어빵 가게";
        String pictureUrl = "https://rabbit.com";
        String location = "신정네거리역 1번 출구";
        String description = "슈크림 2개 1000원 입니다!";
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime endTime = LocalTime.of(21, 0);

        String menuName = "팥 붕어빵";
        int menuPrice = 2000;
        int menuAmount = 1;
        String menuPictureUrl = "https://menu.com";
        List<MenuRequest> menuRequests = List.of(MenuRequest.testInstance(menuName, menuAmount, menuPrice, menuPictureUrl));
        List<PaymentMethod> paymentMethods = List.of(PaymentMethod.CASH, PaymentMethod.ACCOUNT_TRANSFER);

        AddNewStoreRequest request = AddNewStoreRequest.testBuilder()
            .name(name)
            .pictureUrl(pictureUrl)
            .location(location)
            .description(description)
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
        assertStore(stores.get(0), name, pictureUrl, location, description, memberId, startTime, endTime);

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(1);
        assertMenu(menus.get(0), stores.get(0).getId(), menuName, menuAmount, menuPrice, menuPictureUrl);

        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(2);
        assertPayment(payments.get(0), stores.get(0).getId(), PaymentMethod.CASH);
        assertPayment(payments.get(1), stores.get(0).getId(), PaymentMethod.ACCOUNT_TRANSFER);
    }

    @Test
    void 가게를_수정한다() {
        // given
        // member
        String email = "tokki@gmail.com";
        String nickName = "토끼";
        String memberName = "고토끼";
        String profileUrl = "234234tokki.jpg";
        Member member = memberRepository.save(Member.newGoogleInstance(memberName, nickName, email, profileUrl));

        // store
        Long memberId = member.getId();
        String name = "토끼의 붕어빵 가게";
        String pictureUrl = "https://rabbit.com";
        String location = "신정네거리역 1번 출구";
        String description = "슈크림 2개 1000원 입니다!";
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime endTime = LocalTime.of(21, 0);

        // newStore
        String newName = "토끼의 붕어빵";
        String newPictureUrl = "rabbit.jpg";
        String newLocation = "신정네거리 3번 출구";
        String newDescription = "슈크림 3개 1000원 이벤트!";
        LocalTime newStartTime = LocalTime.of(18, 0);
        LocalTime newEndTime = LocalTime.of(22, 0);

        Store store = storeRepository.save(Store.newInstance(memberId, name, pictureUrl, location, description, startTime, endTime));

        // menu
        String menuName = "슈크림 붕어빵";
        int menuPrice = 1000;
        int menuAmount = 1;
        String menuPictureUrl = "https://menu.com";
        List<MenuRequest> menuRequests = List.of(MenuRequest.testInstance(menuName, menuAmount, menuPrice, menuPictureUrl));
        List<Menu> menus = menuRequests.stream().map(menu -> menu.toEntity(store)).collect(Collectors.toList());
        PaymentMethod accountTransfer = PaymentMethod.ACCOUNT_TRANSFER;
        PaymentMethod cash = PaymentMethod.CASH;

        // paymentMethod
        List<PaymentMethod> methods = List.of(accountTransfer, cash);

        StoreUpdateRequest request = StoreUpdateRequest.testBuilder()
            .name(newName)
            .pictureUrl(newPictureUrl)
            .location(newLocation)
            .description(newDescription)
            .startTime(newStartTime)
            .endTime(newEndTime)
            .menus(menus)
            .paymentMethods(methods)
            .build();


        // when
        storeService.updateMyStore(store.getMemberId(), store.getId(), request);

        // then
        List<Store> stores = storeRepository.findAll();
        List<Menu> menuList = menuRepository.findAll();
        List<Payment> payments = paymentRepository.findAll();

        assertThat(stores).hasSize(1);
        assertThat(menuList).hasSize(1);
        assertThat(payments).hasSize(2);
        assertStore(stores.get(0), newName, newPictureUrl, newLocation, newDescription, member.getId(), newStartTime, newEndTime);
        assertMenu(menuList.get(0), store.getId(), menuName, menuAmount, menuPrice, menuPictureUrl);
        assertPayment(payments.get(0), store.getId(), accountTransfer);
        assertPayment(payments.get(1), store.getId(), cash);

    }

    @Test
    void 가게를_삭제한다() {
        // given
        String email = "tokki@gmail.com";
        String nickName = "토끼";
        String memberName = "고토끼";
        String profileUrl = "234234tokki.jpg";
        Member member = memberRepository.save(Member.newGoogleInstance(memberName, nickName, email, profileUrl));

        Long memberId = member.getId();
        String name = "토끼의 붕어빵 가게";
        String pictureUrl = "https://rabbit.com";
        String location = "신정네거리역 1번 출구";
        String description = "슈크림 2개 1000원 입니다!";
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime endTime = LocalTime.of(21, 0);

        Store store = storeRepository.save(Store.newInstance(memberId, name, pictureUrl, location, description, startTime, endTime));

        // when
        storeService.deleteMyStore(memberId, store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getStatus()).isEqualTo(StoreStatus.DELETED);

    }

    private void assertPayment(Payment payment, Long storeId, PaymentMethod paymentMethod) {
        assertThat(payment.getStore().getId()).isEqualTo(storeId);
        assertThat(payment.getPaymentMethod()).isEqualTo(paymentMethod);
    }

    private void assertMenu(Menu menu, Long storeId, String menuName, int menuAmount, int menuPrice, String menuPictureUrl) {
        assertThat(menu.getStore().getId()).isEqualTo(storeId);
        assertThat(menu.getName()).isEqualTo(menuName);
        assertThat(menu.getAmount()).isEqualTo(menuAmount);
        assertThat(menu.getPrice()).isEqualTo(menuPrice);
        assertThat(menu.getPictureUrl()).isEqualTo(menuPictureUrl);
    }

    private void assertStore(Store store, String name, String pictureUrl, String location, String description, Long memberId, LocalTime startTime, LocalTime endTime) {
        assertThat(store.getName()).isEqualTo(name);
        assertThat(store.getPictureUrl()).isEqualTo(pictureUrl);
        assertThat(store.getLocation()).isEqualTo(location);
        assertThat(store.getDescription()).isEqualTo(description);
        assertThat(store.getMemberId()).isEqualTo(memberId);
        assertThat(store.getOpeningTime()).isEqualTo(OpeningTime.of(startTime, endTime));
    }

}
