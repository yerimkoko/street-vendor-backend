package store.streetvendor.domain.domain.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import store.streetvendor.domain.domain.store.Store;

import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreByStoreId(Long id);

    Store findStoreByStoreIdAndMemberId(Long id, Long memberId);

    List<Store> findStoreByBossId(Long bossId);
}
