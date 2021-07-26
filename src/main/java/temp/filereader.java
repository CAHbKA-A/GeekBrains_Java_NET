package temp;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class filereader {
        public static void main(String[] args) throws IOException {

      //  String fileName = "txt.txt";
        String fileName = "test.png";
        File inputFile = new File(fileName);
        int bufferSize = 10240;
            FileInputStream fis = new FileInputStream(inputFile);
        System.out.println("File: "+ fileName +"/  size:  "+ fis.available()+ " bytes");

        ByteBuffer byteBuffer;// = ByteBuffer.allocate(bufferSize);
            byte[] buffer = new byte[bufferSize];
            fis.read(buffer);
            System.out.println();
            byteBuffer = ByteBuffer.wrap(buffer);
          //  System.out.println(StandardCharsets.UTF_8.decode(byteBuffer).toString());

        }


}
