package com.library.lendit_book_kiosk.Security.Custom;

import com.library.lendit_book_kiosk.User.User;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The Secret.class uses AES encryption algorithm to generate a random 256 bit
 * secretKey used to encrypt plaintext string password. The password hash can
 * only be accessed via getter. Both the secretkey and encrypted are needed to
 * decrypt the password back to plaintext.
 *
 * Every implementation of the Java platform is required to support the following
 * standard Cipher transformations with the keysizes in parentheses:
 * AES/CBC/NoPadding (128) <br/>
 * AES/CBC/PKCS5Padding (128) <br/>
 * AES/ECB/NoPadding (128) <br/>
 * AES/ECB/PKCS5Padding (128) <br/>
 * DES/CBC/NoPadding (56) <br/>
 * DES/CBC/PKCS5Padding (56) <br/>
 * DES/ECB/NoPadding (56) <br/>
 * DES/ECB/PKCS5Padding (56) <br/>
 * DESede/CBC/NoPadding (168) <br/>
 * DESede/CBC/PKCS5Padding (168) <br/>
 * DESede/ECB/NoPadding (168) <br/>
 * DESede/ECB/PKCS5Padding (168) <br/>
 * RSA/ECB/PKCS1Padding (1024, 2048) <br/>
 * RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048) <br/>
 * RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048) <br/>
 */
@Entity
@Table(name = "secret")
public class Secret implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Secret.class);
    private static final String ALGORITHM = "AES";  // algorithm
    private static final String KEY_PAIR_ALGORITHM = "RSA"; // KEY PAIR ALGORITHM
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String KEY_FORMAT = "RAW"; // What format to return the key in?
    private static final int GCM_TAG_LENGTH = 16;  // greatest common multiple length
    private static final int GEN_KEY_SIZE = 256;
    private static final int GEN_SEC_KEY_SIZE = 2048;
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "user_id",
            unique = true,
            columnDefinition = "bigint",
            nullable = false)
    private Long Id;
    @Column(
            name = "secretKey",
            columnDefinition = "varbinary(2048)"
    )
    protected SecretKey secretKey = new SecretKey()
    {
        @Override
//        @Column(name = "algorithm",columnDefinition = "varchar(255)")
        public String getAlgorithm() {
            return ALGORITHM;
        }
        @Override
//        @Column(name = "format",columnDefinition = "varchar(255)")
        public String getFormat() {
            return KEY_FORMAT;
        }
        @Override // 16 bit encoding aka block sizes must be a multiples of 16
//        @Column(name = "encoding",columnDefinition = "varbinary(255)")
        public byte[] getEncoded() {
            return new byte[GCM_TAG_LENGTH];
        }
    };
    @Column(
            name = "password",
            columnDefinition = "varchar(255)"
    )
    protected String password = null;
    @Column(
            name = "isInitialized",
            columnDefinition = "bit(1)"
    )
    private boolean initialized = false;
    @OneToMany(mappedBy = "password")
    @PrimaryKeyJoinColumn(name = "user_id")
    private Set<User> user;
    /////////////////////////////////////////////////////////////////
    public Secret() {}

    /**
     * Creates an Secret Object.
     * @param password
     */
    public Secret(String password)
    {
        try{

            log.info("New Raw Secret String: {}", password);
            if (secretKey != null){
                initialized = true;
                log.info(secretKey.toString());
            }
            this.password = encrypt(password,  secretKey);
            log.info("New Encrypted Secret: {}", this.password);
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Encrypts the raw password String and assigns encrypted value
     * to inner password field.
     * @param rawPassword the raw password String
     */
    public Secret setPassword(String rawPassword){
        try{
            if (secretKey != null){
                initialized = true;
                log.info(secretKey.toString());
            }
            log.info("New Raw Secret String: {}", rawPassword);
            this.password = encrypt(rawPassword, secretKey);
            log.info("New Encrypted Secret: {}", this.password);
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Automated method call setSecretKey() will create new random
     * secretKey at runtime or startup.
     * @throws NoSuchAlgorithmException
     */
    @BeforeClass
    protected void setSecretKey() throws NoSuchAlgorithmException {
        try{
            secretKey = generateKey(GEN_KEY_SIZE);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Returns the SecretKey
     * @return the secret key
     */
    public SecretKey getSecretKey(){return secretKey;}

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public Set<User> getUser() {
        return this.user;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }


    public String getPassword() {
        return this.password;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Gets the encrypted password string.
     * @return the encrypted password string
     */
    public String getPasswordToString(){return this.password;}

    /**
     * Tests if rawPassword matches encrypted secret.
     * @param rawPassword a raw secret string
     * @param secret an encrypted secret object
     * @return true if they match, false if no match found
     */
    public static boolean matches(String rawPassword, Secret secret){
        return rawPassword.equalsIgnoreCase(decrypt(secret.getSecretKey(),
                secret.getPasswordToString()));
    }
    /**
     * Tests if rawPassword matches encrypted password.
     * @param password1 an encrypted password string
     * @param password2 an encrypted password string
     * @return true if they match, false if no match found
     */
    public static boolean matches(String password1, String password2){
        return password1.trim().equalsIgnoreCase(password2.trim());
    }
    /**
     * Tests if rawPassword matches encrypted password.
     * @param secret1 an encrypted password Object
     * @param secret2 an encrypted password Object
     * @return true if they match, false if no match found
     */
    public static boolean matches(Secret secret1, Secret secret2){
        return decrypt(secret1.getSecretKey(), secret1.getPasswordToString())
                .equalsIgnoreCase(
                        decrypt(secret2.getSecretKey(), secret2.getPasswordToString())
                );
    }
    /**
     * For generating a secret key, we can use the KeyGenerator class.
     * Let’s define a method for generating the AES key with the size
     * of n (128, 192, and 256) bits.
     * AES (128)
     * DES (56)
     * DESede (168)
     * HmacSHA1
     * HmacSHA256
     * @param size number of bits [128, 192, 256]
     * @return SecretKey
     */
    public SecretKey generateKey(int size) {
        try {
            try {
                KeyGenerator key = KeyGenerator.getInstance(ALGORITHM);
                key.init(size);
                SecretKey secretKey = key.generateKey();
                log.info("Secret Key: {}", secretKey);
                return secretKey;
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e.toString());
            }
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    /**
     * For generating a secret key, we can use the KeyGenerator class.
     * Let’s define a method for generating the AES key with the size
     * of n (128, 192, and 256) bits.
     * AES (128)
     * DES (56)
     * DESede (168)
     * HmacSHA1
     * HmacSHA256
     * @param size number of bits [128, 192, 256]
     * @param algorithm algoritms available include: [ RSA, AES, DES ]
     * @return SecretKey
     */
    public SecretKey generateKey(int size, String algorithm) {
        try {
            KeyGenerator key = KeyGenerator.getInstance(algorithm);
            key.init(size);
            this.secretKey = key.generateKey();
            log.info("Secret Key: {}", this);
            return this.secretKey;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    /**
     * V is a pseudo-random value and has the same size as the block
     * that is encrypted. We can use the SecureRandom class to generate
     * a random IV.
     * @return IvParameterSpec
     */
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[GCM_TAG_LENGTH];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * To implement input string encryption, we first need to generate the
     * secret key and IV according to the previous section. As the next step,
     * we create an instance from the Cipher class by using the getInstance()
     * method.
     * Additionally, we configure a cipher instance using the init() method
     * with a secret key, IV, and encryption mode. Finally, we encrypt the
     * input string by invoking the doFinal() method. This method gets bytes
     * of input and returns ciphertext in bytes
     * @param plaintext
     * @param secretKey
     * @return ciphertext in bytes
     */
    public static String encrypt(String plaintext, SecretKey secretKey) {

        try {
            log.info("Block Size of plaintext: {}",plaintext.getBytes(StandardCharsets.UTF_8).length);
            log.info("SecretKey Algorithm: {}",secretKey.getAlgorithm());
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            final int blockSize = cipher.getBlockSize();
            log.info("Block Size: {}", blockSize);
            byte[] initVector = new byte[blockSize];
            (new SecureRandom()).nextBytes(initVector);
            log.info("Init Vector: {}",initVector.length);
            IvParameterSpec ivSpec =  new IvParameterSpec(initVector);
            log.info("IvParameterSpec: {}", ivSpec.getIV());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encoded = plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            byte[] cipherText = new byte[initVector.length + cipher.getOutputSize(encoded.length)];
            for (int i=0; i < initVector.length; i++)
            {
                cipherText[i] = initVector[i];
            }
            // Perform encryption & return the number of bytes stored in output
            int num_of_encrypted_bits = cipher.doFinal(encoded, 0, encoded.length, cipherText, initVector.length);
            String base64EncodedString = Base64.getEncoder().withoutPadding().encodeToString(cipherText);
            log.info("New Encrypted CipherText Length: {}, " +
                            "\nEncrypted CipherText Value: {}, " +
                            "\nEncoding Length of Original PlainText String: {}, " +
                            "\nNumber of Bits of NEW Encrypted CipherText: {}" +
                            "\nBase64 Encoded String of Encypted CipherText: {}",
                    cipherText.length,
                    cipherText,
                    encoded.length,
                    num_of_encrypted_bits,
                    base64EncodedString);
            return base64EncodedString;
            //  return cipherText;
        } catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * To implement input string encryption, we first need to generate the
     * secret key and IV according to the previous section. As the next step,
     * we create an instance from the Cipher class by using the getInstance()
     * method.
     * Additionally, we configure a cipher instance using the init() method
     * with a secret key, IV, and encryption mode. Finally, we encrypt the
     * input string by invoking the doFinal() method. This method gets bytes
     * of input and returns ciphertext in bytes
     * @param plaintext
     * @param privateKey
     * @return ciphertext in bytes
     */
    public static String encrypt(String plaintext, PrivateKey privateKey) {

        try {
            byte[] cipherText = null;
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            final int blockSize = cipher.getBlockSize();
            log.info("Block Size: {}", blockSize);
            byte[] initVector = new byte[blockSize];
            (new SecureRandom()).nextBytes(initVector);
            log.info("Init Vector: {}",initVector.length);
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);
            log.info("IvParameterSpec: {}", ivSpec.getIV());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey, ivSpec);
            byte[] encoded = plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            cipherText = new byte[initVector.length + cipher.getOutputSize(encoded.length)];
            for (int i=0; i < initVector.length; i++) {
                cipherText[i] = initVector[i];
            }
            // Perform encryption & return the number of bytes stored in output
            int num_of_encrypted_bits = cipher.doFinal(encoded, 0, encoded.length, cipherText, initVector.length);
            String base64EncodedString = Base64.getEncoder().withoutPadding().encodeToString(cipherText);
            log.info("New Encrypted CipherText Length: {}, " +
                            "\nEncrypted CipherText Value: {}, " +
                            "\nEncoding Length of Original PlainText String: {}, " +
                            "\nNumber of Bits of NEW Encrypted CipherText: {}" +
                            "\nBase64 Encoded String of Encypted CipherText: {}",
                    cipherText.length,
                    cipherText,
                    encoded.length,
                    num_of_encrypted_bits,
                    base64EncodedString);
            return base64EncodedString;
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | ShortBufferException |
                BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException e)
        {
            /* None of these exceptions should be possible if pre-condition is met. */
            throw new IllegalStateException(e.toString());
        }
    }


    /**
     * To implement input string encryption, we first need to generate the
     * secret key and IV according to the previous section. As the next step,
     * we create an instance from the Cipher class by using the getInstance()
     * method.
     * Additionally, we configure a cipher instance using the init() method
     * with a secret key, IV, and encryption mode. Finally, we encrypt the
     * input string by invoking the doFinal() method. This method gets bytes
     * of input and returns ciphertext in bytes
     * @param plaintext
     * @param publicKey
     * @return ciphertext in bytes
     */
    public static String encrypt(String plaintext, PublicKey publicKey) {

        try {
            byte[] cipherText = null;
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            final int blockSize = cipher.getBlockSize();
            log.info("Block Size: {}", blockSize);
            byte[] initVector = new byte[blockSize];
            (new SecureRandom()).nextBytes(initVector);
            log.info("Init Vector: {}",initVector.length);
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);
            log.info("IvParameterSpec: {}", ivSpec.getIV());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, ivSpec);
            byte[] encoded = plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            cipherText = new byte[initVector.length + cipher.getOutputSize(encoded.length)];
            for (int i=0; i < initVector.length; i++) {
                cipherText[i] = initVector[i];
            }
            // Perform encryption & return the number of bytes stored in output
            int num_of_encrypted_bits = cipher.doFinal(encoded, 0, encoded.length, cipherText, initVector.length);
            String base64EncodedString = Base64.getEncoder().withoutPadding().encodeToString(cipherText);
            log.info("New Encrypted CipherText Length: {}, " +
                            "\nEncrypted CipherText Value: {}, " +
                            "\nEncoding Length of Original PlainText String: {}, " +
                            "\nNumber of Bits of NEW Encrypted CipherText: {}" +
                            "\nBase64 Encoded String of Encypted CipherText: {}",
                    cipherText.length,
                    cipherText,
                    encoded.length,
                    num_of_encrypted_bits,
                    base64EncodedString);
            return base64EncodedString;
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | ShortBufferException |
                BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException e)
        {
            /* None of these exceptions should be possible if pre-condition is met. */
            throw new IllegalStateException(e.toString());
        }
    }
    /**
     * For decrypting an input string, initialize our cipher using the
     * DECRYPT_MODE to decrypt the content:
     * @param cipherText
     * @param privateKey
     * @return a decrypted password string
     */
    public static String decrypt(PrivateKey privateKey, String cipherText) {
        try {
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
//            byte[] cipherTextBytes = cipherText;

            log.info("Cypher text length: {} , Value: {}", cipherTextBytes.length, cipherTextBytes);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            final int blockSize = cipher.getBlockSize();
            log.info("Block Size: {}",blockSize);
            byte[] initVector = Arrays.copyOfRange(cipherTextBytes, 0, blockSize);
            log.info("InitVector: {}", initVector.length);
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);
            log.info("IvParameterSpec: {}",ivSpec.getIV());
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivSpec);
            byte[] plaintext = cipher.doFinal(cipherTextBytes, blockSize, cipherTextBytes.length - blockSize);
            return new String(plaintext);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException |
                InvalidKeyException | BadPaddingException | IllegalBlockSizeException |  NoSuchAlgorithmException e)
        {
            /* None of these exceptions should be possible if precond is met. */
            throw new IllegalStateException(e.toString());
        }
    }
    /**
     * For decrypting an input string, initialize our cipher using the
     * DECRYPT_MODE to decrypt the content:
     * @param cipherText
     * @param secretKey
     * @return a decrypted password string
     */
    public static String decrypt(SecretKey secretKey, String cipherText) {
        try {
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
//            byte[] cipherTextBytes = cipherText;

            log.info("Cypher text length: {} , Value: {}", cipherTextBytes.length, cipherTextBytes);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            final int blockSize = cipher.getBlockSize();
            log.info("Block Size: {}",blockSize);
            byte[] initVector = Arrays.copyOfRange(cipherTextBytes, 0, blockSize);
            log.info("InitVector: {}", initVector.length);
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);
            log.info("IvParameterSpec: {}",ivSpec.getIV());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] plaintext = cipher.doFinal(cipherTextBytes, blockSize, cipherTextBytes.length - blockSize);
            return new String(plaintext);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException |
                InvalidKeyException | BadPaddingException | IllegalBlockSizeException |  NoSuchAlgorithmException e)
        {
            /* None of these exceptions should be possible if precond is met. */
            throw new IllegalStateException(e.toString());
        }
    }

    /**
     * For decrypting an input string, initialize our cipher using the
     * DECRYPT_MODE to decrypt the content:
     * @param cipherText
     * @param publicKey
     * @return a decrypted password string
     */
    public static String decrypt(PublicKey publicKey, String cipherText) {
        try {
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
//            byte[] cipherTextBytes = cipherText;

            log.info("Cypher text length: {} , Value: {}", cipherTextBytes.length, cipherTextBytes);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            final int blockSize = cipher.getBlockSize();
            log.info("Block Size: {}",blockSize);
            byte[] initVector = Arrays.copyOfRange(cipherTextBytes, 0, blockSize);
            log.info("InitVector: {}", initVector.length);
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);
            log.info("IvParameterSpec: {}",ivSpec.getIV());
            cipher.init(Cipher.DECRYPT_MODE, publicKey, ivSpec);
            byte[] plaintext = cipher.doFinal(cipherTextBytes, blockSize, cipherTextBytes.length - blockSize);
            return new String(plaintext);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException |
                InvalidKeyException | BadPaddingException | IllegalBlockSizeException |  NoSuchAlgorithmException e)
        {
            /* None of these exceptions should be possible if precond is met. */
            throw new IllegalStateException(e.toString());
        }
    }
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Secret)) return false;
        final Secret other = (Secret) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$Id = this.getId();
        final Object other$Id = other.getId();
        if (!Objects.equals(this$Id, other$Id)) return false;
        final Object this$secret = this.getPasswordToString();
        final Object other$secret = other.getPasswordToString();
        if (!Objects.equals(this$secret, other$secret)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Secret;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $Id = this.getId();
        result = result * PRIME + ($Id == null ? 43 : $Id.hashCode());
        final Object $secret = this.getPasswordToString();
        result = result * PRIME + ($secret == null ? 43 : $secret.hashCode());
        return result;
    }



    @Override
    public String toString() {
        return "Secret{\n" +
//                "\"Id\":" + this.getId() + ",\n" +
                "\"password\":\"" + this.getPasswordToString() + "\",\n" +
                "\"secretKey\":\"{\n\t" +
                "\"encoding\":\"" +this.getSecretKey().getEncoded() + "\",\n\t" +
                "\"format\":\"" + this.getSecretKey().getFormat() + "\",\n\t" +
                "\"algorithm\":\"" + this.getSecretKey().getAlgorithm() + "\"" +
                "\n}\",\n" +
                "\"isInitialized\":\"" + initialized + "\",\n" +
                "}";
    }

//
//    public static void main(String[] args)
//    {
//        try {
//
//            Secret password1 = new Secret("LendIT_Book_Kiosk");
//            log.info("-----------------------------------------| New Secret Object: {}", password1);
//            log.info("-----------------------------------------| Decrypted Plaintext Password1: {}",
//                    Secret.decrypt(password1.getSecretKey(), password1.getPasswordToString()));
//            log.info("-----------------------------------------| New Encrypted Password1: {}",
//                    password1.getPasswordToString());
//            Secret password2 = new Secret("password");
//            log.info("-----------------------------------------| New Secret Object: {}", password2);
//            log.info("-----------------------------------------| Decrypted Plaintext Password2: {}",
//                    Secret.decrypt(password2.getSecretKey(), password2.getPasswordToString()));
//            log.info("-----------------------------------------| New Encrypted Password2: {}",
//                    password2.getPasswordToString());
//
//
//        }catch (Exception e){
//            log.error(e.getMessage());
//            log.info(Arrays.stream(e.getStackTrace()).map(
//                    x -> x + "\n"
//            ).collect(Collectors.toList()).toString());
//        }
//    }
}
