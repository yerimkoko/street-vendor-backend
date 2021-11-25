package store.streetvendor.controller.dto.store;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class StoreUpdateRequest {
    private String name;

    private String pictureUrl;

    private String location;

    private String description;

    private LocalTime startTime;

    private LocalTime endTime;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public StoreUpdateRequest(String name, String pictureUrl, String location, String description, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
