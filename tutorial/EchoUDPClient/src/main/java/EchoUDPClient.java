import java.net.*;
import java.io.*;
import java.util.*;

public class EchoUDPClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage: java EchoUDPClient <hostname> <port number>");
            return;
        }

        InetAddress address = InetAddress.getByName(args[0]);
        int portNumber = Integer.parseInt(args[1]);

        try (
            DatagramSocket socket = new DatagramSocket();
            Scanner sc = new Scanner(System.in);
        ) {
            while (true) {
                String userInput = sc.next();

                byte[] buf = new byte[256];
                buf = userInput.getBytes();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portNumber);
                socket.send(packet);

                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);

                if (received.trim().equalsIgnoreCase("bye."))
                    break;
            }
        }
    }
}

