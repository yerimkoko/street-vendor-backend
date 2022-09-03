package store.streetvendor.domain.domain.store;


public enum Grade {
    one(1),
    two(2),
    three(3),
    four(4),
    five(5);

    private final int value;

    Grade(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}
