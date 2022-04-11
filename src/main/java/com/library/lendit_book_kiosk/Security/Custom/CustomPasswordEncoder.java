package com.library.lendit_book_kiosk.Security.Custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration(value = "CustomPasswordEncoder")
@ComponentScan(basePackages = {"com.library.lendit_book_kiosk.Security.Config"})
public class CustomPasswordEncoder extends BCryptPasswordEncoder{

    @Autowired
    public CustomPasswordEncoder(){ super(-1); }

    public CustomPasswordEncoder(
            BCryptVersion version,
            int strength,
            SecureRandom random
    ) {
       super(version, strength, random);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return super.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return super.upgradeEncoding(encodedPassword);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomPasswordEncoder)) return false;
        CustomPasswordEncoder that = (CustomPasswordEncoder) o;
        return this.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

//    public static void main(String[] args)
//    {
//        CustomPasswordEncoder cpe = new CustomPasswordEncoder();
//        String encodedPasswd = cpe.encode("password");
//        System.out.println("Password: " + encodedPasswd);
//        boolean boolean_encode = cpe.matches("password",encodedPasswd);
//        System.out.println("Matches1: " + boolean_encode);
//        boolean_encode = cpe.upgradeEncoding(encodedPasswd);
//        System.out.println("Upgraded: " + boolean_encode);
//    }
}
