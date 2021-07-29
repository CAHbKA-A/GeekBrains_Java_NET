package services;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private final static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);


    public void start() {
        /*поток для служебных сообщений
         * запусщен всегда*/
        THREAD_POOL.execute(() -> {
            System.out.println("New client started on thread " + Thread.currentThread().getName());
            try {

                SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8888));
                FilePrepare filePrepare = new FilePrepare("test.png");
                sendMessage(channel,"1:redy2trs//");
                //channel.write(ByteBuffer.wrap("1:redy2trs".getBytes(StandardCharsets.UTF_8)));
                while (true){
                    readMessage(channel,10);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });




    }
    public void readMessage(SocketChannel channel, int bufferSize) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        channel.read(byteBuffer);

        String message = new String((byteBuffer.array()));
        System.out.println("Message from server: " + message);
        checkRecivedMessage(channel,message);
    }

    public void checkRecivedMessage(SocketChannel channel,String message) throws IOException {
        String [] command = message.split("//");
        if (command[0].equals("2:letsTrsf")){
            System.out.println("Server wait file info");
            try {
                /*определяем расзмер буфера для передачи инфы о файле, ил
                 */
                FilePrepare filePrepare = new FilePrepare("test.png");
                //  System.out.println();
                sendMessage(channel,filePrepare.getFileInfo("test.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (command[0].equals("4:okLetsGo")){
            System.out.println("sending");
            //    transferFile("txt.txt");
            FilePrepare filePrepare = new FilePrepare("test.png");
         //   channel.write(filePrepare.createByteBuffer("test.png"));
        }
    }



    public void sendMessage(SocketChannel channel,String message) throws IOException {

        channel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));


    }


}