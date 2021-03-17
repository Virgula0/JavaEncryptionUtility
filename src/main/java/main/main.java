package main;

import Arguments.DetectArgumentsFacade;
import Exceptions.ArgumentsNotValidException;
import Loggers.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {

    public static void usage() {
        String usage = "Usage: java -jar utilityEncryption.jar encrypt -bAES \"my secret content goes here\" \n" +
                "\n|-----------------------------REQUIRED ARGUMENTS--------------------------|\n" +
                " <encrypt/decrypt | encryptFile/decryptFile> Choose if you want to encrypt or decrypt data                         \n" +
                " -b<AES | RSA> Choose between RSA and AES encryption                                                               \n" +
                " \"content to encrypt goes here\"                                                                                  " +
                "\n|-----------------------------OPTIONAL ARGUMENTS FOR AES--------------------------|\n" +
                "      -s <128 | 192 | 256> specify the size of the key with one of the shown value.                                \n" +
                "              Default is 128.                                                                                      \n" +
                "      -fiv <FILELOCATION> Import from a file the hexadecimal IV.                                \n" +
                "      -p <PASS:SALT> set a password for encryption/decryption with the specified PASS and SALT                 \n" +
                "      -k <KEYFILELOCATION> Import a Base64 encoded key from a file                             \n" +
                "      -a <CBC|CFB|OFB|CTR> Choose an algorithm type                                                              \n" +
                "              Default is CBS                                                                                       \n" +
                "      -wiv <FILENAME> Creates a file and writes the latest IV used (or specified)                      \n" +
                "      -wk <FILENAME> Creates a file with the latest Key Encoded value used (or specified)        \n" +
                "      -i <FILENAME> Input file encrypted/decrypted or physical file to encrypt/decrypt.                            \n" +
                "      -o <FILENAME> Output file to specify if you want to encrypt/decrypt an entire physical file or               \n" +
                "                    where you want to save encrypted/decrypted content.                                            \n" +
                "      -v Set verbose to true (Use it only for encryptFile/decryptFile when files are big)                                                                                      \n" +
                "              Default is set to false                                                                              \n" +
                "|-----------------------------OPTIONAL ARGUMENTS FOR RSA--------------------------|\n" +
                "      -s <512|1024|2048|4096....|inf> Set the key size, powers of 2!!                                      \n" +
                "              Default is set to 512                                                                               \n" +
                "              You may need to specify -s option when encrypting/decrypting physical files                         \n" +
                "      -fkpr <FILENAME> Import a private key with a PEM format from a file                                  \n" +
                "      -fkpu <FILENAME> Import a public key with a PEM format from a file                                    \n" +
                "      -wkpr <FILENAME> Creates a file with the latest private key used in PEM format                              \n" +
                "      -wkpu <FILENAME> Creates a file with the latest public key used in PEM format                               \n" +
                "      -i <FILENAME> Input file encrypted/decrypted                                                               \n" +
                "      -o <FILENAME> Output file to specify if you want to encrypt/decrypt an entire physical file or               \n" +
                "                          where you want to save encrypted/decrypted content.                         \n" +
                "      -v Set verbose to true (Use it only for encryptFile/decryptFile when files are big)                                                                                     \n" +
                "              Default is set to false                                                                             \n" +
                "";
        System.out.println(usage);
    }

    public static void main(String... args) throws InterruptedException, IOException, ArgumentsNotValidException {

        if (args.length == 0) {
            usage();
            throw new ArgumentsNotValidException("You need to specify input arguments");
        }

        if (args[0].toLowerCase().equals("-h") || args[0].toLowerCase().equals("--help") || args[0].toLowerCase().equals("--h")) {
            usage();
            System.exit(0);
        }

        List<String> arguments = Arrays.asList(args);

        DetectArgumentsFacade instance = new DetectArgumentsFacade(arguments, new ArrayList<>());
        Thread a = new Thread(instance);
        a.start();

        List<Logger> loggers = instance.getLoggers();
        boolean bool = false;

        if (arguments.contains("-v"))
            bool = true;

        while (true) {
            for (Logger x : loggers) {
                String s = x.getHasChangedLog();
                String z = x.getLogs();
                if (z != null && !z.isEmpty())
                    System.out.print(z);
                if (s != null) {
                    System.out.print(s);
                    if (bool)
                        Thread.sleep(250);
                }
            }
        }
    }
}
