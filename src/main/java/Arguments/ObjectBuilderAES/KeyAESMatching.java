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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class KeyAESMatching extends ObjBuilderAESAbstract {

    public KeyAESMatching(ObjBuilderAES aes) {
        super(aes);
    }


    @Override
    public ObjBuilderAES matchBuilder(SimmetricEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, NoSuchAlgorithmException, AESSpecifyPassword, InvalidKeySpecException, BadPaddingException, InterruptedException, InvalidKeyException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidAlgorithmParameterException {
        if (args.contains("-k")) {
            int position = args.indexOf("-k");
            String value = args.get(position + 1);
            if (value.isEmpty())
                throw new ArgumentsNotValidException("-k argument value cannot be empty");
            loggers.forEach(x -> x.log("[File input location for the KEY Specified => " + value + "]"));
            encryptionInstance.setKey(
                    String.join("\n", Files.readAllLines(Paths.get(value)))
                            .replaceAll(" ", "")
                            .replaceAll("\n", ""));
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
