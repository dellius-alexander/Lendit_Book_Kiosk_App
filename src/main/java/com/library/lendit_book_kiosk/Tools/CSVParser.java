package com.library.lendit_book_kiosk.Tools;

import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.Role.UserRole;
import com.library.lendit_book_kiosk.Security.Custom.Secret;
import com.library.lendit_book_kiosk.Student.Major;
import com.library.lendit_book_kiosk.Student.Student;
import com.library.lendit_book_kiosk.User.GENDER;
import com.library.lendit_book_kiosk.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;


public class CSVParser implements Serializable{
    private static final Logger log = LoggerFactory.getLogger(CSVParser.class);
    private FileParser fp = null;
    Map<Integer, String[]> file_contents;
    Scanner csv_file ;
    CSVParser(){}
    public CSVParser(FileParser fp) throws FileNotFoundException {
        this.fp = fp;
        this.csv_file = new Scanner(this.fp.getNext());
        this.file_contents = new HashMap<>();
        CSVInit();
    }
    CSVParser(File file) throws FileNotFoundException {
        this.fp = new FileParser(file);
        this.csv_file = new Scanner(this.fp.getNext());
        this.file_contents = new HashMap<>();
        CSVInit();
    }

    private void CSVInit(){
        int cnt = 0;
        while (this.csv_file.hasNext()){
            this.file_contents.put(cnt, csv_file.nextLine().split(","));
            cnt++;
        }
    }
    public FileParser getFp() {
        return this.fp;
    }

    public void setFp(FileParser fp) {
        this.fp = fp;
    }

    public String[] getRow(int row){
        return this.file_contents.get(row);
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CSVParser)) return false;
        final CSVParser other = (CSVParser) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$fp = this.getFp();
        final Object other$fp = other.getFp();
        if (!Objects.equals(this$fp, other$fp)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CSVParser;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $fp = this.getFp();
        result = result * PRIME + ($fp == null ? 43 : $fp.hashCode());
        return result;
    }

    public List<User> getStudentsInfo(){
        int cnt = 0;
        List<User> users = new ArrayList<>();
        Role STUDENT = new Role(UserRole.STUDENT,"STUDENT");
//       System.out.println(csv.getRow(9).toArray()[0]);
        for(int i = 1; i < file_contents.size(); i++){
//            log.info("{}",csv.file_contents.get(i));
            for (int j = 0; j < 1 ; j++) {
                String[] lUser = file_contents.get(i);

//                log.info(lUser.toString());
                if (String.valueOf(lUser[j+6]).equalsIgnoreCase( "STUDENT" )){
                    log.info(lUser[j+6]);
                    log.info("Size: {}",lUser[j+6].trim());
                    log.info("{}: {}",file_contents.get(0)[j], lUser[0]);
                    cnt++;
                    User user = new User(
                            lUser[j],
                            lUser[j+1],
                            new Secret(lUser[j+2]),
                            (lUser[j+3].equalsIgnoreCase("FEMALE") ? GENDER.FEMALE : GENDER.MALE),
                            LocalDate.of(Integer.parseInt(lUser[j+4].split("-")[0]),
                                    Month.of(Integer.parseInt(lUser[j+4].split("-")[1])),
                                    Integer.parseInt(lUser[j+4].split("-")[2])),
                            lUser[j+5],
                            Set.of(STUDENT),
                            Set.of(new Student(
                                    (lUser[j + 7].equals("1")),
                                    Set.of(new Major(lUser[j + 8]))
                            ))
                    );
                    users.add(user);
                    log.info(user.toString());
                }
            }
        }
        log.info("Total Students: {}",cnt);
        return users;
    }
    public String toString() {
        return "CSVParser(fp=" + this.getFp() + ")";
    }

//    public static void main(String[] args) throws FileNotFoundException {
//       CSVParser csv = new CSVParser(new FileParser(new File("target/classes/mock_data.csv")));
//       System.out.println("ABS Path: " + csv.getFp().getNext().getAbsolutePath());
//       System.out.println("Is File: " + csv.getFp().getNext().isFile());
//       System.out.println(csv.getFp().getNext().toString());
//       int cnt = 0;
////       System.out.println(csv.getRow(9).toArray()[0]);
//        for(int i = 1; i < csv.file_contents.size(); i++){
////            log.info("{}",csv.file_contents.get(i));
//            for (int j = 0; j < 1 ; j++) {
//                String[] lUser = csv.file_contents.get(i);
//
////                log.info(lUser.toString());
//                if (String.valueOf(lUser[j+6]).equalsIgnoreCase( "STUDENT" )){
//                log.info(lUser[j+6]);
//                log.info("Size: {}",lUser[j+6].trim());
//                log.info("{}: {}",csv.file_contents.get(0)[j], lUser[0]);
//                cnt++;
//                User user = new User(
//                        lUser[j],
//                        lUser[j+1],
//                        new Secret(lUser[j+2]),
//                        (lUser[j+3].equalsIgnoreCase("FEMALE") ? GENDER.FEMALE : GENDER.MALE),
//                        LocalDate.of(Integer.parseInt(lUser[j+4].split("-")[0]),
//                                Month.of(Integer.parseInt(lUser[j+4].split("-")[1])),
//                                Integer.parseInt(lUser[j+4].split("-")[2])),
//                        lUser[j+5],
//                        Set.of(new Role((
//                                lUser[j+6].equalsIgnoreCase("STUDENT") ? UserRole.STUDENT :
//                                        lUser[j+6].equalsIgnoreCase("FACULTY") ? UserRole.FACULTY :
//                                                lUser[j+6].equalsIgnoreCase("STAFF") ? UserRole.STAFF:
//                                                        lUser[j+6].equalsIgnoreCase("ADMIN") ? UserRole.ADMIN : UserRole.SUPERUSER),
//                                (lUser[j+6].equalsIgnoreCase("STUDENT") ? "STUDENT"  :
//                                        lUser[j+6].equalsIgnoreCase("FACULTY") ?"FACULTY"  :
//                                                lUser[j+6].equalsIgnoreCase("STAFF") ? "STAFF":
//                                                        lUser[j+6].equalsIgnoreCase("ADMIN") ? "ADMIN" : "SUPERUSER"))),
//                        Set.of(new Student(
//                                (lUser[j + 7].equals("1")),
//                                Set.of(new Major(lUser[j + 8]))
//                        ))
//                );
//
//                log.info(user.toString());
//                    }
//            }
//        }
//        log.info("Total Students: {}",cnt);
//    }
}