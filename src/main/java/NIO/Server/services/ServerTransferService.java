package NIO.Server.services;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;



public class ServerTransferService {
    Boolean isFile = false;

    public ServerTransferService() {
        Selector selector = null;
        ServerSocketChannel serverSocket = null;


        try {
            /*Запускаем селектор */
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress("localhost", 8888));
            serverSocket.configureBlocking(false);
            /*будем отлавливать запрос на подключение*/
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server started.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket error");
        }
        while (true) {
            try {
                /*ждем запрос*/
                selector.select();
                /*выдераем ключи*/
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                /*итератор для перебора*/
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        System.out.println("new selector accept");
                        register(selector, serverSocket);
                    }
                    if (selectionKey.isReadable()) {
                        System.out.println("ready to read");
                        readMessage(selectionKey, 1024000, isFile);
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void register(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("new client connected");
    }

    public void readMessage(SelectionKey selectionKey, int bufferSize, Boolean isFile) throws IOException {
        SocketChannel client = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        client.read(byteBuffer);


        if (isFile) {
            receiveFile(isFile, byteBuffer);
        } else {
            String message = new String((byteBuffer.array()));
            System.out.println("Message from Client: " + message);
            /*пытаемя понять что получили*/
            checkReceivedMessage(client, message);
        }


    }

    private void receiveFile(Boolean isFile, ByteBuffer byteBuffer) throws IOException {
        System.out.println(isFile);
        FileBuilder fileBuilder = new FileBuilder();
        fileBuilder.getFileByByteBuffer(byteBuffer, "test1.png");

        System.out.println("file recived!!");
        String message = new String((byteBuffer.array()));
    }


/* обрабатываем служебные сообщения.
сообщения по 10 byte
1:redy2trs   клиент готов передааввть файил.
2:letsTrsf  "передавай"
3:MyFileInfo передаю инфромацию о файле
4:okLetsGo давай сам фаил уже!

 */

    public void checkReceivedMessage(SocketChannel client, String message) throws IOException {
        String[] command = message.split("//");
        if (command[0].equals("1:redy2trs")) {
            System.out.println("client ready to transfer");
            try {
                //  readMessage(client, 1024, false);
                sendMessage(client, "2:letsTrsf");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (command[0].equals("3:MyFileInfo")) {
            System.out.println("Recieving file " + command[2] + " . Size: " + command[1] + " Byte.");
            ResivedFile  resivedFile = new ResivedFile(command[2], Long.parseLong(command[1]), command[3]);
            isFile = true;
            sendMessage(client, "4:okLetsGo");


        }

    }

    public void sendMessage(SocketChannel client, String message) throws IOException {

        client.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));


    }




}
