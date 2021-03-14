package Utilites;

import Encryption.AESEncryption;
import Encryption.AsyncEncryption;
import Encryption.RSAEncryption;
import Encryption.SimmetricEncryption;
import Loggers.ConsoleLogger;
import Loggers.Logger;
import OperatingSystem.OperatingSystemAbstract;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FactoryClassUtility {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");

    public static SimpleDateFormat dateInstance() {
        return sdf;
    }

    public static SimmetricEncryption AESInstance(List<Logger> loggers) throws NoSuchAlgorithmException {
        return new AESEncryption(loggers);
    }

    public static AsyncEncryption RSAInstance(List<Logger> loggers) throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        return new RSAEncryption(loggers);
    }

    public static Logger getShellLogger(OperatingSystemAbstract op) {
        return new ConsoleLogger(op);
    }

}
