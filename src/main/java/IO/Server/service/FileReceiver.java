package IO.Server.service;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class FileReceiver {
    public static void start() throws IOException, ClassNotFoundException {
        ServerSocket server1 = new ServerSocket(8880);
        Socket s = server1.accept();
        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
      //  System.out.println(in.readObject());
       FilePrepare filePrepare = (FilePrepare) in.readObject();
      //  Cat filePrepare = (Cat) in.readObject();


      //  System.out.println(filePrepare);
        s.close();
    //  saveAsFile(filePrepare);



    }


    private static void saveAsFile(FilePrepare filePrepare) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("res_"+filePrepare.getFileName());
        try {

            fos.write(filePrepare.getFileBin());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//    private static void saveAsFile(Cat filePrepare) throws FileNotFoundException {
//        FileOutputStream fos = new FileOutputStream("res_"+filePrepare.getFileName());
//        try {
//
//        //    fos.write(filePrepare.getFileBin());
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }




}

