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

    public StoreDistanceRequest(Double latitude, Double longitude, Double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = checkDistance(distance);
    }

    private Double checkDistance(Double distance) {
        distance /= 1000;
        if (distance > DISTANCE_LIMIT) {
            return DISTANCE_LIMIT;
        }
        return distance;
    }
}
