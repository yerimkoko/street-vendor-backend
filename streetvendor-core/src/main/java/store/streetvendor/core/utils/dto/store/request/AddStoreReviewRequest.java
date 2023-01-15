package store.streetvendor.core.utils.dto.store.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Rate;

@NoArgsConstructor
@Getter
public class AddStoreReviewRequest {

    private Rate rate;

    private String comment;

    @Builder
    public AddStoreReviewRequest(Rate rate, String comment) {
        this.rate = rate;
        this.comment = comment;
    }

}
