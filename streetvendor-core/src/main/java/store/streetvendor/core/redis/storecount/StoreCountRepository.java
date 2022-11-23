package store.streetvendor.core.redis.storecount;

public interface StoreCountRepository{

    void incrByCount(Long storeId);

    Long getValueByKey(StoreCountKey key);
}
