import java.net.*;
import java.io.*;

public class EchoUDPServer {

    private static final int maxRcvSize = 256;

    public static void main(String[] args) {
        
        if (args.length != 1) {
            System.out.println("Usage: java EchoUDPServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        System.out.println("port " + portNumber + " binding");

        try (
            DatagramSocket socket = new DatagramSocket(portNumber);
        ) {

            while (true) {
                try {
                    byte[] buf = new byte[maxRcvSize];

                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                    String response = new String(packet.getData());
                    
                    buf = response.getBytes();

                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                    
                    System.out.println("response: " + response);
                    if (response.trim().equals("bye.")) {
                        System.out.println("bye bye!");
                        break;
                    }

                } catch (IOException e) {
                    System.err.println("EchoUDPServer I/O Exception.");
                    break;
                }
            }
        } catch (SocketException e) {
            System.err.println("EchoUDPServer Socket Exception");
            System.exit(1);
        }
    }
}

