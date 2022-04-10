package com.library.lendit_book_kiosk.Security.Custom;

import com.library.lendit_book_kiosk.User.User;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.persistence.*;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "passwords")
public class Password implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Password.class);
    public static final int GCM_TAG_LENGTH = 16;
    public static final int GCM_IV_LENGTH = 12;
    public static final int GEN_KEY_SIZE = 256;
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "secret_id",
            unique = true,
            columnDefinition = "bigint",
            nullable = false)
    private Long Id;
    @Column(
            name = "secretKey",
            columnDefinition = "varbinary(255)"
    )
    private SecretKey secretKey = new SecretKey() {
        @Override
        public String getAlgorithm() {
            return "AES";
        }
        @Override
        public String getFormat() {
            return "RAW";
        }

        @Override
        public byte[] getEncoded() {
            return new byte[GCM_TAG_LENGTH];
        }
    };
    @Column(
            name = "password",
            columnDefinition = "varchar(255)"
    )
    private String password;
    @OneToMany(mappedBy = "password")
    @PrimaryKeyJoinColumn(name = "user_id")
    private Set<User> user;
    /////////////////////////////////////////////////////////////////
    public Password() {}

    /**
     * Creates an Password Object.
     * @param password
     */
    public Password(String password)
    {
        try{
            this.password = encrypt(password,generateKey(GEN_KEY_SIZE));
            log.info("New Secret: {}", this.password);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    /**
     * Encrypts the raw password String and assigns encrypted value
     * to inner password field.
     * @param rawPassword the raw password String
     */
    public void setPassword(String rawPassword){
        try{
            log.info("New Raw Password String: {}", rawPassword);
            this.password = encrypt(rawPassword,generateKey(GEN_KEY_SIZE));
            log.info("New Encrypted Password: {}", this.password);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * Automated method call setSecretKey() will create new random
     * secretKey at runtime or startup.
     * @throws NoSuchAlgorithmException
     */
    @BeforeClass
    protected void setSecretKey() throws NoSuchAlgorithmException {
        secretKey = generateKey(GEN_KEY_SIZE);
    }

    /**
     * Returns the SecretKey
     * @return the secret key
     */
    public SecretKey getKey(){return secretKey;}

    public SecretKey getSecretKey() {
        return this.secretKey;
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
     * Tests if rawPassword matches encrypted password.
     * @param rawPassword a raw password string
     * @param password an encrypted password object
     * @return true if they match, false if no match found
     */
    public static boolean matches(String rawPassword, Password password){
        return rawPassword.equalsIgnoreCase(decrypt(password.getKey(), password.getPasswordToString()));
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
     * @param password1 an encrypted password Object
     * @param password2 an encrypted password Object
     * @return true if they match, false if no match found
     */
    public static boolean matches(Password password1, Password password2){
        return decrypt(password1.getKey(), password1.getPasswordToString())
                .equalsIgnoreCase(
                        decrypt(password2.getKey(), password2.getPasswordToString())
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
     * @param n number of bits [128, 192, 256]
     * @return SecretKey
     * @throws NoSuchAlgorithmException
     */
    public SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        try {
            KeyGenerator key = KeyGenerator.getInstance("AES");
            key.init(n);
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
            byte[] cipherText = null;
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final int blockSize = cipher.getBlockSize();
            log.info("Block Size: {}", blockSize);
            byte[] initVector = new byte[blockSize];
            (new SecureRandom()).nextBytes(initVector);
            log.info("Init Vector: {}",initVector.length);
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);
            log.info("IvParameterSpec: {}", ivSpec.getIV());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encoded = plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            cipherText = new byte[initVector.length + cipher.getOutputSize(encoded.length)];
            for (int i=0; i < initVector.length; i++) {
                cipherText[i] = initVector[i];
            }
            // Perform encryption
            cipher.doFinal(encoded, 0, encoded.length, cipherText, initVector.length);
            log.info("Cipher Length: {}, \nCipher Value: {}, \nEncoding String Length: {}, \nBase64.getEncoder().withoutPadding().encodeToString(cipherText): {}",
                    cipherText.length,
                    cipherText,
                    encoded.length,
                    Base64.getEncoder().withoutPadding().encodeToString(cipherText));
            return Base64.getEncoder().withoutPadding().encodeToString(cipherText);
//            return cipherText;
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
     * @param key
     * @return a decrypted password string
     */
    public static String decrypt(SecretKey key, String cipherText) {
        try {
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
            log.info("Cypher text length: {} , Value: {}", cipherTextBytes.length, cipherTextBytes);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final int blockSize = cipher.getBlockSize();
            log.info("Block Size: {}",blockSize);
            byte[] initVector = Arrays.copyOfRange(cipherTextBytes, 0, blockSize);
            log.info("InitVector: {}", initVector.length);
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);
            log.info("IvParameterSpec: {}",ivSpec.getIV());
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
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
        if (!(o instanceof Password)) return false;
        final Password other = (Password) o;
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
        return other instanceof Password;
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
        return "Password{\n" +
//                "\"Id\":" + this.getId() + ",\n" +
                "\"password\":\"" + this.getPasswordToString() + "\",\n" +
                "\"secretKey\":\"{\n\t" +
                    "\"encoding\":\"" +this.getKey().getEncoded() + "\",\n\t" +
                    "\"format\":\"" + this.getKey().getFormat() + "\",\n\t" +
                    "\"algorithm\":\"" + this.getKey().getAlgorithm() +
                    "\"\n}\"" +
                "\n}";
    }


//    public static void main(String[] args)
//    {
//        try {
////            String input = "baeldung";
////            SecretKey key = Password.generateKey(256);
////            String cipherText = Password.encrypt(input, key);
////            String plainText = Password.decrypt(key,cipherText);
////            log.info("Cipher Text: {}",cipherText);
////            log.info("Plain Text: {}",plainText);
////            String cipherText2 = Password.encrypt(input, key);
////            boolean matched = Password.matches(cipherText,cipherText2);
////            log.info("Is Matched: {}", matched);
//
//            Password password1 = new Password("password");
//            log.info("New Password Object: {}", password1.toString());
//            log.info("New Plaintext Password1: {}", Password.decrypt(password1.getKey(), password1.getPasswordToString()));
//            log.info("New Encrypted Password1: {}", password1.getPasswordToString());
//        }catch (Exception e){
//            log.error(e.getMessage());
//            log.info(Arrays.stream(e.getStackTrace()).map(
//                    x -> x + "\n"
//            ).collect(Collectors.toList()).toString());
//        }
//    }

}
