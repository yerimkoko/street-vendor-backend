package store.streetvendor.service.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.store.*;
import store.streetvendor.domain.domain.model.exception.AlreadyExistedException;
import store.streetvendor.domain.domain.model.exception.NotFoundException;
import store.streetvendor.domain.domain.store.StoreImage;
import store.streetvendor.service.store.dto.request.*;
import store.streetvendor.service.store.dto.response.StoreDetailResponse;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.streetvendor.domain.domain.store.StoreCategory.OTHER_DESSERT;

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

    @AfterEach
    void cleanUp() {
        super.cleanup();
        businessHoursRepository.deleteAllInBatch();
        paymentRepository.deleteAllInBatch();
        menuRepository.deleteAllInBatch();
        storeImageRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
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
        storeService.addNewStore(request, boss.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertStore(stores.get(0), request.getName(), request.getLocation(), request.getStoreDescription(), boss.getId(), request.getCategory());
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
        storeService.addNewStore(request, boss.getId());

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
        storeService.addNewStore(request, boss.getId());

        // then
        List<Store> stores = storeRepository.findAll();

        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(2);
        assertPayment(payments.get(0), stores.get(0).getId(), PaymentMethod.CASH);
        assertPayment(payments.get(1), stores.get(0).getId(), PaymentMethod.ACCOUNT_TRANSFER);
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
        storeService.addNewStore(request, boss.getId());

        // then
        List<BusinessHours> businessHours = businessHoursRepository.findAll();
        assertThat(businessHours).hasSize(1);
        assertThat(businessHours.get(0).getDays()).isEqualTo(businessHourRequest.getDays());
        assertThat(businessHours.get(0).getOpeningTime()).isEqualTo(OpeningTime.of(businessHourRequest.getStartTime(), businessHourRequest.getEndTime()));
    }

    @Test
    void 멤버의_사장님_정보가_없으면_가게_등록시_에러가_나타난다() {
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

        // when & then
        assertThatThrownBy(() -> storeService.addNewStore(request, member.getId()))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 가게를_수정한다() {
        // given
        Store store = storeFixture(boss.getId());
        store.addMenus(List.of(createMenu(store)));
        store.addPayments(List.of(PaymentMethod.CASH));
        store.addBusinessDays(List.of(createBusinessHours(store, Days.FRI, LocalTime.of(9, 0), LocalTime.of(18, 0))));
        store.addStoreImages(List.of(createStoreImage(store)));
        storeRepository.save(store);

        PaymentMethod accountTransfer = PaymentMethod.ACCOUNT_TRANSFER;
        PaymentMethod cash = PaymentMethod.CASH;

        LocalTime newStartTime = LocalTime.of(10, 0);
        LocalTime newEndTime = LocalTime.of(18, 0);
        Days saturday = Days.SAT;

        Store newStore = storeFixture(boss.getId());
        List<MenuRequest> newMenuRequest = createNewMenuRequest();
        List<StoreImageRequest> newStoreRequest = List.of(createNewStoreImage());

        StoreUpdateRequest updateRequest = updateRequest(newStore, newMenuRequest, newStoreRequest);

        // when
        storeService.updateMyStore(store.getMemberId(), store.getId(), updateRequest);

        // then
        List<Store> stores = storeRepository.findAll();
        List<Menu> menuList = menuRepository.findAll();
        List<Payment> payments = paymentRepository.findAll();
        List<BusinessHours> findBusinessHours = businessHoursRepository.findAll();
        List<StoreImage> storeImages = storeImageRepository.findAll();

        assertThat(stores).hasSize(1);
        assertThat(menuList).hasSize(1);
        assertThat(payments).hasSize(2);
        assertThat(findBusinessHours).hasSize(1);
        assertThat(storeImages).hasSize(1);


        assertStore(stores.get(0), updateRequest.getName(), updateRequest.getLocation(), updateRequest.getDescription(), boss.getId(), updateRequest.getCategory());
        assertMenu(menuList.get(0), store.getId(), newMenuRequest.get(0).getName(), newMenuRequest.get(0).getMenuCount(), newMenuRequest.get(0).getPrice(), newMenuRequest.get(0).getPictureUrl());
        assertPayment(payments.get(0), store.getId(), accountTransfer);
        assertPayment(payments.get(1), store.getId(), cash);
        assertStoreImage(storeImages.get(0), createNewStoreImage().getIsThumbNail(), createNewStoreImage().getImageUrl());

        assertThat(findBusinessHours.get(0).getOpeningTime()).isEqualTo(OpeningTime.of(newStartTime, newEndTime));
        assertThat(findBusinessHours.get(0).getDays()).isEqualTo(saturday);
    }

    private void assertStoreImage(StoreImage storeImage, Boolean isThumbNail, String imageUrl) {
        assertThat(storeImage.getIsThumbNail()).isEqualTo(isThumbNail);
        assertThat(storeImage.getPictureUrl()).isEqualTo(imageUrl);
    }

    @Test
    void 가게를_수정하려고_했을_때_가게_id가_없는경우() {
        // given
        Store store = createStore(boss);

        // newStore
        String newName = "토끼 붕어";
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
            .location(location)
            .description(newDescription)
            .paymentMethods(paymentMethods)
            .menus(menuRequests)
            .build();

        // when & then
        assertThatThrownBy(() -> storeService.updateMyStore(boss.getId(), store.getId() + 1, request))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 가게를_삭제한다() {
        // given
        Store store = createStore(boss);

        // when
        storeService.deleteMyStore(boss.getId(), store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getStatus()).isEqualTo(StoreStatus.DELETED);
    }

    @Test
    void 가게_상세정보를_조회한다() {
        // given
        Store store = createStore(boss);
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
        Store store = createStore(boss);
        StoreSalesStatus open = StoreSalesStatus.OPEN;

        // when
        storeService.storeOpen(boss.getId(), store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getSalesStatus()).isEqualTo(open);
    }

    @Test
    void 가게를_종료시킨다() {
        // given
        Store store = createSalesStore(boss);
        StoreSalesStatus close = StoreSalesStatus.CLOSED;

        // when
        storeService.storeClose(boss.getId(), store.getId());

        // when
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getSalesStatus()).isEqualTo(close);

    }

    @Test
    void 이미_운영중인_가게가_있는경우() {
        // given
        createSalesStore(boss);
        Store store = createStore(boss);

        // when & then
        assertThatThrownBy(() -> storeService.storeOpen(boss.getId(), store.getId()))
            .isInstanceOf(AlreadyExistedException.class);

    }

    @Test
    void 운영중인_가게에_운영하기를_호출할_경우() {
        // given
        Store store = createSalesStore(boss);

        // when & then
        assertThatThrownBy(() -> storeService.storeOpen(boss.getId(), store.getId()))
            .isInstanceOf(AlreadyExistedException.class);
    }

    @Test
    void 종료된_가게에_종료를_호출할경우() {
        // given
        Store store = createStore(boss);

        // when & then
        assertThatThrownBy(() -> storeService.storeClose(boss.getId(), store.getId()))
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

        createSalesStore(boss);
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
        Store store = createStore(boss);
        MenuSalesStatus soldOut = MenuSalesStatus.SOLD_OUT;
        Menu menu = createMenu(store);
        menuRepository.save(menu);

        // when
        storeService.changeMenuStatus(store.getId(), boss.getId(), menu.getId(), soldOut);

        // then
        List<Store> stores = storeRepository.findAll();
        List<Menu> menus = menuRepository.findAll();

        MenuSalesStatus menuSalesStatus = menus.get(0).getSalesStatus();
        assertThat(stores).hasSize(1);
        assertThat(menus).hasSize(1);
        assertThat(menuSalesStatus).isEqualTo(soldOut);
    }

    private Store createStore(Member member) {
        // store
        Long memberId = this.boss.getId();
        String name = "토끼의 붕어빵 가게";
        Location location = new Location(37.78639644286605, 126.40572677813635);

        String storeDescription = "슈크림 맛집 입니다!";
        String locationDescription = "당정역 1번 출구 앞";
        StoreCategory category = StoreCategory.BUNG_EO_PPANG;

        return storeRepository.save(Store.newInstance(memberId, name, location, storeDescription, locationDescription, category));
    }

    private Store createSalesStore(Member member) {
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

    private StoreImage createStoreImage(Store store) {
        return StoreImage.of(store, true, "pictureUrl");
    }

    private StoreImageRequest createNewStoreImage() {
        return StoreImageRequest.testInstance(false, "newPictureUrl");
    }


    private BusinessHours createBusinessHours(Store store, Days days, LocalTime startTime, LocalTime endTime) {
        return BusinessHours.of(store, days, startTime, endTime);
    }

    private List<MenuRequest> createNewMenuRequest() {
        String newName = "팥 붕어빵";
        int menuCount = 3;
        int price = 2000;
        String pictureUrl = "rabbit";

        return List.of(MenuRequest.testInstance(newName, menuCount, price, pictureUrl));
    }

    private StoreUpdateRequest updateRequest(Store newStore, List<MenuRequest> newMenuRequests,
                                             List<StoreImageRequest> storeImageRequests) {
        PaymentMethod accountTransfer = PaymentMethod.ACCOUNT_TRANSFER;
        PaymentMethod cash = PaymentMethod.CASH;

        LocalTime newStartTime = LocalTime.of(10, 0);
        LocalTime newEndTime = LocalTime.of(18, 0);
        Days saturday = Days.SAT;

        return StoreUpdateRequest.testBuilder()
            .name(newStore.getName())
            .location(newStore.getLocation())
            .description(newStore.getStoreDescription())
            .menus(newMenuRequests)
            .paymentMethods(List.of(accountTransfer, cash))
            .businessHours(List.of(new BusinessHourRequest(newStartTime, newEndTime, saturday)))
            .category(newStore.getCategory())
            .storeImages(storeImageRequests)
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

    private void assertStore(Store store, String name, Location location, String description, Long memberId, StoreCategory category) {
        assertThat(store.getName()).isEqualTo(name);
        assertThat(store.getLocation()).isEqualTo(location);
        assertThat(store.getStoreDescription()).isEqualTo(description);
        assertThat(store.getMemberId()).isEqualTo(memberId);
        assertThat(store.getCategory()).isEqualTo(category);
    }

}
