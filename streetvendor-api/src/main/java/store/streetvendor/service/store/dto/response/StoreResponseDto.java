package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import store.streetvendor.domain.domain.store.Menu;
import store.streetvendor.domain.domain.store.Payment;
import store.streetvendor.domain.domain.store.PaymentMethod;
import store.streetvendor.domain.domain.store.Store;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class StoreResponseDto {

    private Long storeId;

    private Long bossId;

    private String name;

    private String pictureUrl;

    private String location;

    private String description;

    private LocalTime startTime;

    private LocalTime endTime;

    private List<PaymentMethod> paymentMethods;

    private List<MenuResponse> menus;

    @Builder
    public StoreResponseDto(Long storeId, Long bossId, String name, String pictureUrl, String location, String description,
                            LocalTime startTime, LocalTime endTime, List<PaymentMethod> paymentMethods, List<MenuResponse> menus) {
        this.storeId = storeId;
        this.bossId = bossId;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.paymentMethods = paymentMethods;
        this.menus = menus;
    }

    public static StoreResponseDto of(Store store) {
        return StoreResponseDto.builder()
            .storeId(store.getId())
            .bossId(store.getMemberId())
            .name(store.getName())
            .pictureUrl(store.getPictureUrl())
            .location(store.getLocation())
            .description(store.getDescription())
            .startTime(store.getOpeningTime().getStartTime())
            .endTime(store.getOpeningTime().getEndTime())
            .paymentMethods(store.getPaymentMethods().stream()
                .map(Payment::getPaymentMethod)
                .collect(Collectors.toList()))
            .menus(getMenuList(store))
            .build();
    }

    private static List<MenuResponse> getMenuList(Store store) {
        return store.getMenus().stream()
            .map(MenuResponse::of)
            .collect(Collectors.toList());

    }
}
