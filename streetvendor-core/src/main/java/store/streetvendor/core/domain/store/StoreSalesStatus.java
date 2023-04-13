package store.streetvendor.core.domain.store;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StoreSalesStatus {
    OPEN("영업중"),
    CLOSED("영업종료");

    private final String description;

    public String getDescription() {
        return this.description;
    }


}
