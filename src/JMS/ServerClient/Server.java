package JMS.ServerClient;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server
{
    /* Steps
    * 1st going to make the server the buffer to relay
    * messages and then going to add messages
    * to clients respective message lists*/
    private final int port;
    private final String IpAddress;
    private ServerSocket serverSocket;
    Socket sock;

    private final BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>();
    private final Map<String, ClientHandler> phoneToClient = new HashMap<>();
    private final ArrayList<String> setPhoneNumbers = new ArrayList<>();

    public Server(String phoneNum1, String phoneNum2, int port, String IpAddress) throws IOException {
        System.out.println("line 1: Server constructor is called.");
        setPhoneNumbers.add(phoneNum1);
        System.out.println("line 2: Added phone number: " + phoneNum1);
        setPhoneNumbers.add(phoneNum2);
        System.out.println("line 3: Added phone number: " + phoneNum2);
        this.IpAddress = IpAddress;
        System.out.println("line 4: Server address set: " + IpAddress);
        this.port = port;
        System.out.println("line 5: Port set: " + port);
        if (!isPortInUse(port)) {// if port is free
            serverSocket = new ServerSocket(port);
            serverSocket.accept();
        }
    }
    public boolean isPortInUse(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // Successfully opened the port, it's not in use
            return false;
        } catch (IOException e) {
            // Port is already in use or other I/O error occurred
            return true;
        }
    }
    public void trySomething() throws IOException {
        System.out.println("line 6: trySomething method is called.");
        try
        {
            System.out.println("line 7: Server is on port " + port);

            new Thread(this::processMessages).start();
            for (int i = 0; i < 2; i++) //Only 2 times
            {

                System.out.println("line 8: Waiting for clients.");
                sock = new Socket(IpAddress,port);
                System.out.println("line 9: Client accepted.");
                ClientHandler clientHandler = new ClientHandler(sock);
                System.out.println("line 10: Added new client to the list. Current client count: ");
                clientHandler.start();
            }

            System.out.println("line 11: Connecting to server as client...");
            //clients?
            Client client = new Client(IpAddress, port);
            client.connectToServer();
        }
        catch (IOException e)
        {
            for (int i = 0; i < 2; i++)
            {

                sock = new Socket(IpAddress,port);
                System.out.println("line 12: Port is in use, connecting to server as client...");
                ClientHandler handler = new ClientHandler(sock);
                handler.start();
            }

            Client client = new Client(IpAddress, port);
            client.connectToServer();
        }
    }
    private void processMessages()
    {
        while (true)
        {
            try
            {
                String message = msgQueue.take();
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted");
            }
        }
    }
    public void stopServer()
    {
        System.out.println("line 13: stopServer method is called.");
        if (serverSocket != null && !serverSocket.isClosed())
        {
            try {
                serverSocket.close();
                System.out.println("line 14: Server socket closed.");
            }
            catch (IOException e)
            {
                System.err.println("line 15: Error closing socket: " + e.getMessage());
            }
        }
    }

    private class ClientHandler extends Thread {
        private final Socket socket;
        private DataOutputStream dataOutputStream;
        private String phoneNumber;

        public ClientHandler(Socket socket) {
            System.out.println("line 16: ClientHandler constructor is called.");
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("line 17: run method of ClientHandler is called.");
            try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
                System.out.println("line 18: Getting input stream from socket.");
                phoneNumber = /** ternary*/ setPhoneNumbers.isEmpty() ? null : setPhoneNumbers.remove(0);
                System.out.println("line 19: Assigned phone number: " + phoneNumber);
                if (phoneNumber != null)
                {
                    synchronized (phoneToClient)
                    {
                        phoneToClient.put(phoneNumber, this);
                        System.out.println("line 21: Client and phone number " + phoneNumber + " are connected.");
                    }
                }
                while (true)
                {
                    String message = inputStream.readUTF();
                    System.out.println("Client: ("+phoneNumber+"): "+ message);

                    msgQueue.put("From "+phoneNumber+": "+message);

                    broadcastMessage(message, phoneNumber);
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("line 23: IOException occurred: " + e.getMessage());
                throw new RuntimeException(e);
            }
            finally {
                synchronized (phoneToClient)
                {
                    phoneToClient.remove(phoneNumber);
                }
                System.out.println("Client is disconnected.");
            }
        }

        private void broadcastMessage(String message, String senderPhoneNumber) {
            System.out.println("line 24: broadcastMessage method is called.");
            try {
                String otherPhoneNumber = getOtherPhoneNumber(senderPhoneNumber);
                if (otherPhoneNumber != null) {
                    ClientHandler otherClient = phoneToClient.get(otherPhoneNumber);
                    if (otherClient != null) {
                        otherClient.sendMessage(message);
                        System.out.println("line 25: Message sent to " + otherPhoneNumber);
                    } else {
                        System.out.println("line 26: Error - no client with phone number " + otherPhoneNumber);
                    }
                }
            } catch (Exception e) {
                System.out.println("line 27: Exception in broadcastMessage: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private String getOtherPhoneNumber(String phoneNumber) {
            System.out.println("line 28: getOtherPhoneNumber method is called.");
            for (String number : setPhoneNumbers) {
                if (!number.equals(phoneNumber)) {
                    System.out.println("line 29: Found other phone number: " + number);
                    return number;
                }
            }
            System.out.println("line 30: No other phone number found.");
            return null;
        }

        private void sendMessage(String message) {
            System.out.println("line 31: sendMessage method is called.");
            try {
                if (dataOutputStream == null) {
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    System.out.println("line 32: DataOutputStream initialized.");
                }
                dataOutputStream.writeUTF(message);
                System.out.println("line 33: Message sent: " + message);
            } catch (IOException e) {
                System.out.println("line 34: IOException in sendMessage: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
