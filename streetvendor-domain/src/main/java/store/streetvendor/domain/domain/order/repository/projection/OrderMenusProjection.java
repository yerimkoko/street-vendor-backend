package store.streetvendor.domain.domain.order.repository.projection;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderMenusProjection {
    private final long menuId;
    private final String menuName;
    private final int menuPrice;

    @QueryProjection
    public OrderMenusProjection(Long menuId, String menuName, int menuPrice) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

}
