package Arguments.ObjectBuilderAES;

import Encryption.SimmetricEncryption;
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

public class VerbosityMatching extends ObjBuilderAESAbstract {

    public VerbosityMatching(ObjBuilderAES aes) {
        super(aes);

    }


    @Override
    public ObjBuilderAES matchBuilder(SimmetricEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InterruptedException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, Base64NotValid, InvalidKeySpecException, AESSpecifyPassword {
        if (args.contains("-v")) {
            loggers.forEach(x -> x.log("[Verbosity for huge files has set to true.]"));
            encryptionInstance.setVerbosity(true);
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
