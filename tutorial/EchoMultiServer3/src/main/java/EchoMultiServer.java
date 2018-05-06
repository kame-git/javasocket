import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class EchoMultiServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        ExecutorService executor = null;
        executor = Executors.newFixedThreadPool(3);

        try (
            ServerSocket serverSocket =
                new ServerSocket(portNumber);
        ) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Runnable worker = new EchoMultiServerThread(clientSocket);
                executor.execute(worker);
            }

        } catch (IOException e) {
            System.out.println(
                    "Exception caught when trying to listen on port"
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}

