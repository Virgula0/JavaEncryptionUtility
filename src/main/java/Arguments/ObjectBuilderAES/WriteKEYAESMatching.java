package Arguments.ObjectBuilderAES;

import Encryption.SimmetricEncryption;
import Exceptions.AESSpecifyPassword;
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

public class WriteKEYAESMatching extends ObjBuilderAESAbstract {

    public WriteKEYAESMatching(ObjBuilderAES aes) {
        super(aes);
    }


    @Override
    public ObjBuilderAES matchBuilder(SimmetricEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, NoSuchAlgorithmException, AESSpecifyPassword, InvalidKeySpecException, BadPaddingException, InterruptedException, InvalidKeyException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidAlgorithmParameterException {
        if (args.contains("-wk")) {
            //SHOULD BE CALLED AFTER KEYAESMATCHING SO IN THE CHAIN SHOULD BE THE FIRST
            //SAME FOR WRITEVIAAESMATCHING
            int position = args.indexOf("-wk");
            String value = args.get(position + 1);
            if (value.isEmpty())
                throw new ArgumentsNotValidException("-wk argument value cannot be empty");
            loggers.forEach(x -> x.log("[The latest KEY used will be saved in => " + value + "]"));
            encryptionInstance.writeKey(new File(value));
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
