import java.io.*;
import java.net.*;

public class ReadPLC2 {

    private static final String subHeader = "5000";
    private static final String networkNumber = "00";
    private static final String PCNumber = "FF";
    private static final String reqIOUnitNumber = "03FF";
    private static final String reqStationNumber = "00";
    private static final String reqDataLength = "0018";
    //private static final String reqDataLength = "0017"; //Error!
    //private static final String reqDataLength = "0019"; //Error!
    private static final String watchCPUTimer = "0010";

    private static final String command = "0401";
    private static final String subCommand = "0000";
    private static final String deviceCode = "D*";
    private static final String firstDeviceAddress = "000000";
    private static final String numberOfDevice = "0001";

    private static final int errorFlameSize = 40;
    private static final int startOfErrorCode = 18;
    private static final int interval = 5000;

    public static void main(String[] args) {
    

        /* コマンドライン引数処理 */
        if (args.length != 2) {
            System.err.println("Usage: java ReadPLC2 <host name> <port number>");
            System.exit(1);
        }

        for (String s : args) {
            System.out.println(s);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        /* 送信データの組み立て */
        String d = 
            subHeader + networkNumber + PCNumber + reqIOUnitNumber + 
            reqStationNumber + reqDataLength + watchCPUTimer + 
            command + subCommand + deviceCode + firstDeviceAddress + 
            numberOfDevice;

        System.out.println("送信データ: " + d);

	try (
            /* ソケットの生成 */
            Socket clientSocket = new Socket(hostName, portNumber);

            /* 出力ストリームの取得 */
            OutputStream out = clientSocket.getOutputStream();

            /* 入力ストリームの取得 */
            BufferedInputStream in = new BufferedInputStream(
                clientSocket.getInputStream());

        ) {
            /* 受信データの準備 */
            /* サブヘッダ:4 */
            /*
             *  ネットワーク番号: 2
             *  PC番号: 2
             *  要求先ユニットI/O番号: 4
             *  要求先局番号: 2
             *  応答データ長: 4
             *  終了コード: 4
             */
            int len = 4+ 18 + Integer.parseInt(numberOfDevice, 16) * 4;
            if (len < errorFlameSize) {
                len = errorFlameSize;
            }
            System.out.println("Receive data size : " + len);
            byte[] res = new byte[len];

            for (int i = 0; i < 5; i++) {
                /* データの送信 */
                out.write(d.getBytes("US-ASCII"));
                out.flush();

                /* データの受信 */
                clientSocket.setSoTimeout(2000); 
                try {
                    int lenRcv = in.read(res);
                } catch (SocketTimeoutException e) {
                    System.err.println("Time out!");
                    System.exit(1);
                }

                String resStr = new String(res, "US-ASCII");
                System.out.println("ehoo: " + resStr);

                /* エラーコードを取得 */
                String errorCode = resStr.substring(
                    startOfErrorCode, startOfErrorCode+4);
                if (!errorCode.equals("0000")) {
                    System.out.println("Error: " + errorCode);
                    System.exit(1);
                } else {
                    System.out.println("Success");
                }

                Thread.sleep(interval);
            }
                    
        } catch (InterruptedException e) {
            System.err.println("Interrupted Thread.sleep().");
            System.exit(1);
	} catch(UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch(IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
	}
    }
}

