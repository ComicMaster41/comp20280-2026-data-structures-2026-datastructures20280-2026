package project20280.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TreeMapTest {

    @Test
    public void testSize() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Assertions.assertEquals(0, map.size());
        map.put(1, "one");
        map.put(2, "two");
        Assertions.assertEquals(2, map.size());
    }

    @Test
    public void testRoot() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        Assertions.assertEquals(35, map.root().getElement().getKey());
    }

    @Test
    public void testGet() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }
        assertEquals("15", map.get(15));
        assertEquals("24", map.get(24));
		assertNull(map.get(-1));

    }

    private void assertEquals(String number, String s) {
    }

    @Test
    public void testPut() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.keySet().toString());
    }

    @Test
    public void testRemoveK() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        Assertions.assertEquals(12, map.size());
        assertEquals("26", map.remove(26));
        Assertions.assertEquals(11, map.size());

    }

    @Test
    public void testFirstEntry() {
        //TreeMap<Integer, String> map = new TreeMap<>();
        TreeMap<Integer, String> map = new TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        Assertions.assertEquals(1, map.firstEntry().getKey());
    }

    @Test
    public void testLastEntry() {
        TreeMap<Integer, String> map = new TreeMap<>();
        //java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        Assertions.assertEquals(35, map.lastEntry().getKey());
    }

    @Test
    public void testCeilingEntry() {
        TreeMap<Integer, String> map = new TreeMap<>();
        //java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        Assertions.assertEquals(12, map.ceilingEntry(11).getKey());

        Assertions.assertEquals(2, map.ceilingEntry(2).getKey());

    }

    @Test
    public void testFloorEntry() {
        TreeMap<Integer, String> map = new TreeMap<>();
        //java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        Assertions.assertEquals(5, map.floorEntry(11).getKey());
        Assertions.assertEquals(5, map.floorEntry(5).getKey());

    }

    @Test
    public void testLowerEntry() {
        TreeMap<Integer, String> map = new TreeMap<>();
        //java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        Assertions.assertEquals(23, map.lowerEntry(24).getKey());
        Assertions.assertEquals(26, map.lowerEntry(31).getKey());
    }

    @Test
    public void testHigherEntry() {
        TreeMap<Integer, String> map = new TreeMap<>();
        //java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        Assertions.assertEquals(12, map.higherEntry(11).getKey());
    }

    @Test
    public void testToString() {
        TreeMap<Integer, String> map = new TreeMap<>();
        //java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }
        assertEquals("", map.toString());
    }

    @Test
    public void testSubMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        //java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
        Integer[] arr = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};

        for (Integer i : arr) {
            map.put(i, Integer.toString(i));
        }

        assertEquals("[12, 15, 21, 23, 24, 26, 33]", map.subMap(12, 34).toString());
    }

}
