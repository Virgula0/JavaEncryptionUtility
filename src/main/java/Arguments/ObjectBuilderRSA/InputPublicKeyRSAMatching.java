package Arguments.ObjectBuilderRSA;

import Encryption.AsyncEncryption;
import Exceptions.ArgumentsNotValidException;
import Exceptions.Base64NotValid;
import Loggers.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class InputPublicKeyRSAMatching extends ObjBuilderRSAAbstract {
    public InputPublicKeyRSAMatching(ObjectBuilderRSA object) {
        super(object);
    }

    @Override
    public ObjectBuilderRSA matchBuilder(AsyncEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidKeyException {
        if (args.contains("-fkpu")) {
            int position = args.indexOf("-fkpu");
            String value = args.get(position + 1);
            if (value.isEmpty())
                throw new ArgumentsNotValidException("-fkpu argument value cannot be empty");
            loggers.forEach(x -> x.log("[PEM Public Key from input file detected => " + value + ""));
            loggers.forEach(x -> x.log("    Previous generated public key will be deleted]"));
            encryptionInstance.setPublicKey(
                    String.join("\n", Files.readAllLines(Paths.get(value))));
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
