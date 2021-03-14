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
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class FinalEncryptionFromTerminal extends ObjBuilderRSAAbstract {
    public FinalEncryptionFromTerminal(ObjectBuilderRSA object) {
        super(object);
    }

    @Override
    public ObjectBuilderRSA matchBuilder(AsyncEncryption encryptionInstance, List<String> args, List<Logger> loggers) throws ArgumentsNotValidException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, Base64NotValid {
        if (!args.contains("encrypt") && !args.contains("decrypt") && !args.contains("encryptFile") && !args.contains("decryptFile")) {
            throw new ArgumentsNotValidException("You must specify if you want to encrypt or decrypt.");
        }

        if (args.contains("encrypt")) {
            long start = System.currentTimeMillis();
            //Encryption process
            loggers.forEach(x -> x.log("[Encryption process started]"));
            //Thread.sleep(1000);
            String input = "";
            File fileInputPresent = super.getInputSettedOrNot();
            if (fileInputPresent != null) {
                input = String.join("\n", Files.readAllLines(fileInputPresent.toPath()));
            } else {
                input = args.get(args.size() - 1);
                if (input.isEmpty() || input.contains("-"))
                    throw new ArgumentsNotValidException("You must insert your input to encrypt/decrypt as the latest argument or use input files to encrypt a content instead");
            }
            String output = encryptionInstance.encryptMessage(input);
            loggers.forEach(x -> x.log("----------- Encrypted content ----------- \n" + output + "\n"));
            loggers.forEach(x -> x.log("---------- Private Key used ---------\n" + encryptionInstance.getPEMPrivateKey() + "\n"));
            loggers.forEach(x -> x.log("---------- Public key used--------- \n" + encryptionInstance.getPEMPublicKey() + "\n"));

            File fileOutputPresent = super.getOutputSettedOrNot();

            if (fileOutputPresent != null) {
                loggers.forEach(x -> x.log("[Saving results in the file]"));
                Files.write(fileOutputPresent.toPath(), output.getBytes());
            }
            long stop = System.currentTimeMillis();
            loggers.forEach(x -> x.log("[Encryption process finished. Elapsed=> " + (stop - start) / 1000F + " seconds]"));
        }
        return super.matchBuilder(encryptionInstance, args, loggers);
    }

}
