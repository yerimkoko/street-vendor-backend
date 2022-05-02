package store.streetvendor.domain.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import store.streetvendor.domain.domain.store.repository.StoreRepositoryCustom;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom, PagingAndSortingRepository<Store, Long> {

    @Query(value = "SELECT *, (" +
        "    6371 * acos (" +
        "      cos ( radians( :latitude ) )  " +
        "      * cos( radians( latitude ) )" +
        "      * cos( radians( longitude ) - radians( :longitude ) )" +
        "      + sin ( radians( :latitude ) )" +
        "      * sin( radians( latitude ) )" +
        "    )" +
        "  ) AS distance" +
        "  FROM store" +
        "  GROUP BY id" +
        "  HAVING distance < :distance" +
        "  ORDER BY distance", nativeQuery = true)

    List<Store> findByLocationAndDistanceLessThan(@Param("latitude") final Double latitude,
                                                  @Param("longitude") final Double longitude,
                                                  @Param("distance") final Double distance);

    @Query(value = "SELECT *, (" +
        "    6371 * acos (" +
        "      cos ( radians( :latitude ) )  " +
        "      * cos( radians( latitude ) )" +
        "      * cos( radians( longitude ) - radians( :longitude ) )" +
        "      + sin ( radians( :latitude ) )" +
        "      * sin( radians( latitude ) )" +
        "    )" +
        "  ) AS distance" +
        "  FROM store" +
        "  ORDER BY distance" +
        "  LIMIT 0 , 5", nativeQuery = true)
    List<Store> findAllByAddress(@Param("latitude") final Double latitude,
                                 @Param("longitude") final Double longitude);

    @Query(value = "SELECT *, (" +
        "    6371 * acos(" +
        "      cos( radians( :latitude ) )  " +
        "      * cos( radians( latitude ) )" +
        "      * cos( radians( longitude ) - radians( :longitude ) )" +
        "      + sin( radians( :latitude ) )" +
        "      * sin( radians( latitude ) )" +
        "    )" +
        "  ) AS distance" +
        "  FROM store s" +
        "  WHERE s.category = :category" +
        "  GROUP BY s.id" +
        "  HAVING distance >= :radiusStart AND distance < :radiusEnd" +
        "  ORDER BY distance", nativeQuery = true)
    List<Store> findByDistanceBetweenAndCategory(@Param("latitude") final Double latitude,
                                                 @Param("longitude") final Double longitude,
                                                 @Param("radiusStart") final Double distanceStart,
                                                 @Param("radiusEnd") final Double distanceEnd,
                                                 @Param("category") final String category);


}
