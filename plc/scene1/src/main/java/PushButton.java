public class PushButton {

    String terminal;
    String name;
    PilotLamp target;

    public void setTerminal(String t) {
        this.terminal = t;
    }

    public void setName(String n) {
        this.name = n;
    }

    public boolean setTaret(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof PilotLamp)) {
            return false;
        }
        this.target = (PilotLamp) o;

        return true;
    }

    public boolean press() {
        return true;
    }


    @Override
    public String toString()
    {
        return "PushButton name is " + name + "." +
            "Connected terminal : " + terminal;
    }

}

