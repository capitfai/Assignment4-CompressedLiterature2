import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    void main(String[] args) throws IOException {

        LZW lzw = new LZW();
        final long startTime = System.currentTimeMillis();

        if (args[0].equals("c")) {

            Path path = Paths.get(args[1]);
            Stream<String> stringStream = Files.lines(path);
            lzw.compress(stringStream.toString());
            final long endTime = System.currentTimeMillis();
            System.out.println("Compression runtime: " + (endTime - startTime));

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

    public void testMyCuckooTable() {

    }

    public void testLZW() {

    }

}
