package store.streetvendor.core.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;

@NoArgsConstructor
@Getter
public class ConvertUtil {

    public static double getAverageEvaluation(Store store) {
        return store.getReviews().stream()
            .mapToDouble(review -> review.getGrade().getValue()).sum() / store.getReviews().size();

    }


}
