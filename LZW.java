import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LZW {

    // fields

    /**
     * LZW control code that will reset cuckoo table.
     */
    static final int RESTART = 256;

    public MyCuckooTable<String, Integer> myTable;

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
        List<Integer> decompressedData = new ArrayList<>(); // array list for decompressed data

        // initialize/reset MyCuckooTable
        MyCuckooTable<Integer, String> myTable2 = new MyCuckooTable<>();
        for(int i = 0; i < RESTART; i++) {
            myTable2.put(i, "" + (char) i);
        }

        int dictionarySize = myTable2.size(); // size
        int nextCode = dictionarySize + 1; // size+1

        StringBuilder currentSequence = new StringBuilder(); // string for current sequence
        for (byte b : compressed) { // for each byte in input
            int code = b & 0xFF; // Convert the byte to an unsigned integer
            String entry = myTable2.get(code); // var entry is the value of the code

            if (entry == null) { // if entry doesn't exist
                entry = currentSequence.toString() + currentSequence.charAt(0); // reassign entry to be curr + first char of the sequence
            }

            decompressedData.addAll(getCodes(entry)); // add the code to decompressed data from the entry

            if (!currentSequence.isEmpty()) { // if curr isn't empty
                String newSequence = currentSequence.toString() + entry.charAt(0); // new is current plus first char of the entry
                myTable2.put(nextCode++, newSequence); // put into the table nextcode++ and the new sequence
            }

            currentSequence = new StringBuilder(entry); // current sequence is string of the entry
        }

        return decompressedData.toString(); // return decompressed data
    }
    private List<Integer> getCodes (String entry) {
        List<Integer> codes = new ArrayList<>();
        for (char c : entry.toCharArray()) {
            codes.add((int) c);
        }
        return codes;
    }
}
