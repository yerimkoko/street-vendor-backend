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
        "  WHERE store.status = 'ACTIVE' AND store.sales_status = 'OPEN' " +
        "  GROUP BY id" +
        "  HAVING distance < :distance" +
        "  ORDER BY distance", nativeQuery = true)
    List<Store> findOpenedStoreByLocationAndDistanceLessThan(
        @Param("latitude") final Double latitude,
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
        "  WHERE store.status = 'ACTIVE' AND store.sales_status = 'OPEN' AND store.id = :storeId" +
        "  GROUP BY id" +
        "  HAVING distance < :distance" +
        "  ORDER BY distance", nativeQuery = true)
    Store findOpenedStoreByStoreIdAndLocationAndDistanceLessThan(
        @Param("storeId") final long storeId,
        @Param("latitude") final Double latitude,
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
        "  WHERE store.status = 'ACTIVE' " +
        "  GROUP BY id" +
        "  HAVING distance < :distance" +
        "  ORDER BY distance", nativeQuery = true)
    List<Store> findAllStoresByLocationAndDistanceLessThan(@Param("latitude") final Double latitude,
                                                           @Param("longitude") final Double longitude,
                                                           @Param("distance") final Double distance);


}
