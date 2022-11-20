package store.streetvendor.core.domain.store.star;


import java.util.List;

public interface StarRepositoryCustom {

    Star findByStarIdAndMemberId(Long starId, Long memberId);

    List<Star> findMyStars(Long memberId);

}
