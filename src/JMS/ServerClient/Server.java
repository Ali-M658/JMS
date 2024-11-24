package JMS.ServerClient;

import java.io.*;
import java.net.*;

public class Client
{
    String phoneNumber;
    int phoneNum;
    int port = GeneratePort.randomNum();
    public Client(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    private void setPhoneNum()
    {
        phoneNum = Integer.parseInt(phoneNumber);
    }
}
