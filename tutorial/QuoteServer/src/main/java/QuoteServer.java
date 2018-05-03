import java.net.*;
import java.io.*;

public class QuoteServer {
    public static void main(String[] args) {

        try {

            QuoteServerThread task = 
                new QuoteServerThread("QuoteServer");
            task.start();

        } catch (IOException e) {
            System.err.println("Interrupted!");
            System.exit(1);
        }
    }
}

