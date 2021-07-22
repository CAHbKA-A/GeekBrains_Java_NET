package Client.services;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private final static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(1);


    public void start() {

        THREAD_POOL.execute(() -> {
            System.out.println("New client started on thread " + Thread.currentThread().getName());
            try {

                SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8888));
                FilePrepare filePrepare = new FilePrepare();
              //  channel.write(filePrepare.createByteBuffer("test.png"));
                channel.write(filePrepare.createByteBuffer("txt.txt"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


}
