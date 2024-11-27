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
            //initalizing socket and dataoutptu\ strea,
            socket = new Socket(serverAddress, serverPort);
            outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Output is initalized!");
            inputStream = new DataInputStream(socket.getInputStream());
        System.out.println("Server address: "+serverAddress+":"+serverPort);

        //New thread for messages
            new Thread(this::listenForMessages).start();
        } catch (IOException e) {
            System.err.println("error to server" + e.getMessage());
        }
    }

    public void sendMessage(String message)
    {
        try
        {
            System.out.println("Trying the socket...");
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Trying the outputstream...");
            outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(message);
            System.out.println("Message sent to other server: "+ message);
        } catch (IOException e) {
            System.err.println("Error sending the messages: " + e.getMessage());
        }
    }

    private void listenForMessages()
    {
        try
        {
            while (true)
            {
                String gotMessage = inputStream.readUTF();
                if (messageListener != null)
                {
                    messageListener.onMessageRecieved(gotMessage);
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Error listening for the messages: " + e.getMessage());
        }
    }
    public void setMessageListener(MessageListener listener)
    {
        this.messageListener = listener;
    }

    public interface MessageListener
    {
        void onMessageRecieved(String message);
    }
}
