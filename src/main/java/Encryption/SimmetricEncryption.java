package Encryption;

import Exceptions.AESSpecifyPassword;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * -s <128 | 192 | 256> specify the size of the key with one of the shown value.                                \n" +
 * Default is 128.                                                                                      \n" +
 * -fiv <FILELOCATION> specify the hexadecimal file where specified VI islocated                                \n" +
 * -p <PASS:SALT> specify a password for encryption/decryption with the specified PASS and SALT                 \n" +
 * -k <KEYFILELOCATION> specify location for setting a specific Base64 encoded key                              \n" +
 * -a <CBS|CFB|OFB|CTR|GCM> choose alghorithm type                                                              \n" +
 * Default is CBS                                                                                       \n" +
 * -wiv <FILENAME> Creates a file with the latest VI used or the specified one if there is.                     \n" +
 * -wk <FILENAME> Creates a file with the latest Key Encoded value used or the specified one if there is.       \n" +
 * -i <FILENAME> Input file encrypted/decrypted or physical file to encrypt/decrypt.                            \n" +
 * -o <FILENAME> Output file to specify if you want to encrypt/decrypt an entire physical file or               \n" +
 * where you want to save encrypted/decrypted content.                                            \n" +
 * -v Set verbose to true                                                                                       \n" +
 * Default is set to false
 */
public interface SimmetricEncryption extends Encryption {
    /**
     * The method can be used to set a password to encrypt data
     *
     * @param password
     * @param salt
     * @return It can be used to encrypt data with a custom password+salt
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    void setKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException, AESSpecifyPassword;

    /**
     * Specify a specific IV For AES encryption.
     *
     * @param iv: Accept an string of an HEX format. Example: 318BF554BCAEB2497EC72EB77465B018
     * @return void
     */
    void setIv(String iv);

    void writeIv(File file) throws IOException;

    void writeKey(File file) throws IOException;

    void setSize(int size);

    void setAlgo(String algo);

    String getIV();

    String getKey();

    /**
     * Set a generated password, should be a valid AES generated base64 encoded key.\
     *
     * @param password set the encoded password for the AES cipher
     */
    void setKey(String password);
}