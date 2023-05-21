import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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
        int code = RESTART;

        for(int i = 1; i < data.length; i++) {
            currentChar = data[i];
            if (myTable.get(currentPrefix + currentChar) != null) {
                currentPrefix += currentChar;
            } else {
                myTable.put(currentPrefix + currentChar, code);
                code++;
                out.write(myTable.get(currentPrefix + currentChar));
                currentPrefix = currentChar;
            }
        }

        return out.toByteArray();
    }

    String decompress(byte[] compressed) {

//        myTable = new MyCuckooTable<>();
//        String[] data = (compressed + "").split("");

//        myTable = new MyCuckooTable<>();
//        List<String> out = new ArrayList<>();
//        int curr = compressed[0];
//        String seq;
//        out.add(Character.toString((char) curr));
//        for (int i = 1; i < compressed.length; i++) {
//            int code = compressed[i];
//            if (myTable.get(code) != null) {
//                seq = myTable.get(code);
//                out.add(seq);
//                String next = seq + seq.charAt(0);
//                myTable.put(myTable.size(), next);
//            } else {
//                seq = myTable.get(curr);
//                seq += seq.charAt(0);
//                out.add(seq);
//                myTable.put(myTable.size(), seq);
//            }
//            curr = code;
//        }
//        return String.join("", out);
//
        return null;
    }

}
