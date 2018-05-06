import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClient1 {
    public static void main(String[] args) {
    
        /* コマンドライン引数処理 */
        if (args.length != 2) {
            System.err.println("Usage: java EchoClinet <host name> <port number>");
            System.exit(1);
        }

        for (String s : args) {
            System.out.println(s);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

		try (
            /* ソケットの生成 */
            Socket echoSocket = new Socket(hostName, portNumber);

            /* 出力ストリームの取得 */
            PrintWriter out = new PrintWriter(
                echoSocket.getOutputStream(), true);
            
            /* 入力ストリームの取得 */
            /* BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    echoSocket.getInputStream())); */

            Scanner sockIn = new Scanner(
                new InputStreamReader(
                    echoSocket.getInputStream()));

            /* 標準入力のストリームを取得 */
            Scanner stdIn = new Scanner(System.in);
                
        ) {
            /* ユーザー入力処理 */
            while (stdIn.hasNext()) {
                out.println(stdIn.next());
                /* System.out.println("echo: " + in.readLine()); */
                System.out.println("echo: " + sockIn.next());
            }
		} catch(UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	    } catch(IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " + hostName);
	            System.exit(1);
		}
    }
}

