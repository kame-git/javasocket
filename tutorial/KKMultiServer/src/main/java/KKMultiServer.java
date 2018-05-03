import java.net.*;
import java.io.*;

public class KKMultiServer {
    public static void main(String[] args) {

        /* 引数処理 */
        if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (
            ServerSocket serverSocket = new ServerSocket(portNumber)
        ) {
            while (listening) {
                new KKMultiServerThread(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port" + portNumber);
            System.exit(-1); 
        }
    }
}

