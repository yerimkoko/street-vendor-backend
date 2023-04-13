package store.streetvendor.core.domain.review.reviewcount;

import lombok.Builder;
import lombok.Getter;
import store.streetvendor.core.redis.StringRedisKey;
import store.streetvendor.core.utils.JsonUtils;

import java.time.Duration;

@Getter
public class ReviewCountKey implements StringRedisKey<ReviewCountKey, Long> {

    private final Long storeId;

    @Builder
    public ReviewCountKey(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String getKey() {
        return "storeId : " + storeId;
    }

    @Override
    public Long deserializeValue(String value) {
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("역직렬화 중 에러가 발생하였습니다. value: (%s)", value));
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
