package Arguments.ObjectBuilderRSA;

import Encryption.AsyncEncryption;
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

public class VerbosityMatchingRSA extends ObjBuilderRSAAbstract {

    public VerbosityMatchingRSA(ObjectBuilderRSA rsa) {
        super(rsa);

    }


    @Override
    public ObjectBuilderRSA matchBuilder(AsyncEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws InvalidKeySpecException, ArgumentsNotValidException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidKeyException {
        if (args.contains("-v")) {
            loggers.forEach(x -> x.log("[Verbosity for huge files has set to true.]"));
            encryptionInstance.setVerbosity(true);
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
