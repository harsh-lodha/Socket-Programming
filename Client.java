import java.io.*;
import java.util.*;
import java.util.stream.Stream;
import java.net.*;

public class Client {
    PrintStream streamToServer;
    BufferedReader streamFromServer;
    Socket toServer;

    public Client() {
        connectToServer();
    }

    private void connectToServer() {
        try {
            String name = "";
            while (!name.equals("q")) {
                toServer = new Socket("192.168.0.219", 1001);
                streamFromServer = new BufferedReader(new InputStreamReader((toServer.getInputStream())));
                streamToServer = new PrintStream(toServer.getOutputStream());
                System.out.println("Enter Connection Name");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                name = reader.readLine();
                streamToServer.println(name);
                String str = streamFromServer.readLine();
                System.out.println("The Server Says " + str);
            }
            toServer.close();
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }

    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        if (netint.getName().equals("wlan0")) {
            System.out.printf("Display name: %s\n", netint.getDisplayName());
            System.out.printf("Name: %s\n", netint.getName());
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                System.out.printf("InetAddress: %s\n", inetAddress);
            }
            System.out.printf("\n");
        }
    }

    public static void main(String args[]) throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            displayInterfaceInformation(netint);
        }
        new Client();
    }
}