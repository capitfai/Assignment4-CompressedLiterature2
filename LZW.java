public class LZW {

    // fields

    /**
     * LZW control code that will reset cuckoo table.
     */
    static final int RESTART = 256;

    MyCuckooTable<String, Integer> myTable;

    // constructors

    /**
     * Dummy constructor.
     */
    public LZW() { }

    // methods

    /**
     * Compresses String text to list of encoded bytes and initializes dictionary.
     *
     * @param fullText String text to be encoded.
     * @return list of encoded bytes.
     */
    byte[] compress(String fullText) {
        // initialize/reset MyCuckooTable
        myTable = new MyCuckooTable<>();
        for(int i = 0; i < RESTART; i++) {
            myTable.put(String.valueOf((char) i), i);
        }

        StringBuilder current = new StringBuilder();
        String currentString;
        char byteIn;

        while (fullText.length() > 0) {
            // initialize first input character
            current.append(fullText);
            currentString = current.toString();
            // if (!myTable.)
        }

        return null;
    }

    String decompress(byte[] compressed) {
        return "";
    }

}
