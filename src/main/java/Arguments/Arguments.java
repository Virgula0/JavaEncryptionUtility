package Arguments;

import Encryption.Encryption;
import Exceptions.AESSpecifyPassword;
import Exceptions.ArgumentsNotValidException;
import Exceptions.Base64NotValid;
import Loggers.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface Arguments {
    Encryption matches(List<String> args, List<Logger> loggers) throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, AESSpecifyPassword, ArgumentsNotValidException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, Base64NotValid;

    List<Logger> getLoggers();

    void setLoggers(List<Logger> loggers);
}
