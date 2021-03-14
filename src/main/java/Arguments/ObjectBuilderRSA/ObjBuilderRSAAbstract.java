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

public abstract class ObjBuilderRSAAbstract implements ObjectBuilderRSA {
    private static File fileI = null;
    private static File fileO = null;
    private final ObjectBuilderRSA obj;

    public ObjBuilderRSAAbstract(ObjectBuilderRSA object) {
        this.obj = object;
    }

    @Override
    public ObjectBuilderRSA matchBuilder(AsyncEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, Base64NotValid {
        if (this.obj != null) {
            return this.obj.matchBuilder(encryptionInstance, args, loggers);
        }
        return null;
    }

    @Override
    public void setFileInputEncryptOrDecrypt(File fileP) {
        fileI = fileP;
    }

    @Override
    public File getInputSettedOrNot() {
        return fileI;
    }

    @Override
    public void setFileOutputEncryptOrDecrypt(File fileP) {
        fileO = fileP;
    }

    @Override
    public File getOutputSettedOrNot() {
        return fileO;
    }
}
