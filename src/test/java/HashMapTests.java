import map.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

public class HashMapTests {
    static List<String> correctValues = List.of("zero", "one", "two", "three", "four");
    static List<Integer> correctKeys = List.of(0, 1, 2, 3, 4);
    static HashMap<Integer, String> testMap;
    static Map<Integer, String> correctMap = new java.util.HashMap<>();

    @BeforeAll
    static void setupMaps() {
        testMap = new HashMap<>(10);
        correctMap = new java.util.HashMap<>();
        for (int n = 0; n < correctValues.size(); n++) {
            testMap.put(correctKeys.get(n), correctValues.get(n));
            correctMap.put(correctKeys.get(n), correctValues.get(n));
        }
    }

    @Test
    public void putGetTest() {
        for (int n = 0; n < correctValues.size(); n++) {
            assertEquals(correctMap.get(n), testMap.get(n));
        }
    }

    @Test
    public void entrySetTest() {
        assertEquals(correctMap.entrySet(), testMap.entrySet());
    }

    @Test
    public void keySetTest() {
        assertEquals(correctMap.keySet(), testMap.keySet());
    }

    @Test
    public void valuesTest() {
        assertArrayEquals(correctMap.values().toArray(), testMap.values().toArray());
    }
}
