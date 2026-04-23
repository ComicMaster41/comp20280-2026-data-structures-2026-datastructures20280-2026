package project20280.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreapTest{
    @Test
    void testSize() {
        Treap<Integer, String> map = new Treap<>();
        assertEquals(0, map.size());
        map.put(1, "one");
        map.put(2, "two");
        assertEquals(2, map.size());
    }

    @Test
    void testGet() {
        Treap<Integer, String> map = new Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }
        assertEquals("15", map.get(15));
        assertEquals("24", map.get(24));
        assertNull(map.get(-1));

    }

    @Test
    void testPut() {
        Treap<Integer, String> map = new Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals(12, map.size());
        assertEquals("15", map.get(15));
        assertEquals("1", map.get(1));
    }

    @Test
    void testRemoveK() {
        Treap<Integer, String> map = new Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals(12, map.size());
        assertEquals("26", map.remove(26));
        assertEquals(11, map.size());

    }

    @Test
    void testFirstEntry() {
        //Treap<Integer, String> map = new Treap<>();
        Treap<Integer, String> map = new Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals(1, map.firstEntry().getKey());
    }

    @Test
    void testLastEntry() {
        Treap<Integer, String> map = new Treap<>();
        //java.util.Treap<Integer, String> map = new java.util.Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals(35, map.lastEntry().getKey());
    }

    @Test
    void testCeilingEntry() {
        Treap<Integer, String> map = new Treap<>();
        //java.util.Treap<Integer, String> map = new java.util.Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals(12, map.ceilingEntry(11).getKey());

        assertEquals(2, map.ceilingEntry(2).getKey());

    }

    @Test
    void testFloorEntry() {
        Treap<Integer, String> map = new Treap<>();
        //java.util.Treap<Integer, String> map = new java.util.Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals(5, map.floorEntry(11).getKey());
        assertEquals(5, map.floorEntry(5).getKey());

    }

    @Test
    void testLowerEntry() {
        Treap<Integer, String> map = new Treap<>();
        //java.util.Treap<Integer, String> map = new java.util.Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals(23, map.lowerEntry(24).getKey());
        assertEquals(26, map.lowerEntry(31).getKey());
    }

    @Test
    void testHigherEntry() {
        Treap<Integer, String> map = new Treap<>();
        //java.util.Treap<Integer, String> map = new java.util.Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals(12, map.higherEntry(11).getKey());
    }

    @Test
    void testToString() {
        Treap<Integer, String> map = new Treap<>();
        //java.util.Treap<Integer, String> map = new java.util.Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }
//        assertEquals("", map.toString());
        assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.toString());

    }

    @Test
    void testSubMap() {
        Treap<Integer, String> map = new Treap<>();
        //java.util.Treap<Integer, String> map = new java.util.Treap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals("[12, 15, 21, 23, 24, 26, 33]", map.subMap(12, 34).toString());
    }
}
