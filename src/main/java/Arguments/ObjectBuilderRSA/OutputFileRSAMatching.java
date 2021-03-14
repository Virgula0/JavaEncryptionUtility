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

public class OutputFileRSAMatching extends ObjBuilderRSAAbstract {
    public OutputFileRSAMatching(ObjectBuilderRSA object) {
        super(object);
    }

    @Override
    public ObjectBuilderRSA matchBuilder(AsyncEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidKeyException {
        if (args.contains("-o")) {
            int position = args.indexOf("-o");
            String value = args.get(position + 1);
            if (value.isEmpty())
                throw new ArgumentsNotValidException("-o argument value cannot be empty");
            loggers.forEach(x -> x.log("[The encrypted/decrypted content will be saved in => " + value + "]"));
            super.setFileOutputEncryptOrDecrypt(new File(value));
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }

}
