package store.streetvendor.domain.domain.store;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentMethod {

    ACCOUNT_TRANSFER("계좌이체"),
    CASH("현금결제"),
    ;

    private final String description;

}
