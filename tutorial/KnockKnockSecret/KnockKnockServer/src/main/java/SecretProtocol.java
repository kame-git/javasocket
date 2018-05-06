import java.net.*;
import java.io.*;

public class SecretProtocol {

    /* 秘密の鍵 */
    private String key1;
    private String key2;

    /* 状態の定義 */
    private static final int WAITING = 0;
    private static final int SECRET1 = 1;
    private static final int SECRET2 = 2;
    private static final int SUCCESS = 3;

    private int state = WAITING;

    private String[] questions = {
        "Input secret keyword1: ",
        "Input secret keyword2: "
    };

    public SecretProtocol(String key1, String key2)
    {
        this.key1 = key1;
        this.key2 = key2;
    }

    public String processInput(String theInput) {

        String theOutput = null;

        if (state == WAITING) {
            theOutput = questions[0];
            state = SECRET1;
        } else if (state == SECRET1) {
            if (theInput.equalsIgnoreCase(key1)) {
                theOutput = questions[1];
                state = SECRET2; 
            } else {
                theOutput = "Who are you? Goodbye.";
                state = WAITING; 
            }
        } else if (state == SECRET2) {
            if (theInput.equalsIgnoreCase(key2)) {
                theOutput = "OK My secret is ..." + "Continue(y/n)? ";
                state = SUCCESS;
            } else {
                theOutput = "Who are you? Goodbye.";
                state = WAITING; 
            }
        } else if (state == SUCCESS) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = questions[0];
                state = SECRET1;
            } else {
                theOutput = "Bye.";
                state = WAITING;
            }
        }
        return theOutput;
    }
}

