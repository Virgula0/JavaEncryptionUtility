# JavaEncryptionUtility
A handy utility to quickly encrypt/decrypt strings from terminal and files using AES/RSA and avoiding the use of OpenSSL binaries.

### Summary

* [Arguments List](#arguments-list) <br>
* [Quick use](#quick-use) <br>
* [API](#use-in-your-projects)

## Arguments List

```
                 ----------------------------------REQUIRED ARGUMENTS--------------------------
                 <encrypt/decrypt | encryptFile/decryptFile> Choose if you want to encrypt or decrypt data                        
                 -b<AES | RSA> Choose between RSA and AES encryption                                                               
                 \"content to encrypt goes here\"                                                                                  
                 --------------------------OPTIONAL ARGUMENTS FOR AES--------------------------
                 -s <128 | 192 | 256> specify the size of the key with one of the shown value.                                
                          Default is 128.                                                                                      
                 -fiv <FILELOCATION> specify the hexadecimal file where specified VI islocated                                
                 -p <PASS:SALT> specify a password for encryption/decryption with the specified PASS and SALT                 
                 -k <KEYFILELOCATION> specify location for setting a specific Base64 encoded key                              
                 -a <CBS|CFB|OFB|CTR|GCM> choose alghorithm type                                                              
                          Default is CBS                                                                                       
                 -wiv <FILENAME> Creates a file with the latest VI used or the specified one if there is.                     
                 -wk <FILENAME> Creates a file with the latest Key Encoded value used or the specified one if there is.       
                 -i <FILENAME> Input file encrypted/decrypted or physical file to encrypt/decrypt.                            
                 -o <FILENAME> Output file to specify if you want to encrypt/decrypt an entire physical file or               
                          where you want to save encrypted/decrypted content.                                            
                 -v Set verbose to true (Use it only for encryptFile/decryptFile when files are big)                                                                         
                          Default is set to false                                                                              
                  ---------------------------------OPTIONAL ARGUMENTS FOR RSA--------------------------
                  -s <512|1024|2048|4096....|inf> Specify the key size, multiples of 2!!                                      
                          Default is set to 512                                                                               
                   You may need to specify -s option when encrypting/decrypting physical files                         
                  -fkpr <FILENAME> Specify format private key with a PEM format from a file                                   
                  -fkpu <FILENAME> Specify format public key with a PEM format from a file                                    
                  -wkpr <FILENAME> Creates a file with the latest private key used in PEM format                              
                  -wkpu <FILENAME> Creates a file with the latest public key used in PEM format                               
                  -i <FILENAME> Input file encrypted/decrypted                                                                
                  -o <FILENAME> Output file to specify if you want to encrypt/decrypt an entire file.                         
                  -v Set verbose to true (Use it only for encryptFile/decryptFile when files are big)                                                                                                       Default is set to false                                                                             
```
## Quick Use

#### Examples

```
1)Encrypt a string from terminal with AES. It will create files with iv and key respectively with names: iv.txt and key.txt
java -jar JavaEncrytionUtility.jar encrypt -bAES -wiv iv.txt -wk key.txt -s 256 -a CBC "ThisContentWillBeEncrypted"

2)Encrypt a string from terminal with RSA writing the generated key pair in private.txt and public.txt files.
java -jar JavaEncrytionUtility.jar encrypt -bRSA -s 4096 -wkpr private.txt -wkpu public.txt "ThisContentWillBeEncrypted"

3)Decrypt a string from terminal with AES using iv and key generated in the (1) step.
java -jar JavaEncrytionUtility.jar decrypt -bAES -s 256 -a CBC -fiv iv.txt -k key.txt "EncryptedStringFromStep1"

4)Decrypt a string from terminal with RSA reading the generated key pair from files generated from step (2). Supported format for private and public key is PEM, use your own if you have.
java -jar JavaEncrytionUtility.jar decrypt -bRSA -s 4096 -fkpr private.txt -fkpu public.txt "EncryptedContentFromTheStep2"

5)Encrypt the content of a file and saving it into an output file with AES.
java -jar JavaEncrytionUtility.jar encrypt -bAES -wiv iv.txt -wk key.txt -s 256 -a CBC -i inputToEncrypt.txt -o outputEncrypted.txt

6)Encrypt the content of a file and saving it into an output file with RSA.
java -jar JavaEncrytionUtility.jar encrypt -bRSA -s 4096 -wkpr private.txt -wkpu public.txt -i inputToEncrypt.txt -o outputEncrypted.txt

7)Decrypt the content of a file from terminal with AES using encrypted output file from the step (5)
java -jar JavaEncrytionUtility.jar decrypt -bAES -fiv iv.txt -k key.txt -s 256 -a CBC -i outputEncrypted.txt -o decrypted.txt

8)Decrypt the content of a file from terminal with RSA using encrypted output file from the step (6)
java -jar JavaEncrytionUtility.jar decrypt -bRSA -s 4096 -fkpr private.txt -fkpu public.txt -i outputEncrypted.txt -o decrypted.txt

9)Encrypt a file with AES (The difference with the example 5 is that this method is specifically coded also for huge files)
java -jar JavaEncrytionUtility.jar encryptFile -bAES -wiv iv.txt -wk key.txt -s 256 -a CBC -i fileToEncrypt.txt -o saveEncrypted.txt

10)Encrypt a file with RSA (The difference with the example 6 is that this method is specifically coded also for huge files)
WARNING: RSA is not made for encrypt/decrypt huge files. It is extremely slow. Use it only for educational purpose or with files which their size is few kbs.
java -jar JavaEncrytionUtility.jar encryptFile -bRSA -s 4096 -wkpr private.txt -wkpu public.txt -i inputToEncrypt.txt -o outputEncrypted.txt

11)Decrypt a file with AES 
java -jar JavaEncrytionUtility.jar decryptFile -bAES -fiv iv.txt -k key.txt -s 256 -a CBC -i saveEncrypted.txt -o decrypted.txt

10)Decrypt a file with RSA 
WARNING: RSA is not made for encrypt/decrypt huge files. It is extremely slow. Use it only for educational purpose or with files which their size is few kbs.
java -jar JavaEncrytionUtility.jar decryptFile -bRSA -s 4096 -fkpr private.txt -fkpu public.txt -i outputEncrypted.txt -o decrypted.txt

```

You can also consider to use binaries frovided for each operating system instead of the Jar file.

## Use in your projects

### Import
Import the jar in your project. How to do it depends on what IDE you're using.

### AES

```java
        OperatingSystemAbstract o = new Windows(new Linux(new MacOSX(null))); //Detect Operating System
        OperatingSystemAbstract op = o.matches(null);

        List<Logger> loggers = new ArrayList<>();
        loggers.add(new ConsoleLogger(op));
        SimmetricEncryption enc = new AESEncryption(loggers);

        //Set a personalized size
        enc.setSize(256);

        //Write Iv and Key in a File
        enc.writeIv(new File("iv.txt"));
        enc.writeKey(new File("key.txt"));

        //Get the generated Key and Iv
        String generatedKey = enc.getKey();
        String generatedIv = enc.getIV();

        enc.setKey(generatedKey);
        enc.setIv(generatedIv);
        String encrypted = enc.encryptMessage("encrypt Me");
        System.out.println("Encrypted: "+encrypted + "\nDecrypted: " + enc.decryptMessage(encrypted));
        loggers.forEach(x -> System.out.print(x.getLogs())); //Print logs infos
```

### RSA

```java
        OperatingSystemAbstract o = new Windows(new Linux(new MacOSX(null))); //Detect Operating System
        OperatingSystemAbstract op = o.matches(null);

        List<Logger> loggers = new ArrayList<>();
        loggers.add(new ConsoleLogger(op));
        AsyncEncryption enc = new RSAEncryption(loggers);

        //set a key personal key size
        enc.setKeySize(4096);

        //write key pair in files
        enc.writePublicKeyToFile(new File("public.txt"));
        enc.writePrivateKeyToFile(new File("private.txt"));

        String encrypted = enc.encryptMessage("encrypt Me");
        System.out.println("Encrypted: "+encrypted + "\nDecrypted: " + enc.decryptMessage(encrypted));
        loggers.forEach(x -> System.out.print(x.getLogs())); //Print logs infos

```

For the remaining methods and infos check generated JavaDoc. 


