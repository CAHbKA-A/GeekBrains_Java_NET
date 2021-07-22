package Client.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class FilePrepare {
    private int bufferSize;

    public int getBufferSize() {
        return bufferSize;
    }

    public ByteBuffer createByteBuffer(String fileName) throws IOException {

        File inputFile = new File(fileName);

        FileInputStream fis = new FileInputStream(inputFile);
        System.out.println("Prepair to send File: " + fileName + "/  :  " + fis.available() + " bytes");
        /* пока не знаю способа из стрима в байтБуфер передать. делаю через массив байт*/
        ByteBuffer byteBuffer;// = ByteBuffer.allocate(bufferSize);
        bufferSize = fis.available();
        byte[] buffer = new byte[bufferSize];
        fis.read(buffer);
        System.out.println();
        byteBuffer = ByteBuffer.wrap(buffer);
        // System.out.println(StandardCharsets.UTF_8.decode(byteBuffer).toString());
        // System.out.println(byteBuffer);
        fis.close();
        return byteBuffer;
    }

}
