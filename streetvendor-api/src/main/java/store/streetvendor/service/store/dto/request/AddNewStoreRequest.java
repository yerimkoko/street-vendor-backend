package store.streetvendor.service.store.dto.request;

import lombok.*;
import store.streetvendor.domain.domain.store.PaymentMethod;
import store.streetvendor.domain.domain.store.Store;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddNewStoreRequest {

    @NotBlank
    private String name;

    private String pictureUrl;

    private String location;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private List<MenuRequest> menus;

    private List<PaymentMethod> paymentMethods;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public AddNewStoreRequest(@NotBlank String name, String pictureUrl, String location, @NotNull LocalTime startTime,
                              @NotNull LocalTime endTime, List<MenuRequest> menus, List<PaymentMethod> paymentMethods) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.menus = menus;
        this.paymentMethods = paymentMethods;
    }

    public Store toEntity(Long memberId) {
        Store store = Store.newInstance(memberId, name, pictureUrl, location, startTime, endTime);
        store.addMenus(this.menus.stream()
            .map(menu -> menu.toEntity(store))
            .collect(Collectors.toList()));
        store.addPayments(paymentMethods);
        return store;
    }

}
