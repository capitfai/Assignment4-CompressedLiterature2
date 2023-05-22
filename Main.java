import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        LZW lzw = new LZW();
        final long startTime = System.currentTimeMillis();

//        if (args[0].equals("c")) {
//
//            Path path = Paths.get(args[1]);
//            Stream<String> stringStream = Files.lines(path);
//            lzw.compress(stringStream.toString());
//            final long endTime = System.currentTimeMillis();
//            System.out.println("Original length: " + stringStream.toString().length());
//            System.out.println("Compressed size: " + lzw.myTable.size());
//            System.out.println("Compression ratio: " + (lzw.myTable.size() / stringStream.toString().length()) + "%");
//            System.out.println("Compression runtime: " + (endTime - startTime));
//            System.out.println("Total number of LZW codes: " + lzw.myNumCodes);
//            System.out.println("Fraction of occupied table entries: " + (lzw.myTable.size() / MyCuckooTable.TABSIZE));
//            System.out.println("Average number of evictions: " + lzw.myTable.getEvictions());
//
//
//        } else if (args[0].equals("d")) {
//
//                String filePath = args[1];
//                byte[] bytes = Files.readAllBytes(Paths.get(filePath));
//                String decompressedText = lzw.decompress(bytes);
//                try  (FileWriter output = new FileWriter(args[2])) {
//                output.write(decompressedText);
//                final long endTime = System.currentTimeMillis();
//                System.out.println("Decompression runtime: " + (endTime - startTime));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


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
        LZW testLZW = new LZW();
        System.out.println(testLZW.compress("abcdabcdabcdabcd"));
        System.out.println(testLZW.decompress(bArr));
    }

}
