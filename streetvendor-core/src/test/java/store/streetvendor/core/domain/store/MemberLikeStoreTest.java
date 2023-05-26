package store.streetvendor.core.domain.store;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberLikeStoreTest {

    @Test
    void 내가_좋아한_가게를_취소한다() {
        // given
        Long memberId = 999L;
        Store store = Store.newSalesStore(1L, "name", new Location(23.333, 33.3), "storeDescription", "locationDescription", StoreCategory.TAKOYAKI, new BankInfo(BankType.WOORI, "1002-222-2222"));
        MemberLikeStore memberLikeStore = MemberLikeStore.newInstance(memberId, store);

        // when
        memberLikeStore.delete();

        // then
        assertThat(memberLikeStore.getStatus()).isEqualTo(MemberLikeStoreStatus.INACTIVE);
    }


}
