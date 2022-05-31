package store.streetvendor.domain.domain.store;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChangeMenuStatusTest {

    @Test
    void 메뉴의_상태가_변한다() {
        // given
        Location location = new Location(24.2222, 33.2222);
        MenuSalesStatus soldOut = MenuSalesStatus.SOLD_OUT;

        Store store = Store.newSalesStore(999L, "붕어빵", "23.dd", location, "안뇽", "ㅋㅋ", StoreCategory.BUNG_EO_PPANG);

        Menu menu = Menu.of(store, "koko", 2, 1000, "33333");

        // when
        menu.changeMenuStatus(soldOut);

        // then
        assertThat(menu.getSalesStatus()).isEqualTo(soldOut);

    }

    @Test
    void 메뉴의_요청상태와_기존상태가_같을때() {
        // given
        Location location = new Location(24.2222, 33.2222);
        MenuSalesStatus sales = MenuSalesStatus.ON_SALE;

        Store store = Store.newSalesStore(999L, "붕어빵", "23.dd", location, "안뇽", "ㅋㅋ", StoreCategory.BUNG_EO_PPANG);

        Menu menu = Menu.of(store, "붕어빵", 2, 1000, "33.dd");

        // when & then
        assertThatThrownBy(() -> menu.changeMenuStatus(sales))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
