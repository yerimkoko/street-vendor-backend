package store.streetvendor.service.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PopularStoresAndMenusResponse {

    private List<PopularStoresResponse> popularStoresResponses;

    private List<PopularFoodResponse> popularFoodResponses;

    public PopularStoresAndMenusResponse(List<PopularStoresResponse> popularStoresResponses, List<PopularFoodResponse> popularFoodResponses) {
        this.popularStoresResponses = popularStoresResponses;
        this.popularFoodResponses = popularFoodResponses;
    }

}
