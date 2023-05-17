import java.util.Hashtable;

public class MyCuckooTable<K, V> {
    Hashtable<K,V> myTable;
    public static final int TAB_SIZE = 1 << 16;
    /**
     * max length of the cuckoo key loop, (3lg2^16)=48.
     */
    private static final int MaxLoop = (int) Math.ceil(3 * Math.log(TAB_SIZE) / Math.log(2));
    private final SipHash sh = new SipHash();
    public MyCuckooTable() {
        myTable = new Hashtable<K, V>(TAB_SIZE);
        // control code
    }
    public int size() {
        return myTable.size();
    }
    public void reset() {

    }
    public boolean put(K searchKey, V newValue) {
//        myTable.put(searchKey, newValue);
        // all 0-255 one char strings
        // control code at 256 empty string
        // code length set to 9 bits
        return false;
    }
    public V get(K searchKey) {
        return myTable.getOrDefault(searchKey, null);
    }
    private int hash(K key, int fno) {
        return (int) (sh.hash(fno + "" + key) & (TAB_SIZE - 1));
    }
}
