package Arguments.ObjectBuilderRSA;

import Encryption.AsyncEncryption;
import Exceptions.ArgumentsNotValidException;
import Exceptions.Base64NotValid;
import Loggers.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class InputFileRSAMatching extends ObjBuilderRSAAbstract {
    public InputFileRSAMatching(ObjectBuilderRSA object) {
        super(object);
    }

    @Override
    public ObjectBuilderRSA matchBuilder(AsyncEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidKeyException {
        if (args.contains("-i")) {
            int position = args.indexOf("-i");
            String value = args.get(position + 1);
            if (value.isEmpty())
                throw new ArgumentsNotValidException("-i argument value cannot be empty");
            loggers.forEach(x -> x.log("[Input file to encrypt/decrypt detected => " + value + "]"));
            super.setFileInputEncryptOrDecrypt(new File(value));
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
