package Encryption;

import Exceptions.AESSpecifyPassword;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * -s <128 | 192 | 256> specify the size of the key with one of the shown value.
 * Default is 128.
 * -fiv <FILELOCATION> specify the hexadecimal file where specified VI islocated
 * -p <PASS:SALT> specify a password for encryption/decryption with the specified PASS and SALT
 * -k <KEYFILELOCATION> specify location for setting a specific Base64 encoded key
 * -a <CBS|CFB|OFB|CTR|GCM> choose alghorithm type
 * Default is CBS
 * -wiv <FILENAME> Creates a file with the latest VI used or the specified one if there is.
 * -wk <FILENAME> Creates a file with the latest Key Encoded value used or the specified one if there is.
 * -i <FILENAME> Input file encrypted/decrypted or physical file to encrypt/decrypt.
 * -o <FILENAME> Output file to specify if you want to encrypt/decrypt an entire physical file or
 * where you want to save encrypted/decrypted content.
 * -v Set verbose to true
 * Default is set to false
 **/
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

    /**
     * Write Iv Generated (Or set with setIv method) in a file.
     *
     * @param file
     * @throws IOException
     */
    void writeIv(File file) throws IOException;

    /**
     * Write Key Generated (Or set with setKey method) in a file.
     *
     * @param file
     * @throws IOException
     */
    void writeKey(File file) throws IOException;

    /**
     * Set size with the generated key which will be used when encrypting/decrypting.
     *
     * @param size
     */
    void setSize(int size);

    /**
     * Set algorithm default: AES/CBC/PKCS5Padding
     * Replace CBS with CFB OR OFB OR CTR OR GCM.
     *
     * @param algo
     */
    void setAlgo(String algo);

    /**
     * Get the generated IV or setted IV as a string of bytes
     *
     * @return
     */
    String getIV();

    /**
     * Get the generated key as Base64 encoded String value.
     *
     * @return
     */
    String getKey();

    /**
     * Set a generated password, should be a valid AES generated base64 encoded key.\
     *
     * @param password set the encoded password for the AES cipher
     */
    void setKey(String password);

}