package service;


import lib.FileObjectLibClass;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;


public class FileReceiver {


    public static void start(Socket socket) throws IOException, ClassNotFoundException {

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        FileObjectLibClass filePrepare = (FileObjectLibClass) in.readObject();
        saveAsFile(filePrepare);
    }


    private static void saveAsFile(FileObjectLibClass filePrepare) throws FileNotFoundException {
        //ClientHandler.sendMessage("/lets_go");
        //  sendMessage("/complit");
        FileOutputStream fos = new FileOutputStream("SERVER_FOLDER/" + filePrepare.getFileName());
        try {
            fos.write(filePrepare.getFileBin());
            System.out.println(filePrepare.getFileBin());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

