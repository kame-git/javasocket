public class PilotLamp {

    String terminal;
    String name;
    private int status;

    public void setTerminal(String t) {
        this.terminal = t;
    }

    public void setName(String n) {
        this.name = n;
    }

    public boolean turnOn() {
        return false;
    }

    public boolean turnOff() {
        return false;
    }

    public String getStatusString() {
        String message = null;

        if (status == 0)
            message = name + "は消灯中です。";
        else if (status == 1)
            message = name + "は点灯中です。";
        else if (status == -1)
            message = name + "の情報を取得できません。";

        return message;
    }

    @Override
    public String toString() {
        return "PilotLamp name is " + name + "." +
            "Connected terminal : " + terminal;
    }
}

