package store.streetvendor.domain.domain.store.menu;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MenuSalesStatus {

    ON_SALE("판매중"),
    SOLD_OUT("품절"),
    ;

    private final String description;

}
