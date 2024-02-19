package map;
import static org.junit.jupiter.api.Assertions.*;

import map.SeparateChainingHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSeparateChainingHashMap {

    private SeparateChainingHashMap<String, Integer> map;

    @BeforeEach
    public void setup() {
        map = new SeparateChainingHashMap<>();
    }

    @Test
    public void testPutAndGet() {
        assertNull(map.get("java"));
        map.put("java", 120);
        assertEquals(120, map.get("java").intValue());
    }

    @Test
    public void testUpdateValue() {
        map.put("java", 11);
        map.put("java", 92);
        assertEquals(92, map.get("java").intValue());
    }

    @Test
    public void testContains() {
        assertFalse(map.contains("java"), "Map should not contain java initially");

        map.put("java", 100);
        assertTrue(map.contains("java"));

        map.remove("java");
        assertFalse(map.contains("java"));
    }


    @Test
    public void testRemove() {
        SeparateChainingHashMap<String, Integer> map = new SeparateChainingHashMap<>();


        map.put("java", 12);

        assertEquals(12, map.get("java"));

        map.remove("java");

        assertNull(map.get("java"));
    }


    @Test
    public void testSizeAndIsEmpty() {
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());

        map.put("javaFX", 17);
        assertFalse(map.isEmpty());
        assertEquals(1, map.size());
    }




    @Test
    public void testKeySet() {
        for (int i = 0; i < 39; i++) {
            map.put("HTML" + i, i);
        }
        assertEquals(39, map.keySet().size());
        for (int i = 0; i < 39; i++) {
            assertTrue(map.keySet().contains("HTML" + i));
        }
    }

    @Test
    public void testRemapFunctionAfterInsertionOfElementsAboveTheSetCapacity() {
        // Here i add enough entries to exceed the default capacity with the given load factor
        // 10 * 0.75 = 7.5, so i need to add at least 8 items
        for (int i = 0; i < 8; i++) {
            map.put("java" + i, i);
        }

        // Now, after insertion of the 8th item, remap should have been triggered.
        // Therefore, there should be8 items in the map and the map should be resized.
        assertEquals(8, map.size());

        // Let's validate the entries
        for (int i = 0; i < 8; i++) {
            assertEquals(i, map.get("java" + i));
        }

    }

    @Test
    public void testNullKeyShouldThrowAnException() {
        assertThrows(NullPointerException.class, () -> {
            map.put(null, 100);
        });
    }

    @Test
    public void testOverwriteingAfterRemovingAnEntry() {
        map.put("Minecraft", 62);
        map.remove("Minecraft");
        map.put("Minecraft", 73);
        assertEquals(73, map.get("Minecraft").intValue());
    }

    @Test
    public void testRandomLargeEntryInput() {
        for (int i = 0; i < 2131; i++) {
            map.put("nintendo" + i, i);
        }

        for (int i = 0; i < 2131; i++) {
            assertEquals(i, map.get("nintendo" + i));
        }
    }

    @Test
    public void testClear() {
        map.put("up", 1);
        map.put("down", 2);
        map.remove("up");
        map.remove("down");

        assertNull(map.get("up"));
        assertNull(map.get("down"));
        assertEquals(0, map.size());
    }
    @Test
    public void testToString() {
        String emptyMapString = "SeparateChainingHashMap {}";
        map.put("java", 100);
        map.put("python", 200);
        String nonEmptyMapString = map.toString();

        assertTrue(nonEmptyMapString.contains("java=100"), "String should contain 'java=100'");
        assertTrue(nonEmptyMapString.contains("python=200"), "String should contain 'python=200'");
        assertTrue(nonEmptyMapString.startsWith("SeparateChainingHashMap {") && nonEmptyMapString.endsWith("}"),
                "String should start with 'SeparateChainingHashMap {' and end with '}'");
    }


}
