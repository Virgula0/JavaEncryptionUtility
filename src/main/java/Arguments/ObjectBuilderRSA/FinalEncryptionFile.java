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

public class FinalEncryptionFile extends ObjBuilderRSAAbstract {

    public FinalEncryptionFile(ObjectBuilderRSA object) {
        super(object);
    }

    @Override
    public ObjectBuilderRSA matchBuilder(AsyncEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, Base64NotValid {
        if (!args.contains("encrypt") && !args.contains("decrypt") && !args.contains("encryptFile") && !args.contains("decryptFile")) {
            throw new ArgumentsNotValidException("You must specify if you want to encrypt or decrypt. ");
        }

        if (args.contains("encryptFile")) {
            long start = System.currentTimeMillis();
            loggers.forEach(x -> x.log("[Encryption file process started]"));
            //Thread.sleep(1000);
            String input = "";
            File fileInputPresent = super.getInputSettedOrNot();
            File fileOutputPresent = super.getOutputSettedOrNot();
            if (fileInputPresent == null || fileOutputPresent == null) {
                throw new ArgumentsNotValidException("You must specify the file to encrypt/decrypt with -i and output with -o");
            }
            encryptionInstance.encryptFile(fileInputPresent, fileOutputPresent);
            loggers.forEach(x -> x.log("----------- Encrypted content ----------- \nSaved in:" + fileOutputPresent.getAbsolutePath() + "\n"));
            loggers.forEach(x -> x.log("---------- Private Key used ---------\n" + encryptionInstance.getPEMPublicKey() + "\n"));
            loggers.forEach(x -> x.log("---------- Private Key used--------- \n" + encryptionInstance.getPEMPrivateKey() + "\n"));

            long stop = System.currentTimeMillis();
            loggers.forEach(x -> x.log("[Encryption process finished. Elapsed=> " + (stop - start) / 1000F + " seconds]"));
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }
}
