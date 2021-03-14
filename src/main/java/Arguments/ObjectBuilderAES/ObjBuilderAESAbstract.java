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

public abstract class ObjBuilderAESAbstract implements ObjBuilderAES {
    private static File fileI = null;
    private static File fileO = null;
    private final ObjBuilderAES obj;

    public ObjBuilderAESAbstract(ObjBuilderAES object) {
        this.obj = object;
    }

    @Override
    public ObjBuilderAES matchBuilder(SimmetricEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, NoSuchAlgorithmException, AESSpecifyPassword, InvalidKeySpecException, BadPaddingException, InterruptedException, InvalidAlgorithmParameterException, IllegalBlockSizeException, Base64NotValid, NoSuchPaddingException, InvalidKeyException {
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

