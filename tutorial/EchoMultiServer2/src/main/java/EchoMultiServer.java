import java.io.*;
import java.util.*;
import java.net.*;

public class EchoMultiServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        int maxThread = 3;
        Thread[] th = new Thread[maxThread];
        //Arrays.fill(th, null);
    
        try (
            ServerSocket serverSocket =
                new ServerSocket(portNumber);
        ) {

            while (true) {

                 /* 配列に空きがあればスレッドを生成 */
                for (int i = 0; i < th.length; i++) {
                    if (th[i] == null) {
                        Socket socket = serverSocket.accept();
                        th[i] = new EchoMultiServerThread(socket);
                        th[i].start();
                        System.out.println("Added index of " + i);
                        printThreadsStatus(th);
                    }
                }

                /* スレッドの生存確認 */
                for (int i = 0; i < th.length; i++) {
                    if (th[i] != null) {
                        if (!th[i].isAlive()) {
                            th[i] = null;
                            System.out.println("Dead index of " + i);
                            printThreadsStatus(th);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(
                    "Exception caught when trying to listen on port"
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    /* スレッド管理用配列の状態を表示 */
    private static void printThreadsStatus(Thread th[]) {

        System.out.print("Thread[");
        for (int i = 0; i < th.length; i++) {
            if (th[i] == null)
                System.out.print("X");
            else
                System.out.print("O");
        }
        System.out.println("]");
    }

}

