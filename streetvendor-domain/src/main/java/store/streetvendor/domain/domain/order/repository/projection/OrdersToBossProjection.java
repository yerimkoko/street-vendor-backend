package store.streetvendor.domain.domain.order.repository.projection;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
public class OrdersToBossProjection {

    private Long memberId;

    private Long orderId;

    private OrderStatus orderStatus;

    private List<OrderMenu> orderMenus;

    private LocalDateTime createTime;

    @QueryProjection
    public OrdersToBossProjection(Long memberId, Long orderId, OrderStatus orderStatus, List<OrderMenu> orderMenus, LocalDateTime createTime) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderMenus = orderMenus;
        this.createTime = createTime;
    }
}
