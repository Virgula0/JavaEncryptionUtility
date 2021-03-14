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

public class SizeKeyAESMatching extends ObjBuilderAESAbstract {

    public SizeKeyAESMatching(ObjBuilderAES aes) {
        super(aes);

    }


    @Override
    public ObjBuilderAES matchBuilder(SimmetricEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InterruptedException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, Base64NotValid, InvalidKeySpecException, AESSpecifyPassword {
        if (args.contains("-s")) {
            int position = args.indexOf("-s");
            String value = args.get(position + 1);
            if (value.isEmpty())
                throw new ArgumentsNotValidException("-s argument value cannot be empty");
            loggers.forEach(x -> x.log("[Size Key Option has been specified. Value => " + value + "]"));
            int v = Integer.parseInt(value);
            if (v != 128 && v != 192 && v != 256)
                throw new ArgumentsNotValidException("Key size supported: 128 or 192 or 256");
            encryptionInstance.setSize(v);
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
