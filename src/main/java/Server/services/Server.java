package Server.services;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public Server() {
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
                        readMessage(selectionKey);
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

    public void readMessage(SelectionKey selectionKey) throws IOException {
        SocketChannel client = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(36);
        client.read(byteBuffer);
        /* преобразуем байтбуфер в фаил*/
        FileBuilder fileBuilder = new FileBuilder();
        fileBuilder.getFileByByteBuffer(byteBuffer, "recived.txt");
        //fileBuilder.getFileByByteBuffer(byteBuffer, "recived.png");
        String message = new String((byteBuffer.array()));
        System.out.println("Message: "+ message);
    }


}
