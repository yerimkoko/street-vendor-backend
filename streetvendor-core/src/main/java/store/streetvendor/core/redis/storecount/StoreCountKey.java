package store.streetvendor.core.redis.storecount;


import lombok.Builder;
import lombok.Getter;
import store.streetvendor.core.redis.StringRedisKey;
import store.streetvendor.core.utils.JsonUtils;

import java.time.Duration;

@Getter
public class StoreCountKey implements StringRedisKey<StoreCountKey, Long> {

    private final Long storeId;

    @Builder
    public StoreCountKey(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String getKey() {
        return "storeId:v1:" + storeId;
    }

    @Override
    public Long deserializeValue(String value) {
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("역직렬화중 에러가 발생하였습니다. value: (%s)", value));
        }
    }

    @Override
    public String serializeValue(Long value) {
        return JsonUtils.toJson(value);
    }

    @Override
    public Duration getTtl() {
        return null;
    }
}
