import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LRUTestUnit {
    private final Random random = new Random();

    private static <K, V> LRUCache<K, V> getInstance(int capacity) {
        return new LRUCache<>(capacity);
    }

    @Nested
    @DisplayName("Test cache on instantiation")
    public class InitializationTestUnit {

        @Test
        @DisplayName("Test cache on instantiation")
        public void testSuccessfulInit() {
            assertNotNull(getInstance(10));
        }

        @Test
        @DisplayName("Test cache on invalid values of constructor")
        public void testUnsuccessfulInit() {
            try {
                LRUCache<Integer, String> cache = getInstance(-1);
                assertEquals(cache.getCapacity(), 0);
            } catch (Exception | AssertionError e) {
                return;
            }

            fail("Created instance with non-positive capacity");
        }

        @Test
        @DisplayName("Test cache on valid value of capacity")
        public void testCapacity() {
            int capacity = 1 + random.nextInt(1000);

            LRUCache<Integer, String> cache = getInstance(capacity);
            assertEquals(cache.getCapacity(), capacity);
        }

    }

    @Nested
    @DisplayName("Test cache on PUT")
    public class InsertTestUnit {

        @Test
        @DisplayName("Test cache on single PUT")
        public void testNormalSingleInsert() {
            LRUCache<Integer, String> cache = getInstance(100);
            cache.put(32, "Value");

            assertEquals(1, cache.getSize());
        }

        @Test
        @DisplayName("Test cache on PUT null value with normal key")
        public void testNullValueInsert() {
            LRUCache<Integer, String> cache = getInstance(100);
            cache.put(63, null);

            assertEquals(1, cache.getSize());
        }

        @Test
        @DisplayName("Test cache on PUT value with NULL key")
        public void testNullKeyInsert () {
            LRUCache<Integer, String> cache = getInstance(100);
            try {
                cache.put(null, "NULL");
            } catch (Exception | AssertionError e) {
                return;
            }

            fail("Inserted value with null key");
        }

        @Test
        @DisplayName("Test cache on insert of several values")
        public void testSeveralInserts() {
            int capacity = 10 + random.nextInt(10000),
                    toInsert = 5 + random.nextInt(capacity / 2);
            LRUCache<Integer, String> cache = getInstance(capacity);
            for (int i = 0; i < toInsert; i++) {
                int value = random.nextInt(capacity * 2);
                cache.put(i, "" + value);
            }

            assertEquals(toInsert, cache.getSize ());
        }

        @Test
        @DisplayName("Test cache on huge insert")
        public void testOverCapacityInserts() {
            int capacity = 10 + random.nextInt (10000),
                    toInsert = capacity + 1 + random.nextInt(capacity);
            LRUCache<Integer, String> cache = getInstance(capacity);
            for (int i = 0; i < toInsert; i++) {
                int value = random.nextInt(toInsert);
                cache.put (i, "" + value);
            }

            assertEquals(capacity, cache.getSize());
        }

        @Test
        @DisplayName ("Test PUT on repeating keys")
        public void testRepeatedKey () {
            LRUCache<Integer, String> cache = getInstance(1000);
            int insert = 10 + random.nextInt(10000);
            Set<Integer> keys = new HashSet<>();

            for (int i = 0; i < insert; i++) {
                int key = random.nextInt(cache.getCapacity());
                if (keys.contains(key)) {
                    try {
                        cache.put(key, "" + key);
                    } catch (Exception | AssertionError e) {
                        continue;
                    }

                    fail("Inserted value with repeated key");
                } else {
                    keys.add(key);
                    cache.put(key, key + "");
                    assertEquals(keys.size (), cache.getSize());
                }
            }
        }

    }

    @Nested
    @DisplayName("Test cache on GET")
    public class FunctionalityTestUnit {

        @Test
        @DisplayName("Test GET from empty cache by random key")
        public void testGetFromEmpty() {
            LRUCache<Integer, String> cache = getInstance(100);
            assertNull(cache.get(random.nextInt()));
        }

        @Test
        @DisplayName("Test GET after single PUT")
        public void testInsertAndGet() {
            LRUCache<Integer, String> cache = getInstance(100);

            int key = random.nextInt();
            String value = "" + (key * 2);
            cache.put(key, value);

            String answer = cache.get(key);
            assertEquals(value, answer);
        }

        @Test
        @DisplayName("Test cache on correct GET requests")
        public void testInsertAndSeveralGet() {
            LRUCache<Integer, String> cache = getInstance(10000);
            int range = 10 + random.nextInt(1000);

            for (int i = 0; i < cache.getCapacity() + range; i++) {
                cache.put(i, "value-" + i);
            }

            int missed = 0;
            for (int i = cache.getCapacity() + range - 1; i >= 0; i--) {
                missed += (cache.get(i) == null ? 1 : 0);
            }

            assertEquals(missed, range);

            String expectedValueInTail = "value-" + range;
            String actualValueInTail = cache.get(range);
            assertEquals(expectedValueInTail, actualValueInTail);
        }

        @Test
        @DisplayName("Test cache on correct GET requests from tail")
        public void testInsertAndGetFromTail() {
            int capacity = 10 + random.nextInt(10000);
            LRUCache<Integer, String> cache = getInstance(capacity);
            for (int i = 0; i < cache.getCapacity(); i++) {
                cache.put(i, "value-" + i);

                assert i <= 0 || cache.get(i - 1) != null;
            }

            cache.put(-1, "stub");
            assertNull(cache.get(1));
        }

    }

    @Nested
    @DisplayName("My Tests")
    public class MyTestUnit {

        @Test
        @DisplayName("Test with CAPACITY equals 1")
        public void testWithCapacityOne() {
            int capacity = 1;
            int toInsert = capacity + 10 + random.nextInt(capacity);
            LRUCache<Integer, String> cache = getInstance(capacity);
            for (int i = 0; i < toInsert; i++) {
                cache.put(i, "value-" + i);
            }

            String expectedValueInTail = "value-" + (toInsert - 1);
            String actualValueInTail = cache.get(toInsert - 1);
            assertEquals(expectedValueInTail, actualValueInTail);
        }

        @Test
        @DisplayName("Test GET with key  NULL")
        public void testGetNull() {
            int capacity = 100;
            int toInsert = capacity + 10 + random.nextInt(capacity);
            LRUCache<Integer, String> cache = getInstance(capacity);
            for (int i = 0; i < toInsert; i++) {
                cache.put(i, "value-" + i);
            }

            assertNull(cache.get(null));
        }
    }
}
