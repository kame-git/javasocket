import java.io.*;
import java.net.*;

public class EchoServer3 {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
            ServerSocket serverSocket =
                new ServerSocket(portNumber);

        ) {

            for (int i = 0;  i < 3; i++) {
                try (
                    Socket clientSocket = serverSocket.accept();

                    PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                            clientSocket.getInputStream()));
                ) {

                    /* リモートのIPアドレスを取得 */
                    String remoteIpAddress = 
                        clientSocket.getInetAddress().getHostAddress();
                    /* リモートのポート番号を取得 */
                    int remotePortNumber = clientSocket.getPort();

                    System.out.println("Remote IP: " + remoteIpAddress);
                    System.out.println("Remote Port: " + remotePortNumber);

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        out.println(inputLine);
                        System.out.println("Client: " + inputLine);
                    }
                } 
            }
        } catch (IOException e) {
            System.out.println(
                "Exception caught when trying to listen on port" + 
                portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

