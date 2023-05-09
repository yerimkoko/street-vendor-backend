package store.streetvendor.core.domain.review.reviewcount;

public interface ReviewCountRepository {

    void incrByCount(Long storeId);

    long getValueByKey(Long storeId);

}
