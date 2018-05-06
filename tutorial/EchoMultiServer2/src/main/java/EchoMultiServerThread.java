import java.io.*;
import java.net.*;

public class EchoMultiServerThread extends Thread {
    
    private Socket clientSocket;

    public EchoMultiServerThread(Socket ck) {
        this.clientSocket = ck;
    }

    public void run() { 

        try (
                
            PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("EchoMutiSeverThread Error!");
            System.out.println(e.getMessage());
        }
    }
}

