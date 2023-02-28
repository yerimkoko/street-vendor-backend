package store.streetvendor.service.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.exception.AlreadyExistedException;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.domain.store.star.StarStatus;
import store.streetvendor.core.domain.store.storeimage.StoreImage;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.menu.MenuRepository;
import store.streetvendor.core.domain.store.menu.MenuSalesStatus;
import store.streetvendor.core.domain.store.review.Review;
import store.streetvendor.core.domain.store.review.ReviewRepository;
import store.streetvendor.core.domain.store.star.Star;
import store.streetvendor.core.domain.store.star.StarRepository;
import store.streetvendor.core.domain.store.storeimage.StoreImageRepository;
import store.streetvendor.core.redis.storecount.StoreCountKey;
import store.streetvendor.core.redis.storecount.StoreCountRepository;
import store.streetvendor.core.utils.dto.store.request.*;
import store.streetvendor.service.SetupBoss;
import store.streetvendor.core.utils.dto.store.response.StoreDetailResponse;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    private ReviewRepository reviewRepository;

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private StoreCountRepository storeCountRepository;


    @AfterEach
    void cleanUp() {
        starRepository.deleteAllInBatch();
        reviewRepository.deleteAllInBatch();
        businessHoursRepository.deleteAllInBatch();
        paymentRepository.deleteAllInBatch();
        menuRepository.deleteAllInBatch();
        storeImageRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
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
        storeService.updateMyStore(store.getBossId(), store.getId(), updateRequest);

        // then
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(1);

        assertMenu(menuList.get(0), store.getId(), newMenu.getName(), newMenu.getMenuCount(), newMenu.getPrice(), newMenu.getPictureUrl());

    }

    private void assertMenu(Menu menu, Long storeId, String menuName, int count, int menuPrice, String menuPictureUrl) {
        assertThat(menu.getStore().getId()).isEqualTo(storeId);
        assertThat(menu.getName()).isEqualTo(menuName);
        assertThat(menu.getMenuCount()).isEqualTo(count);
        assertThat(menu.getPrice()).isEqualTo(menuPrice);
        assertThat(menu.getPictureUrl()).isEqualTo(menuPictureUrl);
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
        storeService.updateMyStore(store.getBossId(), store.getId(), updateRequest);

        // then
        List<Payment> paymentMethods = paymentRepository.findAll();
        assertThat(paymentMethods).hasSize(1);
        assertPayment(paymentMethods.get(0), store.getId(), PaymentMethod.CASH);
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
        storeService.updateMyStore(store.getBossId(), store.getId(), updateRequest);

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
        storeService.updateMyStore(store.getBossId(), store.getId(), storeUpdateRequest);

        // then
        List<StoreImage> storeImages = storeImageRepository.findAll();
        assertThat(storeImages).hasSize(1);
        assertStoreImage(storeImages.get(0), newRequest.getIsThumbNail(), newRequest.getImageUrl());


    }

    private void assertStoreImage(StoreImage storeImage, Boolean isThumbNail, String imageUrl) {
        assertThat(storeImage.getIsThumbNail()).isEqualTo(isThumbNail);
        assertThat(storeImage.getPictureUrl()).isEqualTo(imageUrl);
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
        assertThatThrownBy(() -> storeService.updateMyStore(boss.getId(), store.getId() + 1, request))
            .isInstanceOf(NotFoundException.class);
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
    void 가게를_삭제한다() {
        // given
        Store store = storeFixture(boss.getId());
        storeRepository.save(store);

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
        Store store = storeFixture(boss.getId());
        store.addMenus(List.of(Menu.of(store, "붕어빵", 2, 1000, "pictureUrl")));
        store.addPayments(List.of(PaymentMethod.CASH, PaymentMethod.ACCOUNT_TRANSFER));
        store.addStoreImages(List.of(StoreImage.of(store, true, "pictureUrl")));

        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        Days friDay = Days.FRI;

        store.addBusinessDays(List.of(BusinessHours.of(store, friDay, startTime, endTime)));
        storeRepository.save(store);

        // when
        StoreDetailResponse response = storeService.getStoreDetail(store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(response.getCategory()).isEqualTo(store.getCategory());
        assertThat(response.getStoreDescription()).isEqualTo(store.getStoreDescription());
        assertThat(response.getStoreId()).isEqualTo(store.getId());

    }

    @Test
    void 가게_운영을_시킨다() {
        // given
        Store store = storeFixture(boss.getId());
        storeRepository.save(store);

        // when
        storeService.storeOpen(boss.getId(), store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getSalesStatus()).isEqualTo(StoreSalesStatus.OPEN);
    }

    @Test
    void 가게를_종료시킨다() {
        // given
        Store store = storeRepository.save(Store.newSalesStore(boss.getId(), "토끼네", new Location(33.33, 33.33), "sdfsdf", "sdfsdf", StoreCategory.BUNG_EO_PPANG));

        // when
        storeService.storeClose(boss.getId(), store.getId());

        // when
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);
        assertThat(stores.get(0).getSalesStatus()).isEqualTo(StoreSalesStatus.CLOSED);

    }

    @Test
    void 이미_운영중인_가게가_있는경우() {
        // given
        Store store = createSalesStore(boss);

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
        Store store = storeFixture(boss.getId());
        storeRepository.save(store);

        // when & then
        assertThatThrownBy(() -> storeService.storeClose(boss.getId(), store.getId()))
            .isInstanceOf(AlreadyExistedException.class);
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
        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(1);
        assertThat(menus.get(0).getSalesStatus()).isEqualTo(soldOut);
    }

    @Test
    void 별점이_등록된다() {
        // given
        Store store = createStore(boss);
        Long memberId = 999L;
        String comment = "진짜 맛집이에요.";
        Rate rate = Rate.five;
        AddStoreReviewRequest request = AddStoreReviewRequest.builder()
            .comment(comment)
            .rate(rate)
            .build();

        // when
        storeService.addEvaluation(memberId, store.getId(), request);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);

        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(1);

        assertReview(reviews.get(0), comment, rate, store.getId());

    }

    private void assertReview(Review review, String comment, Rate rate, Long storeId) {
        assertThat(review.getComment()).isEqualTo(comment);
        assertThat(review.getRate()).isEqualTo(rate);
        assertThat(review.getStore().getId()).isEqualTo(storeId);

    }

    @Test
    void 리뷰를_수정한다() {
        // given
        Store store = createStore(boss);
        String comment = "진짜 맛집이에요.";
        String updateComment = "인정하는 맛집";

        Rate rate = Rate.five;
        Rate upgradeRate = Rate.three;

        Review review = reviewRepository.save(Review.of(store, store.getBossId(), rate, comment));

        UpdateStoreReviewRequest request = UpdateStoreReviewRequest.builder()
            .reviewId(review.getId())
            .comment(updateComment)
            .rate(upgradeRate)
            .build();

        // when
        storeService.updateReview(store.getBossId(), store.getId(), request);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);

        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(1);

        assertReview(reviews.get(0), updateComment, upgradeRate, store.getId());

    }

    @Test
    void 리뷰를_삭제한다() {
        Store store = createStore(boss);
        Long memberId = 999L;
        String comment = "진짜 맛집이에요.";
        Rate rate = Rate.five;
        Review review = reviewRepository.save(Review.of(store, memberId, rate, comment));

        // when
        storeService.deleteReview(memberId, store.getId(), review.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);

        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).isEmpty();

    }

    @Test
    void 가게를_즐겨찾기에_추가한다() {
        // given
        Store store = createStore(boss);
        Long memberId = 999L;

        // when
        storeService.addStar(memberId, store.getId());

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);

        List<Star> stars = starRepository.findAll();
        assertThat(stars).hasSize(1);

        assertThat(stars.get(0).getStore().getId()).isEqualTo(store.getId());
    }

    @Test
    void 가게_즐겨찾기를_제거한다() {
        // given
        Store store = createStore(boss);
        Star star = createStar(store, member.getId());

        // when
        storeService.deleteStar(star.getMemberId(), star.getId());

        // then
        List<Star> stars = starRepository.findAll();
        assertThat(stars).hasSize(1);
        assertThat(stars.get(0).getStatus()).isEqualTo(StarStatus.DELETE);
    }

    @Test
    void 내가_즐겨찾기_한_가게들을_가져온다() {
        // given
        Store store = createStore(boss);
        Long memberId = 199L;
        Star star = createStar(store, memberId);

        // when
        storeService.getMyStars(memberId);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).hasSize(1);

        List<Star> stars = starRepository.findAll();
        assertThat(stars).hasSize(1);
        assertThat(stars.get(0).getMemberId()).isEqualTo(memberId);
        assertThat(stars.get(0).getId()).isEqualTo(star.getId());
        assertThat(stars.get(0).getStore().getId()).isEqualTo(store.getId());

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

    private Star createStar(Store store, Long memberId) {
        return starRepository.save(Star.of(store, memberId));

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

}
