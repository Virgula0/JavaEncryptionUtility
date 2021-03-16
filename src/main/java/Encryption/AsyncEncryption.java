package Encryption;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * <b>Terminal commands</b> <p>
 * -s <512|1024|2048|4096....|inf> Specify the key size, multiples of 2!! <p>
 * Default is set to 512 <p>
 * You may need to specify -s option when encrypting/decrypting physical files <p>
 * -fkpr <FILENAME> Specify format private key with a PEM format from a file <p>
 * -fkpu <FILENAME> Specify format public key with a PEM format from a file <p>
 * -wkpr <FILENAME> Creates a file with the latest private key used in PEM format <p>
 * -wkpu <FILENAME> Creates a file with the latest public key used in PEM format <p>
 * -i <FILENAME> Input file encrypted/decrypted <p>
 * -o <FILENAME> Output file to specify if you want to encrypt/decrypt an entire file. <p>
 * -v Set verbose to true <p>
 * Default is set to false <p>
 **/
public interface AsyncEncryption extends Encryption {
    /**
     * Get Public Key in PEM Format
     *
     * @return PEM public key as a string
     */
    String getPEMPublicKey();

    /**
     * Get Private Key in PEM Format
     *
     * @return PEM private key as a string
     */
    String getPEMPrivateKey();

    /**
     * Set the size key. Should be a power of 2. 2^10=1024 for example.
     *
     * @return void
     */
    void setKeySize(int size) throws NoSuchAlgorithmException;

    /**
     * Set the public key. You should provide a public key with the following format:
     * <p>
     * -----BEGIN PUBLIC KEY----- <p>
     * XXXXXXXXXXXXXXXXXXXXXXXXXX <p>
     * -----END PUBLIC KEY----- <p>
     *
     * @param publicKey
     * @throws InvalidKeySpecException  The Specified key is not in the correct format.
     * @throws NoSuchAlgorithmException Algorithm not found.
     */
    void setPublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException;

    /**
     * Set the private key. You should provide a private key with the following format:
     * <p>
     * -----BEGIN RSA PRIVATE KEY----- <p>
     * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX <p>
     * -----END RSA PRIVATE KEY----- <p>
     *
     * @param privateKey
     * @throws InvalidKeySpecException The Specified key is not in the correct format.
     * @throws NoSuchAlgorithmException Algorithm not found.
     */
    void setPrivateKey(String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException;

    /**
     * Write the generated or set public key into a file with a PEM format.
     *
     * @param file
     * @throws IOException File does not exists or you don't have privileges to read it.
     */
    void writePublicKeyToFile(File file) throws IOException;

    /**
     * Write the generated or set private key into a file with a PEM format.
     *
     * @param file
     * @throws IOException File does not exists or you don't have privileges to read it.
     */
    void writePrivateKeyToFile(File file) throws IOException;
}
