import java.io.*;
import java.net.*;

public class EchoMultiServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        while (true) {
            try (
                ServerSocket serverSocket =
                    new ServerSocket(portNumber);
            ) {
                (new EchoMultiServerThread(
                        serverSocket.accept())).start();

            } catch (IOException e) {
                System.out.println(
                        "Exception caught when trying to listen on port"
                        + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
        
    }
}

