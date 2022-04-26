package com.library.lendit_book_kiosk.Book;
///////////////////////////////////////////////////////////////////////////////

import com.library.lendit_book_kiosk.Book.Copy.Book_Copy;
import com.library.lendit_book_kiosk.Book.Donated.Donated_Book;
import com.library.lendit_book_kiosk.Department.Course.Course;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "book")
public class Book implements Serializable {
    /////////////////////////////////////////////////////////////////
// Removed Gererator because the id is String and not auto generated integer
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
//    )
    @Id
    @Column(
            name = "isbn",
            unique = true,
            columnDefinition = "VARCHAR(224)"

    )
    public String isbn;
    ///////////////////////////////////////////////////////
    private String title;
    private String series;
    @Column(
            name = "authors",
            columnDefinition = "TEXT"
    )
    private String authors;
    @Column(
            name = "description",
            columnDefinition = "MEDIUMTEXT"
    )
    private String description;
    private String language;
    private Double rating;
    private String genres;
    private Long num_of_pages;
    private String publisher;
    private LocalDate publication_date;
    private String cover_img;
    ///////////////////////////////////////////////////////
    @OneToMany(
            cascade = CascadeType.PERSIST,
            mappedBy = "donated_books"  // maps to field donated_books in Donated_Book.donated_books
    )
    private Set<Donated_Book> donated_books;
    @OneToMany(
            cascade = CascadeType.PERSIST,
            mappedBy = "book_copy"  // maps to field donated_books in Donated_Book.donated_books
    )
    private Set<Book_Copy> book_copy;
    @ManyToMany(
            cascade = CascadeType.PERSIST,
            mappedBy = "books"
    )
    private Set<Course> courses;
    ///////////////////////////////////////////////////////


    public Book(Set<Donated_Book> donated_books, String isbn, String title, String series, String authors, String description, String language, Double rating, String genres, Long num_of_pages, String publisher, LocalDate publication_date, String cover_img) {
        this.donated_books = donated_books;
        this.isbn = isbn;
        this.title = title;
        this.series = series;
        this.authors = authors;
        this.description = description;
        this.language = language;
        this.rating = rating;
        this.genres = genres;
        this.num_of_pages = num_of_pages;
        this.publisher = publisher;
        this.publication_date = publication_date;
        this.cover_img = cover_img;
    }

    public Book(Set<Book_Copy> book_copy, Set<Course> courses, String isbn, String title, String series, String authors, String description, String language, Double rating, String genres, Long num_of_pages, String publisher, LocalDate publication_date, String cover_img) {
        this.book_copy = book_copy;
        this.courses = courses;
        this.isbn = isbn;
        this.title = title;
        this.series = series;
        this.authors = authors;
        this.description = description;
        this.language = language;
        this.rating = rating;
        this.genres = genres;
        this.num_of_pages = num_of_pages;
        this.publisher = publisher;
        this.publication_date = publication_date;
        this.cover_img = cover_img;
    }

    public Book(String isbn, String title, String series, String authors, String description, String language, Double rating, String genres, Long num_of_pages, String publisher, LocalDate publication_date, String cover_img) {
        this.isbn = isbn;
        this.title = title;
        this.series = series;
        this.authors = authors;
        this.description = description;
        this.language = language;
        this.rating = rating;
        this.genres = genres;
        this.num_of_pages = num_of_pages;
        this.publisher = publisher;
        this.publication_date = publication_date;
        this.cover_img = cover_img;
    }

    public Book(Book book) {
        this.isbn = book.isbn;
        this.title = book.title;
        this.series = book.series;
        this.authors = book.authors;
        this.description = book.description;
        this.language = book.language;
        this.rating = book.rating;
        this.genres = book.genres;
        this.num_of_pages = book.num_of_pages;
        this.publisher = book.publisher;
        this.publication_date = book.publication_date;
        this.cover_img = book.cover_img;
    }

    public Book() {
    }

    protected Book getBook(){return this;}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSeries() {
        return this.series;
    }

    public String getAuthors() {
        return this.authors;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLanguage() {
        return this.language;
    }

    public Double getRating() {
        return this.rating;
    }

    public String getGenres() {
        return this.genres;
    }

    public Long getNum_of_pages() {
        return this.num_of_pages;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public LocalDate getPublication_date() {
        return this.publication_date;
    }

    public String getCover_img() {
        return this.cover_img;
    }

    public Set<Donated_Book> getDonated_books() {
        return this.donated_books;
    }

    public Set<Book_Copy> getBook_copy() {
        return this.book_copy;
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setNum_of_pages(Long num_of_pages) {
        this.num_of_pages = num_of_pages;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublication_date(LocalDate publication_date) {
        this.publication_date = publication_date;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public void setDonated_books(Set<Donated_Book> donated_books) {
        this.donated_books = donated_books;
    }

    public void setBook_copy(Set<Book_Copy> book_copy) {
        this.book_copy = book_copy;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Book)) return false;
        final Book other = (Book) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$isbn = this.getIsbn();
        final Object other$isbn = other.getIsbn();
        if (!Objects.equals(this$isbn, other$isbn)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (!Objects.equals(this$title, other$title)) return false;
        final Object this$series = this.getSeries();
        final Object other$series = other.getSeries();
        if (!Objects.equals(this$series, other$series)) return false;
        final Object this$authors = this.getAuthors();
        final Object other$authors = other.getAuthors();
        if (!Objects.equals(this$authors, other$authors)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (!Objects.equals(this$description, other$description))
            return false;
        final Object this$language = this.getLanguage();
        final Object other$language = other.getLanguage();
        if (!Objects.equals(this$language, other$language)) return false;
        final Object this$rating = this.getRating();
        final Object other$rating = other.getRating();
        if (!Objects.equals(this$rating, other$rating)) return false;
        final Object this$genres = this.getGenres();
        final Object other$genres = other.getGenres();
        if (!Objects.equals(this$genres, other$genres)) return false;
        final Object this$num_of_pages = this.getNum_of_pages();
        final Object other$num_of_pages = other.getNum_of_pages();
        if (!Objects.equals(this$num_of_pages, other$num_of_pages))
            return false;
        final Object this$publisher = this.getPublisher();
        final Object other$publisher = other.getPublisher();
        if (!Objects.equals(this$publisher, other$publisher)) return false;
        final Object this$publication_date = this.getPublication_date();
        final Object other$publication_date = other.getPublication_date();
        if (!Objects.equals(this$publication_date, other$publication_date))
            return false;
        final Object this$cover_img = this.getCover_img();
        final Object other$cover_img = other.getCover_img();
        if (!Objects.equals(this$cover_img, other$cover_img)) return false;
        final Object this$donated_books = this.getDonated_books();
        final Object other$donated_books = other.getDonated_books();
        if (!Objects.equals(this$donated_books, other$donated_books))
            return false;
        final Object this$book_copy = this.getBook_copy();
        final Object other$book_copy = other.getBook_copy();
        if (!Objects.equals(this$book_copy, other$book_copy)) return false;
        final Object this$courses = this.getCourses();
        final Object other$courses = other.getCourses();
        if (!Objects.equals(this$courses, other$courses)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Book;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $isbn = this.getIsbn();
        result = result * PRIME + ($isbn == null ? 43 : $isbn.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $series = this.getSeries();
        result = result * PRIME + ($series == null ? 43 : $series.hashCode());
        final Object $authors = this.getAuthors();
        result = result * PRIME + ($authors == null ? 43 : $authors.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $language = this.getLanguage();
        result = result * PRIME + ($language == null ? 43 : $language.hashCode());
        final Object $rating = this.getRating();
        result = result * PRIME + ($rating == null ? 43 : $rating.hashCode());
        final Object $genres = this.getGenres();
        result = result * PRIME + ($genres == null ? 43 : $genres.hashCode());
        final Object $num_of_pages = this.getNum_of_pages();
        result = result * PRIME + ($num_of_pages == null ? 43 : $num_of_pages.hashCode());
        final Object $publisher = this.getPublisher();
        result = result * PRIME + ($publisher == null ? 43 : $publisher.hashCode());
        final Object $publication_date = this.getPublication_date();
        result = result * PRIME + ($publication_date == null ? 43 : $publication_date.hashCode());
        final Object $cover_img = this.getCover_img();
        result = result * PRIME + ($cover_img == null ? 43 : $cover_img.hashCode());
        final Object $donated_books = this.getDonated_books();
        result = result * PRIME + ($donated_books == null ? 43 : $donated_books.hashCode());
        final Object $book_copy = this.getBook_copy();
        result = result * PRIME + ($book_copy == null ? 43 : $book_copy.hashCode());
        final Object $courses = this.getCourses();
        result = result * PRIME + ($courses == null ? 43 : $courses.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"isbn\":\"" + this.getIsbn() + "\",\n" +
                "\"title\":\"" + this.getTitle() + "\",\n" +
                "\"series\":\"" + this.getSeries() + "\",\n" +
                "\"authors\":\"" + this.getAuthors() + "\",\n" +
                "\"description\":\"" + this.getDescription() + "\",\n" +
                "\"language\":\"" + this.getLanguage() + "\",\n" +
                "\"rating\":\"" + this.getRating() + "\",\n" +
                "\"genres\":\"" + this.getGenres() + "\",\n" +
                "\"num_of_pages\":\"" + this.getNum_of_pages() + "\",\n" +
                "\"publisher\":\"" + this.getPublisher() + "\",\n" +
                "\"publication_date\":\"" + this.getPublication_date() + "\",\n" +
                "\"cover_img\":\"" + this.getCover_img() + "\"" +
                "\n}";
    }

//    public static void main(String[] args) {
//        String x = "['Middle Grade', 'Childrens', 'Humor', 'Fiction', 'Realistic Fiction', 'Contemporary', 'School', 'Young Adult', 'Graphic Novels', 'Juvenile']";
//        Matcher m = Pattern.compile("\'(.*?)\'").matcher(x);
//        while (m.find()){
//            System.out.println(m.group(1));
//        }
//    }
}
///////////////////////////////////////////////////////////////////////////////
