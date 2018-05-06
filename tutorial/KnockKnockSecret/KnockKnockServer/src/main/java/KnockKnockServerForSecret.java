import java.net.*;
import java.io.*;

public class KnockKnockServerForSecret {
    public static void main(String[] args) {

        /* 引数処理 */
        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
            /* リソース確保 */
            ServerSocket serverSocket = new ServerSocket(portNumber);
            /* クライアントの接続待ち */
            Socket clientSocket = serverSocket.accept();

            /* クライアント出力用ストリーム取得 */
            PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);

            /* クライアント入力用ストリーム取得 */
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    clientSocket.getInputStream()));
        ) {

            String inputLine, outputLine;

            /* プロトコル生成 */
            SecretProtocol sp = 
                new SecretProtocol("ibaraki", "jyoso");

            /* 最初のプロトコルを取得 */
            outputLine = sp.processInput(null);

            /* 最初のデータをクライアントに送信 */
            out.println(outputLine);

            /* 引き続きクライアントを会話を行う */
            while ((inputLine = in.readLine()) != null) {
                outputLine = sp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port" + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

