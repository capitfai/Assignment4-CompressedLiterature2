import java.util.Hashtable;

public class MyCuckooTable<K, V> {
    /**
     * Hashtable containing keys (type K) and values (type V).
     */
    Hashtable<K,V> myTable;
    /**
     * Table size, 1 left shifted 16 times.
     */
    public static final int TAB_SIZE = 1 << 16;
    /**
     * max length of the cuckoo key loop, (3lg2^16)=48.
     */
    private static final int MaxLoop = (int) Math.ceil(3 * Math.log(TAB_SIZE) / Math.log(2));
    /**
     * SipHash object.
     */
    private final SipHash sh = new SipHash();

    /**
     * Cuckoo Table constructor.
     */
    public MyCuckooTable() {
        myTable = new Hashtable<K, V>(TAB_SIZE);
    }

    /**
     * Return table size.
     * @return int
     */
    public int size() {
        return myTable.size();
    }

    /**
     * Clears the table.
     */
    public void reset() {
        myTable.clear();
    }

    /**
     * Adds a new entry to the table, returns true if successful, false if not.
     * @param searchKey search key
     * @param newValue new value
     * @return boolean
     */
    public boolean put(K searchKey, V newValue) {
        myTable.put(searchKey, newValue);
        return myTable.contains(newValue);
    }

    /**
     * Returns the value for a given key, or null.
     * @param searchKey search key
     * @return V
     */
    public V get(K searchKey) {
        return myTable.getOrDefault(searchKey, null);
    }

    /**
     * Hashes a key by the function defined by fno.
     * @param key key
     * @param fno fno
     * @return int
     */
    private int hash(K key, int fno) {
        return (int) (sh.hash(fno + "" + key) & (TAB_SIZE - 1));
    }
}
