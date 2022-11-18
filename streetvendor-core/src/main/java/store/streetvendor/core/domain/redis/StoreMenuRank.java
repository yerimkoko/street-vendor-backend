package store.streetvendor.core.domain.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.menu.Menu;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@RedisHash(value = "store_menu_rank")
public class StoreMenuRank {

    @Id
    private Long id;

    private Long storeId;

    private Long memberId;

    private Long menuId;

    private LocalDateTime createTime;

    @Builder
    public StoreMenuRank(Long id, Long storeId, Long memberId, Long menuId, LocalDateTime createTime) {
        this.id = id;
        this.storeId = storeId;
        this.memberId = memberId;
        this.menuId = menuId;
        this.createTime = createTime;
    }

    public static StoreMenuRank newStoreMenu(Store store, Member member, Long menuId) {
        return StoreMenuRank.builder()
            .menuId(menuId)
            .storeId(store.getId())
            .memberId(member.getId())
            .createTime(LocalDateTime.now())
            .build();
    }


}
