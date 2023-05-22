/*
 * Spring 2023 - Assignment 4 - Compressed Literature 2
 * Program by Keegan Sanders, Faith Capito
 */
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        LZW lzw = new LZW();
        final long startTime = System.currentTimeMillis();
        FileWriter out = new FileWriter(args[2]);
        if (args[0].equals("c")) {

            Path path = Paths.get(args[1]);
            Stream<String> stringStream = Files.lines(path);
            byte[] compressed = lzw.compress(stringStream.toString());
            try {
                out.write(Arrays.toString(compressed));
            } catch (IOException e) {
                e.printStackTrace();
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Original length: " + stringStream.toString().length());
            System.out.println("Compressed size: " + out.toString().length());
            System.out.println("Compression ratio: " + (out.toString().length() /
                                stringStream.toString().length()) * 100 + "%");
            System.out.println("Compression runtime: " + (endTime - startTime));
            System.out.println("Total number of LZW codes: " + lzw.myNumCodes);
            System.out.println("Fraction of occupied table entries: " + (lzw.myTable.size() / MyCuckooTable.TABSIZE));
            System.out.println("Average number of evictions: " + lzw.myTable.getEvictions());


        } else if (args[0].equals("d")) {

                String filePath = args[1];
                byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                String decompressedText = lzw.decompress(bytes);
                try  (FileWriter output = new FileWriter(args[2])) {
                output.write(decompressedText);
                final long endTime = System.currentTimeMillis();
                System.out.println("Decompression runtime: " + (endTime - startTime));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        testMyCuckooTable();
        testLZW();
    }

    public static void testMyCuckooTable() {
        MyCuckooTable<String, Integer> testTable = new MyCuckooTable<>();
        testTable.put("String", 1);
        System.out.println("Get: " +  testTable.get("String"));
        System.out.println("Size: " + testTable.size());
        testTable.reset();
    }

    public static void testLZW() {
        byte[] bArr = new byte[10];
        bArr[0] = 1;
        String testString = "abcdabcdabcdabcd";
        String testString2 = "bana has a banana in a bandana";
        String testString3 = "zaza";
        LZW testLZW = new LZW();
        System.out.println(testLZW.compress(testString));
        System.out.println(testLZW.decompress(testLZW.compress(testString)));
    }

}
