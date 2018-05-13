public class PushButton {

    private String terminal;
    private String name;
    private PilotLamp target;
    private ButtonRole role;
    
    public void setTerminal(String t) {
        this.terminal = t;
    }

    public void setName(String n) {
        this.name = n;
    }

    public boolean setTarget(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof PilotLamp)) {
            return false;
        }
        this.target = (PilotLamp) o;

        return true;
    }

    public void setRole(ButtonRole r) {
        this.role = r;
    }

    public boolean press() {
        if (role == ButtonRole.OFF) {
            if (target.turnOff()) {
                return true;
            } else {
                return false;
            }
        } else if (role == ButtonRole.ON) {
            if (target.turnOn()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    @Override
    public String toString()
    {
        return "PushButton name is " + name + "." +
            "Connected terminal : " + terminal;
    }

}

