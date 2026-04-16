package project20280.hashtable;

import project20280.interfaces.Entry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Map implementation using hash table with separate chaining.
 */

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    // a fixed capacity array of UnsortedTableMap that serve as buckets
    private UnsortedTableMap<K, V>[] table; // initialized within createTable

    /**
     * Creates a hash table with capacity 11 and prime factor 109345121.
     */
    public ChainHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ChainHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ChainHashMap(int cap, int p) {
        super(cap, p);
    }

    /**
     * Creates an empty table having length equal to current capacity.
     */
    @Override
    @SuppressWarnings({"unchecked"})
    protected void createTable() {
        table = new UnsortedTableMap[capacity];
    }

    /**
     * Returns value associated with key k in bucket with hash value h. If no such
     * entry exists, returns null.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return associate value (or null, if no such entry)
     */
    @Override
    protected V bucketGet(int h, K k) {
        // TODO
        if (table[h] == null) return null;
        return table[h].get(k);
    }

    /**
     * Associates key k with value v in bucket with hash value h, returning the
     * previously associated value, if any.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @param v the value to be associated
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketPut(int h, K k, V v) {
        // TODO
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null) {
            bucket = new UnsortedTableMap<>();
            table[h] = bucket;
        }

        int bucketSize = table[h].size();
        V oldBucket = table[h].put(k, v); // we could do bucketGet but that's doing a lot
        if (table[h].size() > bucketSize) {
            n++;
        }
        return oldBucket;
    }


    /**
     * Removes entry having key k from bucket with hash value h, returning the
     * previously associated value, if found.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketRemove(int h, K k) {
        // TODO
        if (table[h] == null) return null;
        V oldBucket = table[h].get(k);
        if (oldBucket != null) n--;
        table[h].remove(k);
        return oldBucket;
    }

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        /*
        for each element in (UnsortedTableMap []) table
            for each element in bucket:
                print element
        */
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        for (UnsortedTableMap<K, V> tm : table) {
            if (tm != null) {
                for (Entry<K, V> e : tm.entrySet()) {
                    entries.add(e);
                }
            }
        }
        return entries;
    }

    public String toString() {
        return entrySet().toString();
    }

    public static void countFreq() throws FileNotFoundException {
        File f = new File("src/project20280/hashtable/sample_text.txt");
        ChainHashMap<String, Integer> counter = new ChainHashMap<String, Integer>();

        Scanner scanner = new Scanner(f);
        while (scanner.hasNext()) {
            String word = scanner.next();

            Integer freq = counter.get(word);

            // if word not in hashmap, add with count = 1
            if (freq == null) {
                counter.put(word, 1);
            }
            // otherwise, find the entry for this word and increment it by 1
            else {
                counter.put(word, freq + 1);
            }
        }

        // sort keys values
        counter.entrySet();

        // can you sort entries by values?
        scanner.close();

        // sort to get the 10 highest frequencies
        for (Entry<String, Integer> e : counter.entrySet()) {
            if (e.getValue() >= 10) {
                System.out.println(e.getKey() + " and " + e.getValue());
            }
        }

    }

    public static int countCollisions(int p, boolean isPoly) throws FileNotFoundException {
        File f = new File("src/project20280/hashtable/words.txt");
        ChainHashMap<Integer, Integer> counter = new ChainHashMap<Integer, Integer>();
        int collisions = 0;

        Scanner scanner = new Scanner(f);
        while (scanner.hasNext()) {
            String word = scanner.next();
            int h = 0;
            if (isPoly)
                h = hash_poly(word, p);
            else
                h = hash_cyclic(word, p);

            Integer freq = counter.get(h);

            // if word not in hashmap, add with count = 1
            if (freq == null) {
                counter.put(h, 1);
            }
            // otherwise, find the entry for this word and increment it by 1
            else {
                collisions++;
                counter.put(h, freq + 1);
            }
        }
        // can you sort entries by values?
        scanner.close();

        System.out.println("P: " + p + " has " + collisions);
        return collisions;
    }

    public static int oldCountCollision() throws FileNotFoundException {
        File f = new File("src/project20280/hashtable/words.txt");
        ChainHashMap<Integer, Integer> counter = new ChainHashMap<Integer, Integer>();
        int collisions = 0;

        Scanner scanner = new Scanner(f);
        while (scanner.hasNext()) {
            String word = scanner.next();
            int h = oldJavaHashCode(word);

            Integer freq = counter.get(h);

            // if word not in hashmap, add with count = 1
            if (freq == null) {
                counter.put(h, 1);
            }
            // otherwise, find the entry for this word and increment it by 1
            else {
                collisions++;
                counter.put(h, freq + 1);
            }
        }
        // can you sort entries by values?
        scanner.close();

        System.out.println("Old collision has " + collisions);
        return collisions;
    }

    public static int hash_poly(String s, int a) {
        int h = 0;
        int n = s.length();
        for(int i=0; i<n; i++) {
            char s_i = (char) s.charAt(i);
            int v = s_i * ((int) Math.pow(a,n-i-1));
            h += v;
        }
        return h;
    }

    public static int hash_cyclic(String s, int shift) {
        int h = 0;
        for (int i = 0; i < s.length(); ++i) {
            h = (h << shift) | (h >>> (32-shift));
            h += (int) s.charAt(i);
        }
        return h;
    }

    public static int oldJavaHashCode(String s) {
        int hash = 0;
        int skip = Math.max(1, s.length() / 8);
        for (int i = 0; i < s.length(); i += skip)
            hash = (hash * 37) + s.charAt(i);
        return hash;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ChainHashMap<Integer, String> m = new ChainHashMap<Integer, String>();
        m.put(12, "Twelve");
        m.put(44, "Forty-Four");
        m.put(13, "Thirteen");
        m.put(88, "Eighty-Eight");
        m.put(23, "Twenty-Three");
        m.put(94, "Nintey-Four");
        m.put(11, "Eleven");
        m.put(39, "Thirty-Nine");
        m.put(20, "Twenty");
        m.put(16, "Sixteen");
        m.put(5, "Five");

        System.out.println("m: " + m);

        // m.remove(11);

        // Q5) write a function to count frequencies of words in a file
        // sample_text.txt
        countFreq();

        countCollisions(41, true);
        countCollisions(17, true);
        countCollisions(7, false);
        // For d)
        int smallestCollision = Integer.MAX_VALUE;
        for (int i = 0; i < 32; i++) {
            int small = countCollisions(i, false);
            if (smallestCollision > small)
                smallestCollision = small;
        }

        System.out.println("Smallest collision is: "+ smallestCollision);
        oldCountCollision();
    }
}
