package store.streetvendor.core.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class StringRedisRepositoryImpl<K extends StringRedisKey<K, V>, V> implements StringRedisRepository<K, V> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public V get(K k) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return k.deserializeValue(operations.get(k.getKey()));
    }

    @Override
    public List<V> getBulk(List<K> keys) {
        if (keys.isEmpty()) {
            return new ArrayList<>();
        }
        K k = keys.get(0);

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        List<String> values = operations.multiGet(keys.stream()
            .map(K::getKey)
            .collect(Collectors.toList())
        );

        if (values == null) {
            return Collections.emptyList();
        }

        return values.stream()
            .map(k::deserializeValue)
            .collect(Collectors.toList());
    }

    @Override
    public void set(K k, V v) {
        setWithTtl(k, v, k.getTtl());
    }

    @Override
    public void setBulk(Map<K, V> keyValues) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        redisTemplate.executePipelined((RedisCallback<Object>) pipeline -> {
            keyValues.forEach((k, v) -> operations.set(k.getKey(), String.valueOf(v)));
            return null;
        });
    }

    @Override
    public void setWithTtl(K k, V v, Duration ttl) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        if (ttl == null) {
            operations.set(k.getKey(), k.serializeValue(v));
            return;
        }
        operations.set(k.getKey(), k.serializeValue(v), ttl.getSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void incr(K k) {
        incrBy(k, 1);
    }

    @Override
    public void incrBulk(List<K> keys) {
        redisTemplate.executePipelined((RedisCallback<Object>) pipeline -> {
            keys.forEach(k -> pipeline.incr(k.getKey().getBytes(StandardCharsets.UTF_8)));
            return null;
        });
    }

    @Override
    public void incrBy(K k, long value) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.increment(k.getKey(), value);
    }

    @Override
    public void decr(K k) {
        decrBy(k, 1);
    }

    @Override
    public void decrBulk(List<K> ks) {
        redisTemplate.executePipelined((RedisCallback<Object>) pipeline -> {
            ks.forEach(k -> pipeline.decr(k.getKey().getBytes(StandardCharsets.UTF_8)));
            return null;
        });
    }

    @Override
    public void decrBy(K k, long value) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.decrement(k.getKey(), value);
    }

    @Override
    public void del(K k) {
        redisTemplate.delete(k.getKey());
    }

    @Override
    public void delBulk(List<K> ks) {
        Set<String> keyStrings = ks.stream()
            .map(K::getKey)
            .collect(Collectors.toSet());
        redisTemplate.delete(keyStrings);
    }

}
