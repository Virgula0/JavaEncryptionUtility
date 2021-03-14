package OperatingSystem;

public abstract class OperatingSystemAbstract {

    private final OperatingSystemAbstract obJ;

    public OperatingSystemAbstract(OperatingSystemAbstract obj) {
        this.obJ = obj;
    }

    public abstract String[] clearTerminalCommand();

    public OperatingSystemAbstract matches(OperatingSystemAbstract a) {
        if (this.obJ != null) {
            return this.obJ.matches(a);
        }
        return null;
    }

}
