/*
 * Spring 2023 - Assignment 4 - Compressed Literature 2
 * Program by Keegan Sanders, Faith Capito
 */
import java.util.HashMap;

public class MyCuckooTable<K, V> {
    /**
     * Hashtable containing keys (type K) and values (type V).
     */
    private final HashMap<K,V> table1;
    private final HashMap<K, V> table2;
    /**
     * Table size, 1 left shifted 16 times.
     */
    public static final int TABSIZE = 1 << 16;
    /**
     * max length of the cuckoo key loop, (3lg2^16)=48.
     */
    private static final int MaxLoop = (int) Math.ceil(3 * Math.log(TABSIZE) / Math.log(2));
    /**
     * SipHash object.
     */
    private final SipHash sh = new SipHash();
    private int evictions;

    /**
     * Cuckoo Table constructor.
     */
    public MyCuckooTable() {
        table1 = new HashMap<>(TABSIZE);
        table2 = new HashMap<>(TABSIZE);
        evictions = 0;

    }

    /**
     * Return table size.
     * @return int
     */
    public int size() {
        return table1.size() + table2.size();
    }

    /**
     * Clears the table.
     */
    public void reset() {
        table1.clear();
        table2.clear();
    }

    /**
     * Adds a new entry to the table, returns true if successful, false if not.
     * @param searchKey search key
     * @param newValue new value
     * @return boolean
     */
    public boolean put(K searchKey, V newValue) {
        int hash1 = hash(searchKey, 1);
        int hash2 = hash(searchKey, 2);
        V evictedValue;
        for (int i = 0; i < MaxLoop; i++) { // loop up to max-loop times
            if (table1.containsKey(searchKey)) { // if the spot in table1 is filled
                evictedValue = table1.get(searchKey); // evict old value
                table1.put(searchKey, newValue); // put in new value
                evictions++; // add to evictions
                if (table2.containsKey(searchKey)) { // if the spot is filled in table 2
                    newValue = table2.get(searchKey); // save old value
                    table2.put(searchKey, evictedValue); // evict old value with table 1 evicted value
                    evictions++; // add to evictions
                } else {
                    table2.put(searchKey, evictedValue);
                }
            } else {
                table1.put(searchKey, newValue);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value for a given key, or null.
     * @param searchKey search key
     * @return V
     */
    public V get(K searchKey) {
        int hash1 = hash(searchKey, 1);
        int hash2 = hash(searchKey, 2);
        if (table1.containsKey(searchKey)) {
            return table1.get(searchKey);
        } else if (table2.containsKey(searchKey)) {
            return table2.get(searchKey);
        }
        return  null;
    }

    /**
     * Hashes a key by the function defined by fno.
     * @param key key
     * @param fno fno
     * @return int
     */
    private int hash(K key, int fno) {
        return (int) sh.hash(fno + "" + key) & (TABSIZE - 1);
    }

    public int getEvictions() {
        return evictions;
    }
}
