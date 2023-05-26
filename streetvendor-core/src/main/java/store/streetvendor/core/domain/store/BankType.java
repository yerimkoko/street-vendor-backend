package store.streetvendor.core.domain.store;

import lombok.Getter;

@Getter
public enum BankType {

    WOORI("우리은행"),
    SHINHAN("신한은행"),
    ;
    private final String description;

    BankType(String description) {
        this.description = description;
    }

}
