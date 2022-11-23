package store.streetvendor.core.domain.storemenuordercount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.streetvendor.core.redis.StringRedisRepository;

@RequiredArgsConstructor
@Repository
public class StoreMenuOrderCountRepositoryImpl implements StoreMenuOrderCountRepository {

    private final StringRedisRepository<StoreMenuOrderCountKey, Long> stringRedisRepository;

    @Override
    public void increaseByCount(Long storeId, Long menuId, int count) {
        StoreMenuOrderCountKey key = StoreMenuOrderCountKey.builder()
            .storeId(storeId)
            .menuId(menuId)
            .build();
        stringRedisRepository.incrBy(key, count);
    }

    @Override
    public Long getCount(Long storeId, Long menuId) {
        StoreMenuOrderCountKey key = StoreMenuOrderCountKey.builder()
            .storeId(storeId)
            .menuId(menuId)
            .build();
        return stringRedisRepository.get(key);
    }
}
