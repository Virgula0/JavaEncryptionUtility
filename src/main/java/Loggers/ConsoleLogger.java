package Loggers;

import OperatingSystem.OperatingSystemAbstract;
import Utilites.FactoryClassUtility;

import java.io.IOException;
import java.util.Date;

public class ConsoleLogger implements Logger {

    private final StringBuilder builder;
    private final StringBuilder changeableBuilder;
    private final OperatingSystemAbstract op;
    private boolean changed = false;
    private String tempPhrase = "";

    public ConsoleLogger(OperatingSystemAbstract op) {
        builder = new StringBuilder();
        changeableBuilder = new StringBuilder();
        this.op = op;
    }

    @Override
    public String log(String toLog) {
        //I use a single append call to avoid asynch problems since we are using threads
        String extracted = "[" + FactoryClassUtility.dateInstance().format(new Date()) +
                "] " + toLog + "\n";
        builder.append(extracted);
        changeableBuilder.append(extracted);
        return builder.toString();
    }

    @Override
    public String getLogs() {
        String copy = "";
        if (this.builder.length() > 0) {
            copy = this.builder.toString();
            this.builder.setLength(0);
        }
        return copy;
    }

    @Override
    public void changeableLogger(String str) {
        if (tempPhrase.isEmpty()) {
            changeableBuilder.append(str);
            tempPhrase = str;
        } else {
            int pos = changeableBuilder.indexOf(tempPhrase);
            changeableBuilder.replace(pos, changeableBuilder.length(), str);
            tempPhrase = str;
            this.changed = true;
        }
    }

    @Override
    public String getHasChangedLog() throws IOException {
        boolean cng = this.changed;
        if (cng) {
            this.changed = false;
            new ProcessBuilder(this.op.clearTerminalCommand()).inheritIO().start();
            return changeableBuilder.toString();
        }
        return null;
    }

}
