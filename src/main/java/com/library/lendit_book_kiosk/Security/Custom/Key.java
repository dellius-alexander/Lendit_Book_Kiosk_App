package com.library.lendit_book_kiosk.Security.Custom;

import javax.crypto.SecretKey;
import javax.persistence.*;
import javax.security.auth.DestroyFailedException;
import java.util.Objects;
import java.util.Set;
//
//@Entity
//@Table( name = "secretKey")
//public class Key implements SecretKey {
//    @Id
//    @GeneratedValue(
//            // strategy = AUTO
//            strategy = GenerationType.SEQUENCE,
//            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
//    )
//    @Column(
//            name = "key_id",
//            unique = true,
//            columnDefinition = "bigint",
//            nullable = false)
//    private Long id;
//    private String format;
//    private String algorithm;
//    @ManyToOne(
//            targetEntity = Encoding.class,
//            fetch = FetchType.EAGER,
//            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//    @JoinColumn(
//            columnDefinition = "varchar(255)",
//            name = "encoding",
//            referencedColumnName = "encoding"
//    )
//    private Encoding encoding;
//    @OneToMany(mappedBy = "secretKey")
//    private Set<Password> password;
//    /////////////////////////////////////////////////////////////////////
//    public Key(Long id, String format, String algorithm, Encoding encoding) {
//        this.id = id;
//        this.format = format;
//        this.algorithm = algorithm;
//        this.encoding = encoding;
//    }
//
//    public Key(String format, String algorithm, Encoding encoding) {
//        this.format = format;
//        this.algorithm = algorithm;
//        this.encoding = encoding;
//    }
//    public Key() {}
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    /**
//     * Returns the standard algorithm name for this key. For
//     * example, "DSA" would indicate that this key is a DSA key.
//     * See the <a href=
//     * "{@docRoot}/../specs/security/standard-names.html">
//     * Java Security Standard Algorithm Names</a> document
//     * for more information.
//     *
//     * @return the name of the algorithm associated with this key.
//     */
//    @Override
//    public String getAlgorithm() {
//        return this.algorithm;
//    }
//
//    /**
//     * Returns the name of the primary encoding format of this key,
//     * or null if this key does not support encoding.
//     * The primary encoding format is
//     * named in terms of the appropriate ASN.1 data format, if an
//     * ASN.1 specification for this key exists.
//     * For example, the name of the ASN.1 data format for public
//     * keys is <I>SubjectPublicKeyInfo</I>, as
//     * defined by the X.509 standard; in this case, the returned format is
//     * {@code "X.509"}. Similarly,
//     * the name of the ASN.1 data format for private keys is
//     * <I>PrivateKeyInfo</I>,
//     * as defined by the PKCS #8 standard; in this case, the returned format is
//     * {@code "PKCS#8"}.
//     *
//     * @return the primary encoding format of the key.
//     */
//    @Override
//    public String getFormat() {
//        return this.format;
//    }
//
//    /**
//     * Returns the key in its primary encoding format, or null
//     * if this key does not support encoding.
//     *
//     * @return the encoded key, or null if the key does not support
//     * encoding.
//     */
//    @Override
//    public byte[] getEncoded() {
////        return new byte[0];
//        return this.encoding.getEncoded();
//    }
//
//    public Encoding getEncoding(){return this.encoding;}
//    /**
//     * Destroy this {@code Object}.
//     *
//     * <p> Sensitive information associated with this {@code Object}
//     * is destroyed or cleared.  Subsequent calls to certain methods
//     * on this {@code Object} will result in an
//     * {@code IllegalStateException} being thrown.
//     *
//     * @throws DestroyFailedException if the destroy operation fails.
//     * @throws SecurityException      if the caller does not have permission
//     *                                to destroy this {@code Object}.
//     * @implSpec The default implementation throws {@code DestroyFailedException}.
//     */
//    @Override
//    public void destroy() throws DestroyFailedException {
//        SecretKey.super.destroy();
//    }
//
//    /**
//     * Determine if this {@code Object} has been destroyed.
//     *
//     * @return true if this {@code Object} has been destroyed,
//     * false otherwise.
//     * @implSpec The default implementation returns false.
//     */
//    @Override
//    public boolean isDestroyed() {
//        return SecretKey.super.isDestroyed();
//    }
//
////    public Set<Password> getPassword() {
////        return this.password;
////    }
//
//    public void setFormat(String format) {
//        this.format = format;
//    }
//
//    public void setAlgorithm(String algorithm) {
//        this.algorithm = algorithm;
//    }
//
//    public void setEncoding(byte[] encoded) {
//        this.encoding.setEncoded( encoded );
//    }
//
////    public void setDestroyed(boolean destroyed) {
////        this.destroyed = destroyed;
////    }
//
////    public void setPassword(Set<Password> password) {
////        this.password = password;
////    }
//
//    public boolean equals(final Object o) {
//        if (o == this) return true;
//        if (!(o instanceof Key)) return false;
//        final Key other = (Key) o;
//        if (!other.canEqual((Object) this)) return false;
//        final Object this$id = this.getId();
//        final Object other$id = other.getId();
//        if (!Objects.equals(this$id, other$id)) return false;
//        final Object this$format = this.getFormat();
//        final Object other$format = other.getFormat();
//        if (!Objects.equals(this$format, other$format)) return false;
//        final Object this$algorithm = this.getAlgorithm();
//        final Object other$algorithm = other.getAlgorithm();
//        if (!Objects.equals(this$algorithm, other$algorithm)) return false;
//        final Object this$encoded = this.getEncoded();
//        final Object other$encoded = other.getEncoded();
//        if (!Objects.equals(this$encoded, other$encoded)) return false;
////        if (this.isDestroyed() != other.isDestroyed()) return false;
////        final Object this$password = this.getPassword();
////        final Object other$password = other.getPassword();
////        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
//        return true;
//    }
//
//    protected boolean canEqual(final Object other) {
//        return other instanceof Key;
//    }
//
//    public int hashCode() {
//        final int PRIME = 59;
//        int result = 1;
//        final Object $id = this.getId();
//        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
//        final Object $format = this.getFormat();
//        result = result * PRIME + ($format == null ? 43 : $format.hashCode());
//        final Object $algorithm = this.getAlgorithm();
//        result = result * PRIME + ($algorithm == null ? 43 : $algorithm.hashCode());
//        final Object $encoded = this.getEncoded();
//        result = result * PRIME + ($encoded == null ? 43 : $encoded.hashCode());
////        result = result * PRIME + (this.isDestroyed() ? 79 : 97);
////        final Object $password = this.getPassword();
////        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
//        return result;
//    }
//
//    public String toString() {
//        return "Key{\n" +
//                "\"id\":" + this.getId() + ",\n" +
//                "\"format\":\"" + this.getFormat() + "\",\n" +
//                "\"algorithm\":\"" + this.getAlgorithm() + "\",\n" +
//                "\"encoded\":\"" + this.getEncoded() + "\",\n" +
//                "\"destroyed\":\"" + this.isDestroyed() + "\"" +
////                "\"password\":\"" + this.getPassword() + "\"" +
//                "\n}";
//    }
//}
