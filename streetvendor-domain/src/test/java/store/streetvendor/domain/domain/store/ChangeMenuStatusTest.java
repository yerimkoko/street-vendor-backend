package store.streetvendor.domain.domain.store;

import org.junit.jupiter.api.Test;
import store.streetvendor.domain.domain.store.menu.Menu;
import store.streetvendor.domain.domain.store.menu.MenuSalesStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChangeMenuStatusTest {

    @Test
    void 메뉴의_상태가_변한다() {
        // given
        MenuSalesStatus soldOut = MenuSalesStatus.SOLD_OUT;
        Store store = createStore();
        Menu menu = createMenu(store);

        // when
        menu.changeMenuStatus(soldOut);

        // then
        assertThat(menu.getSalesStatus()).isEqualTo(soldOut);

    }

    @Test
    void 메뉴의_요청상태와_기존상태가_같을때() {
        // given
        MenuSalesStatus sales = MenuSalesStatus.ON_SALE;
        Store store = createStore();
        Menu menu = createMenu(store);

        // when & then
        assertThatThrownBy(() -> menu.changeMenuStatus(sales))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private Store createStore() {
        Location location = new Location(24.2222, 33.2222);
        return Store.newSalesStore(999L, "붕어빵", location, "안뇽", "ㅋㅋ", StoreCategory.BUNG_EO_PPANG);
    }

    private Menu createMenu(Store store) {
        return Menu.of(store, "붕어빵", 2, 1000, "33.dd");
    }
}
