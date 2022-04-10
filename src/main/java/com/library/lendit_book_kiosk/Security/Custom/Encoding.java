package com.library.lendit_book_kiosk.Security.Custom;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
//
//@Entity
//@Table( name = "encoding")
//public class Encoding {
//    @Id
//    @Column(
//            name = "encode_id",
//            unique = true
//    )
//    private Long id;
//    @Column(
//            name = "encoding",
//            columnDefinition = "varchar(224)"
//    )
//    private byte[] encoded;
//    @OneToMany
//    private Set<Key> key;
//    /////////////////////////////////////////////////////////////////////
//    public Encoding(Long id, byte[] encoded) {
//        this.id = id;
//        this.encoded = encoded;
//    }
//    public Encoding(byte[] encoded) {
//        this.encoded = encoded;
//    }
//    public Encoding() {}
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public byte[] getEncoded() {
//        return this.encoded;
//    }
//
////    public Set<Key> getKey() {
////        return this.key;
////    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setEncoded(byte[] encoded) {
//        this.encoded = encoded;
//    }
//
////    public void setKey(Set<Key> key) {
////        this.key = key;
////    }
//
//    public boolean equals(final Object o) {
//        if (o == this) return true;
//        if (!(o instanceof Encoding)) return false;
//        final Encoding other = (Encoding) o;
//        if (!other.canEqual((Object) this)) return false;
//        final Object this$id = this.getId();
//        final Object other$id = other.getId();
//        if (!Objects.equals(this$id, other$id)) return false;
//        if (!java.util.Arrays.equals(this.getEncoded(), other.getEncoded())) return false;
////        final Object this$key = this.getKey();
////        final Object other$key = other.getKey();
////        if (this$key == null ? other$key != null : !this$key.equals(other$key)) return false;
//        return true;
//    }
//
//    protected boolean canEqual(final Object other) {
//        return other instanceof Encoding;
//    }
//
//    public int hashCode() {
//        final int PRIME = 59;
//        int result = 1;
//        final Object $id = this.getId();
//        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
//        result = result * PRIME + java.util.Arrays.hashCode(this.getEncoded());
////        final Object $key = this.getKey();
////        result = result * PRIME + ($key == null ? 43 : $key.hashCode());
//        return result;
//    }
//
//    public String toString() {
//        return "Encoding{\n" +
//                "\"id\":" + this.getId() + "\",\n" +
//                "\"encoded\":\"" + java.util.Arrays.toString(this.getEncoded()) + "\",\n" +
////                "\"key\":\"" + this.getKey() + "\"" +
//                "\n}";
//    }
//}
