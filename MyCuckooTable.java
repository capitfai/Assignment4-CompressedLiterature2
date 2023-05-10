import java.util.Hashtable;

public class MyCuckooTable<K, V> {

    Hashtable myTable;

    public static final int TABSIZE = 1 << 16;

    private static final int MaxLoop = (int) Math.ceil(3 * Math.log(TABSIZE) / Math.log(2));

    private final SipHash sh = new SipHash();

    private int mySize;

    MyCuckooTable() {

        myTable = new Hashtable<K, V>(TABSIZE);
        mySize = 0;

    }

    public int size() {
        return mySize;
    }

    public void reset() {

    }

    public boolean put(K searchKey, V newValue) {
        return false;
    }

    public V get() {
        return null;
    }

    private int hash(K key, int fno) {
        // use this instead of hashCode() method
        return 0;
    }


}
