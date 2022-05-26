package store.streetvendor.service.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.exception.model.AlreadyExistedException;
import store.streetvendor.exception.model.NotFoundException;
import store.streetvendor.service.store.dto.request.*;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.store.*;
import store.streetvendor.service.store.dto.response.StoreDetailResponse;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.streetvendor.domain.domain.store.StoreCategory.OTHER_DESSERT;

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
    private BusinessHoursRepository businessHoursRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void cleanUp() {
        businessHoursRepository.deleteAllInBatch();
        paymentRepository.deleteAllInBatch();
        menuRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    void 새로운_가게를_등록히면_가게와_가게분류와_메뉴와_결제_방법과_운영_시간이_저장된다() {
        // given
        Member member = createBossMember();
        Store store = createNewStore(member.getId());
        Menu menu = createMenu(store);
        List<MenuRequest> menuRequests = createMenuRequests(menu);
        List<PaymentMethod> paymentMethods = createPaymentMethod();
        List<BusinessHourRequest> businessHour = createBusinessHourRequest();

        AddNewStoreRequest storeRequest = addNewStoreRequest(store, menuRequests, paymentMethods, businessHour);

        // when
        storeService.addNewStore(storeRequest, member.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertStore(stores.get(0), store.getName(), store.getPictureUrl(), store.getLocation(), store.getStoreDescription(), member.getId(), store.getCategory());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(1);
        assertMenu(menus.get(0), stores.get(0).getId(), menu.getName(), menu.getMenuCount(), menu.getPrice(), menu.getPictureUrl());

        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(2);
        assertPayment(payments.get(0), stores.get(0).getId(), PaymentMethod.CASH);
        assertPayment(payments.get(1), stores.get(0).getId(), PaymentMethod.ACCOUNT_TRANSFER);

        List<BusinessHours> businessHours = businessHoursRepository.findAll();
        assertThat(businessHours).hasSize(1);
        assertThat(businessHours.get(0).getDays()).isEqualTo(createBusinessHourRequest().get(0).getDays());
        assertThat(businessHours.get(0).getOpeningTime()).isEqualTo(OpeningTime
            .of(createBusinessHourRequest().get(0).getStartTime(), createBusinessHourRequest().get(0).getEndTime()));
    }

    @Test
    void 멤버의_사장님_정보가_없으면_에러가_나타난다() {
        // given
        Member member = createMember();
        Store store = createNewStore(member.getId());
        Menu menu = createMenu(store);
        List<MenuRequest> menuRequests = createMenuRequests(menu);
        List<PaymentMethod> paymentMethods = createPaymentMethod();
        List<BusinessHourRequest> businessHour = createBusinessHourRequest();

        AddNewStoreRequest request = addNewStoreRequest(store, menuRequests, paymentMethods, businessHour);

        // when & then
        assertThatThrownBy(() -> storeService.addNewStore(request, member.getId()))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 가게를_수정한다() {
        // given
        Member member = createMember();
        Store store = createStore(member);
        store.addMenus(List.of(createMenu(store)));
        store.addPayments(List.of(PaymentMethod.CASH));
        store.addBusinessDays(List.of(createBusinessHours(store, Days.FRI, LocalTime.of(9, 0), LocalTime.of(18, 0))));
        storeRepository.save(store);

        PaymentMethod accountTransfer = PaymentMethod.ACCOUNT_TRANSFER;
        PaymentMethod cash = PaymentMethod.CASH;

        LocalTime newStartTime = LocalTime.of(10, 0);
        LocalTime newEndTime = LocalTime.of(18, 0);
        Days saturday = Days.SAT;

        Store newStore = createNewStore(member.getId());

        List<MenuRequest> newMenuRequest = createNewMenuRequest();
        StoreUpdateRequest updateRequest = updateRequest(newStore, newMenuRequest);

        // when
        storeService.updateMyStore(store.getMemberId(), store.getId(), updateRequest);

        // then
        List<Store> stores = storeRepository.findAll();
        List<Menu> menuList = menuRepository.findAll();
        List<Payment> payments = paymentRepository.findAll();
        List<BusinessHours> findBusinessHours = businessHoursRepository.findAll();

        assertThat(stores).hasSize(1);
        assertThat(menuList).hasSize(1);
        assertThat(payments).hasSize(2);
        assertThat(findBusinessHours).hasSize(1);


        assertStore(stores.get(0), updateRequest.getName(), updateRequest.getPictureUrl(), updateRequest.getLocation(), updateRequest.getDescription(), member.getId(), updateRequest.getCategory());
        assertMenu(menuList.get(0), store.getId(), newMenuRequest.get(0).getName(), newMenuRequest.get(0).getMenuCount(), newMenuRequest.get(0).getPrice(), newMenuRequest.get(0).getPictureUrl());
        assertPayment(payments.get(0), store.getId(), accountTransfer);
        assertPayment(payments.get(1), store.getId(), cash);

        assertThat(findBusinessHours.get(0).getOpeningTime()).isEqualTo(OpeningTime.of(newStartTime, newEndTime));
        assertThat(findBusinessHours.get(0).getDays()).isEqualTo(saturday);
    }

    @Test
    void 가게를_수정하려고_했을_때_가게_id가_없는경우() {
        // given
        Member member = createMember();

        Store store = createStore(member);

        // newStore
        String newName = "토끼 붕어";
        String newPictureUrl = "https://rabbit.com";
        String newDescription = "오픈 기념 슈크림 3개 1000원 이벤트!";
        Location location = new Location(35.3333, 127.43444);

        // menu
        String menuName = "슈크림 붕어빵";
        int menuPrice = 1000;
        int menuCount = 3;
        String menuPictureUrl = "https://menu.com";
        List<MenuRequest> menuRequests = List.of(MenuRequest.testInstance(menuName, menuCount, menuPrice, menuPictureUrl));
        PaymentMethod accountTransfer = PaymentMethod.ACCOUNT_TRANSFER;
        PaymentMethod cash = PaymentMethod.CASH;

        store.addMenus(List.of(Menu.of(store, menuName, menuCount, menuPrice, menuPictureUrl)));
        store.addPayments(List.of(PaymentMethod.CASH));

        // paymentMethod
        List<PaymentMethod> paymentMethods = List.of(accountTransfer, cash);

        StoreUpdateRequest request = StoreUpdateRequest.testBuilder()
            .name(newName)
            .pictureUrl(newPictureUrl)
            .location(location)
            .description(newDescription)
            .paymentMethods(paymentMethods)
            .menus(menuRequests)
            .build();

        // when & then
        assertThatThrownBy(() -> storeService.updateMyStore(member.getId(), store.getId() + 1, request))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 가게를_삭제한다() {
        // given
        Member member = createMember();
        Store store = createStore(member);

        // when
        storeService.deleteMyStore(member.getId(), store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getStatus()).isEqualTo(StoreStatus.DELETED);
    }


    @Test
    void 가게_상세정보를_조회한다() {
        // given
        Member member = createBossMember();
        Store store = createStore(member);
        store.addMenus(List.of(createMenu(store)));
        store.addPayments(List.of(PaymentMethod.CASH, PaymentMethod.ACCOUNT_TRANSFER));
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        Days friDay = Days.FRI;
        store.addBusinessDays(List.of(BusinessHours.of(store, friDay, startTime, endTime)));
        storeRepository.save(store);

        // when
        StoreDetailResponse detailResponse = storeService.getStoreDetail(store.getId());

        // then
        assertThat(detailResponse.getBusinessHours().get(0).getDays()).isEqualTo(friDay);

    }

    @Test
    void 가게_운영을_시킨다() {
        // given
        Member member = createMember();
        Store store = createStore(member);
        StoreSalesStatus open = StoreSalesStatus.OPEN;

        // when
        storeService.storeOpen(member.getId(), store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getSalesStatus()).isEqualTo(open);

    }

    @Test
    void 가게를_종료시킨다() {
        // given
        Member member = createMember();
        Store store = createSalesStore(member);
        StoreSalesStatus close = StoreSalesStatus.CLOSED;

        // when
        storeService.storeClose(member.getId(), store.getId());

        // when
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getSalesStatus()).isEqualTo(close);

    }

    @Test
    void 이미_운영중인_가게가_있는경우() {
        // given
        Member member = createMember();
        createSalesStore(member);
        Store store = createStore(member);

        // when & then
        assertThatThrownBy(() -> storeService.storeOpen(member.getId(), store.getId()))
            .isInstanceOf(AlreadyExistedException.class);

    }

    @Test
    void 운영중인_가게에_운영하기를_호출할_경우() {
        // given
        Member member = createMember();
        Store store = createSalesStore(member);

        // when & then
        assertThatThrownBy(() -> storeService.storeOpen(member.getId(), store.getId()))
            .isInstanceOf(AlreadyExistedException.class);
    }

    @Test
    void 종료된_가게에_종료를_호출할경우() {
        // given
        Member member = createMember();
        Store store = createStore(member);

        // when & then
        assertThatThrownBy(() -> storeService.storeClose(member.getId(), store.getId()))
            .isInstanceOf(AlreadyExistedException.class);
    }

    @Test
    void 카테고리별_가게를_보여준다() {
        // given
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;
        StoreSalesStatus open = StoreSalesStatus.OPEN;
        StoreStatus status = StoreStatus.ACTIVE;
        Double latitude = 37.78639644286605;
        Double longitude = 126.40572677813635;
        Double distance = 999.00;

        Member member = createMember();
        createSalesStore(member);
        StoreCategoryRequest request = new StoreCategoryRequest(category, open, status, latitude, longitude, distance);

        // when
        storeService.getStoresByCategoryAndLocationAndStoreStatus(request);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getCategory()).isEqualTo(category);
        assertThat(stores.get(0).getSalesStatus()).isEqualTo(open);
    }

    @Test
    void 가게의_메뉴상태를_변경한다() {
        // given
        Member member = createMember();
        Store store = createStore(member);
        MenuSalesStatus soldOut = MenuSalesStatus.SOLD_OUT;
        Menu menu = createMenu(store);
        menuRepository.save(menu);

        // when
        storeService.changeMenuStatus(store.getId(), menu.getId(), soldOut);

        // then
        List<Store> stores = storeRepository.findAll();
        List<Menu> menus = menuRepository.findAll();

        MenuSalesStatus menuSalesStatus = menus.get(0).getSalesStatus();
        assertThat(stores).hasSize(1);
        assertThat(menus).hasSize(1);
        assertThat(menuSalesStatus).isEqualTo(soldOut);
    }


    private Member createMember() {
        String name = "yerimkoko";
        String nickName = "yerimko";
        String email = "street-vendor@naver.com";
        String pictureUrl = "https://rabbit.com";

        Member member = Member.newGoogleInstance(name, nickName, email, pictureUrl);
        return memberRepository.save(member);
    }

    private Member createBossMember() {
        String name = "yerimkoko";
        String nickName = "yerimko";
        String email = "gochi97@naver.com";
        String pictureUrl = "https://rabbit.com";
        String bossName = "고토끼";
        String bossPhoneNumber = "010-2345-6789";

        Member member = Member.bossInstance(name, nickName, email, pictureUrl, bossName, bossPhoneNumber);

        return memberRepository.save(member);
    }

    private Store createStore(Member member) {
        // store
        Long memberId = member.getId();
        String name = "토끼의 붕어빵 가게";
        String pictureUrl = "https://rabbit.com";
        Location location = new Location(37.78639644286605, 126.40572677813635);

        String storeDescription = "슈크림 맛집 입니다!";
        String locationDescription = "당정역 1번 출구 앞";
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;

        return storeRepository.save(Store.newInstance(memberId, name, pictureUrl, location, storeDescription, locationDescription, category));
    }

    private Store createSalesStore(Member member) {
        Long memberId = member.getId();
        String name = "토끼의 붕어빵 가게";
        String pictureUrl = "https://rabbit.com";
        Location location = new Location(34.232323, 128.242424);
        String storeDescription = "슈크림 맛집 입니다!";
        String locationDescription = "당정역 1번 출구 앞";
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;

        return storeRepository.save(Store.newSalesStore(memberId, name, pictureUrl, location, storeDescription, locationDescription, category));

    }


    private List<MenuRequest> createMenuRequests(Menu menu) {
        return List.of(MenuRequest.testInstance(menu.getName(), menu.getMenuCount(), menu.getPrice(), menu.getPictureUrl()));
    }

    private List<PaymentMethod> createPaymentMethod() {
        return List.of(PaymentMethod.CASH, PaymentMethod.ACCOUNT_TRANSFER);
    }

    private List<BusinessHourRequest> createBusinessHourRequest() {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        Days friDay = Days.FRI;
        return List.of(new BusinessHourRequest(startTime, endTime, friDay));
    }


    private Store createNewStore(Long memberId) {
        // newStore
        String name = "토끼의 새로운 붕어빵";
        String pictureUrl = "tokki.jpg";
        String storeDescription = "팥 붕어빵 맛집";
        String locationDescription = "군포역 2번 출구 앞";
        StoreCategory category = OTHER_DESSERT;
        Location location = new Location(34.2222, 128.222);

        return Store.newInstance(memberId, name, pictureUrl, location, storeDescription, locationDescription, category);
    }

    private Menu createMenu(Store store) {
        return Menu.of(store, "붕어빵", 2, 2000, "pictureUrl");
    }

    private BusinessHours createBusinessHours(Store store, Days days, LocalTime startTime, LocalTime endTime) {
        return BusinessHours.of(store, days, startTime, endTime);
    }

    private AddNewStoreRequest addNewStoreRequest(Store store, List<MenuRequest> menuRequests, List<PaymentMethod> paymentMethods, List<BusinessHourRequest> businessHour) {

        return AddNewStoreRequest.testBuilder()
            .name(store.getName())
            .pictureUrl(store.getPictureUrl())
            .location(store.getLocation())
            .storeDescription(store.getStoreDescription())
            .locationDescription(store.getLocationDescription())
            .menus(menuRequests)
            .paymentMethods(paymentMethods)
            .businessHours(businessHour)
            .category(store.getCategory())
            .build();
    }

    private List<MenuRequest> createNewMenuRequest() {
        String newName = "팥 붕어빵";
        int menuCount = 3;
        int price = 2000;
        String pictureUrl = "rabbit";

        return List.of(MenuRequest.testInstance(newName, menuCount, price, pictureUrl));
    }

    private StoreUpdateRequest updateRequest(Store newStore, List<MenuRequest> newMenuRequests) {
        PaymentMethod accountTransfer = PaymentMethod.ACCOUNT_TRANSFER;
        PaymentMethod cash = PaymentMethod.CASH;

        LocalTime newStartTime = LocalTime.of(10, 0);
        LocalTime newEndTime = LocalTime.of(18, 0);
        Days saturday = Days.SAT;

        return StoreUpdateRequest.testBuilder()
            .name(newStore.getName())
            .pictureUrl(newStore.getPictureUrl())
            .location(newStore.getLocation())
            .description(newStore.getStoreDescription())
            .menus(newMenuRequests)
            .paymentMethods(List.of(accountTransfer, cash))
            .businessHours(List.of(new BusinessHourRequest(newStartTime, newEndTime, saturday)))
            .category(newStore.getCategory())
            .build();
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

    private void assertStore(Store store, String name, String pictureUrl, Location location, String description, Long memberId, StoreCategory category) {
        assertThat(store.getName()).isEqualTo(name);
        assertThat(store.getPictureUrl()).isEqualTo(pictureUrl);
        assertThat(store.getLocation()).isEqualTo(location);
        assertThat(store.getStoreDescription()).isEqualTo(description);
        assertThat(store.getMemberId()).isEqualTo(memberId);
        assertThat(store.getCategory()).isEqualTo(category);
    }

}
