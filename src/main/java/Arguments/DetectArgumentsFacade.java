package Arguments;

import Encryption.Encryption;
import Exceptions.AESSpecifyPassword;
import Exceptions.ArgumentsNotValidException;
import Exceptions.Base64NotValid;
import Loggers.Logger;
import OperatingSystem.Linux;
import OperatingSystem.OperatingSystemAbstract;
import OperatingSystem.Windows;
import Utilites.FactoryClassUtility;
import main.main;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class DetectArgumentsFacade implements Runnable {

    private final List<String> args;
    private final List<Logger> logger;

    public DetectArgumentsFacade(List<String> arguments, List<Logger> log) {
        this.args = arguments;
        this.logger = log;
    }

    public List<Logger> getLoggers() {
        return this.logger;
    }

    public void detectArguments() throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, AESSpecifyPassword, ArgumentsNotValidException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, Base64NotValid {
        Arguments encryptionsAvailable = new DetectAESEncryption(
                new DetectRSAEncryption(null));

        OperatingSystemAbstract op = new Linux(
                new Linux(
                        new Windows(null)
                )
        );
        OperatingSystemAbstract finalOP = op.matches(null);
        this.logger.add(FactoryClassUtility.getShellLogger(finalOP));
        encryptionsAvailable.setLoggers(logger);
        Encryption enc = encryptionsAvailable.matches(args, this.logger);
        if (enc == null) {
            main.usage();
            throw new ArgumentsNotValidException("You must choose to use RSA or AES with -b option");
        }
    }

    @Override
    public void run() {
        try {
            this.detectArguments();
            System.exit(0);
        } catch (Exception ex) {
            System.out.println("Exception raised while processing: " + ex);
            System.exit(-1);
        }
    }
}
