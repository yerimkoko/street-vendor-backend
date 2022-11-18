package store.streetvendor.service.redis.storemenurank;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.redis.StoreMenuRank;
import store.streetvendor.core.domain.redis.StoreMenuRankRepository;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.service.store.SetUpStore;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StoreMenuRankTest extends SetUpStore {

    @Autowired
    private StoreMenuRankRepository storeMenuRankRepository;

    @AfterEach
    public void cleanUp() {
        storeMenuRankRepository.deleteAll();
    }

    @Test
    public void Rank_를_등록하기() {
        // given
        Long rankId = 1L;
        Menu menu = Menu.of(store, "붕어빵", 3, 2000, "dfdf");
        LocalDateTime createTime = LocalDateTime.of(2022, 11, 18, 0, 0);

        StoreMenuRank storeMenuRank = StoreMenuRank.builder()
            .id(rankId)
            .storeId(store.getId())
            .menuId(menu.getId())
            .createTime(createTime)
            .memberId(member.getId())
            .build();

        // when
        storeMenuRankRepository.save(storeMenuRank);

        // then
        StoreMenuRank findStoreMenuRank = storeMenuRankRepository.findById(rankId).get();
        assertThat(findStoreMenuRank.getStoreId()).isEqualTo(store.getId());
        assertThat(findStoreMenuRank.getMenuId()).isEqualTo(menu.getId());
        assertThat(findStoreMenuRank.getCreateTime()).isEqualTo(createTime);
        assertThat(findStoreMenuRank.getMemberId()).isEqualTo(member.getId());

    }
}
