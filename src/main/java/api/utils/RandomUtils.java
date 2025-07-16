package api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {

    public static <T> T selectRandomItem(List<T> list) {
        List<T> newList = new ArrayList<>(list);
        Collections.shuffle(newList);
        return newList.getFirst();
    }
}
