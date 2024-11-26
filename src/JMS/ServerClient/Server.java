package JMS.ServerClient;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import JMS.PortSaver;

public class Server
{
    int phoneNum;
    String message;
    int port = PortSaver.getPort();
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static Map<String, ClientHandler> phoneToClient = new HashMap<>();
    private static ArrayList<String> setPhoneNumbers = new ArrayList<>();
    private static MessageBuffer messageBuffer = new MessageBuffer();

    public Server(String phoneNum1, String phoneNum2)
    {
        setPhoneNumbers.add(phoneNum1);
        setPhoneNumbers.add(phoneNum2);
    }
    public void trySomething()
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is on port "+ port);

            while (clients.size() < 2) //Only 2 times
            {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private DataOutputStream dataOutputStream;
        private String phoneNumber;

        /**
         * The client handler takes a socket of that port and
         * reads the input stream from that
         */
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
                phoneNumber = inputStream.readUTF();
                String otherMessage = inputStream.readUTF();

                synchronized (phoneToClient) {
                    if (!phoneToClient.containsKey(phoneNumber)) {
                        phoneToClient.put(phoneNumber, this);
                        System.out.println("Client and phone number " + phoneNumber + "Is connected");
                    }
                }
                synchronized (messageBuffer) {
                    messageBuffer.message.add(otherMessage);
                    System.out.println("MEssage reviec and added to buffer: " + otherMessage);
                    broadcastMessage(otherMessage, phoneNumber);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void broadcastMessage(String message, String senderPhoneNumber) {
            try {
                String otherPhoneNumber = getOtherPhoneNumber(senderPhoneNumber);
                if (otherPhoneNumber != null) {
                    ClientHandler otherClient = phoneToClient.get(otherPhoneNumber);
                    if (otherClient != null) {
                        otherClient.sendMessage(message);
                    } else {
                        System.out.println("Error server.java line 96 \n no client with phone number " + otherPhoneNumber);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String getOtherPhoneNumber(String phoneNumber) {
            //Not senders and only 2
            for (String number : setPhoneNumbers) {
                if (!number.equals(phoneNumber)) ;
            }
            return null;
        }

        private void sendMessage(String message) {
            try {
                if (dataOutputStream == null) {
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                }
                dataOutputStream.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

