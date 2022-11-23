package store.streetvendor.core.domain.storemenuordercount;

public interface StoreMenuOrderCountRepository {

    void increaseByCount(Long storeId, Long menuId, int count);

    Long getCount(Long storeId, Long menuId);



}
