package Encryption;

import Loggers.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

public class RSAEncryption implements AsyncEncryption {

    private final List<Logger> loggers;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private int size = 512;
    private boolean sizeChanged = false;
    private boolean verbosity = false;

    //private PublicKey publicKeyOfOtherNode;


    public RSAEncryption(List<Logger> loggers) throws NoSuchAlgorithmException, IOException, InterruptedException, InvalidKeySpecException {
        this.loggers = loggers;
        this.generateKeys(this.size);
    }


    @Override
    public String getPEMPublicKey() {
        return this.generateKeyToPem("-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----", this.publicKey);
    }

    @Override
    public String getPEMPrivateKey() {
        return this.generateKeyToPem("-----BEGIN RSA PRIVATE KEY-----", "-----END RSA PRIVATE KEY-----", this.privateKey);
    }

    @Override
    public void setKeySize(int size) throws NoSuchAlgorithmException {
        this.size = size;
        this.sizeChanged = true;
        this.generateKeys(size);
    }

    @Override
    public void setPublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.publicKey = PublicKeyParser(publicKey.getBytes());
    }

    @Override
    public void writePublicKeyToFile(File file) throws IOException {
        Files.write(file.toPath(), this.getPEMPublicKey().getBytes());
    }

    @Override
    public void writePrivateKeyToFile(File file) throws IOException {
        Files.write(file.toPath(), this.getPEMPrivateKey().getBytes());
    }

    @Override
    public void setPrivateKey(String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.privateKey = PrivateKeyParser(privateKey.getBytes());
    }


    private X509EncodedKeySpec refactoredParserPublicKey(byte[] decodedBytesKey) throws NoSuchAlgorithmException {

        String privateKeyPEM = new String(decodedBytesKey)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("\\n", "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        return new X509EncodedKeySpec(encoded);
    }

    private PKCS8EncodedKeySpec refactoredParserPrivateKey(byte[] decodedBytesKey) throws NoSuchAlgorithmException {
        //-----BEGIN PRIVATE KEY-----
        String privateKeyPEM = new String(decodedBytesKey)
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replaceAll("\\n", "")
                .replace("-----END RSA PRIVATE KEY-----", "");
        //-----END PRIVATE KEY-----
        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        return new PKCS8EncodedKeySpec(encoded);
    }

    private PrivateKey PrivateKeyParser(byte[] decodedBytesKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = refactoredParserPrivateKey(decodedBytesKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private PublicKey PublicKeyParser(byte[] decodedBytesKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = refactoredParserPublicKey(decodedBytesKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    private String generateKeyToPem(String header, String footer, Key key) {

        StringBuilder keypublic = new StringBuilder();

        //-----BEGIN PUBLIC KEY-----
        keypublic.append(header + "\n");
        keypublic.append(Base64.getMimeEncoder().encodeToString(key.getEncoded()) + "\n");
        keypublic.append(footer + "\n");
        //-----END PUBLIC KEY-----
        //String keyEncodedPublic = Base64.getEncoder().encodeToString(keypublic.toString().getBytes());
        return keypublic.toString();
        //this.publicKeyOfOtherNode = PublicKeyParser(decodedBytesKey);
    }

    private void generateKeys(int size) throws NoSuchAlgorithmException {
        this.loggers.forEach(x -> x.log("[Generating new key pairs with size " + size + "]"));
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(size);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    @Override
    public String encryptMessage(String encrypt) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(encrypt.getBytes()));
    }

    @Override
    public String decryptMessage(String decrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        return
                new String(cipher.doFinal(
                        Base64.getDecoder().decode(decrypt)));
    }

    /**
     * RSA is not a good choice to encrypt big amount of data.
     * Consider to use AES for files instead. Use the encryptFile and decryptFile
     * methods only for testing on files with a size of few Kbs.
     *
     * @param inputFile
     * @param outputFile
     * @throws IOException
     */
    @Override
    public void encryptFile(File inputFile, File outputFile) throws IOException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException {
        long length = Files.size(inputFile.toPath());
        long sizeMB = (length / 1024) / 1024;
        if (sizeMB > 20) {
            loggers.forEach(x -> x.log("[WARNING] [File is bigger than 20MB. RSA does not work properly with huge files]"));
            loggers.forEach(x -> x.log("[It can require a lot of time. Use AES instead]]"));
        }
        if (!this.sizeChanged)
            loggers.forEach(x -> x.log("[WARNING] [Key size -s option has not set this could be a problem in decryption phase. Content will be encrypted with default generated key pair. Pay attention.]"));

        loggers.forEach(x -> x.log("[File size is : " + length + " bytes] "));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[(this.size / 8) - 11];
        int bytesRead;
        int totalRead = 0;
        byte[] outputBytes = null;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            long finalBytesRead = totalRead += buffer.length;
            if (verbosity)
                loggers.forEach(x -> x.changeableLogger("[" + (int) ((finalBytesRead * 100) / length) + "%]Processing " + finalBytesRead + " over " + length + "\n"));
            cipher.update(buffer, 0, bytesRead);
            outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        }
        inputStream.close();
        outputStream.close();
    }

    /**
     * RSA is not a good choice to encrypt big amount of data.
     * Consider to use AES for files instead.. Use the encryptFile and decryptFile
     * methods only for testing on files with a size of few Kbs.
     *
     * @param inputFile
     * @param outputFile
     * @throws IOException
     */
    @Override
    public void decryptFile(File inputFile, File outputFile) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        long length = Files.size(inputFile.toPath());
        long sizeMB = (length / 1024) / 1024;

        if (sizeMB > 20) {
            loggers.forEach(x -> x.log("[WARNING] [File is bigger than 20MB. RSA does not work properly with huge files]"));
            loggers.forEach(x -> x.log("[It can require a lot of time. Use AES instead]]"));
        }
        if (!this.sizeChanged)
            loggers.forEach(x -> x.log("[WARNING] [Key size -s option has not set this could be a problem in decryption phase. Content will be decrypted with default key size. Pay attention.]"));

        loggers.forEach(x -> x.log("[File size is : " + length + " bytes]"));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[this.size / 8];
        int bytesRead;
        int totalRead = 0;
        byte[] outputBytes = null;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            long finalBytesRead = totalRead += bytesRead;
            if (verbosity)
                loggers.forEach(x -> x.changeableLogger("[" + (int) ((finalBytesRead * 100) / length) + "%]Processing " + finalBytesRead + " over " + length + "\n"));
            cipher.update(buffer, 0, bytesRead);
            outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        }
        inputStream.close();
        outputStream.close();
    }

    @Override
    public void setVerbosity(boolean value) {
        this.verbosity = value;
    }
}