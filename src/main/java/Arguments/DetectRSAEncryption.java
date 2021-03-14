package Arguments;

import Arguments.ObjectBuilderRSA.*;
import Encryption.AsyncEncryption;
import Encryption.Encryption;
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

public class DetectRSAEncryption extends DetectArguments {
    public DetectRSAEncryption(Arguments obj) {
        super(obj);
    }

    @Override
    public Encryption matches(List<String> args, List<Logger> loggers) throws InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, AESSpecifyPassword, ArgumentsNotValidException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, Base64NotValid {
        if (args.contains("-bRSA")) {
            // initialize constructors with factory class utility
            ObjectBuilderRSA builderRSA =
                    new SizeKeyRSAMatching(
                            new VerbosityMatchingRSA(
                                    new InputPublicKeyRSAMatching(
                                            new InputPrivateKeyRSAMatching(
                                                    new InputFileRSAMatching(
                                                            new OutputFileRSAMatching(
                                                                    new WritePrivateKeyRSAMatching(
                                                                            new WritePublicKeyRSAMatching(
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
                    );
            AsyncEncryption instance = FactoryClassUtility.RSAInstance(loggers);
            ObjectBuilderRSA t = builderRSA.matchBuilder(instance, args, loggers);
            return instance;
        }
        return super.matches(args, loggers);
    }
}
