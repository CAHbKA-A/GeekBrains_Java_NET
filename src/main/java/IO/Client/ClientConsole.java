package IO.Client;


import IO.Client.Services.FilePrepare;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ClientConsole {
    private Socket socket;
    private final int PORT = 8899;
    private final String HOST = "localhost";
    private DataInputStream dis;
    private DataOutputStream dos;

    public static void main(String[] args) {
        ClientConsole clientConsole = new ClientConsole();
    }
    private ClientConsole() {

      //  GUIClient();

        try {
            connection();

            readMessage();
//TODO /*ТУТ будет GUI авторизации*/
            singIn ("A","A");


            //TODO  тут будет запуск сбора списка фалов  и принятие решение на синхронизаци.
            //TODO тут будет запущена синхра
            /*передаем фаил*/
            sendMessage("/ready_to_transfer_File");

           // sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






private void singIn(String login, String passwodr){
   sendMessage("/auth " + login + " " + passwodr);

}




    private void connection() throws IOException {
        System.out.println("Connecting..");
        while (true) {
            try {
                socket = new Socket(HOST, PORT);
                if (socket.isConnected()) {
                    System.out.println("Server connected ");
                    // Loging.writeToLog("New session. " + getDate(), loginField.getText());
                    break;
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            } catch (IOException io) {
                System.out.println("....");
            }
        }
    }


    private void readMessage() throws IOException {
        System.out.println("Connected! Enter you login/password");
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                while (true) {
                    String serverMess = dis.readUTF();
                    if (serverMess.startsWith("/")) {


                        if (serverMess.equals("/authok")) {
                            System.out.println("\n\n" +  "\nYou connected! \n");

                        }

                        if (serverMess.equals("/q")) {

                            System.out.println("Connection closed \n");
                            break;
                        }
                        if (serverMess.equals("/lets_go")) {

                            System.out.println("transfer.....-> Server....");
                            sendFile("txt.txt");
                            break;
                        }
                    } else {
                        System.out.println(serverMess + "\n");
                        //    Loging.writeToLog(serverMess, loginField.getText());
                    }
                }
            } catch (IOException ignored) {
            }
        }).start();
    }

    public  void sendMessage(String message) {

        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendFile(String fileName) {

        try {
         //   sendMessage("/ready_to_transfer_File");
            FilePrepare filePrepare = new FilePrepare(fileName);
           // Cat filePrepare = new Cat();

                sleep(1000);
            Socket s = new Socket("localhost", 8880);

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            System.out.println(filePrepare);
            out.writeObject(filePrepare);
          //  out.writeObject(filePrepare);
            out.flush();
            System.out.println("done");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }






//
//    public static void serializeAddressJDK7(FilePrepare filePrepare) {
//
//        try (ObjectOutputStream oos =
//                     new ObjectOutputStream(new FileOutputStream("serial.ser"))) {
//
//            oos.writeObject(filePrepare);
//            System.out.println("Done");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }



}
