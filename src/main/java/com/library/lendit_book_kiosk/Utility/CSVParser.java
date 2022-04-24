package com.library.lendit_book_kiosk.Utility;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.Role.UserRole;
import com.library.lendit_book_kiosk.Security.Secret.Secret;
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
import java.util.stream.Collectors;


public class CSVParser implements Serializable{
    private static final Logger log = LoggerFactory.getLogger(CSVParser.class);
    protected HierarchicalCheck hck = new HierarchicalCheck();
    private Map<Integer, Object[]> file_contents = new HashMap<>();
    /**
     * Negative lookahead provides the solution: q(?!...). The negative
     * lookahead construct is the pair of parentheses, with the opening
     * parenthesis followed by a question mark and an exclamation point.
     * Inside the lookahead, we have the trivial regex u.
     * Positive lookahead works just the same. q(?=u) matches a q that is
     * followed by a u, without making the u part of the match. The positive
     * lookahead construct is a pair of parentheses, with the opening parenthesis
     * followed by a question mark and an equals sign.
     */
    private String comma_regex = String.format(",");
    private String comma_quote_positive_lookahead_regex = String.format(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    private FileParser fp = null;

    Scanner csv_file ;
    CSVParser(){}
    public CSVParser(FileParser fp) throws FileNotFoundException {
        this.fp = fp;
        this.csv_file = new Scanner(this.fp.getNext());
    }
    CSVParser(File file) throws FileNotFoundException {
        this.fp = new FileParser(file);
        this.csv_file = new Scanner(this.fp.getNext());
    }

    public FileParser getFp() {
        return this.fp;
    }

    public void setFp(FileParser fp) {
        this.fp = fp;
    }

    public Object getRow(int row){
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

    /**
     * Initialize file contents
     * @param regexSeparator regulay expression csv separator
     */
    private void CSVInit(String regexSeparator){
        int cnt = 0;
        while (this.csv_file.hasNext()){

            this.file_contents.put(cnt, csv_file.nextLine().split(regexSeparator));
            cnt++;
        }
    }

    /**
     * Gets User/Student info from CSV file
     * @return {@literal List<User>}
     */
    public List<User> getUsersFromCsvFile(){
        int cnt = 0;
        List<User> users = new ArrayList<>();
        CSVInit(String.format(","));
        Role STUDENT = new Role(UserRole.STUDENT,"STUDENT");
//       System.out.println(csv.getRow(9).toArray()[0]);
        for(int i = 1; i < file_contents.size(); i++){
            log.info("User: {}", Arrays.stream(file_contents.get(i)).collect(Collectors.toList()));
            List<Object> lUser = Arrays.stream(file_contents.get(i)).collect(Collectors.toList());
            if (String.valueOf(lUser.get(6)).equalsIgnoreCase( "STUDENT" )){
                log.info(String.valueOf(lUser.get(6)));
                log.info("Size: {}",String.valueOf(lUser.get(6)));
                cnt++;
                User user = new User(
                        lUser.get(0).toString().trim(),
                        lUser.get(1).toString().trim(),
                        new Secret(lUser.get(2).toString().trim()),
                        (lUser.get(3).toString().trim().equalsIgnoreCase("FEMALE") ? GENDER.FEMALE : GENDER.MALE),
                        LocalDate.of(Integer.parseInt(lUser.get(4).toString().trim().split("-")[0]),
                                Month.of(Integer.parseInt(lUser.get(4).toString().trim().split("-")[1])),
                                Integer.parseInt(lUser.get(4).toString().trim().split("-")[2])),
                        lUser.get(5).toString().trim(),
                        Set.of(STUDENT),
                        Set.of(new Student(
                                (lUser.get(7).equals("1")),
                                Set.of(new Major(lUser.get(8).toString().trim()))
                        ))
                );
                users.add(user);
                log.info(user.toString());
            }
        }
        log.info("Total Users: {}",cnt);
        return users;
    }

    /**
     * Get Book info from CSV file
     * @return {@literal List<Book>}
     */
    public List<Book> getBooksFromCsvFile(){
        int cnt = 0;
        List<Book> books = new ArrayList<>();
        // regex to reject comma inside quoted text like: "set,", during split function
        CSVInit(String.format(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1));
        try{
            log.info("Size: {}",file_contents.size());
            for (int i = 1; i < file_contents.size(); i++)
            {
                if(Arrays.stream(file_contents.get(i)).collect(Collectors.toList()).size() < 11){
                    continue; // dont parse books with less fields than required
                }
                log.info("User: {}", Arrays.stream(file_contents.get(i)).collect(Collectors.toList()));

                String[] lBook = (String[]) file_contents.get(i);
//                log.info("New Book Added: {}", lBook[1]);
//                log.info("{}: {}",cnt, lBook);
                for (int j = 0; j < 1; j++)
                {
                    // TODO: use hierarchicalCheck to check each value
                    Book book = new Book(
                            (lBook[j].isEmpty() ? "9999999999" : lBook[j]),   // 0 isbn,
                            lBook[j+11],// 11 title
                            lBook[j+10], // 10 series,
                            lBook[j+1], // 1 authors,
                            lBook[j+3].replaceAll("\"\"",""), // 3 description,
                            lBook[j+5], // 5 language,
                            (lBook[j+9] == null ? 0.0 : Double.parseDouble(lBook[j+9])), // 9 rating,
                            lBook[j+4].replaceAll("\"\"",""), // 4 genres,
                            lBook[j+6].isEmpty() ? 0 : Long.parseLong(lBook[j+6]), // 6 num_of_pages,
                            lBook[j+8], // 8 publisher,
                            // 7 publication_date,
                            LocalDate.of( (lBook[j+7].isEmpty() ? 0000 : Integer.parseInt(lBook[j+7].split("-")[0])), // year
                                    Month.of( (lBook[j+7].isEmpty() ? 1 : Integer.parseInt(lBook[j+7].split("-")[1]))), // month
                                    (lBook[j+7].isEmpty() ? 1 : Integer.parseInt(lBook[j+7].split("-")[2]))), // day // publication_date
                            lBook[j+2]  // 2 cover_img
                    );
//                    log.info("New Book Added: {}", book);
                    books.add(book);
                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        return books;
    }

///////////////////////////////////////////////////////////////////////////////
//    public static void main(String[] args) throws IOException {
//        CSVParser csv = new CSVParser(new FileParser(new File("SQL/mysql_files/Lendit_Book_Kiosk_book.csv")));
////        CSVParser csv = new CSVParser(new FileParser(new File("src/main/resources/users.csv")));
//        log.info("ABS Path: " + csv.getFp().getNext().getAbsolutePath());
//        log.info("Is File: " + csv.getFp().getNext().isFile());
//        log.info(csv.getFp().getNext().toString());
//        log.info(csv.getBooksFromCsvFile().toString());
//
//    }

}