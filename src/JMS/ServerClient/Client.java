package JMS.ServerClient;

import com.ctc.wstx.shaded.msv_core.grammar.DifferenceNameClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private MessageListener messageListener;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connectToServer() {
        try
        {
            //initalizing socket and OutPutStream
            socket = new Socket(serverAddress, serverPort);

            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            boolean bool = socket.isClosed();
            System.out.println("Is socket open: "+bool);
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);
            System.out.println("Output is initalized!");

            } catch (IOException e) {
            System.err.println("error to server" + e.getMessage());
        }
    }
    public void messageListenThreads()
    {
        new Thread(this::listenForMessages).start();
    }
    public void sendMessage(String message)
    {
        try
        {
            if (socket != null && !socket.isClosed()) {
                System.out.println("Trying the socket...");
                System.out.println("Trying the outputstream...");

                System.out.println("Message sent to other server: " + message);
            }
            else {
                System.err.println("Socket is closed so unable to send message");
                outputStream.writeUTF(message);
            }
        } catch (IOException e) {
            System.err.println("Error sending the messages: " + e.getMessage());
        }
    }

    private void listenForMessages()
    {
        new Thread(() ->
        {
            try {
                while (true) {
                    String gotMessage = inputStream.readUTF();
                    if (messageListener != null) {
                        messageListener.onMessageRecieved(gotMessage);
                        System.out.println("The message was reciedved" + gotMessage);
                    } else {
                        System.out.println("Inputs are null");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error listening for the messages: " + e.getMessage());
            }
        }).start();
    }
    public void setMessageListener(MessageListener listener)
    {
        this.messageListener = listener;
    }
    public boolean isConnected()
    {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
    public interface MessageListener
    {
        void onMessageRecieved(String message);
    }
}

