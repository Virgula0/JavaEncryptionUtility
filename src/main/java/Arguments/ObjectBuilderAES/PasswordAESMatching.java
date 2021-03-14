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

public class PasswordAESMatching extends ObjBuilderAESAbstract {

    public PasswordAESMatching(ObjBuilderAES aes) {
        super(aes);
    }


    @Override
    public ObjBuilderAES matchBuilder(SimmetricEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, NoSuchAlgorithmException, AESSpecifyPassword, InvalidKeySpecException, BadPaddingException, InterruptedException, InvalidKeyException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidAlgorithmParameterException {
        if (args.contains("-p")) {
            int position = args.indexOf("-p");
            String value = args.get(position + 1);
            if (value.isEmpty())
                throw new ArgumentsNotValidException("-p argument value cannot be empty");
            String pass = value;
            String salt = ""; //if it's empty it will throw an exception anyway
            if (value.contains(":")) {
                pass = value.split(":")[0];
                salt = value.split(":")[1];
            }
            loggers.forEach(x -> x.log("[Pass+Salt detected => " + value + "]"));
            encryptionInstance.setKeyFromPassword(pass, salt);
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
