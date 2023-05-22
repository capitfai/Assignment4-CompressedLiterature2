/*
 * Spring 2023 - Assignment 4 - Compressed Literature 2
 * Program by Keegan Sanders, Faith Capito
 */
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LZW {

    // fields

    /**
     * LZW control code that will reset cuckoo table.
     */
    static final int RESTART = 256;

    /**
     * Table containing Strings and their LZW codes.
     */
    public MyCuckooTable<String, Integer> myTable;

    /**
     * Number of codes counted in table.
     */
    public int myNumCodes;

    // constructors

    /**
     * Dummy constructor.
     */
    public LZW() { }

    // methods

    /**
     * Compresses String text to list of encoded bytes and initializes table.
     *
     * @param fullText String text to be encoded.
     * @return list of encoded bytes.
     */
    byte[] compress(String fullText) {

        // Output stream for LZW codes
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // initialize/reset MyCuckooTable
        myTable = new MyCuckooTable<>();
        for(int i = 0; i < RESTART; i++) {
            myTable.put("" + (char) i, i);
        }


        String[] data = (fullText + "").split("");
        String currentPrefix = data[0];
        String currentChar;
        myNumCodes = 256;

        for(int i = 1; i < data.length; i++) {
            currentChar = data[i];
            if (myTable.get(currentPrefix + currentChar) != null) {
                currentPrefix += currentChar;
            } else {
                if (!myTable.put(currentPrefix + currentChar, myNumCodes)) {
                    myTable.reset();
                    for(int j = 0; j < RESTART; j++) {
                        myTable.put("" + (char) j, j);
                    }
                }
                myNumCodes++;
                out.write(myTable.get(currentPrefix + currentChar));
                currentPrefix = currentChar;
            }
        }

        return out.toByteArray();
    }

    /**
     * Decompresses byte array into original text.
     *
     * @param compressed input byte array
     * @return String representation of original text.
     */
    String decompress(byte[] compressed) {
        String out = "";
        MyCuckooTable<Integer, String> myTable2 = new MyCuckooTable<>();
        for(int i = 0; i < RESTART; i++) {
            myTable2.put(i,"" + (char) i);
        }
//        System.out.println("here: " + myTable2.get(0));
        int old = compressed[0] & 0xFF;
        out += myTable2.get(old);
        for (int i : compressed) {
            int next = compressed[i] & 0xFF;
            if (myTable2.get(next) != null) {
                s = myTable2.get(old);
                s += c;
            } else {
                s = myTable2.get(next);
            }
            out += s;
            c = s.charAt(0);
            myTable2.put(old, String.valueOf(c));
            old = next;
        }
        return out;
    }
    private List<Integer> getCodes (String entry) {
        List<Integer> codes = new ArrayList<>();
        for (char c : entry.toCharArray()) {
            codes.add((int) c);
        }
        return codes;
    }
}
