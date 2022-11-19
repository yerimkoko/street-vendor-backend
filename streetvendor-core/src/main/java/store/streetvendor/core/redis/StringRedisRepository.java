package store.streetvendor.core.redis;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface StringRedisRepository<K extends StringRedisKey<K, V>, V> {

    V get(K k);

    List<V> getBulk(List<K> ks);

    void set(K k, V v);

    void setBulk(Map<K, V> keyValues);

    void setWithTtl(K k, V v, Duration ttl);

    void incr(K k);

    void incrBulk(List<K> ks);

    void incrBy(K k, long value);

    void decr(K k);

    void decrBulk(List<K> ks);

    void decrBy(K k, long value);

    void del(K k);

    void delBulk(List<K> ks);

}
