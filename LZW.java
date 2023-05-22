import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LZW {

    // fields

    /**
     * LZW control code that will reset cuckoo table.
     */
    static final int RESTART = 256;

    private MyCuckooTable<String, Integer> myTable;

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
        myTable = new MyCuckooTable<>();
//        List<String> out = new ArrayList<>();
//        String curr = compressed[0] + "";
//        String seq;
//        out.add(curr);
//        for (int i = 1; i < compressed.length; i++) {
//            String code = compressed[i] + "";
//            if (myTable.get(code) != null) {
//                seq = myTable.get(code) + "";
//                out.add(seq);
//                String next = seq + seq.charAt(0);
//                myTable.put(next, myTable.size());
//            } else {
//                seq = myTable.get(curr) + "";
//                seq += seq.charAt(0);
//                out.add(seq);
//                myTable.put(seq, myTable.size());
//            }
//            curr = code;
//        }
//        return String.join("", out);
        List<Integer> decompressedData = new ArrayList<>();

        // initialize/reset MyCuckooTable
        MyCuckooTable<Integer, String> myTable2 = new MyCuckooTable<>();
        for(int i = 0; i < RESTART; i++) {
            myTable2.put(i, "" + (char) i);
        }

        int dictionarySize = myTable2.size();
        int nextCode = dictionarySize + 1;

        StringBuilder currentSequence = new StringBuilder();
        for (byte b : compressed) {
            int code = b & 0xFF; // Convert the byte to an unsigned integer
            String entry = myTable2.get(code);

            if (entry == null) {
                entry = currentSequence.toString() + currentSequence.charAt(0);
            }

            decompressedData.addAll(getCodes(entry));

            if (!currentSequence.isEmpty()) {
                String newSequence = currentSequence.toString() + entry.charAt(0);
                myTable2.put(nextCode++, newSequence);
            }

            currentSequence = new StringBuilder(entry);
        }

        return decompressedData.toString();
    }
    private List<Integer> getCodes (String entry) {
        List<Integer> codes = new ArrayList<>();
        for (char c : entry.toCharArray()) {
            codes.add((int) c);
        }
        return codes;
    }
}
