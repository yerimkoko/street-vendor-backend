package store.streetvendor.core.domain.store;


import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Rate {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

    Rate(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public static Rate valueOfRate(int rate) {
        return Arrays.stream(values())
            .filter(value -> value.name().equals(String.valueOf(rate)))
            .findAny()
            .orElse(null);
    }

    private static final Map<Integer, String> CODE_MAP = Collections.unmodifiableMap(
        Stream.of(values())
            .collect(Collectors.toMap(Rate::getValue, Rate::name)));

    public static Rate of(final Integer value) {
        return Rate.valueOf(CODE_MAP.get(value));
    }

}
