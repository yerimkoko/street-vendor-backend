package store.streetvendor.core.domain.store;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberLikeStoreStatus {
    ACTIVE("활성화"),
    INACTIVE("비활성화")
    ;
    private final String description;

    public String getDescription() {
        return this.description;
    }
}
