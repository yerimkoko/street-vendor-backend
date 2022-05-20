package store.streetvendor.service.store.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.StoreSalesStatus;

@Getter
@NoArgsConstructor
public class StoreDistanceRequest {

    private static final double DISTANCE_LIMIT = 2;

    private Double latitude;

    private Double longitude;

    private Double distance;

    private String salesStatus;

    public StoreDistanceRequest(Double latitude, Double longitude, Double distanceKm, String salesStatus) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = checkDistance(distanceKm);
        this.salesStatus = salesStatus(salesStatus);
    }

    private Double checkDistance(Double distanceKm) {
        if (distanceKm > DISTANCE_LIMIT) {
            return DISTANCE_LIMIT;
        }
        return distanceKm;
    }

    private String salesStatus(String salesStatus) {
        return "'" + salesStatus + "'";
    }
}
