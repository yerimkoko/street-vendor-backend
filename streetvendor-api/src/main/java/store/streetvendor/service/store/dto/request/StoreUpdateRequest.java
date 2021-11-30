package store.streetvendor.service.store.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Menu;
import store.streetvendor.domain.domain.store.Payment;
import store.streetvendor.domain.domain.store.PaymentMethod;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class StoreUpdateRequest {
    private String name;

    private String pictureUrl;

    private String location;

    private String description;

    private LocalTime startTime;

    private LocalTime endTime;

    private List<Menu> menus;

    private List<PaymentMethod> paymentMethods;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public StoreUpdateRequest(String name, String pictureUrl, String location, String description,
                              LocalTime startTime, LocalTime endTime, List<Menu> menus, List<PaymentMethod> paymentMethods) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.menus = menus;
        this.paymentMethods = paymentMethods;
    }

}
