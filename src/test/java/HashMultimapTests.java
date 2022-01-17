import multimap.HashMultimap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashMultimapTests {
    static List<Integer> correctValues = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    static List<String> correctKeys = List.of("123", "456", "789");
    static HashMultimap<String, Integer> testMap;
    static Set<Map.Entry<String, Integer>> correctEntries = new HashSet<>();

    @BeforeAll
    static void setupMaps() {
        testMap = new HashMultimap<>(10);

        for (int v = 0; v < correctValues.size(); v++) {
            testMap.put(correctKeys.get(v / 3), correctValues.get(v));
            correctEntries.add(new AbstractMap.SimpleEntry<>(correctKeys.get(v / 3), correctValues.get(v)));
        }
    }

    @Test
    public void entrySetTest() {
        for (Map.Entry<String, Integer> correctEntry : correctEntries) {
            assertTrue(testMap.entrySet().stream()
                    .anyMatch(stringIntegerEntry ->
                            Objects.equals(stringIntegerEntry.getKey(), correctEntry.getKey())
                            && Objects.equals(stringIntegerEntry.getValue(), correctEntry.getValue())));
        }

    }
}
