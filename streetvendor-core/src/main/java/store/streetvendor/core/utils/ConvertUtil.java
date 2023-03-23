package store.streetvendor.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertUtil {

    public static String countMenu(String menuName, int count) {
        return menuName + "외 " + (count - 1) + "개";

    }

//    public static String getAverageSpoon(Store store) {
//        if (store.getReviews() == null) {
//            return "아직 리뷰가 없어요";
//        }
//
//        long total = store.getReviews().stream()
//            .mapToLong(review -> Review.getGradeValue(review.getRate()))
//            .sum();
//
//        return String.format("%.1f", total / (double)store.getReviews().size());
//    }
}
