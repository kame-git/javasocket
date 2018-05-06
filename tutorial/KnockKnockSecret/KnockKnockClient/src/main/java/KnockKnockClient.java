import java.net.*;
import java.io.*;

public class KnockKnockClient {
    public static void main(String[] args) {

        /* 引数処理 */
        if (args.length != 2) {
            System.err.println("Usage: java KnockKnockClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            /* リソース確保 */
            Socket kkSocket = new Socket(hostName, portNumber);

            /* クライアント出力用ストリーム取得 */
            PrintWriter out = new PrintWriter(
                kkSocket.getOutputStream(), true);

            /* クライアント入力用ストリーム取得 */
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));

            String fromServer, fromUser;

            /* 引き続きクライアントを会話を行う */
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for connection to "
                    + hostName);
            System.exit(1);
        }
    }
}

