package NIO.Server.services;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class FileBuilder {


    public void getFileByByteBuffer (ByteBuffer byteBuffer, String outputName) throws IOException {
        FileOutputStream fos = new FileOutputStream(outputName);
        fos.write(byteBuffer.array());
        fos.close();

    }
}
