package store.streetvendor.core.domain.store;


public enum Rate {
    one(1),
    two(2),
    three(3),
    four(4),
    five(5);

    private final int value;

    Rate(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}