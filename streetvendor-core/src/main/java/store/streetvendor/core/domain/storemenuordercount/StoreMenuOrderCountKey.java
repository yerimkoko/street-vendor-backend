package store.streetvendor.core.domain.storemenuordercount;

import lombok.Builder;
import lombok.Getter;
import store.streetvendor.core.redis.StringRedisKey;
import store.streetvendor.core.utils.JsonUtils;

import java.time.Duration;

@Getter
public class StoreMenuOrderCountKey implements StringRedisKey<StoreMenuOrderCountKey, Long> {

    private static final long DEFAULT_VALUE = 0L;

    private final Long storeId;

    private final Long menuId;

    @Builder
    private StoreMenuOrderCountKey(Long storeId, Long menuId) {
        this.storeId = storeId;
        this.menuId = menuId;
    }



    // store:v1:{{STORE_ID}}:menu:{{MENU_ID}}
    @Override
    public String getKey() {
        return "store:v1:" + storeId + ":" + "menu:" + menuId;
    }

    @Override
    public Long deserializeValue(String value) {
        if (value == null) {
            return DEFAULT_VALUE;
        }
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
