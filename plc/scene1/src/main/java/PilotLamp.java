public class PilotLamp {

    String terminal;
    String name;

    enum State {
        OFF, ON
    };
    private State state = State.OFF;
    
    public void setTerminal(String t) {
        this.terminal = t;
    }

    public void setName(String n) {
        this.name = n;
    }

    public boolean turnOn() {
        state = state.ON;
        return true;
    }

    public boolean turnOff() {
        state = state.OFF;
        return true;
    }

    public String getStatusString() {
        String message = null;

        if (state == state.OFF)
            message = name + "は消灯中です。";
        else if (state == state.ON)
            message = name + "は点灯中です。";

        return message;
    }

    @Override
    public String toString() {
        return "PilotLamp name is " + name + "." +
            "Connected terminal : " + terminal;
    }
}

