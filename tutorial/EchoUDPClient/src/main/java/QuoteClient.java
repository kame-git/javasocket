import java.net.*;
import java.io.*;
import java.util.*;

public class QuoteClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Usage: java QuoteClient <hostname>");
            return;
        }

        DatagramSocket socket = new DatagramSocket();

        byte[] buf = new byte[256];
        buf = new String("IBARAKI").getBytes();

        InetAddress address = InetAddress.getByName(args[0]);
        //DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 7);
        socket.send(packet);

        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);

        socket.close();
    }
}

