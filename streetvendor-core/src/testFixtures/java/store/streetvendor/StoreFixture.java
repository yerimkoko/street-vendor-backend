package store.streetvendor;

import store.streetvendor.core.domain.store.Location;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreCategory;

public class StoreFixture extends MemberFixture {

    public static Store store() {
        String name = "뽀미네 가게";

        Location location = new Location(33.3333, 127.33);

        String storeDescription = "뽀미가 네발로 정성껏 만들었어요";

        String locationDescription = "뽀미의 분홍색 집을 찾아보세요!";

        StoreCategory storeCategory = StoreCategory.BUNG_EO_PPANG;

        return Store.newSalesStore(boss().getId(), name, location, storeDescription, locationDescription, storeCategory);
    }


}
