import java.io.*;
import java.util.*;
import java.net.*;
import javax.xml.bind.*;

public class WritePLC3 {

    private static final String subHeader = "5000";
    private static final String networkNumber = "00";
    private static final String PCNumber = "FF";
    private static final String reqIOUnitNumber = "03FF";
    private static final String reqStationNumber = "00";
    //private static final String reqDataLength = "001C";
    private static final String watchCPUTimer = "0010";

    private static final String command = "1401";
    private static final String subCommand = "0000";
    private static final String deviceCode = "D*";
    private static final String numberOfFirstDevice = "000000";
    private static final String numberOfDevice = "000A";
    //private static final String sendData = "5A5A";

    public static void main(String[] args) {
    

        /* コマンドライン引数処理 */
        if (args.length != 2) {
            System.err.println("Usage: java WritePLC3 <host name> <port number>");
            System.exit(1);
        }

        for (String s : args) {
            System.out.println(s);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        /* 送信データの取得 */
        String sendData = null;
        try (
            Scanner sc = new Scanner(System.in);
        ) {
            System.out.println("Input send data: ");
            sendData = sc.next();
        }

        /* 送信データ長の取得 */
        int num = Integer.parseInt(numberOfDevice, 16);
        /* watchCPUTimerからnumOfDeviceまで24バイト
         * 1つの書込みデータに4バイト必要
         */
        int l = 24 + num * 4;
        String reqDataLength = String.format("%04X", l);
        System.out.println("reqDataLength: " + reqDataLength);

        /* 送信データの組み立て */
        String qHeader = subHeader + networkNumber + PCNumber +
            reqIOUnitNumber + reqStationNumber + reqDataLength +
            watchCPUTimer;

        String characterPart = command + subCommand + deviceCode +
            numberOfFirstDevice + numberOfDevice;

        StringBuilder sb = new StringBuilder(characterPart);
        for (int i = 0; i < num; i++) {
            sb.append(sendData);
        }

        String d = qHeader + sb.toString();

        //d = "500000FF03FF000018000A04010000D*000000000A";

        System.out.println("送信データ: " + d);

	try (
            /* ソケットの生成 */
            Socket echoSocket = new Socket(hostName, portNumber);

            OutputStream out = echoSocket.getOutputStream();

            BufferedInputStream in = new BufferedInputStream(
                echoSocket.getInputStream());

        ) {
            // データ送信
            out.write(d.getBytes("US-ASCII"));
            out.flush();

            /* データの受信 */
            /* 
             * サブヘッダ:          4バイト
             * ネットワーク番号:    2バイト
             * PC番号:              2バイト
             * 要求ユニットI/O番号: 4バイト
             * 要求ユニット局番号:  2バイト
             * 応答データ長:        4バイト
             * 終了コード:          4バイト
             */
            byte[] res = new byte[22];
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

