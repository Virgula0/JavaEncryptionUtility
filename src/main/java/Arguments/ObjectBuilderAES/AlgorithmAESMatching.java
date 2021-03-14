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

public class AlgorithmAESMatching extends ObjBuilderAESAbstract {

    public AlgorithmAESMatching(ObjBuilderAES aes) {
        super(aes);
    }


    @Override
    public ObjBuilderAES matchBuilder(SimmetricEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, NoSuchAlgorithmException, AESSpecifyPassword, InvalidKeySpecException, BadPaddingException, InterruptedException, InvalidKeyException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidAlgorithmParameterException {
        if (args.contains("-a")) {
            int position = args.indexOf("-a");
            String value = args.get(position + 1);
            if (value.isEmpty())
                throw new ArgumentsNotValidException("-a argument value cannot be empty");
            loggers.forEach(x -> x.log("[Algorithm request detected => " + value + "]"));
            encryptionInstance.setAlgo("AES/" + value.toUpperCase() + "/PKCS5Padding");
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
