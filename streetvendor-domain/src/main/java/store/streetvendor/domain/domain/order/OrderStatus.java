package store.streetvendor.domain.domain.order;

public enum OrderStatus {
    REQUEST,
    READY,
    COMPLETE;

    public boolean canChangeToReady() {
        return this == OrderStatus.REQUEST;
    }

}
