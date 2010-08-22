package de.repower.android.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionsUtil {

    public static <T> List<T> list(T... args) {
        List<T> result = new ArrayList<T>();
        result.addAll(Arrays.asList(args));
        return result;
    }
}
