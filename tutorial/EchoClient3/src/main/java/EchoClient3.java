import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClient3 {
    public static void main(String[] args) {
    
        /* コマンドライン引数処理 */
        if (args.length != 3) {
            System.err.println(
                "Usage: java EchoClinet <host name> <port number> <filename>");
            System.exit(1);
        }

        for (String s : args) {
            System.out.println(s);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String fileName = args[2];
        
        File f = new File(fileName);
        if (!f.isFile()) {
            System.err.println("正しいファイル名を指定してください。");
            System.exit(1);
        }

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
            /* Scanner stdIn = new Scanner(System.in); */
            Scanner fileIn = new Scanner(f);
                
        ) {
            while (fileIn.hasNext()) {
                out.println(fileIn.next());
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

