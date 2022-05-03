package store.streetvendor.domain.domain.store;

public enum StoreSalesStatus {
    OPEN,
    CLOSED;

    public boolean canChangeToOpen() {
        return this == StoreSalesStatus.CLOSED;
    }

    public boolean canChangeToClosed() {
        return this == StoreSalesStatus.OPEN;
    }

}
