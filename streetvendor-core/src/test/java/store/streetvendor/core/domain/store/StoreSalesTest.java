package store.streetvendor.core.domain.store;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StoreSalesTest {

    @Test
    void 가게를_영업시킨다() {
        // given
        Long memberId = 999L;
        Location location = new Location(33.2222, -128.333522);
        Store store = Store.newInstance(memberId, "참붕어빵", location, "모나카 맛집", "당정역 1번 출구 앞", StoreCategory.OTHER_MEAL);

        // when
        store.changeSalesStatus(StoreSalesStatus.OPEN);

        // then
        assertThat(store.getSalesStatus()).isEqualTo(StoreSalesStatus.OPEN);
    }

    @Test
    void 가게를_종료시킨다() {
        // given
        Long memberId = 999L;
        Location location = new Location(33.2222, -128.333522);

        Store store = Store.builder()
            .memberId(memberId)
            .salesStatus(StoreSalesStatus.OPEN)
            .location(location)
            .name("참 붕어빵")
            .build();

        // when
        store.changeSalesStatus(StoreSalesStatus.CLOSED);

        // then
        assertThat(store.getSalesStatus()).isEqualTo(StoreSalesStatus.CLOSED);
    }



}
