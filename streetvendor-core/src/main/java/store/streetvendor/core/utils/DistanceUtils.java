package store.streetvendor.core.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DistanceUtils {

    private static final int EARTH_RADIUS = 6371;

    public static double getDistance(double storeLatitude, double currentLatitude, double storeLongitude, double currentLongitude) {
        double distanceLatitude = Math.toRadians(storeLatitude - currentLatitude);
        double distanceLongitude = Math.toRadians(storeLongitude - currentLongitude);
        double a = Math.sin(distanceLatitude / 2) * Math.sin(distanceLatitude / 2) +
            Math.cos(Math.toRadians(currentLatitude)) * Math.cos(Math.toRadians(storeLatitude)) *
                Math.sin(distanceLongitude / 2) * Math.sin(distanceLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c * 1000;
    }
}
