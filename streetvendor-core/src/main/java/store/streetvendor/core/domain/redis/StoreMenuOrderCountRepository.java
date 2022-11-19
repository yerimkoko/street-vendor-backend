package store.streetvendor.core.domain.redis;

public interface StoreMenuOrderCountRepository {

    void increaseByCount(Long storeId, Long menuId, int count);

    Long getCount(Long storeId, Long menuId);



}
