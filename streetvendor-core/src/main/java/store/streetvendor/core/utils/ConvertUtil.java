package store.streetvendor.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.review.Review;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertUtil {

    public static String countMenu(String menuName, int count) {
        return menuName + "외 " + (count - 1) + "개";

    }

    public static String getAverageSpoon(Store store) {
        long total = store.getReviews().stream()
            .mapToLong(review -> Review.getGradeValue(review.getRate()))
            .sum();

        return String.format("%.1f", total / (double)store.getReviews().size());
    }
}
