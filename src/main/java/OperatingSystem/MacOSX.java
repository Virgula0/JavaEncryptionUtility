package OperatingSystem;

public class MacOSX extends OperatingSystemAbstract {
    public MacOSX(OperatingSystemAbstract obj) {
        super(obj);
    }

    @Override
    public String[] clearTerminalCommand() {
        return new String[]{"/bin/bash", "clear"};
    }

    @Override
    public OperatingSystemAbstract matches(OperatingSystemAbstract a) {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            return this;
        }
        return super.matches(a);
    }
}
