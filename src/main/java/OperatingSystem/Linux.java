package OperatingSystem;

public class Linux extends OperatingSystemAbstract {
    public Linux(OperatingSystemAbstract obj) {
        super(obj);
    }

    @Override
    public String[] clearTerminalCommand() {
        return new String[]{"/bin/bash", "clear"};
    }

    @Override
    public OperatingSystemAbstract matches(OperatingSystemAbstract a) {
        if (System.getProperty("os.name").toLowerCase().contains("nix")) {
            return this;
        }
        return super.matches(a);
    }
}
