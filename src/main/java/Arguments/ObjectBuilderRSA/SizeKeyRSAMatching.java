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

public class SizeKeyRSAMatching extends ObjBuilderRSAAbstract {
    public SizeKeyRSAMatching(ObjectBuilderRSA object) {
        super(object);
    }

    @Override
    public ObjectBuilderRSA matchBuilder(AsyncEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidKeyException {
        if (args.contains("-s")) {
            int position = args.indexOf("-s");
            String value = args.get(position + 1);
            if (value.isEmpty())
                throw new ArgumentsNotValidException("-s argument value cannot be empty");
            loggers.forEach(x -> x.log("[Size Key Option has been specified. Value => " + value + "]"));
            int v = Integer.parseInt(value);
            encryptionInstance.setKeySize(v);
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }


}
