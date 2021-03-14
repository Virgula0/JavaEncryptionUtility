package Loggers;

import java.io.IOException;

public interface Logger {
    String log(String toLog);

    String getLogs();

    void changeableLogger(String str);

    String getHasChangedLog() throws IOException, InterruptedException;
}
