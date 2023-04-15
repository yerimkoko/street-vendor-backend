package store.streetvendor.core.domain.store;

import java.util.List;

public interface MemberLikeStoreCustom {

    List<MemberLikeStore> findByMemberId(Long memberId, Integer cursor, int size);

    MemberLikeStore findLikeStoreByMemberIdAndStoreId(Long memberId, Long storeId);

}
