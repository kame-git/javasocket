import java.io.*;
import java.net.*;
import javax.xml.bind.*;

public class HelloPLC {

    private static final String subHeader = "5000";
    private static final String networkNumber = "00";
    private static final String PCNumber = "FF";
    private static final String reqIOUnitNumber = "03FF";
    private static final String reqStationNumber = "00";
    private static final String reqDataLength = "0014";
    private static final String watchCPUTimer = "0010";

    private static final String command = "0619";
    private static final String subCommand = "0000";
    private static final String dataLength = "0004";
    private static final String sendData = "5A5A";

    public static void main(String[] args) {
    

        /* コマンドライン引数処理 */
        if (args.length != 2) {
            System.err.println("Usage: java HelloPLC <host name> <port number>");
            System.exit(1);
        }

        for (String s : args) {
            System.out.println(s);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        /* 送信データの組み立て */
        String d = 
            subHeader + networkNumber + PCNumber + reqIOUnitNumber + reqStationNumber +
            reqDataLength + watchCPUTimer + command + subCommand + dataLength + sendData;

        d = "500000FF03FF000018000A04010000D*000000000A";

        System.out.println("送信データ: " + d);

	try (
            /* ソケットの生成 */
            Socket echoSocket = new Socket(hostName, portNumber);

            /* 出力ストリームの取得 */
            //PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            OutputStream out = echoSocket.getOutputStream();

            /* 入力ストリームの取得 */
            //BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedInputStream in = new BufferedInputStream(
                echoSocket.getInputStream());

            /* 標準入力のストリームを取得 */
            BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in))
                
        ) {
            /* ユーザー入力処理 */
/*            String userInput;

            System.out.println("データを送信します。Enterキーを押してください。");

            userInput = stdIn.readLine();
*/
            /* データの送信 */
            //out.write(sendByteData);
            out.write(d.getBytes("US-ASCII"));
            out.flush();

            /* データの受信 */
            byte[] res = new byte[30];
            int len = in.read(res);

            //String resStr = DatatypeConverter.printHexBinary(res);
            String resStr = new String(res, "US-ASCII");
            System.out.println("ehoo: " + resStr);
                    
	} catch(UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch(IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
	}
    }
}

