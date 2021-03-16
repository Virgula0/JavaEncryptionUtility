package Encryption;


import Exceptions.AESSpecifyPassword;
import Exceptions.ArgumentsNotValidException;
import Exceptions.Base64NotValid;
import Loggers.Logger;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.Objects;

public class AESEncryption implements SimmetricEncryption {
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.ISO_8859_1);
    private final List<Logger> loggers;
    boolean settedPersonalKey = false;
    boolean settedPersonalIV = false;
    private int size = 128;
    private IvParameterSpec iv;
    private SecretKey key;
    private String algorithm = "AES/CBC/PKCS5Padding";
    private boolean verbosity = false;


    public AESEncryption(List<Logger> loggers) throws NoSuchAlgorithmException {
        this.key = generateKey(this.size);
        this.iv = generateIv();
        this.loggers = loggers;
    }

    public SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    @Override
    public void writeKey(File file) throws IOException {
        Files.write(file.toPath(), Base64.encodeBase64(this.key.getEncoded()));
        loggers.forEach(x -> x.log("Key wrote into: " + file.toPath()));
    }

    @Override
    public void setSize(int size) throws NoSuchAlgorithmException {
        this.key = generateKey(size);
        this.size = size;
    }

    @Override
    public void setAlgo(String algo) {
        this.algorithm = algo;
    }

    @Override
    public String getIV() {
        return bytesToHex(this.iv.getIV());
    }

    @Override
    public String getKey() {
        return Base64.encodeBase64String(this.key.getEncoded());
    }

    @Override
    public void setKey(String password) {
        byte[] bytes = Base64.decodeBase64(password);
        this.key = new SecretKeySpec(bytes, 0, bytes.length, "AES");
        this.settedPersonalKey = true;
    }

    // The following 2 methods were get from StackOverflow, nothing too much difficult but it avoided me to
    // waste a lot of time.

    private byte[] hexStringToByteArray(String s) {
        if (s.startsWith("0x"))
            s = s.replace("0x", "");
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    @Override
    public void writeIv(File file) throws IOException {
        Files.write(file.toPath(), bytesToHex(this.iv.getIV()).getBytes());
        loggers.forEach(x -> x.log("IV wrote into: " + file.toPath()));
    }


    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public void setKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException, AESSpecifyPassword {
        if (password == null || Objects.equals(password, ""))
            throw new AESSpecifyPassword("Specified password is empty or not inserted");
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
        this.key = secret;
    }

    @Override
    public void setIv(String iv) {
        this.iv = new IvParameterSpec(hexStringToByteArray(iv));
        this.settedPersonalIV = true;
    }


    @Override
    public String encryptMessage(String encrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(this.algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, this.key, this.iv);
        byte[] cipherText = cipher.doFinal(encrypt.getBytes());
        return Base64.encodeBase64String(cipherText);
    }

    @Override
    public String decryptMessage(String decrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException, Base64NotValid, ArgumentsNotValidException {
        if (!settedPersonalKey || !settedPersonalIV)
            throw new ArgumentsNotValidException("For decryping you need to set both a KEY and IV from a file");

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        if (!Base64.isBase64(decrypt)) {
            throw new Base64NotValid("The inserted string should be a Base64 valid encoded string", this.loggers);
        }
        byte[] plainText = cipher.doFinal(Base64.decodeBase64(decrypt));
        return new String(plainText);
    }


    @Override
    public void encryptFile(File inputFile, File outputFile) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        long length = Files.size(inputFile.toPath());
        loggers.forEach(x -> x.log("File length is : " + length));
        Cipher cipher = Cipher.getInstance(this.algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, this.key, iv);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[64];
        int bytesRead;
        long totalRead = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            long finalBytesRead = totalRead += buffer.length;
            if ((finalBytesRead % 393216 == 0 || length < 393216) && verbosity)
                loggers.forEach(x -> x.changeableLogger("[" + (int) ((finalBytesRead * 100) / length) + "%]Processing " + finalBytesRead + " over " + length + "\n"));
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();
    }

    @Override
    public void decryptFile(File inputFile, File outputFile) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException, ArgumentsNotValidException {
        if (!settedPersonalKey || !settedPersonalIV)
            throw new ArgumentsNotValidException("For decryping you need to set both a KEY and IV from a file");

        long length = Files.size(inputFile.toPath());
        loggers.forEach(x -> x.log("File length is : " + length));

        Cipher cipher = Cipher.getInstance(this.algorithm);
        cipher.init(Cipher.DECRYPT_MODE, this.key, this.iv);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[64];
        int bytesRead;
        long totalRead = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            long finalBytesRead = totalRead += buffer.length;
            if ((finalBytesRead % 393216 == 0 || length < 393216) && verbosity)
                loggers.forEach(x -> x.changeableLogger("[" + (int) ((finalBytesRead * 100) / length) + "%]Processing " + finalBytesRead + " over " + length + "\n"));
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();
    }

    @Override
    public void setVerbosity(boolean value) {
        this.verbosity = value;
    }
}
