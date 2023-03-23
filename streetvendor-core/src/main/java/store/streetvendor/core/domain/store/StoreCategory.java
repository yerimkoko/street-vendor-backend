package store.streetvendor.core.domain.store;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StoreCategory {
    BUNG_EO_PPANG("붕어빵"),
    TAKOYAKI("타코야끼"),
    TTEOK_BOKKI("떡볶이"),
    HO_DDEOK("호떡"),
    EGG_BREAD("계란빵"),
    SUNDAE("순대"),
    OTHER_MEAL("기타 식사류"),
    OTHER_DESSERT("기타 간식")
    ;

    private final String description;

    public String getDescription() {
        return this.description;
    }

}
