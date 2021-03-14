package Encryption;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * -s <512|1024|2048|4096....|inf> Specify the key size, multiples of 2!!
 * Default is set to 512
 * You may need to specify -s option when encrypting/decrypting physical files
 * -fkpr <FILENAME> Specify format private key with a PEM format from a file
 * -fkpu <FILENAME> Specify format public key with a PEM format from a file
 * -wkpr <FILENAME> Creates a file with the latest private key used in PEM format
 * -wkpu <FILENAME> Creates a file with the latest public key used in PEM format
 * -i <FILENAME> Input file encrypted/decrypted
 * -o <FILENAME> Output file to specify if you want to encrypt/decrypt an entire file.
 * -v Set verbose to true
 * Default is set to false
 **/
public interface AsyncEncryption extends Encryption {
    String getPEMPublicKey();

    String getPEMPrivateKey();

    void setKeySize(int size) throws NoSuchAlgorithmException;

    void setPublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException;

    void setPrivateKey(String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException;

    void writePublicKeyToFile(File file) throws IOException;

    void writePrivateKeyToFile(File file) throws IOException;
}
