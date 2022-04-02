package com.library.lendit_book_kiosk.WebApp.Fragments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component(value = "SearchBook")
public class SearchBook implements Serializable {
    private String title;
    private String author;
    private String course;

    public SearchBook(String title, String author, String course) {
        this.title = title;
        this.author = author;
        this.course = course;
    }

    public SearchBook() {
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getCourse() {
        return this.course;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SearchBook)) return false;
        final SearchBook other = (SearchBook) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (!Objects.equals(this$title, other$title)) return false;
        final Object this$author = this.getAuthor();
        final Object other$author = other.getAuthor();
        if (!Objects.equals(this$author, other$author)) return false;
        final Object this$course = this.getCourse();
        final Object other$course = other.getCourse();
        if (!Objects.equals(this$course, other$course)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SearchBook;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $author = this.getAuthor();
        result = result * PRIME + ($author == null ? 43 : $author.hashCode());
        final Object $course = this.getCourse();
        result = result * PRIME + ($course == null ? 43 : $course.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"title\":\"" + this.getTitle() + "\",\n" +
                "\"author\":\"" + this.getAuthor() + "\",\n" +
                "\"course\":\"" + this.getCourse() + "\"" +
                "\n}";
    }
}