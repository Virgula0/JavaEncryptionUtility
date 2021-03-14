package OperatingSystem;

public class Windows extends OperatingSystemAbstract {

    public Windows(OperatingSystemAbstract obj) {
        super(obj);
    }

    @Override
    public String[] clearTerminalCommand() {
        return new String[]{"cmd.exe", "/c", "cls"};
    }

    @Override
    public OperatingSystemAbstract matches(OperatingSystemAbstract a) {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return this;
        }
        return super.matches(a);
    }


}
