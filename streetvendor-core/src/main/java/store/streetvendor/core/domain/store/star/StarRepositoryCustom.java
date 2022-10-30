package store.streetvendor.core.domain.store.star;


import java.util.List;

public interface StarRepositoryCustom {

    Star findByStarId(Long starId);

    List<Star> findMyStars(Long memberId);

}
