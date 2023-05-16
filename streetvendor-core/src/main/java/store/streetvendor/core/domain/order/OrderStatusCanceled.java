package store.streetvendor.core.domain.order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatusCanceled {
    ACTIVE("주문진행중"),
    CANCELED("주문취소"),
    DONE("주문완료")
    ;

    public final String description;
}
