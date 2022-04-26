package com.library.lendit_book_kiosk.Book;


import java.io.Serializable;
import java.util.Objects;

public class BookSelection extends Book implements Serializable {
    private boolean borrow;
    private boolean reserve;
    private boolean save;
    private boolean checked_out;
    private Book book;

    public BookSelection(boolean borrow, boolean reserve, boolean save, boolean checked_out, Book book) {
        this.borrow = borrow;
        this.reserve = reserve;
        this.save = save;
        this.checked_out = checked_out;
        this.book = book;
    }

    public BookSelection() {
    }

    public BookSelection(Book book){
        super(book);
        this.book = book;
    }
    @Override
    public String toString() {
        return "BookSelection{\n" +
                "\"borrow\":" + this.isBorrow() + ",\n" +
                "\"reserve\":" + this.isReserve() + ",\n" +
                "\"save\":" + this.isSave() + ",\n" +
                "\"checked_out\":" + this.isChecked_out() + ",\n" +
                "\"book\":" + super.toString() +
                "\n}";
    }

    public boolean isBorrow() {
        return this.borrow;
    }

    public boolean isReserve() {
        return this.reserve;
    }

    public boolean isSave() {
        return this.save;
    }

    public boolean isChecked_out() {
        return this.checked_out;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBorrow(boolean borrow) {
        this.borrow = borrow;
    }

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public void setChecked_out(boolean checked_out) {
        this.checked_out = checked_out;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BookSelection)) return false;
        final BookSelection other = (BookSelection) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.isBorrow() != other.isBorrow()) return false;
        if (this.isReserve() != other.isReserve()) return false;
        if (this.isSave() != other.isSave()) return false;
        if (this.isChecked_out() != other.isChecked_out()) return false;
        final Object this$book = this.getBook();
        final Object other$book = other.getBook();
        if (!Objects.equals(this$book, other$book)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookSelection;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isBorrow() ? 79 : 97);
        result = result * PRIME + (this.isReserve() ? 79 : 97);
        result = result * PRIME + (this.isSave() ? 79 : 97);
        result = result * PRIME + (this.isChecked_out() ? 79 : 97);
        final Object $book = this.getBook();
        result = result * PRIME + ($book == null ? 43 : $book.hashCode());
        return result;
    }
}
