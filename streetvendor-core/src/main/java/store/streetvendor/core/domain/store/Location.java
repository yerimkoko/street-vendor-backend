package store.streetvendor.core.domain.store;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class Location {

    private Double latitude;

    private Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Location location = (Location) o;
        return Objects.equals(latitude, location.getLatitude()) && Objects.equals(longitude, location.getLongitude());

    }

    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

}
