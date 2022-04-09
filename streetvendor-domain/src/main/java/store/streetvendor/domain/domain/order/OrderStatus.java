package store.streetvendor.domain.domain.order;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {
    REQUEST("유저가 주문을 할 때"),
    PREPARING("사장님이 주문을 승인할 때(조리를 한다)"),
    READY_TO_PICK_UP("사장님이 조리를 완료한 후 유저에게 완료되었음을 알려줌");

    public boolean canChangeToReady() {
        return this == OrderStatus.REQUEST;
    }

    public boolean cantCancelOrder() {
        return this == OrderStatus.READY_TO_PICK_UP;
    }

    public boolean cantUserCancelOrder() {
        return this == OrderStatus.PREPARING;
    }

    private final String description;

}
