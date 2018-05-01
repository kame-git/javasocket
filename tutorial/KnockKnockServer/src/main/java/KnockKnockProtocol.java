import java.net.*;
import java.io.*;

public class KnockKnockProtocol {

    /* 状態の定義 */
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;

    /* ジョークの数 */
    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;

    private String[] clues = {
        "Turnip", "Little Old Lady", "Atch", "Who", "Who"
    };
    private String[] answers = {
        "Turnip the head, it's cold in here!",
        "I ditn't know you could yodel!",
        "Bless you!",
        "Is there an owl in here?",
        "Is there an echo in here?"
    };

    public String processInput(String theInput) {

        String theOutput = null;

        if (state == WAITING) {

        } else if (state == SENTKNOCKKNOCK) {
           
        } else if (state == SENTCLUE) {

        } else if (state == ANOTHER) {

        }
        return theOutput;
    }
}

