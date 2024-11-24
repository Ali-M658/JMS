package JMS.ServerClient;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import JMS.PortSaver;

public class Server
{
    String phoneNumber;
    int phoneNum;
    String message;
    int port = GeneratePort.randomNum();
    int portAgain = new PortSaver(port).getPort();
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static MessageBuffer messageBuffer = new MessageBuffer("");

    public Server(String phoneNumber, String message)
    {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }
    private void setPhoneNum()
    {
        phoneNum = Integer.parseInt(phoneNumber);
    }
    public void trySomething()
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is on port "+ port);

            while (clients.size() < 2)
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

    private static class ClientHandler extends Thread{
        private Socket socket;
        private DataOutputStream dataOutputStream;

        public ClientHandler(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            try (DataInputStream inputStream = new DataInputStream(socket.getInputStream()))
            {
                String otherMessage = inputStream.readUTF();
                synchronized (messageBuffer)
                {
                    messageBuffer.message.add(otherMessage);
                    System.out.println("MEssage reviec and added to buffer: "+otherMessage);
                    broadcastMessage(otherMessage);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        private void broadcastMessage(String message)
        {
            for (ClientHandler client : clients)
            {
                try
                {
                    if (client != this)// other client
                    {
                        client.dataOutputStream.writeUTF(message);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
