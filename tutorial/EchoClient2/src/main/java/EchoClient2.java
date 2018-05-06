import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClient2 {
    public static void main(String[] args) {
    
        /* コマンドライン引数処理 */
        if (args.length != 3) {
            System.err.println(
                "Usage: java EchoClinet <host name> <port number> <data>");
            System.exit(1);
        }

        for (String s : args) {
            System.out.println(s);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String sendData = args[2];

		try (
            /* ソケットの生成 */
            Socket echoSocket = new Socket(hostName, portNumber);

            /* 出力ストリームの取得 */
            PrintWriter out = new PrintWriter(
                echoSocket.getOutputStream(), true);
            
            Scanner sockIn = new Scanner(
                new InputStreamReader(
                    echoSocket.getInputStream()));

            /* 標準入力のストリームを取得 */
            Scanner stdIn = new Scanner(System.in);
                
        ) {
            /* ユーザー入力処理 */
            /* while (stdIn.hasNext()) {
                out.println(stdIn.next());
                System.out.println("echo: " + sockIn.next());
            } */

            for (int i = 0; i < 10; i++) {
                out.println(sendData);
                System.out.println("echo: " + sockIn.next());
                Thread.sleep(1000);
            }

		} catch(UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	        } catch(IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " + hostName);
	            System.exit(1);
		} catch (InterruptedException e) {
	            System.err.println("割込みが発生しました");
	            System.exit(1);
        }
    }
}

