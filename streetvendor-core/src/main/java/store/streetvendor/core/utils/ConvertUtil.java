package store.streetvendor.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertUtil {

    public static String countMenu(String menuName, int count) {
        return menuName + "외 " + (count - 1) + "개";

    }
}
