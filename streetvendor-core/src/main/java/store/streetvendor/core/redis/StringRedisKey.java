package store.streetvendor.core.redis;

import java.time.Duration;

public interface StringRedisKey<K, V> {

    String getKey();

    V deserializeValue(String value);

    String serializeValue(V value);

    Duration getTtl();

}
