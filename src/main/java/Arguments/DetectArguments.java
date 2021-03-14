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

public abstract class DetectArguments implements Arguments {
    private final Arguments obj;
    private List<Logger> loggers;

    public DetectArguments(Arguments obj) {
        this.obj = obj;
    }


    @Override
    public Encryption matches(List<String> args, List<Logger> loggers) throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, AESSpecifyPassword, ArgumentsNotValidException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, Base64NotValid {
        if (this.obj != null) {
            return this.obj.matches(args, loggers);
        }
        return null;
    }

    @Override
    public List<Logger> getLoggers() {
        return this.loggers;
    }

    @Override
    public void setLoggers(List<Logger> loggers) {
        this.loggers = loggers;
    }
}
