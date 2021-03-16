package Arguments;

import Arguments.ObjectBuilderAES.*;
import Encryption.Encryption;
import Encryption.SimmetricEncryption;
import Exceptions.AESSpecifyPassword;
import Exceptions.ArgumentsNotValidException;
import Exceptions.Base64NotValid;
import Loggers.Logger;
import Utilites.FactoryClassUtility;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class DetectAESEncryption extends DetectArguments {
    public DetectAESEncryption(Arguments obj) {
        super(obj);
    }

    @Override
    public Encryption matches(List<String> args, List<Logger> loggers) throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, AESSpecifyPassword, ArgumentsNotValidException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, Base64NotValid {
        if (args.contains("-bAES")) {
            // initialize constructors with factory class utility
            ObjBuilderAES objectBuilder =
                    new VerbosityMatching(
                            new SizeKeyAESMatching(
                                    new KeyAESMatching(
                                            new IVAESMatching(
                                                    new AlgorithmAESMatching(
                                                            new PasswordAESMatching(
                                                                    new OutputFileAESMatching(
                                                                            new InputFileAESMatching(
                                                                                    new WriteKEYAESMatching(
                                                                                            new WriteIVAESMatching(
                                                                                                    new FinalEncryptionFromTerminal(
                                                                                                            new FinalDecryptionFromTerminal(
                                                                                                                    new FinalEncryptionFile(
                                                                                                                            new FinalDecryptionFile(
                                                                                                                                    null
                                                                                                                            )
                                                                                                                    )
                                                                                                            )
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )));
            SimmetricEncryption instance = FactoryClassUtility.AESInstance(loggers);
            ObjBuilderAES t = objectBuilder.matchBuilder(instance, args, loggers);
            return instance;
        }
        return super.matches(args, loggers);
    }

}
