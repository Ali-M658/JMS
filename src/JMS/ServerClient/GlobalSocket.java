package JMS.ServerClient;

import java.io.IOException;
import java.net.Socket;

// GlobalSocket.java (Utility class to manage the socket)
public class GlobalSocket {
    private static Socket globalSocket;

    // Initialize the socket
    public static void initializeSocket(String host, int port) {
        if (globalSocket == null) {
            try {
                globalSocket = new Socket(host, port);
            }
            catch (IOException e)
            {
                e.getMessage();
            }
            System.out.println("Socket initialized: " + globalSocket);
        }
    }

    // Get the global socket
    public static Socket getSocket() {
        return globalSocket;
    }

    // Close the global socket
    public static void closeSocket() throws IOException {
        if (globalSocket != null && !globalSocket.isClosed()) {
            globalSocket.close();
            System.out.println("Socket closed.");
        }
    }
}
