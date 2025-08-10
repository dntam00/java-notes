package collection;

import java.util.Map;

public class HashMap {

    public static void main(String[] args) {
        Map<Long, Long> map = new java.util.HashMap<>();
        map.put(null, null);
        System.out.printf("Map size: %d%n", map.size());
        Long l = map.get(null);
        // hash of null key is 0 -> it is stored in the first bucket
        System.out.printf("Value for null key: %s%n", l);
    }
}
