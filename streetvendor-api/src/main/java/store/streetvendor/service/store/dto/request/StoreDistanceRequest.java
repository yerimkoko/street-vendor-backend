package store.streetvendor.service.store.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreDistanceRequest {

    private static final double DISTANCE_LIMIT = 2;

    private Double latitude;

    private Double longitude;

    private Double distance;

    public StoreDistanceRequest(Double latitude, Double longitude, Double distanceKm) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = checkDistance(distanceKm);
    }

    private Double checkDistance(Double distanceKm) {
        if (distanceKm > DISTANCE_LIMIT) {
            return DISTANCE_LIMIT;
        }
        return distanceKm;
    }
}
